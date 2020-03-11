package www.jingkan.com.view_model;

import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import www.jingkan.com.db.dao.CrossTestDataDao;
import www.jingkan.com.db.dao.ProbeDao;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.entity.CrossTestDataEntity;
import www.jingkan.com.db.entity.ProbeEntity;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.util.DataUtil;
import www.jingkan.com.util.SingleLiveEvent;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.util.TimeUtil;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view_model.base.BaseViewModel;

import static www.jingkan.com.util.bluetooth.BluetoothCommService.MESSAGE_READ;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.MESSAGE_STATE_CHANGE;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECTED;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECTING;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECT_FAILED;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECT_LOST;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_LISTEN;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_NONE;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 * {@link www.jingkan.com.view.CrossTestActivity}
 */
public class CrossTestViewModel extends BaseViewModel {

    public final MutableLiveData<String> strProjectNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strHoleNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strCuCoefficient = new MutableLiveData<>();
    public final MutableLiveData<String> strCuLimit = new MutableLiveData<>();
    public final MutableLiveData<String> strCuInitial = new MutableLiveData<>();
    public final MutableLiveData<String> strCuEffective = new MutableLiveData<>();
    public final MutableLiveData<String> deg = new MutableLiveData<>();
    public final MutableLiveData<Integer> intTestNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strDeep = new MutableLiveData<>();
    public final MutableLiveData<String> strSoilType = new MutableLiveData<>();
    public final MutableLiveData<Boolean> linked = new MutableLiveData<>();
    public final MutableLiveData<Boolean> start = new MutableLiveData<>();
    public final SingleLiveEvent<List<CrossTestDataEntity>> ldCrossTestDataEntities = new SingleLiveEvent<>();
    public String mac;
    private BluetoothUtil bluetoothUtil;
    private BluetoothCommService bluetoothCommService;
    private TestDao testDao;
    private CrossTestDataDao crossTestDataDao;
    private ProbeDao probeDao;
    private DataUtil dataUtil;
    private ISkip iSkip;

    public CrossTestViewModel(@NonNull Application application,
                              BluetoothUtil bluetoothUtil,
                              BluetoothCommService bluetoothCommService,
                              TestDao testDao,
                              CrossTestDataDao crossTestDataDao,
                              ProbeDao probeDao,
                              DataUtil dataUtil) {
        super(application);
        this.bluetoothUtil = bluetoothUtil;
        this.bluetoothCommService = bluetoothCommService;
        this.testDao = testDao;
        this.crossTestDataDao = crossTestDataDao;
        this.probeDao = probeDao;
        this.dataUtil = dataUtil;
    }

    private TestEntity testEntity;

    private void getTestParameters() {
        testDao.getTestEntityByPrjNumberAndHoleNumber(strProjectNumber.getValue(), strHoleNumber.getValue()).observe(lifecycleOwner, testEntities -> {
            if (testEntities != null && testEntities.size() > 0) {
                testEntity = testEntities.get(0);
                crossTestDataDao.getCrossTestDataByTestDataId(strProjectNumber.getValue() + "_" + strHoleNumber.getValue()).observe(lifecycleOwner, crossTestDataEntities -> {
                    if (crossTestDataEntities != null && crossTestDataEntities.size() > 0) {
                        CrossTestDataEntity crossTestDataEntity = crossTestDataEntities.get(crossTestDataEntities.size() - 1);
                        deg.setValue(String.valueOf(crossTestDataEntity.deg));
                        strDeep.setValue(String.valueOf(crossTestDataEntity.deep));
                        intTestNumber.setValue(crossTestDataEntity.number);
                        strSoilType.setValue(crossTestDataEntity.type);
                        Integer intTestNumberValue = intTestNumber.getValue();
                        //                                myView.get().showTestData(models);
                        if (intTestNumberValue != null)
                            crossTestDataDao.getCrossTestDataByTestDataIdAndNumber(strProjectNumber.getValue() + "_" + strHoleNumber.getValue(), intTestNumberValue).observe(lifecycleOwner, ldCrossTestDataEntities::setValue);
                    } else {
                        toast("没有试验数据");
                    }
                });
            } else {
                toast("找不到该孔信息");
            }
        });
    }

    @Override
    public void inject(Object... objects) {
        iSkip = (ISkip) objects[0];
        bluetoothCommService.getBluetoothMessageMutableLiveData().observe(lifecycleOwner, callbackMessage -> {
            switch (callbackMessage.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (callbackMessage.arg1) {
                        case STATE_NONE:
                            break;
                        case STATE_LISTEN:// 监听连接
                            break;
                        case STATE_CONNECTING: // now initiating an outgoing connection
                            toast("正在连接");
                            break;
                        case STATE_CONNECTED:   // 已连接上远程设备
                            action.setValue("closeWaitDialog");
//                            closeWaitDialog();
                            toast("连接成功");
                            linked.setValue(true);
                            break;
                        case STATE_CONNECT_FAILED: // 连接失败
                            action.setValue("closeWaitDialog");
//                            closeWaitDialog();
                            toast("连接失败");
                            linked.setValue(false);
                            break;
                        case STATE_CONNECT_LOST: // 失去连接
                            toast("失去连接");
                            linked.setValue(false);
                            break;
                    }
                    break;
                case MESSAGE_READ:
                    byte[] b = (byte[]) callbackMessage.obj;
                    String mDate = new String(b);
                    if (mDate.length() > 40) {
                        if (mDate.contains("\r")) {
                            mDate = mDate.substring(0, mDate.indexOf("\r"));
                        }
                        mDate = mDate.replace(" ", "");
                        if (mDate.contains("Sn:")) {
                            String sn = mDate.substring(mDate.indexOf("Sn:") + 3, mDate.indexOf("Sn:") + 11);
                            identificationProbe(sn);
                            strCuEffective.setValue(getCuEffectiveValue(mDate, strCuInitial.getValue()));
                        }
                    }
                    break;
            }
        });
        strSoilType.setValue("原状土");
        /*for test*/
//        strCuInitial.setValue("0kPa");
//        strDeep.setValue("1m");
//        strCuEffective.setValue("18.2");
//        deg.setValue("10");
//        List<CrossTestDataEntity> list = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 10; i++) {
//            CrossTestDataEntity crossTestDataEntity = new CrossTestDataEntity();
//            crossTestDataEntity.deg = i;
//            if (i < 5) {
//                crossTestDataEntity.cu = i + random.nextFloat();
//            } else {
//                crossTestDataEntity.cu = 10 - i + random.nextFloat();
//            }
//            crossTestDataEntity.deep = 1;
//            crossTestDataEntity.number = 1;
//            crossTestDataEntity.probeID = "JKV50-007";
//            crossTestDataEntity.testDataID = "vtest_1";
//            crossTestDataEntity.type = "原状土";
//            list.add(crossTestDataEntity);
//        }
//        ldCrossTestDataEntities.setValue(list);
        /*end test*/
        getTestParameters();
    }

    private String getCuEffectiveValue(String mDate, String cuInitialValue) {
        if (mDate.contains("Cu:") && mDate.contains("kPa")) {
            String cu = mDate.substring(mDate.indexOf("Cu:") + 3, mDate.indexOf("kPa"));
            if (StringUtil.isFloat(cu)) {
                if (null != cuInitialValue) {
                    return StringUtil.format(Float.parseFloat(cu) - Float.parseFloat(cuInitialValue), 2);
                } else {
                    return StringUtil.format(Float.parseFloat(cu), 2);
                }
            } else {
                return "0";
            }

        } else {
            return "0";
        }

    }

    private boolean isIdentification;
    private ProbeEntity probeEntity;

    private void identificationProbe(String sn) {
        if (!isIdentification) {
            isIdentification = true;
            probeDao.getProbeByProbeId(sn).observe(lifecycleOwner, probeEntities -> {
                if (null != probeEntities && probeEntities.size() != 0) {
                    probeEntity = probeEntities.get(0);
                    strCuCoefficient.setValue(String.valueOf(probeEntity.qc_coefficient));
                    strCuLimit.setValue(String.valueOf(probeEntity.qc_limit));
                } else {
                    toast("该探头未添加到探头列表中，暂时不能使用，请在探头列表里添加该探头");
                }
            });
        }
    }

    private List<CrossTestDataEntity> crossTestDataEntityList;

    public void saveTestDataToSD() {
        if (null != testEntity)
            crossTestDataDao.getCrossTestDataByTestDataId(testEntity.projectNumber + "_" + testEntity.holeNumber).observe(lifecycleOwner, crossTestDataEntities -> {
                if (null != crossTestDataEntities && crossTestDataEntities.size() != 0) {
                    crossTestDataEntityList = crossTestDataEntities;
                    dataUtil.saveDataToSd(crossTestDataEntities, testEntity, iSkip);
                } else {
                    toast("读取数据失败！");
                }
            });

    }

    public void emailTestData() {
        dataUtil.emailData(crossTestDataEntityList, testEntity, iSkip);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {
        bluetoothCommService.stop();
    }

    public void modify() {
        action.setValue("modify");
    }

    public void setModify(String deep, String soilType) {
        if (!Objects.equals(strDeep.getValue(), deep)) {
            Integer testNumber = intTestNumber.getValue();
            testNumber += 1;
            intTestNumber.setValue(testNumber);
        }
        strDeep.setValue(deep);
        strSoilType.setValue(soilType);
        resetTest();
    }

    @SuppressLint("HandlerLeak")
    private
    TimeUtil timeUtil = new TimeUtil(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            doRecord();
        }
    });

    private void doRecord() {
        Integer intDeg = Integer.parseInt(deg.getValue());
        intDeg += 1;
        Float fDeep = Float.parseFloat(strDeep.getValue());
        CrossTestDataEntity crossTestDataEntity = new CrossTestDataEntity();
        crossTestDataEntity.testDataID = strProjectNumber.getValue() + "_" + strHoleNumber.getValue();
        crossTestDataEntity.deg = intDeg;
        crossTestDataEntity.cu = Float.parseFloat(strCuEffective.getValue());
        crossTestDataEntity.deep = fDeep;
        if (probeEntity != null)
            crossTestDataEntity.probeID = probeEntity.probeID;
        Integer testNumber = intTestNumber.getValue();
        if (testNumber != null)
            crossTestDataEntity.number = testNumber;
        crossTestDataEntity.type = strSoilType.getValue();
        ExecutorService DB_IO = Executors.newFixedThreadPool(2);
        DB_IO.execute(() -> {
            crossTestDataDao.insertCrossTestDataEntity(crossTestDataEntity);
            DB_IO.shutdown();//关闭线程
        });
        deg.setValue(StringUtil.format(intDeg, 1));
        action.setValue("showRecordValue");
//        getView().showRecordValue(strCuEffective.getValue(), intDeg);
    }

    private void resetTest() {
        Boolean isStart = start.getValue();
        if (isStart != null && isStart) {//处于试验中则重置
            start.setValue(false);
            timeUtil.stopTimedTask();//切换土样类型时停止
        }
        deg.setValue("0");
        action.setValue("resetChart");
//        getView().resetChart();
    }

    public void doInitialValue() {
        strCuInitial.setValue(strCuEffective.getValue());
    }

    public void doStart() {
        Boolean isStart = start.getValue();
        if (isStart != null) {
            start.setValue(!isStart);
        }
        isStart = start.getValue();
        if (isStart != null) {
            if (isStart) {
                timeUtil.timedTask(0, 10000);
            } else {
                timeUtil.stopTimedTask();
            }
        }
    }

    public void end() {
        action.setValue("onBackPressed");
    }

    public void linkDevice() {
        action.setValue("LinkBT");
        BluetoothAdapter bluetoothAdapter = bluetoothUtil.getBluetoothAdapter();
        if (bluetoothAdapter.isEnabled()) {// 蓝牙已打开
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac);
            bluetoothCommService.connect(bluetoothDevice);
        } else {
            action.setValue("OpenBT");
        }
    }


}

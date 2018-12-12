/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.crossTest;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.bluetooth.BluetoothCommService;
import www.jingkan.com.framework.utils.BluetoothUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.framework.utils.TimeUtils;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.commonProbe.ProbeDaoForRoom;
import www.jingkan.com.localData.commonProbe.ProbeEntity;
import www.jingkan.com.localData.test.TestDaoForRoom;
import www.jingkan.com.localData.test.TestEntity;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataDaoForRoom;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataEntity;
import www.jingkan.com.mInterface.ISkip;
import www.jingkan.com.saveUtils.DataUtils;

/**
 * Created by lushengbo on 2018/1/8.
 * 十字板试验VM
 * 逻辑：一个孔的试验可以做几个不同的深度，每个深度可以先做原状土再做重塑土，也可以直接选择重塑土。
 * 修改试验深度是试验编号加一。
 */

public class CrossTestViewModel extends BaseViewModel<CrossTestActivity> implements ISkip {
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (myView != null) {
                switch (msg.what) {
                    case BluetoothCommService.MESSAGE_READ://读数据
                        byte[] b = (byte[]) msg.obj;
                        String mDate = new String(b);
                        if (mDate.length() > 40) {
                            if (mDate.contains("\r")) {
                                mDate = mDate.substring(0, mDate.indexOf("\r"));
                            }
                            mDate = mDate.replace(" ", "");
                            if (mDate.contains("Sn:")) {
                                String sn = mDate.substring(mDate.indexOf("Sn:") + 3, mDate.indexOf("Sn:") + 11);
                                IdentificationProbe(sn);
                                strCuEffective.set(getCuEffectiveValue(mDate, strCuInitial.get()));
                            }
                        }
                        break;
                    case BluetoothCommService.MESSAGE_TOAST://提示信息
                        Bundle bundle = msg.getData();
                        String s = bundle.getString(BluetoothCommService.TOAST);
                        myView.get().showToast(s);
                        break;
                    case BluetoothCommService.MESSAGE_STATE_CHANGE:
                        if (msg.arg1 == BluetoothCommService.STATE_CONNECTED) {
                            getView().closeWaitDialog();
                            myView.get().showToast("连接成功");
                            linked.set(true);
                        } else if (msg.arg1 == BluetoothCommService.STATE_CONNECTING) {
                            linked.set(false);
                        } else if (msg.arg1 == BluetoothCommService.STATE_CONNECT_FAILED) {
                            getView().closeWaitDialog();
                        } else {
                            linked.set(false);
                        }
                        break;
                }
            }
        }
    };
    private BluetoothCommService bluetoothCommService = new BluetoothCommService(mHandler);

    public final ObservableField<String> strProjectNumber = new ObservableField<>();
    public final ObservableField<String> strHoleNumber = new ObservableField<>();
    public final ObservableField<String> strCuCoefficient = new ObservableField<>();
    public final ObservableField<String> strCuLimit = new ObservableField<>();
    public final ObservableField<String> strCuInitial = new ObservableField<>("0");
    public final ObservableField<String> strCuEffective = new ObservableField<>("0");
    public final ObservableField<String> deg = new ObservableField<>("0");


    public final ObservableField<Integer> intTestNumber = new ObservableField<>(1);
    public final ObservableField<Boolean> start = new ObservableField<>(false);
    public final ObservableField<Boolean> linked = new ObservableField<>(false);
    public final ObservableField<String> strSoilType = new ObservableField<>("原状土");
    private final String[] type = {"原状土", "重塑土"};
    private String mac;
    public final ObservableField<String> strDeep = new ObservableField<>("0");


    private void resetTest() {
        Boolean isStart = start.get();
        if (isStart != null && isStart) {//处于试验中则重置
            start.set(false);
            timeUtils.stopTimedTask();//切换土样类型时停止
        }
        deg.set("0");
        getView().resetChart();
    }

    public String[] getType() {
        return type;
    }


    public void doInitialValue() {
        strCuInitial.set(strCuEffective.get());
    }

    private void doRecord() {
        Integer intDeg = Integer.parseInt(deg.get());
        intDeg += 1;
        Float fDeep = Float.parseFloat(strDeep.get());
        CrossTestDataEntity crossTestDataModel = new CrossTestDataEntity();
        crossTestDataModel.testDataID = strProjectNumber.get() + "_" + strHoleNumber.get();
        crossTestDataModel.deg = intDeg;
        crossTestDataModel.cu = Float.parseFloat(strCuEffective.get());
        crossTestDataModel.deep = fDeep;
        if (probeModel != null)
            crossTestDataModel.probeID = probeModel.probeID;
        Integer testNumber = intTestNumber.get();
        if (testNumber != null)
            crossTestDataModel.number = testNumber;
        crossTestDataModel.type = strSoilType.get();
        CrossTestDataDaoForRoom crossTestDataDao = AppDatabase.getInstance(getView().getApplicationContext()).crossTestDataDaoForRoom();
        crossTestDataDao.insertCrossTestDataEntity(crossTestDataModel);
        deg.set(StringUtils.format(intDeg, 1));
        getView().showRecordValue(strCuEffective.get(), intDeg);
    }

    private @SuppressLint("HandlerLeak")
    TimeUtils timeUtils = new TimeUtils(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            doRecord();
        }
    });

    public void modify() {
        getView().showModifyDialog(strDeep.get(), strSoilType.get());
    }

    public void end() {
        getView().onBackPressed();
    }

    public void doStart() {
        Boolean isStart = start.get();
        if (isStart != null) {
            start.set(!isStart);
        }
        isStart = start.get();
        if (isStart != null) {
            if (isStart) {
                timeUtils.timedTask(0, 10000);
            } else {
                timeUtils.stopTimedTask();
            }
        }
    }

    @Override
    protected void init(Object data) {
        String[] strings = (String[]) data;
        mac = strings[0];
        strProjectNumber.set(strings[1]);
        strHoleNumber.set(strings[2]);
        getTestParameters();
        linkDevice();
    }

    private TestEntity testModel;

    private void getTestParameters() {
        TestDaoForRoom testDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).testDaoForRoom();
        LiveData<List<TestEntity>> liveData = testDaoForRoom.getTestEntityByPrjNumberAndHoleNumber(strProjectNumber.get(), strHoleNumber.get());
        List<TestEntity> testEntities = liveData.getValue();
        if (testEntities != null && !testEntities.isEmpty()) {
            testModel = testEntities.get(0);
            if (testModel != null) {
                CrossTestDataDaoForRoom crossTestDataDao = AppDatabase.getInstance(getView().getApplicationContext()).crossTestDataDaoForRoom();
                LiveData<List<CrossTestDataEntity>> liveDataCTDE = crossTestDataDao.
                        getCrossTestDataByTestDataIdAndNumber(strProjectNumber.get() + "_" + strHoleNumber.get()
                                , intTestNumber.get());
                List<CrossTestDataEntity> crossTestDataEntities = liveDataCTDE.getValue();
                if (crossTestDataEntities != null && !crossTestDataEntities.isEmpty()) {


                    CrossTestDataEntity crossTestDataModel = crossTestDataEntities.get(crossTestDataEntities.size() - 1);
                    deg.set(String.valueOf(crossTestDataModel.deg));
                    strDeep.set(String.valueOf(crossTestDataModel.deep));
                    intTestNumber.set(crossTestDataModel.number);
                    strSoilType.set(crossTestDataModel.type);
                    LiveData<List<CrossTestDataEntity>> liveDataCTDE1 = crossTestDataDao.getCrossTestDataByTestDataIdAndNumber(strProjectNumber.get() + "_" + strHoleNumber.get(), intTestNumber.get());
                    List<CrossTestDataEntity> crossTestDataEntities1 = liveDataCTDE1.getValue();
                    if (crossTestDataEntities1 != null && !crossTestDataEntities1.isEmpty()) {
                        myView.get().showTestData(crossTestDataEntities1);
                    }
                }

            } else {
                myView.get().showToast("找不到该孔信息");
            }

//        TestDao testData = DataFactory.getBaseData(TestDao.class);
//        testData.getData(new DataLoadCallBack<TestEntity>() {
//            @Override
//            public void onDataLoaded(List<TestEntity> models) {
//                testModel = models.get(0);
//                if (testModel != null) {
//                    final CrossTestDaoDao crossTestDataDao = DataFactory.getBaseData(CrossTestDaoDao.class);
//                    crossTestDataDao.getData(new DataLoadCallBack<CrossTestDataEntity>() {
//                        @Override
//                        public void onDataLoaded(List<CrossTestDataEntity> models) {
//                            CrossTestDataEntity crossTestDataModel = models.get(models.size() - 1);
//                            deg.set(String.valueOf(crossTestDataModel.deg));
//                            strDeep.set(String.valueOf(crossTestDataModel.deep));
//                            intTestNumber.set(crossTestDataModel.number);
//                            strSoilType.set(crossTestDataModel.type);
//                            crossTestDataDao.getData(new DataLoadCallBack<CrossTestDataEntity>() {
//
//                                @Override
//                                public void onDataLoaded(List<CrossTestDataEntity> models) {
//                                    myView.get().showTestData(models);
//                                }
//
//                                @Override
//                                public void onDataNotAvailable() {
//
//                                }
//                            },strProjectNumber.get() + "_" + strHoleNumber.get(), String.valueOf(intTestNumber.get()));
//
//
//                        }
//
//                        @Override
//                        public void onDataNotAvailable() {
//
//                        }
//                    }, strProjectNumber.get() + "_" + strHoleNumber.get());
//                }
//
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                myView.get().showToast("找不到该孔信息");
//            }
//        }, strProjectNumber.get(), strHoleNumber.get());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {
        bluetoothCommService.stop();
    }

    public void linkDevice() {
        getView().showWaitDialog("正在连接蓝牙", false, false);
        BluetoothAdapter bluetoothAdapter = BluetoothUtils.getInstance().
                getBluetoothAdapter();
        if (bluetoothAdapter.isEnabled()) {// 蓝牙已打开
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac);
            bluetoothCommService.connect(bluetoothDevice);
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            myView.get().startActivityForResult(intent, 0);
        }
    }

    private boolean isIdentification;
    private ProbeEntity probeModel;

    private void IdentificationProbe(String sn) {
        if (!isIdentification) {
            isIdentification = true;
            ProbeDaoForRoom probeDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).probeDaoForRoom();
            LiveData<List<ProbeEntity>> liveData = probeDaoForRoom.getProbeByProbeId(sn);
            List<ProbeEntity> probeEntities = liveData.getValue();
            if (probeEntities != null && !probeEntities.isEmpty()) {
                probeModel = probeEntities.get(0);
                strCuCoefficient.set(String.valueOf(probeModel.qc_coefficient));
                strCuLimit.set(String.valueOf(probeModel.qc_limit));
            } else {
                myView.get().showToast("该探头未添加到探头列表中，暂时不能使用，请在探头列表里添加该探头");
            }

//            ProbeDao probeDao = DataFactory.getBaseData(ProbeDao.class);
//            probeDao.getData(new DataLoadCallBack<ProbeEntity>() {
//                @Override
//                public void onDataLoaded(List<ProbeEntity> models) {
//                    probeModel = models.get(0);
//                    strCuCoefficient.set(String.valueOf(probeModel.qc_coefficient));
//                    strCuLimit.set(String.valueOf(probeModel.qc_limit));
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    myView.get().showToast("该探头未添加到探头列表中，暂时不能使用，请在探头列表里添加该探头");
//                }
//            }, sn);
        }

    }

    private String getCuEffectiveValue(String mDate, String cuInitialValue) {
        if (mDate.contains("Cu:") && mDate.contains("kPa")) {
            String cu = mDate.substring(mDate.indexOf("Cu:") + 3, mDate.indexOf("kPa"));
            if (StringUtils.isFloat(cu)) {
                return StringUtils.format(Float.parseFloat(cu) - Float.parseFloat(cuInitialValue), 2);
            } else {
                return "0";
            }

        } else {
            return "0";
        }

    }

    private List<CrossTestDataEntity> crossTestDataModels;

    public void saveTestDataToSD() {
        CrossTestDataDaoForRoom testDataDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).crossTestDataDaoForRoom();
        LiveData<List<CrossTestDataEntity>> liveData = testDataDaoForRoom.getCrossTestDataByTestDataId(testModel.projectNumber + "_" + testModel.holeNumber);
        List<CrossTestDataEntity> crossTestDataEntities = liveData.getValue();
        if (crossTestDataEntities != null && !crossTestDataEntities.isEmpty()) {
            crossTestDataModels = crossTestDataEntities;
            DataUtils.getInstance().saveDataToSd(getView().getApplicationContext(),
                    crossTestDataModels, testModel, CrossTestViewModel.this);
        } else {
            myView.get().showToast("读取数据失败！");
        }

//        CrossTestDaoDao crossTestDataDao = DataFactory.getBaseData(CrossTestDaoDao.class);
//        crossTestDataDao.getData(new DataLoadCallBack<CrossTestDataEntity>() {
//
//            @Override
//            public void onDataLoaded(List<CrossTestDataEntity> models) {
//                crossTestDataModels = models;
//                DataUtils.getInstance().saveDataToSd(getView().getApplicationContext(),
//                        models, testModel, CrossTestViewModel.this);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                myView.get().showToast("读取数据失败！");
//            }
//        }, testModel.projectNumber + "_" + testModel.holeNumber);

    }

    public void emailTestData() {
        DataUtils.getInstance().emailData(getView().getApplicationContext(),
                crossTestDataModels, testModel, this);
    }

    public void setModify(String deep, String soilType) {
        if (!strDeep.get().equals(deep)) {
            Integer testNumber = intTestNumber.get();
            testNumber += 1;
            intTestNumber.set(testNumber);
        }
        strDeep.set(deep);
        strSoilType.set(soilType);
        resetTest();
    }

    @Override
    public void skipForResult(Intent intent, int requestCode) {
        getView().startActivityForResult(intent, requestCode);
    }

    @Override
    public void skip(Intent intent) {

    }

    @Override
    public void sendToastMsg(String msg) {
        getView().showToast(msg);
    }

}

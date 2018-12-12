package www.jingkan.com.common.baseTest;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.bluetooth.BluetoothCommService;
import www.jingkan.com.framework.utils.BluetoothUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.framework.utils.VibratorUtils;
import www.jingkan.com.framework.utils.headset.HeadSetHelper;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.commonProbe.ProbeDaoForRoom;
import www.jingkan.com.localData.commonProbe.ProbeEntity;
import www.jingkan.com.localData.test.TestDaoForRoom;
import www.jingkan.com.localData.test.TestEntity;
import www.jingkan.com.localData.testData.TestDataDaoForRoom;
import www.jingkan.com.localData.testData.TestDataEntity;
import www.jingkan.com.mInterface.ISkip;
import www.jingkan.com.saveUtils.DataUtils;

/**
 * Created by Sampson on 2018/4/12.
 * LastCPT
 */
public class BaseTestViewModel extends BaseViewModel<BaseTestActivity> implements ISkip {
    public final ObservableField<String> obsProjectNumber = new ObservableField<>("");
    public final ObservableField<String> obsHoleNumber = new ObservableField<>("");
    public final ObservableField<String> obsProbeNumber = new ObservableField<>("");
    public final ObservableField<String> obsQcCoefficient = new ObservableField<>("");
    public final ObservableField<String> obsQcLimit = new ObservableField<>("");
    public final ObservableField<String> obsFsCoefficient = new ObservableField<>("");
    public final ObservableField<String> obsFsLimit = new ObservableField<>("");


    public final ObservableField<Float> obsQcInitialValue = new ObservableField<>(0f);
    public final ObservableField<Float> obsFsInitialValue = new ObservableField<>(0f);
    public final ObservableField<Float> obsQcEffectiveValue = new ObservableField<>(0f);
    public final ObservableField<Float> obsFsEffectiveValue = new ObservableField<>(0f);
    public final ObservableField<Float> obsFaEffectiveValue = new ObservableField<>(0f);
    public final ObservableField<Float> obsTestDeep = new ObservableField<>(0f);
    public final ObservableField<String> obsStringDeepDistance = new ObservableField<>("0.1");
    public final ObservableField<Boolean> obsIsShock = new ObservableField<>(false);

    private boolean isIdentification;
    private String probeID;
    private TestEntity testModel;

    @Override
    protected void init(Object data) {
        HeadSetHelper.getInstance().setOnHeadSetListener(this::doRecord);
        HeadSetHelper.getInstance().open(getView().getApplicationContext());
        String[] strings = (String[]) data;//1.mac,2.工程编号,3.孔号,4.试验类型
        loadTestData(strings[1] + "_" + strings[2]);
        getTestParameters(strings[1], strings[2]);
    }

    private void getTestParameters(String projectNumber, String holeNumber) {
        TestDaoForRoom testDao = AppDatabase.getInstance(getView().getApplicationContext()).testDaoForRoom();
        LiveData<List<TestEntity>> liveData = testDao.getTestEntityByPrjNumberAndHoleNumber(projectNumber, holeNumber);
        List<TestEntity> testEntities = liveData.getValue();
        if (testEntities != null && !testEntities.isEmpty()) {
            testModel = testEntities.get(0);
            obsProjectNumber.set(testModel.projectNumber);
            obsHoleNumber.set(testModel.holeNumber);
        } else {
            myView.get().showToast("找不到该孔信息");
        }

//        TestDao testData = DataFactory.getBaseData(TestDao.class);
//        testData.getData(new DataLoadCallBack<TestModel>() {
//
//            @Override
//            public void onDataLoaded(List<TestModel> models) {
//                testModel = models.get(0);
//                obsProjectNumber.set(testModel.projectNumber);
//                obsHoleNumber.set(testModel.holeNumber);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                myView.get().showToast("找不到该孔信息");
//            }
//        }, projectNumber, holeNumber);
    }

    public void doRecord() {
        Float floatDeep = obsTestDeep.get();
        if (floatDeep != null) {
            if (StringUtils.isFloat(obsStringDeepDistance.get())) {
                obsTestDeep.set(floatDeep + Float.valueOf(obsStringDeepDistance.get()));
            } else {
                getView().showToast("测量间距不合法！");
                return;
            }
        }

        TestDataEntity testDataModel = new TestDataEntity();
        testDataModel.testDataID = testModel.projectNumber + "-" + testModel.holeNumber;
        testDataModel.probeID = probeID;
        Float aFloat = obsTestDeep.get();
        if (aFloat != null)
            testDataModel.deep = aFloat;
        Float qcEffectiveValue = obsQcEffectiveValue.get();
        if (qcEffectiveValue != null)
            testDataModel.qc = qcEffectiveValue;
        Float fsEffectiveValue = obsFsEffectiveValue.get();
        if (fsEffectiveValue != null)
            testDataModel.fs = fsEffectiveValue;
        Float faEffectiveValue = obsFaEffectiveValue.get();
        if (faEffectiveValue != null)
            testDataModel.fa = faEffectiveValue;
        TestDataDaoForRoom testDataDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).testDataDaoForRoom();
        testDataDaoForRoom.insertTestDataEntity(testDataModel);
        Boolean aBoolean = obsIsShock.get();
        if (aBoolean != null)
            if (aBoolean) {
                VibratorUtils.Vibrate(myView.get(), 200);
            }
        if (qcEffectiveValue != null && fsEffectiveValue != null && faEffectiveValue != null && aFloat != null)
            myView.get().showRecordValue(qcEffectiveValue, fsEffectiveValue, faEffectiveValue, aFloat);
    }


    private void loadTestData(String testDataID) {
        TestDataDaoForRoom testDataDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).testDataDaoForRoom();
        LiveData<List<TestDataEntity>> liveData = testDataDaoForRoom.getTestDataByTestId(testDataID);
        List<TestDataEntity> testDataEntities = liveData.getValue();
        if (testDataEntities != null && !testDataEntities.isEmpty()) {
            myView.get().showTestData(testDataEntities);
            obsTestDeep.set(testDataEntities.get(testDataEntities.size() - 1).deep);
        }
//        testDataData.getData(new DataLoadCallBack<TestDataModel>() {
//
//            @Override
//            public void onDataLoaded(List<TestDataModel> models) {
//                myView.get().showTestData(models);
//                obsTestDeep.set(models.get(models.size() - 1).deep);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        }, testDataID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {
        bluetoothCommService.stop();
    }


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
                                identificationProbe(sn);
                                Float qcInitialValue = obsQcInitialValue.get();
                                if (qcInitialValue != null) {
                                    obsQcEffectiveValue.set(getQcEffectiveValue(mDate, qcInitialValue));
                                }
                                Float fsInitialValue = obsFsInitialValue.get();
                                if (fsInitialValue != null)
                                    obsFsEffectiveValue.set(getFsEffectiveValue(mDate, fsInitialValue));
                                obsFaEffectiveValue.set(getFaEffectiveValue(mDate));
                            }
                        }
                        break;
                    case BluetoothCommService.MESSAGE_TOAST://提示信息
                        Bundle bundle = msg.getData();
                        String s = bundle.getString(BluetoothCommService.TOAST);
                        myView.get().showToast(s);
                        break;
                    case BluetoothCommService.MESSAGE_DEVICE_NAME:
                        bundle = msg.getData();
                        String string = bundle.getString(BluetoothCommService.DEVICE_NAME);
                        myView.get().showToast(string);
                        break;
                    case BluetoothCommService.MESSAGE_STATE_CHANGE:
                        if (msg.arg1 == BluetoothCommService.STATE_CONNECTED) {
                            getView().closeWaitDialog();
                            getView().showToast("连接成功");
                        } else if (msg.arg1 == BluetoothCommService.STATE_CONNECT_FAILED) {
                            getView().closeWaitDialog();
                        }
                        break;
                }
            }
        }
    };
    private BluetoothCommService bluetoothCommService = new BluetoothCommService(mHandler);

    /**
     * 载入探头
     *
     * @param sn 探头序列号
     */
    private void identificationProbe(String sn) {
        if (!isIdentification) {
            isIdentification = true;
            probeID = sn;
            ProbeDaoForRoom probeDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).probeDaoForRoom();
            LiveData<List<ProbeEntity>> liveData = probeDaoForRoom.getProbeByProbeId(sn);
            List<ProbeEntity> probeEntities = liveData.getValue();
            if (probeEntities != null && !probeEntities.isEmpty()) {
                ProbeEntity probeModel = probeEntities.get(0);
                obsProbeNumber.set(probeModel.number);
                obsQcCoefficient.set(String.valueOf(probeModel.qc_coefficient));
                obsQcLimit.set(String.valueOf(probeModel.qc_limit));
                obsFsCoefficient.set(String.valueOf(probeModel.fs_coefficient));
                obsFsLimit.set(String.valueOf(probeModel.fs_limit));
            } else {
                myView.get().showToast("该探头未添加到探头列表中，暂时不能使用，请在探头列表里添加该探头");
            }

//            ProbeDao probeDao = DataFactory.getBaseData(ProbeDao.class);
//            probeDao.getData(new DataLoadCallBack<ProbeModel>() {
//
//                @Override
//                public void onDataLoaded(List<ProbeModel> models) {
//                    ProbeModel probeModel = models.get(0);
//                    obsProbeNumber.set(probeModel.number);
//                    obsQcCoefficient.set(String.valueOf(probeModel.qc_coefficient));
//                    obsQcLimit.set(String.valueOf(probeModel.qc_limit));
//                    obsFsCoefficient.set(String.valueOf(probeModel.fs_coefficient));
//                    obsFsLimit.set(String.valueOf(probeModel.fs_limit));
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    myView.get().showToast("该探头未添加到探头列表中，暂时不能使用，请在探头列表里添加该探头");
//                }
//            }, sn);
        }

    }

    private float getQcEffectiveValue(String mDate, float qcInitialValue) {
        if (mDate.contains("Ps:") && mDate.contains("MPa")) {
            String qc = mDate.substring(mDate.indexOf("Ps:") + 3, mDate.indexOf("MPa"));
            if (StringUtils.isFloat(qc)) {
                return Float.parseFloat(qc) - qcInitialValue;
            } else {
                return 0;
            }

        } else if (mDate.contains("Qc:") && mDate.contains("MPa")) {
            String qc = mDate.substring(mDate.indexOf("Qc:") + 3, mDate.indexOf("MPa"));
            if (StringUtils.isFloat(qc)) {
                return Float.parseFloat(qc) - qcInitialValue;
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }

    private float getFsEffectiveValue(String mDate, float fsInitialValue) {
        if (mDate.contains("Fs:") && mDate.contains("kPa")) {
            String fs = mDate.substring(mDate.indexOf("Fs:") + 3, mDate.indexOf("kPa"));
            if (StringUtils.isFloat(fs)) {
                return Float.parseFloat(fs) - fsInitialValue;
            } else {
                return 0;
            }

        } else {
            return 0;
        }

    }

    private float getFaEffectiveValue(String mDate) {
        if (mDate.contains("Fa:") && mDate.contains("Dec")) {
            String fa = mDate.substring(mDate.indexOf("Fa:") + 3, mDate.indexOf("Dec"));
            if (StringUtils.isFloat(fa)) {
                return Float.parseFloat(fa);
            } else {
                return 0;
            }

        } else {
            return 0;
        }
    }

    public void doInitialValue() {
        obsQcInitialValue.set(obsQcEffectiveValue.get());
        obsFsInitialValue.set(obsFsEffectiveValue.get());
    }

    public void modifyDistance() {
        getView().showModifyDialog(obsStringDeepDistance.get());
    }
    public void linkDevice(String mac) {
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

    private List mModels = new ArrayList();

    public void saveTestDataToSD(final String fileType) {
        TestDataDaoForRoom testDataDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).testDataDaoForRoom();
        LiveData<List<TestDataEntity>> liveData = testDataDaoForRoom.getTestDataByTestId(testModel.projectNumber + "_" + testModel.holeNumber);
        List<TestDataEntity> testDataEntities = liveData.getValue();
        if (testDataEntities != null && !testDataEntities.isEmpty()) {
            mModels = testDataEntities;
            DataUtils.getInstance()
                    .saveDataToSd(getView().getApplicationContext(),
                            testDataEntities,
                            fileType,
                            testModel,
                            BaseTestViewModel.this);
        } else {
            myView.get().showToast("读取数据失败！");
        }

//        TestDataDao testDataData = DataFactory.getBaseData(TestDataDao.class);
//        testDataData.getData(new DataLoadCallBack<TestDataModel>() {
//
//            @Override
//            public void onDataLoaded(List<TestDataModel> models) {
//                mModels = models;
//                DataUtils.getInstance()
//                        .saveDataToSd(getView().getApplicationContext(),
//                                models,
//                                fileType,
//                                testModel,
//                                BaseTestViewModel.this);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                myView.get().showToast("读取数据失败！");
//            }
//        }, testModel.projectNumber + "_" + testModel.holeNumber);

    }

    private String mFileType;

    public void emailTestData(String fileType) {
        mFileType = fileType;
        sendEmail();
    }

    private void sendEmail() {
        DataUtils.getInstance().emailData(
                getView().getApplicationContext(),
                mModels,
                mFileType,
                testModel,
                this);
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

    public void setDistance(String distance) {
        obsStringDeepDistance.set(distance);
    }
}

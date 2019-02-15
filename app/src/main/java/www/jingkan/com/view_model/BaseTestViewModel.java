package www.jingkan.com.view_model;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import com.jinkan.www.cpttest.db.dao.ProbeDao;
import com.jinkan.www.cpttest.db.dao.TestDao;
import com.jinkan.www.cpttest.db.dao.TestDataDao;
import com.jinkan.www.cpttest.db.entity.ProbeEntity;
import com.jinkan.www.cpttest.db.entity.TestDataEntity;
import com.jinkan.www.cpttest.db.entity.TestEntity;
import com.jinkan.www.cpttest.util.DataUtil;
import com.jinkan.www.cpttest.util.StringUtil;
import com.jinkan.www.cpttest.util.VibratorUtil;
import com.jinkan.www.cpttest.util.bluetooth.BluetoothCommService;
import com.jinkan.www.cpttest.util.bluetooth.BluetoothUtil;
import com.jinkan.www.cpttest.view_model.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Sampson on 2018/4/12.
 * LastCPT
 */
public class BaseTestViewModel extends BaseViewModel {
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
    public final MediatorLiveData<List<ProbeEntity>> loadProbe = new MediatorLiveData<>();
    public final MutableLiveData<float[]> recordValue = new MutableLiveData<>();
    private boolean isIdentification;
    private String probeID;
    private TestEntity testModel;
    private TestDataDao testDataDao;
    private ProbeDao probeDao;
    private VibratorUtil vibratorUtil;
    private BluetoothUtil bluetoothUtil;
    private ISkip iSkip;
    private BluetoothCommService bluetoothCommService;

    public BaseTestViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        testDataDao = (TestDataDao) objects[0];
        probeDao = (ProbeDao) objects[1];
        vibratorUtil = (VibratorUtil) objects[2];
        bluetoothUtil = (BluetoothUtil) objects[3];
        bluetoothCommService = (BluetoothCommService) objects[4];
        iSkip = (ISkip) objects[5];

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    public LiveData<List<TestEntity>> getTestParameters(TestDao testDao, String projectNumber, String holeNumber) {
        return testDao.getTestEntityByPrjNumberAndHoleNumber(projectNumber, holeNumber);
    }

    public void doRecord() {
        Float floatDeep = obsTestDeep.get();
        if (floatDeep != null) {
            String s = obsStringDeepDistance.get();
            if (s != null) {
                if (StringUtil.isFloat(s)) {
                    obsTestDeep.set(floatDeep + Float.valueOf(s));
                } else {
                    toast("测量间距不合法！");
                    return;
                }
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
        testDataDao.insertTestDataEntity(testDataModel);
        Boolean aBoolean = obsIsShock.get();
        if (aBoolean != null)
            if (aBoolean) {
                vibratorUtil.Vibrate(200);
            }
        if (qcEffectiveValue != null && fsEffectiveValue != null && faEffectiveValue != null && aFloat != null)
            recordValue.setValue(new float[]{qcEffectiveValue, fsEffectiveValue, faEffectiveValue, aFloat});
//            myView.get().showRecordValue(qcEffectiveValue, fsEffectiveValue, faEffectiveValue, aFloat);
    }


    public LiveData<List<TestDataEntity>> loadTestData(String testDataID) {
        return testDataDao.getTestDataByTestId(testDataID);
    }

    public void identificationProbe(String sn) {
        if (!isIdentification) {
            isIdentification = true;
            probeID = sn;
            loadProbe.addSource(probeDao.getProbeByProbeId(sn), loadProbe::setValue);
        }

    }

    public float getQcEffectiveValue(String mDate, float qcInitialValue) {
        if (mDate.contains("Ps:") && mDate.contains("MPa")) {
            String qc = mDate.substring(mDate.indexOf("Ps:") + 3, mDate.indexOf("MPa"));
            if (StringUtil.isFloat(qc)) {
                return Float.parseFloat(qc) - qcInitialValue;
            } else {
                return 0;
            }

        } else if (mDate.contains("Qc:") && mDate.contains("MPa")) {
            String qc = mDate.substring(mDate.indexOf("Qc:") + 3, mDate.indexOf("MPa"));
            if (StringUtil.isFloat(qc)) {
                return Float.parseFloat(qc) - qcInitialValue;
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }

    public float getFsEffectiveValue(String mDate, float fsInitialValue) {
        if (mDate.contains("Fs:") && mDate.contains("kPa")) {
            String fs = mDate.substring(mDate.indexOf("Fs:") + 3, mDate.indexOf("kPa"));
            if (StringUtil.isFloat(fs)) {
                return Float.parseFloat(fs) - fsInitialValue;
            } else {
                return 0;
            }

        } else {
            return 0;
        }

    }

    public float getFaEffectiveValue(String mDate) {
        if (mDate.contains("Fa:") && mDate.contains("Dec")) {
            String fa = mDate.substring(mDate.indexOf("Fa:") + 3, mDate.indexOf("Dec"));
            if (StringUtil.isFloat(fa)) {
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
        callbackMessage.setValue(0);
        getView().callback(callbackMessage);
//        action.setValue("showModifyDialog");
//        getView().showModifyDialog(obsStringDeepDistance.get());
    }

    public void linkDevice(String mac) {
        callbackMessage.setValue(1);
        getView().callback(callbackMessage);
//        action.setValue("showWaitDialog");
//        getView().showWaitDialog("正在连接蓝牙", false, false);
        BluetoothAdapter bluetoothAdapter = bluetoothUtil.getBluetoothAdapter();
        if (bluetoothAdapter.isEnabled()) {// 蓝牙已打开
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac);
            bluetoothCommService.connect(bluetoothDevice);
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            callbackMessage.setValue(2);
            getView().callback(callbackMessage);
//            action.setValue("startActivityForResult");
//            myView.get().startActivityForResult(intent, 0);
        }
    }

    private List mModels = new ArrayList();

    public LiveData<List<TestDataEntity>> saveTestDataToSD() {
        return testDataDao.getTestDataByTestId(testModel.projectNumber + "_" + testModel.holeNumber);

    }

    private String mFileType;

    public void emailTestData(String fileType, DataUtil dataUtil) {
        mFileType = fileType;
        sendEmail(dataUtil);
    }

    private void sendEmail(DataUtil dataUtil) {
        dataUtil.emailData(mModels, mFileType, testModel, iSkip);
    }

    public void setDistance(String distance) {
        obsStringDeepDistance.set(distance);
    }


    public void setTestEntity(TestEntity testEntity) {
        testModel = testEntity;
    }
}

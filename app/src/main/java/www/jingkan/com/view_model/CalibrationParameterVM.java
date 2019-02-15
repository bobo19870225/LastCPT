package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.db.dao.CalibrationProbeDao;
import com.jinkan.www.cpttest.db.entity.CalibrationProbeEntity;
import com.jinkan.www.cpttest.util.PreferencesUtil;
import com.jinkan.www.cpttest.util.StringUtil;
import com.jinkan.www.cpttest.view.CalibrationVerificationActivity;
import com.jinkan.www.cpttest.view.LinkBluetoothActivity;
import com.jinkan.www.cpttest.view.SetCalibrationDataActivity;
import com.jinkan.www.cpttest.view_model.base.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Sampson on 2019/1/16.
 * CPTTest
 */
public class CalibrationParameterVM extends BaseViewModel {
    public final MutableLiveData<String> ldSn = new MutableLiveData<>();
    public final MutableLiveData<String> ldProbeNumber = new MutableLiveData<>();
    public final MutableLiveData<String> ldLoad = new MutableLiveData<>();
    public final MutableLiveData<String> ldArea = new MutableLiveData<>();
    public final MutableLiveData<String> ldLength = new MutableLiveData<>();
    public final MutableLiveData<String> ldStandard = new MutableLiveData<>();

    public final MediatorLiveData<List<CalibrationProbeEntity>> ldCalibrationProbeEntity = new MediatorLiveData<>();
    private CalibrationProbeDao calibrationProbeDao;
    private PreferencesUtil preferencesUtil;
    private String[] strings;

    public CalibrationParameterVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        strings = (String[]) objects[0];
        ldLength.setValue("2");
        ldStandard.setValue("0.9");
        calibrationProbeDao = (CalibrationProbeDao) objects[1];
        preferencesUtil = (PreferencesUtil) objects[2];
        getCalibrationProbeEntity();
    }

    public void confirm() {
        String strSn = ldSn.getValue();

        Map<String, String> linkerPreferences = preferencesUtil.getLinkerPreferences();
        String add = linkerPreferences.get("add");

        if (checkParameter(strSn))
            return;
        CalibrationProbeEntity calibrationProbeEntity = new CalibrationProbeEntity();
        if (strSn != null) {
            calibrationProbeEntity.probeID = strSn;
        }
        calibrationProbeEntity.number = ldProbeNumber.getValue();
        calibrationProbeEntity.work_area = ldArea.getValue();
        calibrationProbeEntity.differential = ldLoad.getValue();
        calibrationProbeDao.insertCalibrationProbe(calibrationProbeEntity);
        switch (strings[0]) {
            case "设置探头内存数据":
                goToSetCalibrationData(strSn, add);
                break;
            case "模拟标定":
                gotoAnalogCalibrationVerification(strSn, add);
                break;
            default:
                gotoCalibrationVerification(strSn, add);
                break;
        }

    }

    private void goToSetCalibrationData(String strSn, String add) {
        if (StringUtil.isEmpty(add)) {
            HashMap<String, String> stringHashMap = new HashMap<>();
            stringHashMap.put("action", "设置探头内部数据");
            stringHashMap.put("Sn", strSn);
            stringHashMap.put("type", strings[1]);
            goTo(LinkBluetoothActivity.class, stringHashMap);
        } else {//传递：1.蓝牙地址 2.探头序列号 3.标定类型
            goTo(SetCalibrationDataActivity.class, new String[]{add, strSn, strings[1]});
        }
    }

    private void gotoAnalogCalibrationVerification(String strSn, String add) {
        if (StringUtil.isEmpty(add)) {//探头序列号，标定类型，明文。
            HashMap<String, String> stringHashMap = new HashMap<>();
            stringHashMap.put("action", "模拟探头标定");
            stringHashMap.put("Sn", strSn);
            stringHashMap.put("type", strings[0] + strings[1]);
            goTo(LinkBluetoothActivity.class, stringHashMap);
        } else {//传递：1.蓝牙地址 2.探头序列号 3.标定类型
            goTo(CalibrationVerificationActivity.class, new String[]{add, strSn, strings[0] + strings[1]});
        }
    }

    private void gotoCalibrationVerification(String strSn, String add) {
        if (StringUtil.isEmpty(add)) {//探头序列号，标定类型，明文。
            HashMap<String, String> stringHashMap = new HashMap<>();
            stringHashMap.put("action", "数字探头标定");
            stringHashMap.put("Sn", strSn);
            stringHashMap.put("type", strings[1]);
            goTo(LinkBluetoothActivity.class, stringHashMap);
        } else {//传递：1.蓝牙地址 2.探头序列号 3.标定类型
            goTo(CalibrationVerificationActivity.class, new String[]{add, strSn, strings[1]});
        }
    }

    private boolean checkParameter(String strSn) {
        if (StringUtil.isEmpty(strSn)) {
            toast("探头序列号不能为空");
            return true;
        }
        if (StringUtil.isEmpty(ldProbeNumber.getValue())) {
            toast("探头编号不能为空");
            return true;
        }
        if (StringUtil.isEmpty(ldArea.getValue())) {
            toast("锥头面积不能为空");
            return true;
        }
        if (StringUtil.isEmpty(ldLength.getValue())) {
            toast("电缆长度不能为空");
            return true;
        }
        if (StringUtil.isEmpty(ldStandard.getValue())) {
            toast("电缆规格不能为空");
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    private void getCalibrationProbeEntity() {
        ldCalibrationProbeEntity.addSource(calibrationProbeDao.getAllCalbrationProbeEntity(), ldCalibrationProbeEntity::setValue);
    }
}

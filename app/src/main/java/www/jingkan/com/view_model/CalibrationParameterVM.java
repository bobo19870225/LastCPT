package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import www.jingkan.com.db.dao.CalibrationProbeDao;
import www.jingkan.com.db.dao.CalibrationProbeDaoHelper;
import www.jingkan.com.db.entity.CalibrationProbeEntity;
import www.jingkan.com.util.PreferencesUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.view.CalibrationVerificationActivity;
import www.jingkan.com.view.LinkBluetoothActivity;
import www.jingkan.com.view.SetCalibrationDataActivity;
import www.jingkan.com.view_model.base.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Sampson on 2019/1/31.
 * CPTTest
 */
public class CalibrationParameterVM extends BaseViewModel {


    public final MutableLiveData<String> lvSn = new MutableLiveData<>();
    public final MutableLiveData<String> lvProbeNumber = new MutableLiveData<>();
    public final MutableLiveData<String> lvDifferential = new MutableLiveData<>();
    public final MutableLiveData<String> lvArea = new MutableLiveData<>();
    public final MutableLiveData<String> lvLength = new MutableLiveData<>();
    public final MutableLiveData<String> lvSpecification = new MutableLiveData<>();
    private CalibrationProbeDaoHelper calibrationProbeDaoHelper;
    private PreferencesUtil preferencesUtil;
    private String[] strings;
    public final MediatorLiveData<List<CalibrationProbeEntity>> lvCalibrationProbeEntities = new MediatorLiveData<>();

    public CalibrationParameterVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        strings = (String[]) objects[0];
        CalibrationProbeDao calibrationProbeDao = (CalibrationProbeDao) objects[1];
        calibrationProbeDaoHelper = (CalibrationProbeDaoHelper) objects[2];
        preferencesUtil = (PreferencesUtil) objects[3];
        lvLength.setValue("2");
        lvSpecification.setValue("0.9");
        lvCalibrationProbeEntities.addSource(calibrationProbeDao.getAllCalbrationProbeEntity(), lvCalibrationProbeEntities::setValue);

    }

    public void confirm() {
        Map<String, String> linkerPreferences = preferencesUtil.getLinkerPreferences();
        String add = linkerPreferences.get("add");
        if (checkParameter(lvSn.getValue())) return;
        CalibrationProbeEntity calibrationProbeEntity = new CalibrationProbeEntity();
        calibrationProbeEntity.probeID = Objects.requireNonNull(lvSn.getValue());
        calibrationProbeEntity.number = lvProbeNumber.getValue();
        calibrationProbeEntity.work_area = lvArea.getValue();
        calibrationProbeEntity.differential = lvDifferential.getValue();
        calibrationProbeDaoHelper.addData(calibrationProbeEntity, () -> toast("建立成功"));
        switch (strings[0]) {
            case "设置探头内存数据":
                goToSetCalibrationData(lvSn.getValue(), add);
                break;
            case "模拟标定":
                gotoAnalogCalibrationVerification(lvSn.getValue(), add);
                break;
            default:
                gotoCalibrationVerification(lvSn.getValue(), add);
                break;
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
        if (StringUtil.isEmpty(lvProbeNumber.getValue())) {
            toast("探头编号不能为空");
            return true;
        }
        if (StringUtil.isEmpty(lvArea.getValue())) {
            toast("锥头面积不能为空");
            return true;
        }
        if (StringUtil.isEmpty(lvLength.getValue())) {
            toast("电缆长度不能为空");
            return true;
        }
        if (StringUtil.isEmpty(lvSpecification.getValue())) {
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
}

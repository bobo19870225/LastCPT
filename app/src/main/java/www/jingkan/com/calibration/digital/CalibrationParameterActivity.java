/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.digital;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.calibration.calibrationVerification.CalibrationVerificationActivity;
import www.jingkan.com.calibration.digital.setCalibrationData.SetCalibrationDataActivity;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.linkBluetooth.LinkBluetoothActivity;
import www.jingkan.com.localData.calibrationProbe.CalibrationProbeDao;
import www.jingkan.com.localData.calibrationProbe.CalibrationProbeModel;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by bobo on 2017/3/19.
 * 数字标定参数
 */

public class CalibrationParameterActivity extends BaseActivity {
    @BindView(id = R.id.confirm, click = true)
    private Button confirm;
    @BindView(id = R.id.sn)
    private EditText sn;
    @BindView(id = R.id.number)
    private EditText number;
    @BindView(id = R.id.area)
    private EditText area;
    @BindView(id = R.id.length)
    private EditText length;
    @BindView(id = R.id.specification)
    private EditText specification;
    @BindView(id = R.id.differential)
    private EditText differential;
    private CalibrationProbeDao calibrationProbeDao = DataFactory.getBaseData(CalibrationProbeDao.class);
    private String[] strings;

    @Override
    protected void setView() {
        strings = (String[]) mData;
        setTitle(strings[1]);
        calibrationProbeDao.getData(new DataLoadCallBack<CalibrationProbeModel>() {

            @Override
            public void onDataLoaded(List<CalibrationProbeModel> models) {
                CalibrationProbeModel calibrationProbeModel = models.get(models.size() - 1);
                sn.setText(calibrationProbeModel.probeID);
                number.setText(calibrationProbeModel.number);
                area.setText(calibrationProbeModel.work_area);
                differential.setText(calibrationProbeModel.differential);
            }

            @Override
            public void onDataNotAvailable() {
                showToast("还没有标定数据");
            }
        });
    }


    @Override
    public int initView() {
        return R.layout.activity_single_brigdge_calibration;
    }

    @Override
    public void onClick(View view) {
        String strSn = sn.getText().toString();
        PreferencesUtils preferencesUtils = new PreferencesUtils(this);
        Map<String, String> linkerPreferences = preferencesUtils.getLinkerPreferences();
        String add = linkerPreferences.get("add");
        switch (view.getId()) {
            case R.id.confirm:
                if (checkParameter(strSn)) break;
                CalibrationProbeModel calibrationProbeModel = new CalibrationProbeModel();
                calibrationProbeModel.probeID = strSn;
                calibrationProbeModel.number = number.getText().toString();
                calibrationProbeModel.work_area = area.getText().toString();
                calibrationProbeModel.differential = differential.getText().toString();
                calibrationProbeDao.addData(calibrationProbeModel);
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
                break;

        }
    }

    private void gotoAnalogCalibrationVerification(String strSn, String add) {
        if (StringUtils.isEmpty(add)) {//探头序列号，标定类型，明文。
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
        if (StringUtils.isEmpty(add)) {
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
        if (StringUtils.isEmpty(add)) {//探头序列号，标定类型，明文。
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
        if (StringUtils.isEmpty(strSn)) {
            showToast("探头序列号不能为空");
            return true;
        }
        if (StringUtils.isEmpty(number.getText().toString())) {
            showToast("探头编号不能为空");
            return true;
        }
        if (StringUtils.isEmpty(area.getText().toString())) {
            showToast("锥头面积不能为空");
            return true;
        }
        if (StringUtils.isEmpty(length.getText().toString())) {
            showToast("电缆长度不能为空");
            return true;
        }
        if (StringUtils.isEmpty(specification.getText().toString())) {
            showToast("电缆规格不能为空");
            return true;
        }
        return false;
    }
}

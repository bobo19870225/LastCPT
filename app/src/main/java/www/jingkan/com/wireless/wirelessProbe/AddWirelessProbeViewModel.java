/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.wirelessProbe;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.framework.acp.Acp;
import www.jingkan.com.framework.acp.AcpListener;
import www.jingkan.com.framework.acp.AcpOptions;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeEntity;
import www.jingkan.com.qrcode.qrSimple.CaptureActivity;

/**
 * Created by lushengbo on 2018/1/24.
 * 添加无缆探头
 */

public class AddWirelessProbeViewModel extends BaseViewModel<AddWirelessProbeActivity> {
    @Override
    protected void init(Object data) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case OK:
                if (resultCode == Activity.RESULT_OK) {// 用户选择连接的设备
                    Bundle dataExtras = data.getExtras();
                    String result = null;
                    if (dataExtras != null) {
                        result = dataExtras.getString("result");
                    }
                    String[] strings;
                    if (result != null) {
                        strings = result.split("\r\n");
                        if (strings.length == 1) {
                            strings = result.split("\n");
                        }
                        if (strings.length == 3) {//单桥
                            WirelessProbeEntity wirelessProbeModel = new WirelessProbeEntity();
                            String[] strProbeNumber = strings[1].split("-");
                            if (strProbeNumber[0].contains("D")) {//单桥探头
                                String mj;
                                wirelessProbeModel.probeID = strings[0];
                                wirelessProbeModel.sn = strings[0];
                                wirelessProbeModel.number = strings[1];
                                if (strProbeNumber[0].contains("DW")) {//单桥测斜探头
                                    mj = strProbeNumber[0].substring(strProbeNumber[0].indexOf("DW") + 2);
                                    wirelessProbeModel.type = "单桥测斜";
                                } else {
                                    mj = strProbeNumber[0].substring(strProbeNumber[0].indexOf("D") + 1);
                                    wirelessProbeModel.type = "单桥测斜";
                                }
                                if (StringUtils.isInteger(mj)) {
                                    wirelessProbeModel.qc_area = mj;
                                } else {
                                    getView().showToast("锥头面积不合法！");
                                    return;
                                }
                                if (StringUtils.isFloat(strings[2])) {
                                    wirelessProbeModel.qc_coefficient = Float.parseFloat(strings[2]);
                                } else {
                                    getView().showToast("锥头系数不合法！");
                                    return;
                                }
                                if (StringUtils.isInteger(strProbeNumber[1])) {
                                    wirelessProbeModel.qc_limit = Integer.parseInt(strProbeNumber[1]) * 12;
                                } else {
                                    getView().showToast("锥头限值不合法！");
                                    return;
                                }
                                wirelessProbeModel.save();
                                getView().goTo(WirelessProbeActivity.class, null, true);
                            }
                        } else if (strings.length == 4) {//双桥探头
                            WirelessProbeEntity wirelessProbeModel = new WirelessProbeEntity();
                            String[] strProbeNumber = strings[1].split("-");
                            String mj;
                            if (strProbeNumber[0].contains("S")) {//双桥探头
                                wirelessProbeModel.probeID = strings[0];
                                wirelessProbeModel.sn = strings[0];
                                wirelessProbeModel.number = strings[1];
                                if (strProbeNumber[0].contains("SW")) {//双桥测斜探头
                                    wirelessProbeModel.type = "双桥测斜";
                                    mj = strProbeNumber[0].substring(strProbeNumber[0].indexOf("SW") + 2);
                                } else {
                                    mj = strProbeNumber[0].substring(strProbeNumber[0].indexOf("S") + 1);
                                    wirelessProbeModel.type = "双桥测斜";
                                }
                                if (StringUtils.isInteger(mj)) {
                                    wirelessProbeModel.qc_area = mj;
                                } else {
                                    getView().showToast("锥头面积不合法！");
                                    return;
                                }
                                if (StringUtils.isFloat(strings[2])) {
                                    wirelessProbeModel.qc_coefficient = Float.parseFloat(strings[2]);
                                } else {
                                    getView().showToast("锥头系数不合法！");
                                    return;
                                }
                                if (StringUtils.isInteger(strProbeNumber[1])) {
                                    wirelessProbeModel.qc_limit = Integer.parseInt(strProbeNumber[1]) * 12;
                                } else {
                                    getView().showToast("锥头限值不合法！");
                                    return;
                                }
                                wirelessProbeModel.fs_area = String.valueOf(Integer.parseInt(mj) * 20);
                                if (StringUtils.isFloat(strings[3])) {
                                    wirelessProbeModel.fs_coefficient = Float.parseFloat(strings[3]);
                                } else {
                                    getView().showToast("侧壁系数不合法！");
                                    return;
                                }
                                wirelessProbeModel.fs_limit = Integer.parseInt(strProbeNumber[1]) * 120;
                                wirelessProbeModel.save();
                                getView().goTo(WirelessProbeActivity.class, null, true);
                            }
                        } else {
                            getView().showToast("该二维码无效！请重试或联系厂家");
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void clear() {

    }

    private final int OK = 0;

    public void scanCode() {
        Acp.getInstance(getView()).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CAMERA)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(getView(), CaptureActivity.class);
                        getView().startActivityForResult(intent, OK);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        getView().showToast(permissions.toString() + "权限拒绝");
                    }
                });
    }

    public void inPut() {
        getView().goTo(AddWirelessProbeInfoActivity.class, null);
    }
}

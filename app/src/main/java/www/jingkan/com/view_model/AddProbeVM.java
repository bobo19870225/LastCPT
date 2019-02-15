package www.jingkan.com.view_model;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import www.jingkan.com.db.dao.ProbeDaoHelper;
import www.jingkan.com.db.dao.WirelessProbeDaoHelper;
import www.jingkan.com.db.entity.ProbeEntity;
import www.jingkan.com.db.entity.WirelessProbeEntity;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.view_model.base.BaseViewModel;

import androidx.annotation.NonNull;

/**
 * Created by Sampson on 2018/12/28.
 * CPTTest
 */
public class AddProbeVM extends BaseViewModel {
    private Boolean isWireless;
    public AddProbeVM(@NonNull Application application) {
        super(application);
    }

    //    private ProbeDao probeDao;
    private ProbeDaoHelper probeDaoHelper;
    private WirelessProbeDaoHelper wirelessProbeDaoHelper;
    @Override
    public void inject(Object... objects) {
        isWireless = objects[0].equals("无缆探头");
        probeDaoHelper = (ProbeDaoHelper) objects[1];
        wirelessProbeDaoHelper = (WirelessProbeDaoHelper) objects[2];
    }

    public void scanCode() {
        callbackMessage.setValue(0);
        getView().action(callbackMessage);
    }

    public void inPut() {
        callbackMessage.setValue(1);
        getView().action(callbackMessage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {// 用户选择连接的设备
                    Bundle dataExtras = data.getExtras();
                    String result = null;
                    if (dataExtras != null) {
                        result = dataExtras.getString("result");
                    }

                    if (result != null) {
                        String[] strings;
                        strings = result.split("\r\n");
                        if (strings.length == 1) {
                            strings = result.split("\n");
                        }
                        if (isWireless) {
                            addWirelessProbe(strings);
                        } else {
                            addOrdinaryProbe(strings);
                        }

                    }
                }
                break;

            default:
                break;
        }
    }

    private void addWirelessProbe(String[] strings) {
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
                if (StringUtil.isInteger(mj)) {
                    wirelessProbeModel.qc_area = mj;
                } else {
                    toast("锥头面积不合法！");
                    return;
                }
                if (StringUtil.isFloat(strings[2])) {
                    wirelessProbeModel.qc_coefficient = Float.parseFloat(strings[2]);
                } else {
                    toast("锥头系数不合法！");
                    return;
                }
                if (StringUtil.isInteger(strProbeNumber[1])) {
                    wirelessProbeModel.qc_limit = Integer.parseInt(strProbeNumber[1]) * 12;
                } else {
                    toast("锥头限值不合法！");
                    return;
                }
                addWirelessProbe(wirelessProbeModel);
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
                if (StringUtil.isInteger(mj)) {
                    wirelessProbeModel.qc_area = mj;
                } else {
                    toast("锥头面积不合法！");
                    return;
                }
                if (StringUtil.isFloat(strings[2])) {
                    wirelessProbeModel.qc_coefficient = Float.parseFloat(strings[2]);
                } else {
                    toast("锥头系数不合法！");
                    return;
                }
                if (StringUtil.isInteger(strProbeNumber[1])) {
                    wirelessProbeModel.qc_limit = Integer.parseInt(strProbeNumber[1]) * 12;
                } else {
                    toast("锥头限值不合法！");
                    return;
                }
                wirelessProbeModel.fs_area = String.valueOf(Integer.parseInt(mj) * 20);
                if (StringUtil.isFloat(strings[3])) {
                    wirelessProbeModel.fs_coefficient = Float.parseFloat(strings[3]);
                } else {
                    toast("侧壁系数不合法！");
                    return;
                }
                wirelessProbeModel.fs_limit = Integer.parseInt(strProbeNumber[1]) * 120;
                addWirelessProbe(wirelessProbeModel);
            }
        } else {
            toast("该二维码无效！请重试或联系厂家");
        }

    }

    private void addWirelessProbe(WirelessProbeEntity wirelessProbeModel) {

        wirelessProbeDaoHelper.addData(wirelessProbeModel, () -> {
            callbackMessage.setValue(3);
            getView().action(callbackMessage);

        });

    }

    private void addOrdinaryProbe(String[] strings) {
        if (strings.length == 3) {//单桥或十字板探头
            ProbeEntity probeModel = new ProbeEntity();
            String[] strProbeNumber = strings[1].split("-");
            if (strProbeNumber[0].contains("D")) {//单桥探头
                String mj;
                probeModel.probeID = strings[0];
                probeModel.sn = strings[0];
                probeModel.number = strings[1];
                if (strProbeNumber[0].contains("DX")) {//单桥测斜探头
                    mj = strProbeNumber[0].substring(strProbeNumber[0].indexOf("DX") + 2);
                    probeModel.type = "单桥测斜";
                } else {
                    mj = strProbeNumber[0].substring(strProbeNumber[0].indexOf("D") + 1);
                    probeModel.type = "单桥";
                }
                if (StringUtil.isInteger(mj)) {
                    probeModel.qc_area = mj;
                } else {
                    toast("锥头面积不合法！");
                    return;
                }
                if (StringUtil.isFloat(strings[2])) {
                    probeModel.qc_coefficient = Float.parseFloat(strings[2]);
                } else {
                    toast("锥头系数不合法！");
                    return;
                }
                if (StringUtil.isInteger(strProbeNumber[1])) {
                    probeModel.qc_limit = Integer.parseInt(strProbeNumber[1]) * 12;
                } else {
                    toast("锥头限值不合法！");
                    return;
                }

                addData(probeModel);

            } else if (strProbeNumber[0].contains("V")) {//十字板探头
                probeModel.probeID = strings[0];
                probeModel.sn = strings[0];
                probeModel.number = strings[1];
                probeModel.type = "十字板";
                probeModel.qc_area = "50";
                if (StringUtil.isFloat(strings[2])) {
                    probeModel.qc_coefficient = Float.parseFloat(strings[2]);
                } else {
                    toast("板头系数不合法！");
                    return;
                }
                probeModel.qc_limit = 140;
                addData(probeModel);
            }
        } else if (strings.length == 4) {//双桥探头
            ProbeEntity probeModel = new ProbeEntity();
            String[] strProbeNumber = strings[1].split("-");
            String mj;
            if (strProbeNumber[0].contains("S")) {//双桥探头
                probeModel.probeID = strings[0];
                probeModel.sn = strings[0];
                probeModel.number = strings[1];
                if (strProbeNumber[0].contains("SX")) {//双桥测斜探头
                    probeModel.type = "双桥测斜";
                    mj = strProbeNumber[0].substring(strProbeNumber[0].indexOf("SX") + 2);
                } else {
                    mj = strProbeNumber[0].substring(strProbeNumber[0].indexOf("S") + 1);
                    probeModel.type = "双桥";
                }
                if (StringUtil.isInteger(mj)) {
                    probeModel.qc_area = mj;
                } else {
                    toast("锥头面积不合法！");
                    return;
                }
                if (StringUtil.isFloat(strings[2])) {
                    probeModel.qc_coefficient = Float.parseFloat(strings[2]);
                } else {
                    toast("锥头系数不合法！");
                    return;
                }
                if (StringUtil.isInteger(strProbeNumber[1])) {
                    probeModel.qc_limit = Integer.parseInt(strProbeNumber[1]) * 12;
                } else {
                    toast("锥头限值不合法！");
                    return;
                }
                probeModel.fs_area = String.valueOf(Integer.parseInt(mj) * 20);
                if (StringUtil.isFloat(strings[3])) {
                    probeModel.fs_coefficient = Float.parseFloat(strings[3]);
                } else {
                    toast("侧壁系数不合法！");
                    return;
                }
                probeModel.fs_limit = Integer.parseInt(strProbeNumber[1]) * 120;
                addData(probeModel);

            }
        } else {
            toast("该二维码无效！请重试或联系厂家");
        }
    }

    private void addData(ProbeEntity probeModel) {
        probeDaoHelper.addData(probeModel, () -> {
            callbackMessage.setValue(2);
            getView().action(callbackMessage);
        });
    }

    @Override
    public void clear() {

    }
}

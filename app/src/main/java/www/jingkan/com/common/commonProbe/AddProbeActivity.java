/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.commonProbe;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.framework.acp.Acp;
import www.jingkan.com.framework.acp.AcpListener;
import www.jingkan.com.framework.acp.AcpOptions;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.commonProbe.ProbeDao;
import www.jingkan.com.localData.commonProbe.ProbeModel;
import www.jingkan.com.qrcode.qrSimple.CaptureActivity;

public class AddProbeActivity extends BaseActivity {
    @BindView(id = www.jingkan.com.R.id.code_scan, click = true)
    private RelativeLayout code_scan;
    @BindView(id = www.jingkan.com.R.id.input, click = true)
    private RelativeLayout input;
    private final int OK = 0;
    private ProbeDao probeDao = DataFactory.getBaseData(ProbeDao.class);

    @Override
    protected void setView() {
        setToolBar("添加探头");
    }

    @Override
    public int initView() {
        return www.jingkan.com.R.layout.activity_add_probe;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                        if (strings.length == 3) {//单桥或十字板探头
                            ProbeModel probeModel = new ProbeModel();
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
                                if (StringUtils.isInteger(mj)) {
                                    probeModel.qc_area = mj;
                                } else {
                                    showToast("锥头面积不合法！");
                                    return;
                                }
                                if (StringUtils.isFloat(strings[2])) {
                                    probeModel.qc_coefficient = Float.parseFloat(strings[2]);
                                } else {
                                    showToast("锥头系数不合法！");
                                    return;
                                }
                                if (StringUtils.isInteger(strProbeNumber[1])) {
                                    probeModel.qc_limit = Integer.parseInt(strProbeNumber[1]) * 12;
                                } else {
                                    showToast("锥头限值不合法！");
                                    return;
                                }

                                probeDao.addData(probeModel);
                                goTo(CommonProbeActivity.class, null, true);


                            } else if (strProbeNumber[0].contains("V")) {//十字板探头
                                probeModel.probeID = strings[0];
                                probeModel.sn = strings[0];
                                probeModel.number = strings[1];
                                probeModel.type = "十字板";
                                probeModel.qc_area = "50";
                                if (StringUtils.isFloat(strings[2])) {
                                    probeModel.qc_coefficient = Float.parseFloat(strings[2]);
                                } else {
                                    showToast("板头系数不合法！");
                                    return;
                                }
                                probeModel.qc_limit = 140;
                                probeDao.addData(probeModel);
                                goTo(CommonProbeActivity.class, null, true);

                            }
                        } else if (strings.length == 4) {//双桥探头
                            ProbeModel probeModel = new ProbeModel();
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
                                if (StringUtils.isInteger(mj)) {
                                    probeModel.qc_area = mj;
                                } else {
                                    showToast("锥头面积不合法！");
                                    return;
                                }
                                if (StringUtils.isFloat(strings[2])) {
                                    probeModel.qc_coefficient = Float.parseFloat(strings[2]);
                                } else {
                                    showToast("锥头系数不合法！");
                                    return;
                                }
                                if (StringUtils.isInteger(strProbeNumber[1])) {
                                    probeModel.qc_limit = Integer.parseInt(strProbeNumber[1]) * 12;
                                } else {
                                    showToast("锥头限值不合法！");
                                    return;
                                }
                                probeModel.fs_area = String.valueOf(Integer.parseInt(mj) * 20);
                                if (StringUtils.isFloat(strings[3])) {
                                    probeModel.fs_coefficient = Float.parseFloat(strings[3]);
                                } else {
                                    showToast("侧壁系数不合法！");
                                    return;
                                }
                                probeModel.fs_limit = Integer.parseInt(strProbeNumber[1]) * 120;
                                probeDao.addData(probeModel);
                                goTo(CommonProbeActivity.class, null, true);

                            }
                        } else {
                            showToast("该二维码无效！请重试或联系厂家");
                        }
                    }
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case www.jingkan.com.R.id.code_scan:
                Acp.getInstance(AddProbeActivity.this).request(new AcpOptions.Builder()
                                .setPermissions(Manifest.permission.CAMERA)
                                .build(),
                        new AcpListener() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(AddProbeActivity.this, CaptureActivity.class);
                                startActivityForResult(intent, OK);
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                showToast(permissions.toString() + "权限拒绝");
                            }
                        });

                break;
            case www.jingkan.com.R.id.input:
                goTo(AddProbeInfoActivity.class, null);
                break;
        }

    }
}

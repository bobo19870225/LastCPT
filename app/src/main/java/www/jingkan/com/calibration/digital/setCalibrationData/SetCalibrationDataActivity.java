/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.digital.setCalibrationData;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.baseMVP.BaseMvpActivity;
import www.jingkan.com.localData.memoryData.MemoryDataModel;

/**
 * 设置探头内部数据界面
 */

public class SetCalibrationDataActivity extends BaseMvpActivity<SetCalibrationDataPresenter> implements SetCalibrationDataContract.View {
    @BindView(id = R.id.initial)
    private TextView initial;
    @BindView(id = R.id.bt_record, click = true)
    private Button bt_record;
    @BindView(id = R.id.valid)
    private TextView valid;
    @BindView(id = R.id.number)
    private TextView number;
    @BindView(id = R.id.differential)
    private TextView differential;
    @BindView(id = R.id.area)
    private TextView area;
    @BindView(id = R.id.sn)
    private TextView sn;

    @BindView(id = R.id.s1)
    private TextView s1;
    @BindView(id = R.id.s2)
    private TextView s2;
    @BindView(id = R.id.s3)
    private TextView s3;
    @BindView(id = R.id.s4)
    private TextView s4;
    @BindView(id = R.id.s5)
    private TextView s5;
    @BindView(id = R.id.s6)
    private TextView s6;
    @BindView(id = R.id.s7)
    private TextView s7;

    @BindView(id = R.id.a1)
    private TextView a1;
    @BindView(id = R.id.a2)
    private TextView a2;
    @BindView(id = R.id.a3)
    private TextView a3;
    @BindView(id = R.id.a4)
    private TextView a4;
    @BindView(id = R.id.a5)
    private TextView a5;
    @BindView(id = R.id.a6)
    private TextView a6;
    @BindView(id = R.id.a7)
    private TextView a7;

    @BindView(id = R.id.ul1)
    private TextView ul1;
    @BindView(id = R.id.ul2)
    private TextView ul2;
    @BindView(id = R.id.ul3)
    private TextView ul3;
    @BindView(id = R.id.ul4)
    private TextView ul4;
    @BindView(id = R.id.ul5)
    private TextView ul5;
    @BindView(id = R.id.ul6)
    private TextView ul6;
    @BindView(id = R.id.ul7)
    private TextView ul7;

    @BindView(id = R.id.aa1)
    private TextView aa1;
    @BindView(id = R.id.aa2)
    private TextView aa2;
    @BindView(id = R.id.aa3)
    private TextView aa3;
    @BindView(id = R.id.aa4)
    private TextView aa4;
    @BindView(id = R.id.aa5)
    private TextView aa5;
    @BindView(id = R.id.aa6)
    private TextView aa6;
    @BindView(id = R.id.aa7)
    private TextView aa7;

    @BindView(id = R.id.ulo1)
    private TextView ulo1;
    @BindView(id = R.id.ulo2)
    private TextView ulo2;
    @BindView(id = R.id.ulo3)
    private TextView ulo3;
    @BindView(id = R.id.ulo4)
    private TextView ulo4;
    @BindView(id = R.id.ulo5)
    private TextView ulo5;
    @BindView(id = R.id.ulo6)
    private TextView ulo6;
    @BindView(id = R.id.ulo7)
    private TextView ulo7;


    @BindView(id = R.id.channel)
    private TextView channel;
    @BindView(id = R.id.shock)
    private CheckBox shock;
    @BindView(id = R.id.tb_ybl)
    private TableLayout tb_ybl;
    @BindView(id = R.id.rl_fa)
    private RelativeLayout rl_fa;
    @BindView(id = R.id.x)
    private TextView tv_x;
    @BindView(id = R.id.y)
    private TextView tv_y;
    @BindView(id = R.id.z)
    private TextView tv_z;

    @BindView(id = R.id.initial_x)
    private TextView initial_x;
    @BindView(id = R.id.initial_y)
    private TextView initial_y;
    @BindView(id = R.id.initial_z)
    private TextView initial_z;
    @BindView(id = R.id.get_obliquity_initial_value, click = true)
    private Button get_obliquity_initial_value;

    private ArrayList<TextView> textViews;
    private boolean isDoubleBridge;
    private boolean isMultifunctional;

    @Override
    protected void setView() {
        String[] strings = (String[]) mData;
        setToolBar(strings[2], R.menu.calibration);
        isDoubleBridge = strings[2].contains("双桥");
        isMultifunctional = strings[2].contains("多功能");
        textViews = new ArrayList<>();
        //标准
        textViews.add(s1);
        textViews.add(s2);
        textViews.add(s3);
        textViews.add(s4);
        textViews.add(s5);
        textViews.add(s6);
        textViews.add(s7);
        //加荷1
        textViews.add(a1);
        textViews.add(a2);
        textViews.add(a3);
        textViews.add(a4);
        textViews.add(a5);
        textViews.add(a6);
        textViews.add(a7);
        //卸荷1
        textViews.add(ul7);
        textViews.add(ul6);
        textViews.add(ul5);
        textViews.add(ul4);
        textViews.add(ul3);
        textViews.add(ul2);
        textViews.add(ul1);
        //加荷2
        textViews.add(aa1);
        textViews.add(aa2);
        textViews.add(aa3);
        textViews.add(aa4);
        textViews.add(aa5);
        textViews.add(aa6);
        textViews.add(aa7);
        //卸荷2
        textViews.add(ulo7);
        textViews.add(ulo6);
        textViews.add(ulo5);
        textViews.add(ulo4);
        textViews.add(ulo3);
        textViews.add(ulo2);
        textViews.add(ulo1);


        shock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPresenter.enableShock(isChecked);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set:
                showSetDialog();
                return false;
            case R.id.reset:
                showResetDialog();
                return false;

            case R.id.link:
                String[] strings = (String[]) mData;
                mPresenter.linkDevice(strings[0]);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param which 0：侧壁通道 1：测斜通道
     */
    private void showSwitchDialog(int which) {
        switch (which) {
            case 0://侧壁通道
                Dialog alertDialog = new AlertDialog.Builder(SetCalibrationDataActivity.this)
                        .setTitle("变换采集通道")
                        .setMessage("即将为您变换到侧壁数据通道")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.switchingChannel(1);
                            }
                        }).setCancelable(false).create();
                alertDialog.show();
                break;
            case 1://测斜通道
                alertDialog = new AlertDialog.Builder(SetCalibrationDataActivity.this)
                        .setTitle("变换采集通道")
                        .setMessage("即将为您变换到测斜数据通道")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.switchingChannel(2);
                            }
                        }).setCancelable(false).create();
                alertDialog.show();
                break;
        }


    }

    private void showSetDialog() {
        Dialog alertDialog = new AlertDialog.Builder(SetCalibrationDataActivity.this)
                .setTitle("设置探头内存数据")
                .setMessage("确定要设置探头内存数据吗？请拔掉蓝牙连接器电源，重插，重连开关打到B再点“确定”")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.setDataToProbe();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    private int deleteWhich = 0;

    private void showResetDialog() {
        final String[] items = {"全部", "锥头", "侧壁", "无"};
        Dialog alertDialog = new AlertDialog.Builder(SetCalibrationDataActivity.this)
                .setTitle("请选择手机中要清除的数据")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteWhich = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.resetDataToProbe(deleteWhich);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteWhich = 0;
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    public int initView() {
        return R.layout.activity_set_calibration_data;
    }

    @Override
    protected void toRefresh() {//每次进入时去连接蓝牙
        super.toRefresh();
        String[] strings = (String[]) mData;
        mPresenter.linkDevice(strings[0]);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_record://记录
                mPresenter.doRecord();
                break;
            case R.id.get_obliquity_initial_value:
                String strX = (String) tv_x.getText();
                String strY = (String) tv_y.getText();
                String strZ = (String) tv_z.getText();
                initial_x.setText(strX);
                initial_y.setText(strY);

                int x = Integer.parseInt(strX);
                int y = Integer.parseInt(strY);
                int z = Integer.parseInt(strZ);
                z = z - 1000;//旧芯片为1333
//                if (z > 1000) {
//                    z = z - 1000;//旧芯片为1333
//                } else {
//                    z = z + 1000;
//                }
                initial_z.setText(String.valueOf(z));
                mPresenter.setObliquityInitialValue(x, y, z);
                break;
        }
    }


    @Override
    public SetCalibrationDataPresenter createdPresenter() {
        return new SetCalibrationDataPresenter();
    }

    @Override
    public void showNotLinkError() {

    }

    @Override
    public void showInitialValue(String value) {
        initial.setText(value);
    }

    @Override
    public void showEffectiveValue(String value) {
        valid.setText(value);
    }

    @Override
    public void showProbeParameters(String strNumber, String strSn, String strDifferential, String workArea) {
        number.setText(strNumber);
        sn.setText(strSn);
        area.setText(workArea);
        differential.setText(strDifferential);
    }


    private int index = 7;

    @Override
    public void showRecordValue(String value) {
        if (index < textViews.size()) {
            textViews.get(index).setText(value);
            index++;
            if (index == 35) {//一个通道数据采集完毕
                if (isDoubleBridge) {//双桥
                    if (channel.getText().equals("锥头")) {
                        showSwitchDialog(0);//变换到侧壁通道
                    } else if (isMultifunctional) {//双桥多功能
                        showSwitchDialog(1);//变换到测斜通道
                    }
                } else if (isMultifunctional) {//单桥多功能
                    showSwitchDialog(1);//变换到测斜通道
                }
            }
        } else {
            showToast("采集完毕，请点击设置写入数据");
        }
    }

    @Override
    public void showDifferential(int differential) {
        this.differential.setText(String.valueOf(differential));
        for (int i = 0; i < 7; i++) {
            textViews.get(i).setText(String.valueOf(differential * i));
        }
    }

    @Override
    public void resetView(String strChannel, List<MemoryDataModel> memoryDataModels) {
        tb_ybl.setVisibility(View.VISIBLE);
        rl_fa.setVisibility(View.GONE);
        channel.setText(strChannel);
        if (memoryDataModels == null) {
            for (TextView textView : textViews) {
                textView.setText("null");
            }
        } else {
            int halfSize = memoryDataModels.size() / 2;
            for (int i = 0; i < halfSize; i++) {
                int adValue = memoryDataModels.get(0).ADValue;
                textViews.get(i + 7).setText(String.valueOf(adValue));
                memoryDataModels.remove(0);
            }
            Collections.reverse(memoryDataModels);
            for (int i = 0; i < memoryDataModels.size(); i++) {
                int adValue = memoryDataModels.get(i).ADValue;
                textViews.get(i + memoryDataModels.size() + 7).setText(String.valueOf(adValue));
            }
        }
        index = 7;
    }

    @Override
    public void showFaChannel() {
        tb_ybl.setVisibility(View.GONE);
        rl_fa.setVisibility(View.VISIBLE);
        channel.setText("斜度");
    }

    @Override
    public void showFaChannelValue(int x, int y, int z) {
        tv_x.setText(String.valueOf(x));
        tv_y.setText(String.valueOf(y));
        tv_z.setText(String.valueOf(z));
        bt_record.setEnabled(false);
        bt_record.setBackgroundColor(getResources().getColor(R.color.rl_gray));
    }


}
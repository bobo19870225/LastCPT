/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.digital;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;

/**
 * Created by bobo on 2017/3/19.
 * 数字探头类型选择界面
 */

public class ProbeTypeActivity extends BaseActivity {
    @BindView(id = R.id.single_bridge, click = true)
    private RelativeLayout single_bridge;
    @BindView(id = R.id.single_multifunctional_bridge, click = true)
    private RelativeLayout single_multifunctional_bridge;
    @BindView(id = R.id.double_bridge, click = true)
    private RelativeLayout double_bridge;
    @BindView(id = R.id.double_multifunctional_bridge, click = true)
    private RelativeLayout double_multifunctional_bridge;
    @BindView(id = R.id.cross, click = true)
    private RelativeLayout cross;
    @BindView(id = R.id.tv_single_bridge)
    private TextView tv_single_bridge;
    @BindView(id = R.id.tv_single_multifunctional_bridge)
    private TextView tv_single_multifunctional_bridge;
    @BindView(id = R.id.tv_double_bridge)
    private TextView tv_double_bridge;
    @BindView(id = R.id.tv_double_multifunctional_bridge)
    private TextView tv_double_multifunctional_bridge;
    @BindView(id = R.id.tv_cross)
    private TextView tv_cross;

    @Override
    protected void setView() {
        setTitle("选择探头类型");
        if (mData.equals("设置探头内存数据")) {
            tv_single_bridge.setText("单桥探头设定");
            tv_single_multifunctional_bridge.setText("单桥多功能探头设定");
            tv_double_bridge.setText("双桥探头设定");
            tv_double_multifunctional_bridge.setText("双桥多功能探头设定");
            tv_cross.setText("十字板探头设定");
        }
    }

    @Override
    public int initView() {
        return R.layout.activity_probe_type;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single_bridge:
                if (mData.equals("设置探头内存数据")) {
                    goTo(CalibrationParameterActivity.class, new String[]{(String) mData, "单桥探头设定"});
                } else {
                    goTo(ChooseCalibrationTypeActivity.class, new String[]{(String) mData, "单桥探头标定"});
                }
                break;
            case R.id.single_multifunctional_bridge:
                if (mData.equals("设置探头内存数据")) {
                    goTo(CalibrationParameterActivity.class, new String[]{(String) mData, "单桥多功能探头设定"});
                } else {
                    goTo(ChooseCalibrationTypeActivity.class, new String[]{(String) mData, "单桥多功能探头标定"});
                }

                break;
            case R.id.double_bridge:
                if (mData.equals("设置探头内存数据")) {
                    goTo(CalibrationParameterActivity.class, new String[]{(String) mData, "双桥设定"});
                } else {
                    goTo(ChooseCalibrationTypeActivity.class, new String[]{(String) mData, "双桥标定"});
                }
                break;
            case R.id.double_multifunctional_bridge:
                if (mData.equals("设置探头内存数据")) {
                    goTo(CalibrationParameterActivity.class, new String[]{(String) mData, "双桥多功能探头设定"});
                } else {
                    goTo(ChooseCalibrationTypeActivity.class, new String[]{(String) mData, "双桥多功能探头标定"});
                }
                break;
            case R.id.cross:
                if (mData.equals("设置探头内存数据")) {
                    goTo(CalibrationParameterActivity.class, new String[]{(String) mData, "十字板探头设定"});
                } else {
                    goTo(CalibrationParameterActivity.class, new String[]{(String) mData, "十字板探头标定"});
                }
                break;
        }
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import android.widget.TextView;

import www.jingkan.com.R;
import www.jingkan.com.view.base.BaseActivity;

/**
 * Created by bobo on 2017/3/19.
 * 数字探头类型选择界面
 */

public class ChooseProbeTypeActivity extends BaseActivity {


    @Override
    protected void setView() {
        setTitle("选择探头类型");
        if (mData.equals("设置探头内存数据")) {
            ((TextView) findViewById(R.id.tv_single_bridge)).setText("单桥探头设定");
            ((TextView) findViewById(R.id.tv_single_multifunctional_bridge)).setText("单桥多功能探头设定");
            ((TextView) findViewById(R.id.tv_double_bridge)).setText("双桥探头设定");
            ((TextView) findViewById(R.id.tv_double_multifunctional_bridge)).setText("双桥多功能探头设定");
            ((TextView) findViewById(R.id.tv_cross)).setText("十字板探头设定");
        }
        findViewById(R.id.single_bridge).setOnClickListener(v -> {
            skip("单桥探头设定", "单桥探头标定");
        });


        findViewById(R.id.single_multifunctional_bridge).setOnClickListener(v -> {
            skip("单桥多功能探头设定", "单桥多功能探头标定");
        });


        findViewById(R.id.double_bridge).setOnClickListener(v -> {
            skip("双桥设定", "双桥标定");
        });


        findViewById(R.id.double_multifunctional_bridge).setOnClickListener(v -> {
            skip("双桥多功能探头设定", "双桥多功能探头标定");
        });


        findViewById(R.id.cross).setOnClickListener(v -> {
            if (mData.equals("设置探头内存数据")) {
                goTo(CalibrationParameterActivity.class, new String[]{(String) mData, "十字板探头设定"});
            } else {
                goTo(CalibrationParameterActivity.class, new String[]{(String) mData, "十字板探头标定"});
            }
        });


    }

    private void skip(String s1, String s2) {
        if (mData.equals("设置探头内存数据")) {
            goTo(CalibrationParameterActivity.class, new String[]{(String) mData, s1});
        } else {
            goTo(ChooseCalibrationTypeActivity.class, new String[]{(String) mData, s2});
        }
    }

    @Override
    public int initView() {
        return R.layout.activity_choose_probe_type;
    }


}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import android.view.View;

import www.jingkan.com.R;
import www.jingkan.com.view.base.BaseActivity;

/**
 * Created by lushengbo on 2017/11/3.
 * 选择验证类型界面
 */

public class ChooseCalibrationTypeActivity extends BaseActivity {

    private String[] strings;
    private boolean isDouble;

    @Override
    protected void setView() {
        strings = (String[]) mData;
        setToolBar(strings[1]);
        View rlQc = findViewById(R.id.rl_qc);
        View rlFs = findViewById(R.id.rl_fs);
        View rlFa = findViewById(R.id.rl_fa);
        if (strings[1].contains("双桥")) {
            isDouble = true;
            rlFs.setVisibility(View.VISIBLE);
        } else {
            isDouble = false;
            rlFs.setVisibility(View.GONE);
        }
        if (strings[1].contains("多功能")) {
            rlFa.setVisibility(View.VISIBLE);
        } else {
            rlFa.setVisibility(View.GONE);
        }
        rlQc.setOnClickListener(v -> {
            if (isDouble) {
                strings[1] = "双桥锥头标定";
            } else {
                strings[1] = "单桥锥头标定";
            }

            goTo(CalibrationParameterActivity.class, strings);
        });

        rlFs.setOnClickListener(v -> {
            strings[1] = "双桥侧壁标定";
            goTo(CalibrationParameterActivity.class, strings);
        });
        rlFa.setOnClickListener(v -> {
            strings[1] = "斜度标定";
            goTo(CalibrationParameterActivity.class, strings);
        });
    }

    @Override
    public int initView() {
        return R.layout.activity_choose_calibration_type;
    }


}

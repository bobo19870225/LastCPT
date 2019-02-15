/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.view.base.BaseActivity;

/**
 * Created by lushengbo on 2017/11/3.
 * 选择验证类型界面
 */

public class ChooseCalibrationTypeActivity extends BaseActivity {

    private String[] strings;

    @Override
    protected void setView() {
        strings = (String[]) mData;
        setToolBar(strings[1]);
        findViewById(R.id.rl_qc).setOnClickListener(v -> {
            strings[1] = "双桥锥头标定";
            goTo(CalibrationParameterActivity.class, strings);
        });


        findViewById(R.id.rl_fs).setOnClickListener(v -> {
            strings[1] = "双桥侧壁标定";
            goTo(CalibrationParameterActivity.class, strings);
        });

    }

    @Override
    public int initView() {
        return R.layout.activity_choose_calibration_type;
    }


}

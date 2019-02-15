/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.view.base.BaseActivity;

/**
 * Created by bobo on 2017/3/19.
 * 数字探头标定
 */

public class DigitalCalibrationActivity extends BaseActivity {


    @Override
    protected void setView() {
        setTitle("数字探头标定");
        findViewById(R.id.set_data).setOnClickListener(v -> goTo(ChooseProbeTypeActivity.class, "设置探头内存数据"));
        findViewById(R.id.start).setOnClickListener(v -> goTo(ChooseProbeTypeActivity.class, "开始标定"));
    }

    @Override
    public int initView() {
        return R.layout.activity_digital_calibration;
    }

}

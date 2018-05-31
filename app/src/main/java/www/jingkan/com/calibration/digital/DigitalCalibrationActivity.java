/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.digital;

import android.view.View;
import android.widget.Button;

import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;

/**
 * Created by bobo on 2017/3/19.
 * 数字探头标定
 */

public class DigitalCalibrationActivity extends BaseActivity {
    @BindView(id = www.jingkan.com.R.id.set_data, click = true)
    private Button set_data;
    @BindView(id = www.jingkan.com.R.id.start, click = true)
    private Button start;


    @Override
    protected void setView() {
        setTitle("数字探头标定");
    }

    @Override
    public int initView() {
        return www.jingkan.com.R.layout.activity_digital_calibration;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case www.jingkan.com.R.id.set_data:
                goTo(ProbeTypeActivity.class, "设置探头内存数据");
                break;
            case www.jingkan.com.R.id.start:
                goTo(ProbeTypeActivity.class, "开始标定");
                break;
        }
    }
}

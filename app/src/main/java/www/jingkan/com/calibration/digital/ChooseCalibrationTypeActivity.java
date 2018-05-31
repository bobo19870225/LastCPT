/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.digital;

import android.view.View;
import android.widget.RelativeLayout;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;

/**
 * Created by lushengbo on 2017/11/3.
 * 选择验证类型界面
 */

public class ChooseCalibrationTypeActivity extends BaseActivity {
    @BindView(id = R.id.rl_qc, click = true)
    private RelativeLayout rl_qc;
    @BindView(id = R.id.rl_fs, click = true)
    private RelativeLayout rl_fs;
    private String[] strings;

    @Override
    protected void setView() {
        strings = (String[]) mData;
        setToolBar(strings[1]);
    }

    @Override
    public int initView() {
        return R.layout.activity_choose_calibration_type;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_qc:
                strings[1] = "双桥锥头标定";
                goTo(CalibrationParameterActivity.class, strings);
                break;
            case R.id.rl_fs:
                strings[1] = "双桥侧壁标定";
                goTo(CalibrationParameterActivity.class, strings);
                break;
        }
    }
}

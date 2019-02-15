/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import android.view.View;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.view.base.BaseActivity;
import com.jinkan.www.cpttest.view.main.CalibrationParameterActivity;

public class AnalogActivity extends BaseActivity {


    @Override
    protected void setView() {
        setTitle("模拟仪器标定");
        findViewById(R.id.rl_qc).setOnClickListener(v -> goTo(CalibrationParameterActivity.class, new String[]{"模拟标定", "锥头标定"}));
        findViewById(R.id.rl_fs).setOnClickListener(v -> goTo(CalibrationParameterActivity.class, new String[]{"模拟标定", "侧壁标定"}));
        findViewById(R.id.rl_cu).setOnClickListener(v -> {
//            goTo(CalibrationParameterActivity.class, new String[]{"模拟标定", "锥头标定"});
        });

    }

    @Override
    public int initView() {
        return R.layout.activity_analog;
    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_qc:
                break;
            case R.id.rl_fs:
                break;
            case R.id.rl_cu:

                break;


            default:
                break;
        }
    }
}

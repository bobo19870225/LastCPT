package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.view.base.BaseActivity;

/**
 * Created by Sampson on 2019/1/31.
 * CPTTest
 */
public class AnalogCalibrationActivity extends BaseActivity {
    @Override
    protected void setView() {
        setTitle("模拟仪器标定");
        findViewById(R.id.rl_zhuitou).setOnClickListener(v -> goTo(CalibrationParameterActivity.class, new String[]{"模拟标定", "锥头标定"}));

        findViewById(R.id.rl_cebi).setOnClickListener(v -> goTo(CalibrationParameterActivity.class, new String[]{"模拟标定", "侧壁标定"}));

    }

    @Override
    public int initView() {
        return R.layout.activity_analog_calibration;
    }
}

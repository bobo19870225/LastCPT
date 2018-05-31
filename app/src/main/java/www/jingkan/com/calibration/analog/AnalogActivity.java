/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.analog;

import android.view.View;
import android.widget.RelativeLayout;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.calibration.digital.CalibrationParameterActivity;

public class AnalogActivity extends BaseActivity {

    @BindView(id = R.id.rl_zhuitou, click = true)
    private RelativeLayout rl_zhuitou;
    @BindView(id = R.id.rl_cebi, click = true)
    private RelativeLayout rl_cebi;
    @BindView(id = R.id.rl_szb, click = true)
    private RelativeLayout rl_szb;


    @Override
    protected void setView() {
        setTitle("模拟仪器标定");
    }

    @Override
    public int initView() {
        return R.layout.activity_moni;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_zhuitou:
                goTo(CalibrationParameterActivity.class, new String[]{"模拟标定", "锥头标定"});
                break;
            case R.id.rl_cebi:
                goTo(CalibrationParameterActivity.class, new String[]{"模拟标定", "侧壁标定"});
                break;
            case R.id.rl_szb:

                break;


            default:
                break;
        }
    }
}

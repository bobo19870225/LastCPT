/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import www.jingkan.com.R;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.linkBluetooth.LinkBluetoothActivity;
import www.jingkan.com.calibration.analog.AnalogActivity;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseFragment;
import www.jingkan.com.calibration.digital.DigitalCalibrationActivity;
import www.jingkan.com.calibration.probeTest.TestingActivity;

import java.util.Map;


public class InstrumentCalibrationFragment extends BaseFragment {
    @BindView(id = R.id.analog, click = true)
    private RelativeLayout analog;
    @BindView(id = R.id.digital, click = true)
    private RelativeLayout digital;
    @BindView(id = R.id.testing, click = true)
    private RelativeLayout testing;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_calibration, container, false);
    }

    @Override
    protected void setView() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.analog:
                goTo(AnalogActivity.class, null);
                break;
            case R.id.digital:
                goTo(DigitalCalibrationActivity.class, null);
                break;
            case R.id.testing:
                PreferencesUtils preferencesUtils = new PreferencesUtils(getContext());
                Map<String, String> linkerPreferences = preferencesUtils.getLinkerPreferences();
                String add = linkerPreferences.get("add");
                if (StringUtils.isEmpty(add)) {
                    goTo(LinkBluetoothActivity.class, "探头检测");
                } else {
                    goTo(TestingActivity.class, add);
                }

                break;
            default:
                break;
        }
    }

}

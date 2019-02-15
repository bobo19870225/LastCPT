/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.FragmentCalibrationBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.PreferencesUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.view.base.BaseMVVMDaggerFragment;
import www.jingkan.com.view_model.InstrumentCalibrationFragmentVM;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;


public class InstrumentCalibrationFragment extends BaseMVVMDaggerFragment<InstrumentCalibrationFragmentVM, FragmentCalibrationBinding> {
    @Inject
    PreferencesUtil preferencesUtil;

    @Inject
    public InstrumentCalibrationFragment() {

    }

    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected int setLayOutId() {
        return R.layout.fragment_calibration;
    }

    @Override
    protected void setView() {

    }


    @Override
    public InstrumentCalibrationFragmentVM createdViewModel() {
        return ViewModelProviders.of(this).get(InstrumentCalibrationFragmentVM.class);
    }

    @Override
    public void action(CallbackMessage callbackMessage) {
        switch (callbackMessage.what) {

            case 2:

                Map<String, String> linkerPreferences = preferencesUtil.getLinkerPreferences();
                String add = linkerPreferences.get("add");
                if (StringUtil.isEmpty(add)) {
                    HashMap<String, String> stringHashMap = new HashMap<>();
                    stringHashMap.put("action", "数字探头检测");
                    goTo(LinkBluetoothActivity.class, stringHashMap);
                } else {
                    goTo(TestingActivity.class, add);
                }

                break;
        }
    }
}

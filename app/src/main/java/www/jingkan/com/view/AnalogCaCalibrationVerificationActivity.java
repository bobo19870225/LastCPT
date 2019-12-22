package www.jingkan.com.view;

import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityAnalogCalibrationVerificationBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.AnalogCaCalibrationVerificationVM;
import www.jingkan.com.view_model.ViewModelFactory;

/**
 * Created by Sampson on 2019-12-18.
 * LastCPT 2
 */
public class AnalogCaCalibrationVerificationActivity extends BaseMVVMDaggerActivity<AnalogCaCalibrationVerificationVM, ActivityAnalogCalibrationVerificationBinding> {
    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData};
    }

    @Override
    protected void setMVVMView() {

    }

    @Override
    public int initView() {
        return R.layout.activity_analog_calibration_verification;
    }

    @Override
    public AnalogCaCalibrationVerificationVM createdViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(AnalogCaCalibrationVerificationVM.class);
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

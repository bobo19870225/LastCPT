package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityCalibrationVerificationBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.CalibrationVerificationVM;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class CalibrationVerificationActivity extends BaseMVVMDaggerActivity<CalibrationVerificationVM, ActivityCalibrationVerificationBinding> {
    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {

    }

    @Override
    public CalibrationVerificationVM createdViewModel() {
        return null;
    }

    @Override
    public int initView() {
        return R.layout.activity_calibration_verification;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

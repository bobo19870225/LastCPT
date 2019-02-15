package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityCalibrationVerificationBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.CalibrationVerificationVM;

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
    public void callback(CallbackMessage callbackMessage) {

    }
}

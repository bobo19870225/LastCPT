package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivitySetCalibrationDataBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.SetCalibrationDataVM;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class SetCalibrationDataActivity extends BaseMVVMDaggerActivity<SetCalibrationDataVM, ActivitySetCalibrationDataBinding> {
    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {

    }

    @Override
    public SetCalibrationDataVM createdViewModel() {
        return null;
    }

    @Override
    public int initView() {
        return R.layout.activity_set_calibration_data;
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

    }
}

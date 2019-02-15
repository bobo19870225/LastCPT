package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivitySetCalibrationDataBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.SetCalibrationDataVM;

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
    public void action(CallbackMessage callbackMessage) {

    }
}

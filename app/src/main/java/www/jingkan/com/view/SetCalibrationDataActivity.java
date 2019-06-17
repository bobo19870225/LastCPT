package www.jingkan.com.view;

import androidx.lifecycle.ViewModelProviders;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

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
        mViewModel.action.observe(this, s -> {
            if (s.equals("outPut")) {
                double[] doubles = testLeastSquareMethodFromApache();
                toast("ok");
            }
        });
    }

    @Override
    public SetCalibrationDataVM createdViewModel() {
        return ViewModelProviders.of(this).get(SetCalibrationDataVM.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_set_calibration_data;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }

    private double[] testLeastSquareMethodFromApache() {
        WeightedObservedPoints obs = new WeightedObservedPoints();
        obs.add(-3, -1);
        obs.add(-2, 0);
        obs.add(-1, 1);
        obs.add(0, 2.3);
        obs.add(1, 3);
        obs.add(2, 4);
        obs.add(3, 5.5);

        // Instantiate a third-degree polynomial fitter.
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(3);
        // Retrieve fitted parameters (coefficients of the polynomial function).
        return fitter.fit(obs.toList());
    }


}

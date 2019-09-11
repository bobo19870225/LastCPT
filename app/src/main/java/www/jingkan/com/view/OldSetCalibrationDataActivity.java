package www.jingkan.com.view;

import androidx.lifecycle.ViewModelProviders;

import www.jingkan.com.view_model.OldSetCalibrationDataVM;
import www.jingkan.com.view_model.SetCalibrationDataVM;

/**
 * Created by Sampson on 2019-09-06.
 * LastCPT 2
 */
public class OldSetCalibrationDataActivity extends SetCalibrationDataActivity {
    @Override
    public SetCalibrationDataVM createdViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(OldSetCalibrationDataVM.class);
    }
}

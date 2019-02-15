package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.view_model.base.BaseViewModel;

import androidx.annotation.NonNull;

/**
 * Created by Sampson on 2019/1/15.
 * CPTTest
 */
public class InstrumentCalibrationFragmentVM extends BaseViewModel {

    public InstrumentCalibrationFragmentVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {

    }

    public void analogCalibration() {
        callbackMessage.setValue(0);
        getView().callback(callbackMessage);
    }

    public void digitalCalibration() {
        callbackMessage.setValue(1);
        getView().callback(callbackMessage);
    }

    public void test() {
        callbackMessage.setValue(2);
        getView().callback(callbackMessage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

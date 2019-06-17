package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;

import www.jingkan.com.util.SingleLiveEvent;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 * {@link www.jingkan.com.view.SetCalibrationDataActivity}
 */
public class SetCalibrationDataVM extends BaseViewModel {
    public final SingleLiveEvent<String> action = new SingleLiveEvent<>();
    public SetCalibrationDataVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        action.setValue("outPut");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

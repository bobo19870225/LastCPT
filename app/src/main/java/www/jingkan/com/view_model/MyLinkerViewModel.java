package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.view_model.base.BaseViewModel;

import androidx.annotation.NonNull;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MyLinkerViewModel extends BaseViewModel {

    public MyLinkerViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    public void setLinker() {
        callbackMessage.setValue(0);
        getView().callback(callbackMessage);
//        action.setValue("setLinker");
    }

    public void setAnalogLinker() {
        callbackMessage.setValue(1);
        getView().callback(callbackMessage);
//        action.setValue("setAnalogLinker");
    }

}

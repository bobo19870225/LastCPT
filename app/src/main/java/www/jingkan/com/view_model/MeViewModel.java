package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import www.jingkan.com.view_model.base.BaseViewModel;

import androidx.annotation.NonNull;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MeViewModel extends BaseViewModel {

    public MeViewModel(@NonNull Application application) {
        super(application);
    }

    public void setLinker() {
        callbackMessage.setValue(0);
        getView().action(callbackMessage);
//        action.setValue("MyLinkerActivity");
    }

    public void setEmail() {
        callbackMessage.setValue(1);
        getView().action(callbackMessage);
//        action.setValue("SetEmailActivity");
    }

    public void setVideo() {
        callbackMessage.setValue(2);
        getView().action(callbackMessage);
//        action.setValue("VideoActivity");
    }

    public void checkVersion() {
        callbackMessage.setValue(3);
        getView().action(callbackMessage);
//        action.setValue("VersionInfoActivity");
    }

    public void myMsg() {
        callbackMessage.setValue(4);
        getView().action(callbackMessage);
//        action.setValue("MyMsgActivity");
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
}

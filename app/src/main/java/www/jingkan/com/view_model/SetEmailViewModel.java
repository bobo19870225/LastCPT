package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import www.jingkan.com.view_model.base.BaseViewModel;

import androidx.annotation.NonNull;

/**
 * Created by Sampson on 2018/12/19.
 * CPTTest
 */
public class SetEmailViewModel extends BaseViewModel {
    public SetEmailViewModel(@NonNull Application application) {
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
}

package www.jingkan.com.view_model.main;

import android.app.Application;
import android.content.Intent;

import www.jingkan.com.view_model.base.BaseViewModel;

import androidx.annotation.NonNull;

/**
 * Created by Sampson on 2018/12/16.
 * CPTTest
 */

public class MainViewModel extends BaseViewModel {


    public MainViewModel(@NonNull Application application) {
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

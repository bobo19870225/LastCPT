package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import www.jingkan.com.view_model.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class CrossTestViewModel extends BaseViewModel {

    public final MutableLiveData<String> strProjectNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strHoleNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strCuCoefficient = new MutableLiveData<>();
    public final MutableLiveData<String> strCuLimit = new MutableLiveData<>();
    public final MutableLiveData<String> strCuInitial = new MutableLiveData<>();
    public final MutableLiveData<String> strCuEffective = new MutableLiveData<>();
    public final MutableLiveData<String> deg = new MutableLiveData<>();
    public final MutableLiveData<Integer> intTestNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strDeep = new MutableLiveData<>();
    public final MutableLiveData<String> strSoilType = new MutableLiveData<>();
    public final MutableLiveData<Boolean> linked = new MutableLiveData<>();
    public final MutableLiveData<Boolean> start = new MutableLiveData<>();


    public CrossTestViewModel(@NonNull Application application) {
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

    public void modify() {
    }

    public void doInitialValue() {

    }

    public void doStart() {
    }

    public void end() {
    }
}

package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.view_model.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class CrossTestDataDetailsMV extends BaseViewModel {

    public final MutableLiveData<String> obsProjectNumber = new MutableLiveData<>();

    public final MutableLiveData<String> obsHoleNumber = new MutableLiveData<>();

    public final MutableLiveData<String> obsTestDate = new MutableLiveData<>();


    public CrossTestDataDetailsMV(@NonNull Application application) {
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

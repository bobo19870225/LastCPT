package www.jingkan.com.view_model.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Sampson on 2018/12/24.
 * CPTTest
 */
public abstract class BaseListViewModel<T> extends BaseViewModel {

    public final MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();

    public BaseListViewModel(@NonNull Application application) {
        super(application);
    }

    public abstract LiveData<T> loadListViewData();

    public abstract void afterLoadListViewData();
}

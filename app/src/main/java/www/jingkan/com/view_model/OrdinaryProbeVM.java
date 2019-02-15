package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.db.dao.ProbeDao;
import com.jinkan.www.cpttest.db.entity.ProbeEntity;
import com.jinkan.www.cpttest.view_model.base.BaseListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class OrdinaryProbeVM extends BaseListViewModel<List<ProbeEntity>> {

    //    public final MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();
    private ProbeDao probeDao;

    public OrdinaryProbeVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<ProbeEntity>> loadListViewData() {
        return probeDao.getAllProbe();
    }

    @Override
    public void afterLoadListViewData() {

    }


    @Override
    public void inject(Object... objects) {
        probeDao = (ProbeDao) objects[1];
    }

    public void addProbe() {
        callbackMessage.setValue(0);
        getView().callback(callbackMessage);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

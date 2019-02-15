package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.db.dao.WirelessTestDao;
import com.jinkan.www.cpttest.db.entity.WirelessTestEntity;
import com.jinkan.www.cpttest.view_model.base.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class WirelessTestFragmentViewModel extends BaseViewModel {

    public final MediatorLiveData<List<WirelessTestEntity>> listMediatorLiveData = new MediatorLiveData<>();

    private WirelessTestDao wirelessTestDao;

    public WirelessTestFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        wirelessTestDao = (WirelessTestDao) objects[1];
    }

    public void newTest() {
        callbackMessage.setValue(0);
        getView().callback(callbackMessage);
    }

    public void reTest() {
        listMediatorLiveData.addSource(wirelessTestDao.getAllWirelessTestEntity(), listMediatorLiveData::setValue);
    }

    public void markupFile() {
        callbackMessage.setValue(1);
        getView().callback(callbackMessage);
    }

    public void wirelessProbe() {
        callbackMessage.setValue(2);
        getView().callback(callbackMessage);

    }

    public void dataSync() {
        callbackMessage.setValue(3);
        getView().callback(callbackMessage);
    }

    public void testData() {
        callbackMessage.setValue(4);
        getView().callback(callbackMessage);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.db.dao.WirelessProbeDao;
import com.jinkan.www.cpttest.db.entity.WirelessProbeEntity;
import com.jinkan.www.cpttest.view_model.base.BaseListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by Sampson on 2019/1/2.
 * CPTTest
 */
public class WirelessProbeVM extends BaseListViewModel<List<WirelessProbeEntity>> {

    private WirelessProbeDao wirelessProbeDao;

    public WirelessProbeVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<WirelessProbeEntity>> loadListViewData() {
        return wirelessProbeDao.getAllWirelessProbeEntity();
    }

    @Override
    public void afterLoadListViewData() {

    }

    @Override
    public void inject(Object... objects) {
        wirelessProbeDao = (WirelessProbeDao) objects[1];
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

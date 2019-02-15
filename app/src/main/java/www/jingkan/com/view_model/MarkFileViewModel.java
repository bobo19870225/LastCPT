package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.db.dao.WirelessTestDao;
import com.jinkan.www.cpttest.db.entity.WirelessTestEntity;
import com.jinkan.www.cpttest.view_model.base.BaseListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MarkFileViewModel extends BaseListViewModel<List<WirelessTestEntity>> {
    private WirelessTestDao wirelessTestDao;

    public MarkFileViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<WirelessTestEntity>> loadListViewData() {
        return wirelessTestDao.getAllWirelessTestEntity();
    }

    @Override
    public void afterLoadListViewData() {

    }

    @Override
    public void inject(Object... objects) {
        wirelessTestDao = (WirelessTestDao) objects[1];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

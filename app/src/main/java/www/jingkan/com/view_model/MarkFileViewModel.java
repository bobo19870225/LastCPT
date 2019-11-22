package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.dao.dao_factory.WirelessTestDaoHelper;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.view.adapter.ItemMarkupFile;
import www.jingkan.com.view_model.base.BaseListViewModel;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MarkFileViewModel extends BaseListViewModel<List<WirelessTestEntity>> {
    private WirelessTestDao wirelessTestDao;
    private WirelessTestDaoHelper wirelessTestDaoHelper;
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
        wirelessTestDaoHelper = (WirelessTestDaoHelper) objects[2];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    public void deleteDBData(ItemMarkupFile itemMarkupFile) {
        wirelessTestDaoHelper.deleteData(new String[]{itemMarkupFile.getProjectNumber(), itemMarkupFile.getHoleNumber()},
                () -> action.setValue("刷新"));
    }
}

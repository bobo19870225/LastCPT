package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.db.dao.TestDao;
import com.jinkan.www.cpttest.db.dao.TestDaoHelper;
import com.jinkan.www.cpttest.db.entity.TestEntity;
import com.jinkan.www.cpttest.view.adapter.ItemHistoryData;
import com.jinkan.www.cpttest.view_model.base.BaseListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
public class HistoryDataViewModel extends BaseListViewModel<List<TestEntity>> {
    //    public final MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();
    private TestDao testDao;
    private TestDaoHelper testDaoHelper;

    public HistoryDataViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        testDao = (TestDao) objects[1];
        testDaoHelper = (TestDaoHelper) objects[2];
    }

    @Override
    public LiveData<List<TestEntity>> loadListViewData() {
        return testDao.getAllTestes();
    }

    @Override
    public void afterLoadListViewData() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }


    public void deleteOneHistoryData(ItemHistoryData itemHistoryData) {
        testDaoHelper.deleteData(
                new String[]{itemHistoryData.getProjectNumber(), itemHistoryData.getHoleNumber()},
                this::loadListViewData);
    }
}

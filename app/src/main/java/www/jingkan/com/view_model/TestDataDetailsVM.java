package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import www.jingkan.com.db.dao.TestDataDao;
import www.jingkan.com.db.entity.TestDataEntity;
import www.jingkan.com.view_model.base.BaseListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class TestDataDetailsVM extends BaseListViewModel<List<TestDataEntity>> {


    private String testId;
    private TestDataDao testDataDao;

    public TestDataDetailsVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<TestDataEntity>> loadListViewData() {
        return testDataDao.getTestDataByTestId(testId);
    }

    @Override
    public void afterLoadListViewData() {

    }


    @Override
    public void inject(Object... objects) {
        testId = (String) objects[0];
        testDataDao = (TestDataDao) objects[1];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

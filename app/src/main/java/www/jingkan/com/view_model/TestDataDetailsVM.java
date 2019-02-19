package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.dao.TestDataDao;
import www.jingkan.com.db.entity.TestDataEntity;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.util.DataUtil;
import www.jingkan.com.view_model.base.BaseListViewModel;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class TestDataDetailsVM extends BaseListViewModel<List<TestDataEntity>> {
    public final MediatorLiveData<List<TestEntity>> ldTestEntities = new MediatorLiveData<>();
//    public final MediatorLiveData<List<TestDataEntity>> ldTestDataEntities = new MediatorLiveData<>();


    public final MutableLiveData<String> ldProjectNumber = new MutableLiveData<>();
    public final MutableLiveData<String> ldHoleNumber = new MutableLiveData<>();
    public final MutableLiveData<String> ldTestDate = new MutableLiveData<>();

    private String testId;
    private TestDataDao testDataDao;
    private DataUtil dataUtil;
    private TestDao testData;

    public TestDataDetailsVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<TestDataEntity>> loadListViewData() {
        return testDataDao.getTestDataByTestDataId(testId);
    }

    @Override
    public void afterLoadListViewData() {

    }


    @Override
    public void inject(Object... objects) {
        testId = (String) objects[0];
        String[] split = testId.split("_");
        getTest(split[0], split[1]);
        testDataDao = (TestDataDao) objects[1];
        dataUtil = (DataUtil) objects[2];
        testData = (TestDao) objects[3];
    }

    private void getTest(String projectNumber, String holeNumber) {
        ldTestEntities.addSource(testData.getTestEntityByPrjNumberAndHoleNumber(projectNumber, holeNumber), ldTestEntities::setValue);
    }


    public void saveTestDataToSD(List testDataModels, String fileType, Object testModel, final ISkip iSkip) {
        if (testDataModels != null) {
            dataUtil.saveDataToSd(
                    testDataModels,
                    fileType,
                    testModel,
                    iSkip);
        } else {
            toast("读取数据失败！");
        }
    }

    public void emailTestData(List testDataModels, String fileType, Object testModel, final ISkip iSkip) {
        dataUtil.emailData(testDataModels, fileType, testModel, iSkip);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

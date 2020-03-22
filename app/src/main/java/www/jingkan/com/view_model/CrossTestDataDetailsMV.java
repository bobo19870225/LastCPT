package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import www.jingkan.com.db.dao.CrossTestDataDao;
import www.jingkan.com.db.dao.ProbeDao;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.entity.CrossTestDataEntity;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.util.DataUtil;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 * {@link www.jingkan.com.view.CrossTestDataDetailsActivity}
 */
public class CrossTestDataDetailsMV extends BaseViewModel {

    public final MutableLiveData<String> obsProjectNumber = new MutableLiveData<>();

    public final MutableLiveData<String> obsHoleNumber = new MutableLiveData<>();

    public final MutableLiveData<String> obsTestDate = new MutableLiveData<>();
    public final MediatorLiveData<List<CrossTestDataEntity>> ldTestData = new MediatorLiveData<>();
    private TestDao testDao;
    private CrossTestDataDao crossTestDataDao;
    private List<CrossTestDataEntity> crossTestDataEntityList;
    private DataUtil dataUtil;
    private TestEntity testEntity;
    private ISkip iSkip;

    CrossTestDataDetailsMV(@NonNull Application application, TestDao testDao, CrossTestDataDao crossTestDataDao, DataUtil dataUtil) {
        super(application);
        this.testDao = testDao;
        this.crossTestDataDao = crossTestDataDao;
        this.dataUtil = dataUtil;
    }

    @Override
    public void inject(Object... objects) {
        String id = (String) objects[0];
        iSkip = (ISkip) objects[1];
        testDao.getTestEntityByTestId(id).observe(lifecycleOwner, testEntities -> {
            if (null != testEntities && testEntities.size() > 0) {
                testEntity = testEntities.get(0);
                obsProjectNumber.setValue(testEntity.projectNumber);
                obsHoleNumber.setValue(testEntity.holeNumber);
                obsTestDate.setValue(testEntity.testDate);
                ldTestData.addSource(crossTestDataDao.getCrossTestDataByTestDataId(testEntity.testDataID), ldTestData::setValue);

            }

        });

    }

    public void saveTestDataToSD() {
        if (null != testEntity)
            crossTestDataDao.getCrossTestDataByTestDataId(testEntity.projectNumber + "_" + testEntity.holeNumber).observe(lifecycleOwner, crossTestDataEntities -> {
                if (null != crossTestDataEntities && crossTestDataEntities.size() != 0) {
                    crossTestDataEntityList = crossTestDataEntities;
                    dataUtil.saveDataToSd(crossTestDataEntities, testEntity, iSkip);
                } else {
                    toast("读取数据失败！");
                }
            });

    }

    public void emailTestData() {
        dataUtil.emailData(crossTestDataEntityList, testEntity, iSkip);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

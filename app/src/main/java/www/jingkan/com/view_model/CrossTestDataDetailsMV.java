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

    private ProbeDao probeDao;
    private DataUtil dataUtil;

    CrossTestDataDetailsMV(@NonNull Application application, TestDao testDao, CrossTestDataDao crossTestDataDao, ProbeDao probeDao, DataUtil dataUtil) {
        super(application);
        this.testDao = testDao;
        this.crossTestDataDao = crossTestDataDao;
        this.probeDao = probeDao;
        this.dataUtil = dataUtil;
    }

    @Override
    public void inject(Object... objects) {
        String id = (String) objects[0];
        testDao.getTestEntityByTestId(id).observe(lifecycleOwner, testEntities -> {
            if (null != testEntities && testEntities.size() > 0) {
                TestEntity testEntity = testEntities.get(0);
                obsProjectNumber.setValue(testEntity.projectNumber);
                obsHoleNumber.setValue(testEntity.holeNumber);
                obsTestDate.setValue(testEntity.testDate);
                ldTestData.addSource(crossTestDataDao.getCrossTestDataByTestDataId(testEntity.testDataID), ldTestData::setValue);

            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

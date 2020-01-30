package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.dao.TestDataDao;
import www.jingkan.com.db.dao.WirelessResultDataDao;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.entity.TestDataEntity;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.db.entity.WirelessResultDataEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.view.chart.DrawChartHelper;
import www.jingkan.com.view.chart.achartengine.util.IndexXYMap;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2018/3/12.
 * LastCPT
 */

public class ShowDataCharViewModel extends BaseViewModel {
    public final ObservableField<String> projectNumber = new ObservableField<>();
    public final ObservableField<String> holeNumber = new ObservableField<>();
    public final ObservableField<String> testDate = new ObservableField<>();
    public final ObservableField<String> strOperators = new ObservableField<>();
    public final ObservableField<Integer> intQc = new ObservableField<>(0);
    public final ObservableField<Integer> intFs = new ObservableField<>(0);
    public final ObservableField<Integer> intFa = new ObservableField<>(0);
    public final ObservableField<Float> floatDeep = new ObservableField<>(0.1f);
    public final MediatorLiveData<List<TestEntity>> ldTestEntities = new MediatorLiveData<>();
    public final MediatorLiveData<List<TestDataEntity>> ldTestDataEntities = new MediatorLiveData<>();
    public final MediatorLiveData<List<WirelessTestEntity>> ldWirelessTestEntities = new MediatorLiveData<>();
    public final MediatorLiveData<List<WirelessResultDataEntity>> ldWirelessResultDataEntities = new MediatorLiveData<>();

    private DrawChartHelper drawChartHelper;
    private TestDao testDao;
    private TestDataDao testDataData;
    private WirelessTestDao wirelessTestDao;
    private WirelessResultDataDao wirelessResultDataDao;
    //    private TestModel testModel;
    private String testDataID;
    private boolean isWireless;

    public ShowDataCharViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        drawChartHelper = (DrawChartHelper) objects[1];
        testDao = (TestDao) objects[2];
        testDataData = (TestDataDao) objects[3];
        wirelessTestDao = (WirelessTestDao) objects[4];
        wirelessResultDataDao = (WirelessResultDataDao) objects[5];
        if (objects[0] instanceof String[]) {//无缆
            isWireless = true;
            String[] strings = (String[]) objects[0];
            testDataID = strings[1];
            getWirelessTest();
            getWirelessResultData();
        } else {//普通
            isWireless = false;
            testDataID = (String) objects[0];
            getTest();
            getTestData();
        }
    }


    /**
     * 刷新试验数据
     */
    public void refreshTestData() {
        if (isWireless) {
            getWirelessResultData();
        } else {
            getTestData();
        }
    }

    private int index = 0;

    public void moveToNext(boolean isNext) {
        if (isNext) {
            index += 1;
        } else {
            index -= 1;
            if (index < 0) {
                index = 0;
                return;
            }
        }
        floatDeep.set(index / 10f);
    }

    /**
     * 修改数据库
     */
    public void modifyTestDataBase(IndexXYMap<Double, Double> mapQc,
                                   IndexXYMap<Double, Double> mapFs,
                                   IndexXYMap<Double, Double> mapFa) {

    }

    /**
     * 修改图形
     */
    public void modifyTestData() {
        Integer qc = intQc.get();
        if (qc != null) {
            drawChartHelper.upDataSeriesQc(index, index / 10.0, qc);
        }
    }

    private void getTest() {
        String[] split = testDataID.split("_");
        ldTestEntities.addSource(testDao.getTestEntityByPrjNumberAndHoleNumber(split[0], split[1]), ldTestEntities::setValue);
    }

    private void getTestData() {
        ldTestDataEntities.addSource(testDataData.getTestDataByTestDataId(testDataID), ldTestDataEntities::setValue);
    }

    private void getWirelessTest() {
        String[] split = testDataID.split("_");
        ldWirelessTestEntities.addSource(wirelessTestDao.getWirelessTestEntityByPrjNumberAndHoleNumber(split[0], split[1]), ldWirelessTestEntities::setValue);
    }

    private void getWirelessResultData() {
        ldWirelessResultDataEntities.addSource(wirelessResultDataDao.getWirelessResultDataEntityByTestDataId(testDataID), ldWirelessResultDataEntities::setValue);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

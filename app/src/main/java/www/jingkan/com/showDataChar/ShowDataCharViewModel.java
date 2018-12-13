package www.jingkan.com.showDataChar;

import android.content.Intent;

import org.achartengine.util.IndexXYMap;

import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.test.TestDaoForRoom;
import www.jingkan.com.localData.test.TestEntity;
import www.jingkan.com.localData.testData.TestDataDaoForRoom;
import www.jingkan.com.localData.testData.TestDataEntity;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataDaoForRoom;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataEntity;
import www.jingkan.com.localData.wirelessTest.WirelessTestDaoForRoom;
import www.jingkan.com.localData.wirelessTest.WirelessTestEntity;

/**
 * Created by Sampson on 2018/3/12.
 * LastCPT
 */

public class ShowDataCharViewModel extends BaseViewModel<ShowDataCharActivity> {
    public final ObservableField<String> projectNumber = new ObservableField<>();
    public final ObservableField<String> holeNumber = new ObservableField<>();
    public final ObservableField<String> testDate = new ObservableField<>();
    public final ObservableField<String> strOperators = new ObservableField<>();
    public final ObservableField<Integer> intQc = new ObservableField<>(0);
    public final ObservableField<Integer> intFs = new ObservableField<>(0);
    public final ObservableField<Integer> intFa = new ObservableField<>(0);
    public final ObservableField<Float> floatDeep = new ObservableField<>(0.1f);

    private TestEntity testModel;
    private String testDataID;
    private boolean isWireless;

    @Override
    protected void init(Object data) {
        if (data instanceof String[]) {//无缆
            isWireless = true;
            String[] strings = (String[]) data;
            testDataID = strings[1];
            getWirelessTest();
            getWirelessResultData();
        } else {//普通
            isWireless = false;
            testDataID = (String) data;
            getTest();
            getTestData();
        }

    }

    /**
     * 刷新试验数据
     */
    protected void refreshTestData() {
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
            getView().drawChartHelper.upDataSeriesQc(index, index / 10.0, qc);
        }
    }

    private void getTest() {
        String[] split = testDataID.split("_");
        TestDaoForRoom testDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).testDaoForRoom();
        LiveData<List<TestEntity>> liveData = testDaoForRoom.getTestEntityByPrjNumberAndHoleNumber(split[0], split[1]);
        List<TestEntity> testEntities = liveData.getValue();
        if (testEntities != null && !testEntities.isEmpty()) {
            testModel = testEntities.get(0);
            projectNumber.set(testModel.projectNumber);
            holeNumber.set(testModel.holeNumber);
            testDate.set(testModel.testDate);
            strOperators.set(testModel.tester);
            myView.get().setCharStrategy(testModel.testType);
        }


//        TestDao testData = DataFactory.getBaseData(TestDao.class);
//        testData.getData(new DataLoadCallBack<TestEntity>() {
//
//            @Override
//            public void onDataLoaded(List<TestEntity> models) {
//                testModel = models.get(0);
//                projectNumber.set(testModel.projectNumber);
//                holeNumber.set(testModel.holeNumber);
//                testDate.set(testModel.testDate);
//                strOperators.set(testModel.tester);
//                myView.get().setCharStrategy(testModel.testType);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        }, split[0], split[1]);
    }

    private void getTestData() {
        TestDataDaoForRoom testDataDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).testDataDaoForRoom();
        LiveData<List<TestDataEntity>> liveData = testDataDaoForRoom.getTestDataByTestId(testDataID);
        List<TestDataEntity> testDataEntities = liveData.getValue();
        if (testDataEntities != null && !testDataEntities.isEmpty()) {
            myView.get().showDataInTheChar(testDataEntities);
        }


//        TestDataDao testDataData = DataFactory.getBaseData(TestDataDao.class);
//        testDataData.getData(new DataLoadCallBack<TestDataModel>() {
//
//            @Override
//            public void onDataLoaded(List<TestDataModel> models) {
//                myView.get().showDataInTheChar(models);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        }, testDataID);
    }

    private void getWirelessTest() {
        String[] split = testDataID.split("_");
        WirelessTestDaoForRoom wirelessTestDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).wirelessTestDaoForRoom();
        LiveData<List<WirelessTestEntity>> liveData = wirelessTestDaoForRoom.getWirelessTestEntityByPrjNumberAndHoleNumber(split[0], split[1]);
        List<WirelessTestEntity> wirelessTestEntities = liveData.getValue();
        if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
            WirelessTestEntity wirelessTestEntity = wirelessTestEntities.get(0);
            testModel = wirelessTestEntity.castToTestEntity();//转成普通实验
            projectNumber.set(testModel.projectNumber);
            holeNumber.set(testModel.holeNumber);
            testDate.set(testModel.testDate);
            strOperators.set(testModel.tester);
            myView.get().setCharStrategy(testModel.testType);
        }


//        WirelessTestDao wirelessTestDao = DataFactory.getBaseData(WirelessTestDao.class);
//        wirelessTestDao.getData(new DataLoadCallBack<WirelessTestEntity>() {
//
//            @Override
//            public void onDataLoaded(List<WirelessTestEntity> models) {
//                WirelessTestEntity wirelessTestEntity = models.get(0);
//                testModel = wirelessTestEntity.castToTestEntity();//转成普通实验
//                projectNumber.set(testModel.projectNumber);
//                holeNumber.set(testModel.holeNumber);
//                testDate.set(testModel.testDate);
//                strOperators.set(testModel.tester);
//                myView.get().setCharStrategy(testModel.testType);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        }, split[0], split[1]);
    }

    private void getWirelessResultData() {
        WirelessResultDataDaoForRoom wirelessResultDataDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).wirelessResultDataDaoForRoom();
        LiveData<List<WirelessResultDataEntity>> liveData = wirelessResultDataDaoForRoom.getWirelessResultDataEntityByTestDataId(testDataID);
        List<WirelessResultDataEntity> wirelessResultDataEntities = liveData.getValue();
        if (wirelessResultDataEntities != null && !wirelessResultDataEntities.isEmpty()) {
            myView.get().showDataInTheChar(wirelessResultDataEntities);
        }

//        WirelessResultDaoDao wirelessResultDataDao = DataFactory.getBaseData(WirelessResultDaoDao.class);
//        wirelessResultDataDao.getData(new DataLoadCallBack<WirelessResultDataModel>() {
//
//            @Override
//            public void onDataLoaded(List<WirelessResultDataModel> models) {
//                myView.get().showDataInTheChar(models);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        }, testDataID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }
}

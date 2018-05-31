package www.jingkan.com.showDataChar;

import android.content.Intent;
import android.databinding.ObservableField;

import com.activeandroid.Model;

import org.achartengine.util.IndexXYMap;

import java.util.List;

import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.test.TestData;
import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.localData.testData.TestDataData;
import www.jingkan.com.localData.testData.TestDataModel;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataData;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataModel;
import www.jingkan.com.localData.wirelessTest.WirelessTestData;
import www.jingkan.com.localData.wirelessTest.WirelessTestModel;

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

    private TestModel testModel;
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

    @SuppressWarnings("unchecked")
    private void getTest() {
        String[] split = testDataID.split("_");
        TestData testData = DataFactory.getBaseData(TestData.class);
        testData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                testModel = (TestModel) models.get(0);
                projectNumber.set(testModel.projectNumber);
                holeNumber.set(testModel.holeNumber);
                testDate.set(testModel.testDate);
                strOperators.set(testModel.tester);
                myView.get().setCharStrategy(testModel.testType);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, split[0], split[1]);
    }

    @SuppressWarnings("unchecked")
    private void getTestData() {
        TestDataData testDataData = DataFactory.getBaseData(TestDataData.class);
        testDataData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                List<TestDataModel> testDataModels = (List<TestDataModel>) models;
                myView.get().showDataInTheChar(testDataModels);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, testDataID);
    }

    @SuppressWarnings("unchecked")
    private void getWirelessTest() {
        String[] split = testDataID.split("_");
        WirelessTestData wirelessTestData = DataFactory.getBaseData(WirelessTestData.class);
        wirelessTestData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                WirelessTestModel wirelessTestModel = (WirelessTestModel) models.get(0);
                testModel = wirelessTestModel.castToTestModel();//转成普通实验
                projectNumber.set(testModel.projectNumber);
                holeNumber.set(testModel.holeNumber);
                testDate.set(testModel.testDate);
                strOperators.set(testModel.tester);
                myView.get().setCharStrategy(testModel.testType);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, split[0], split[1]);
    }

    @SuppressWarnings("unchecked")
    private void getWirelessResultData() {
        WirelessResultDataData wirelessResultDataData = DataFactory.getBaseData(WirelessResultDataData.class);
        wirelessResultDataData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                List<WirelessResultDataModel> wirelessResultDataModels = (List<WirelessResultDataModel>) models;
                myView.get().showDataInTheChar(wirelessResultDataModels);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, testDataID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }
}

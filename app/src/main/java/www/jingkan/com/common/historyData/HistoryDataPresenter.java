/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.historyData;

import android.content.Context;
import android.content.Intent;

import com.activeandroid.Model;

import java.util.List;

import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.test.TestData;
import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.localData.testData.TestDataData;

/**
 * Created by lushengbo on 2017/6/1.
 * 历史数据中介
 */

public class HistoryDataPresenter extends BasePresenter<HistoryDataActivity> implements HistoryDataContract.Presenter {
    private TestData testData = DataFactory.getBaseData(TestData.class);

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void init(Context context, Object data) {

    }


    @SuppressWarnings("unchecked")
    @Override
    public void getHistoryData() {

        testData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                myView.get().showHistoryData((List<TestModel>) models);
            }

            @Override
            public void onDataNotAvailable() {
                myView.get().showHistoryData(null);
            }
        });

    }

    @Override
    public void deleteOneHistoryData(TestModel testModel) {
        testData.deleteData(testModel.projectNumber, testModel.holeNumber);
        //同时删除试验数据
        TestDataData testDataData = DataFactory.getBaseData(TestDataData.class);
        testDataData.deleteData(testModel.testDataID);
        getHistoryData();//刷新
    }
}

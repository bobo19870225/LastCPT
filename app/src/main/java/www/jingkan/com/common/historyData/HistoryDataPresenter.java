///*
// * Copyright (c) 2018. 代码著作权归卢声波所有。
// */
//
//package www.jingkan.com.common.historyData;
//
//import android.content.Context;
//import android.content.Intent;
//
//import java.util.List;
//
//import www.jingkan.com.base.baseMVP.BasePresenter;
//import www.jingkan.com.localData.dataFactory.DataFactory;
//import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
//import www.jingkan.com.localData.test.TestDao;
//import www.jingkan.com.localData.test.TestEntity;
//import www.jingkan.com.localData.testData.TestDataDao;
//
///**
// * Created by lushengbo on 2017/6/1.
// * 历史数据中介
// */
//
//public class HistoryDataPresenter extends BasePresenter<HistoryDataActivity> implements HistoryDataContract.Presenter {
//    private TestDao testData = DataFactory.getBaseData(TestDao.class);
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//    }
//
//    @Override
//    public void clear() {
//
//    }
//
//    @Override
//    public void init(Context context, Object data) {
//
//    }
//
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public void getHistoryData() {
//
//        testData.getData(new DataLoadCallBack<TestEntity>() {
//
//            @Override
//            public void onDataLoaded(List<TestEntity> models) {
//                myView.get().showHistoryData(models);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                myView.get().showHistoryData(null);
//            }
//        });
//
//    }
//
//    @Override
//    public void deleteOneHistoryData(TestEntity testModel) {
//        testData.deleteData(testModel.projectNumber, testModel.holeNumber);
//        //同时删除试验数据
//        TestDataDao testDataData = DataFactory.getBaseData(TestDataDao.class);
//        testDataData.deleteData(testModel.testDataID);
//        getHistoryData();//刷新
//    }
//}

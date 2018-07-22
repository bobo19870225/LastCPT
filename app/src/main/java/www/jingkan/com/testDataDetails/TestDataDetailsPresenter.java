/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.testDataDetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.test.TestDao;
import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.localData.testData.TestDataDao;
import www.jingkan.com.localData.testData.TestDataModel;
import www.jingkan.com.mInterface.ISkip;
import www.jingkan.com.saveUtils.DataUtils;

import static www.jingkan.com.saveUtils.DataUtils.SET_EMAIL;

/**
 * Created by lushengbo on 2017/6/1.
 * 历史数据中介
 */

class TestDataDetailsPresenter extends BasePresenter<TestDataDetailsActivity>
        implements TestDataDetailsContract.Presenter, ISkip {


    private String mFileType;

    @Override
    public void clear() {

    }

    @Override
    public void init(Context context, Object data) {

    }


    private List<TestDataModel> testDataModels;
    private TestModel testModel;

    @Override
    public void getTest(String projectNumber, String holeNumber) {
        TestDao testData = DataFactory.getBaseData(TestDao.class);
        testData.getData(new DataLoadCallBack<TestModel>() {

            @Override
            public void onDataLoaded(List<TestModel> models) {
                testModel = models.get(0);
                getView().showTest(testModel);
            }

            @Override
            public void onDataNotAvailable() {
                getView().showToast("找不到该试验");
            }
        }, projectNumber, holeNumber);
    }

    @Override
    public void getTestData(String testDataID) {
        TestDataDao testDataData = DataFactory.getBaseData(TestDataDao.class);
        testDataData.getData(new DataLoadCallBack<TestDataModel>() {

            @Override
            public void onDataLoaded(List<TestDataModel> models) {
                testDataModels = models;
                myView.get().showTestData(testDataModels);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, testDataID);

    }

    @Override
    public void saveTestDataToSD(String projectNumber, String holeNumber, String fileType, String testType) {
        if (testDataModels != null) {
            DataUtils.getInstance()
                    .saveDataToSd(getView().getApplicationContext(),
                            testDataModels,
                            fileType,
                            testModel,
                            TestDataDetailsPresenter.this);
        } else {
            myView.get().showToast("读取数据失败！");
        }
    }

    @Override
    public void emailTestData(String projectNumber, String holeNumber, String fileType, String testType) {
        mFileType = fileType;
        sendEmail();
    }


    private void sendEmail() {
        DataUtils.getInstance().emailData(
                getView().getApplicationContext(),
                testDataModels,
                mFileType,
                testModel,
                this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SET_EMAIL && resultCode == Activity.RESULT_OK) {
            sendEmail();
        }
    }

    @Override
    public void deleteOneTestData(TestDataModel testDataModel) {
//        TestDataLocalDataSource.getInstance().deleteTestData(testModel.testDataID);
//        getTestData();//刷新
    }

    @Override
    public void skipForResult(Intent intent, int requestCode) {
        getView().startActivityForResult(intent, requestCode);
    }

    @Override
    public void skip(Intent intent) {

    }

    @Override
    public void sendToastMsg(String msg) {
        getView().showToast(msg);
    }
}

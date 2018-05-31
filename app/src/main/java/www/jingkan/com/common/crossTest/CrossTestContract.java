/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.crossTest;

import java.util.List;

import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataModel;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface CrossTestContract {

    interface View {
        void showTestData(List<CrossTestDataModel> crossTestDataModels);

        void showNotLinkError();

        void showRecordValue(String strCu, float deep);


    }

    interface Presenter {
        void loadTestData();


        void getTestParameters();


        void linkDevice();

        /**
         * 保存试验数据到SD卡
         *
         * @param projectNumber 工程编号
         * @param holeNumber    孔号
         * @param fileType      文件类型
         * @param testType      试验类型
         */
        void saveTestDataToSD(String projectNumber, String holeNumber, int fileType, String testType);

        void emailTestData(String fileName);

    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.testDataDetails;

import java.util.List;

import www.jingkan.com.localData.test.TestEntity;
import www.jingkan.com.localData.testData.TestDataEntity;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface TestDataDetailsContract {

    interface View {
        void showTest(TestEntity testModel);

        void showTestData(List<TestDataEntity> testDataModels);

    }

    interface Presenter {
        void getTest(String projectNumber, String holeNumber);

        void getTestData(String testDataID);

        void saveTestDataToSD(final String projectNumber, final String holeNumber, final String fileType, final String testType);

        void emailTestData(final String projectNumber, final String holeNumber, final String fileType, final String testType);

        void deleteOneTestData(TestDataEntity testDataModel);

    }
}

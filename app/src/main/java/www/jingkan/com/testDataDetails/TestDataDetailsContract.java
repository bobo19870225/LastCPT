/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.testDataDetails;

import java.util.List;

import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.localData.testData.TestDataModel;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface TestDataDetailsContract {

    interface View {
        void showTest(TestModel testModel);

        void showTestData(List<TestDataModel> testDataModels);

    }

    interface Presenter {
        void getTest(String projectNumber, String holeNumber);

        void getTestData(String testDataID);

        void saveTestDataToSD(final String projectNumber, final String holeNumber, final int fileType, final String testType);

        void emailTestData(final String projectNumber, final String holeNumber, final int fileType, final String testType);

        void deleteOneTestData(TestDataModel testDataModel);

    }
}

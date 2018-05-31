/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.historyData;

import www.jingkan.com.localData.test.TestModel;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface HistoryDataContract {

    interface View {

        void showHistoryData(List<TestModel> testModels);

    }

    interface Presenter {

        void getHistoryData();

        void deleteOneHistoryData(TestModel testModel);

    }
}

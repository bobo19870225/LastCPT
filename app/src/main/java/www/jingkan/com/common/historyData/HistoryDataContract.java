/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.historyData;

import java.util.List;

import www.jingkan.com.localData.test.TestEntity;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface HistoryDataContract {

    interface View {

        void showHistoryData(List<TestEntity> testModels);

    }

    interface Presenter {

        void getHistoryData();

        void deleteOneHistoryData(TestEntity testModel);

    }
}

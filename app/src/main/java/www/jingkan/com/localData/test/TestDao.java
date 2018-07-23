/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.test;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import www.jingkan.com.localData.dataFactory.BaseDao;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2018/1/3.
 * 试验数据类
 */

public class TestDao extends BaseDao<TestModel> {


    @Override
    public void addData(TestModel model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        if (args.length == 2) {
            String selection = TestConstant.COLUMN_PROJECT_NUMBER + " LIKE ?"
                    + " AND " + TestConstant.COLUMN_HOLE_NUMBER + " LIKE ?";
            new Delete().from(TestModel.class).where(selection, (Object[]) args).execute();
        }
    }

    @Override
    public void modifyData(TestModel model) {

    }

    @Override
    public void getData(DataLoadCallBack<TestModel> dataLoadCallBack, String... args) {
        if (args.length == 0) {//按时间倒序
            List<TestModel> testModels =
                    new Select().all().from(TestModel.class).orderBy(TestConstant.COLUMN_TEST_DATE + " DESC").execute();
            if (testModels != null && testModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(testModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        } else if (args.length == 1) {
            String selection = TestConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
            List<TestModel> testModels =
                    new Select().from(TestModel.class)
                            .where(selection, (Object[]) args).execute();

            if (testModels != null && testModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(testModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        } else if (args.length == 2) {
            String selection = TestConstant.COLUMN_PROJECT_NUMBER + " LIKE ?"
                    + " AND " + TestConstant.COLUMN_HOLE_NUMBER + " LIKE ?";
            List<TestModel> testModels =
                    new Select().from(TestModel.class)
                            .where(selection, (Object[]) args).execute();

            if (testModels != null && testModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(testModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        }

    }


}

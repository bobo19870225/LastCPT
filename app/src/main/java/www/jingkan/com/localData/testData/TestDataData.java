/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.testData;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import www.jingkan.com.localData.dataFactory.BaseData;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2018/1/3.
 * 试验数据
 */

public class TestDataData extends BaseData {
    @Override
    public <T extends Model> void addData(T model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        if (args.length == 1) {
            String selection = TestDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
            new Delete().from(TestDataModel.class).where(selection, (Object[]) args).execute();
        }
    }

    @Override
    public <T extends Model> void modifyData(T model) {

    }

    @Override
    public void getData(DataLoadCallBack dataLoadCallBack, String... args) {
        if (args.length == 1) {
            String selection = TestDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
            List<TestDataModel> testDataModels =
                    new Select().from(TestDataModel.class)
                            .where(selection, (Object[]) args).execute();

            if (testDataModels != null && testDataModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(testDataModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        }
    }
}

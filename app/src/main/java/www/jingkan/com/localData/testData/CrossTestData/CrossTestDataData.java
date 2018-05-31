/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.testData.CrossTestData;

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

public class CrossTestDataData extends BaseData {
    @Override
    public <T extends Model> void addData(T model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        if (args.length == 1) {
            String selection = CrossTestDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
            new Delete().from(CrossTestDataModel.class).where(selection, (Object[]) args).execute();
        }
    }

    @Override
    public <T extends Model> void modifyData(T model) {

    }

    @Override
    public void getData(DataLoadCallBack dataLoadCallBack, String... args) {
        if (args.length == 1) {
            String selection = CrossTestDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
            List<CrossTestDataModel> crossTestDataModels =
                    new Select().from(CrossTestDataModel.class)
                            .where(selection, (Object[]) args).execute();

            if (crossTestDataModels != null && crossTestDataModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(crossTestDataModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        }
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.wirelessTest;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import www.jingkan.com.localData.dataFactory.BaseDao;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2018/1/3.
 * 试验数据类
 */

public class WirelessTestDao extends BaseDao<WirelessTestModel> {

    @Override
    public void addData(WirelessTestModel model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        if (args.length == 2) {
            String selection = WirelessTestConstant.COLUMN_PROJECT_NUMBER + " LIKE ?"
                    + " AND " + WirelessTestConstant.COLUMN_HOLE_NUMBER + " LIKE ?";
            new Delete().from(WirelessTestModel.class).where(selection, (Object[]) args).execute();
        }
    }

    @Override
    public void modifyData(WirelessTestModel model) {

    }

    @Override
    public void getData(DataLoadCallBack<WirelessTestModel> dataLoadCallBack, String... args) {
        if (args.length == 0) {//按时间倒序排列
            List<WirelessTestModel> wirelessTestModels =
                    new Select().all().from(WirelessTestModel.class).orderBy(WirelessTestConstant.COLUMN_TEST_DATE + " DESC").execute();
            if (wirelessTestModels != null && wirelessTestModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(wirelessTestModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        } else if (args.length == 1) {
            String selection = WirelessTestConstant.COLUMN_TEST_ID + " LIKE ?";
            List<WirelessTestModel> wirelessTestModels =
                    new Select().from(WirelessTestModel.class)
                            .where(selection, (Object[]) args).execute();
            if (wirelessTestModels != null && wirelessTestModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(wirelessTestModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        } else if (args.length == 2) {
            String selection = WirelessTestConstant.COLUMN_PROJECT_NUMBER + " LIKE ?"
                    + " AND " + WirelessTestConstant.COLUMN_HOLE_NUMBER + " LIKE ?";
            List<WirelessTestModel> wirelessTestModels =
                    new Select().from(WirelessTestModel.class)
                            .where(selection, (Object[]) args).execute();

            if (wirelessTestModels != null && wirelessTestModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(wirelessTestModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        }

    }


}

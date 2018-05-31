/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.wirelessResultData;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

import www.jingkan.com.localData.dataFactory.BaseData;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2018/1/3.
 * 无缆试验数据类
 */

public class WirelessResultDataData extends BaseData {
    @Override
    public <T extends Model> void addData(T model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        if (args.length == 1) {
            String selection = WirelessResultDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
            new Delete().from(WirelessResultDataModel.class).where(selection, (Object[]) args).execute();
        }
    }

    @Override
    public <T extends Model> void modifyData(T model) {
        WirelessResultDataModel wirelessResultDataModel = (WirelessResultDataModel) model;
        String set = WirelessResultDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?" +
                WirelessResultDataConstant.COLUMN_PROBE_NUMBER + " LIKE ?" +
                WirelessResultDataConstant.COLUMN_DEEP + " LIKE ?" +
                WirelessResultDataConstant.COLUMN_QC + " LIKE ?" +
                WirelessResultDataConstant.COLUMN_FS + " LIKE ?" +
                WirelessResultDataConstant.COLUMN_FA + " LIKE ?";
        String where = WirelessResultDataConstant.COLUMN_DEEP + " LIKE ?";
        new Update(WirelessResultDataModel.class).set(set, wirelessResultDataModel.testDataID,
                wirelessResultDataModel.probeNumber,
                wirelessResultDataModel.deep,
                wirelessResultDataModel.qc,
                wirelessResultDataModel.fs,
                wirelessResultDataModel.fa)
                .where(where, wirelessResultDataModel.deep);
    }

    @Override
    public void getData(DataLoadCallBack dataLoadCallBack, String... args) {
        if (args.length == 1) {
            String selection = WirelessResultDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
            List<WirelessResultDataModel> wirelessResultDataModels =
                    new Select().from(WirelessResultDataModel.class)
                            .where(selection, (Object[]) args).execute();

            if (wirelessResultDataModels != null && wirelessResultDataModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(wirelessResultDataModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        }
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.memoryData;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import www.jingkan.com.localData.dataFactory.BaseData;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2018/1/3.
 * 内存数据类
 */

public class MemoryData extends BaseData {
    @Override
    public <T extends Model> void addData(T model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        if (args.length == 1) {
            String selection = MemoryDataConstant.COLUMN_NAME_PROBE_ID + " LIKE ?";
            new Delete().from(MemoryDataModel.class).where(selection, (Object[]) args).execute();
        } else if (args.length == 2) {
            String selection = MemoryDataConstant.COLUMN_NAME_PROBE_ID + " LIKE ?"
                    + " AND " + MemoryDataConstant.COLUMN_NAME_TYPE + " LIKE ?";
            new Delete().from(MemoryDataModel.class).where(selection, (Object[]) args).execute();
        }
    }

    @Override
    public <T extends Model> void modifyData(T model) {

    }


    @Override
    public void getData(DataLoadCallBack dataLoadCallBack, String... args) {
        if (args.length == 1) {
            String selection = MemoryDataConstant.COLUMN_NAME_PROBE_ID + " LIKE ?";
            List<MemoryDataModel> memoryDataModels =
                    new Select().from(MemoryDataModel.class)
                            .where(selection, (Object[]) args).execute();

            if (memoryDataModels != null && memoryDataModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(memoryDataModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        } else if (args.length == 2) {
            String selection = MemoryDataConstant.COLUMN_NAME_PROBE_ID + " LIKE ?"
                    + " AND " + MemoryDataConstant.COLUMN_NAME_TYPE + " LIKE ?";
            List<MemoryDataModel> memoryDataModels =
                    new Select().from(MemoryDataModel.class)
                            .where(selection, (Object[]) args).execute();

            if (memoryDataModels != null && memoryDataModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(memoryDataModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        }


    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.msgData;

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

public class MsgData extends BaseData {
    @Override
    public <T extends Model> void addData(T model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        if (args.length == 1) {
            String selection = MsgDataConstant.COLUMN_NAME_MSG_ID + " LIKE ?";
            new Delete().from(MsgDataModel.class).where(selection, (Object[]) args).execute();
        }
    }

    @Override
    public <T extends Model> void modifyData(T model) {

    }


    @Override
    public void getData(DataLoadCallBack dataLoadCallBack, String... args) {
        if (args.length == 1) {
            String selection = MsgDataConstant.COLUMN_NAME_MSG_ID + " LIKE ?";
            List<MsgDataModel> msgDataModels = new Select().from(MsgDataModel.class).where(selection, (Object[]) args).execute();
            if (msgDataModels != null && msgDataModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(msgDataModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        } else if (args.length == 0) {
            List<MsgDataModel> msgDataModels = new Select().all().from(MsgDataModel.class).orderBy(MsgDataConstant.COLUMN_NAME_TIME + " DESC").execute();

            if (msgDataModels != null && msgDataModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(msgDataModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        }
    }
}

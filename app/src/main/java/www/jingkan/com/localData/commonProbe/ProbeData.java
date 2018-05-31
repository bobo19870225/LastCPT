/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.commonProbe;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

import www.jingkan.com.localData.dataFactory.BaseData;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2018/1/3.
 * 探头数据类
 */

public class ProbeData extends BaseData {
    @Override
    public <T extends Model> void addData(T model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        String selection = ProbeConstant.COLUMN_PROBE_ID + " LIKE ?";
        new Delete().from(ProbeModel.class).where(selection, (Object[]) args).execute();
    }

    @Override
    public <T extends Model> void modifyData(T model) {
        Update update = new Update(ProbeModel.class);
        String add = " = ? , ";
        String toSet = ProbeConstant.COLUMN_NUMBER + add
                + ProbeConstant.COLUMN_TYPE + add
                + ProbeConstant.COLUMN_QC_AREA + add
                + ProbeConstant.COLUMN_QC_COEFFICIENT + add
                + ProbeConstant.COLUMN_QC_LIMIT + add
                + ProbeConstant.COLUMN_FS_AREA + add
                + ProbeConstant.COLUMN_FS_COEFFICIENT + add
                + ProbeConstant.COLUMN_FS_LIMIT + " = ?";
        String selection = ProbeConstant.COLUMN_PROBE_ID + " = ?";
        ProbeModel probeModel = (ProbeModel) model;
        update.set(toSet, probeModel.number,
                probeModel.type,
                probeModel.qc_area,
                probeModel.qc_coefficient,
                probeModel.qc_limit,
                probeModel.fs_area,
                probeModel.fs_coefficient,
                probeModel.fs_limit).where(selection, probeModel.probeID).execute();
    }


    @Override
    public void getData(DataLoadCallBack dataLoadCallBack, String... args) {
        if (args.length == 0) {
            {
                List<ProbeModel> probeModels = new Select().all().from(ProbeModel.class).execute();
                if (probeModels != null && probeModels.size() > 0) {
                    dataLoadCallBack.onDataLoaded(probeModels);
                } else {
                    dataLoadCallBack.onDataNotAvailable();
                }
            }

        } else if (args.length == 1) {
            String selection = ProbeConstant.COLUMN_PROBE_ID + " LIKE ?";
            List<ProbeModel> probeModels =
                    new Select().from(ProbeModel.class)
                            .where(selection, (Object[]) args).execute();

            if (probeModels != null && probeModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(probeModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        }
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.calibrationProbe;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import www.jingkan.com.localData.dataFactory.BaseDao;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2018/1/3.
 * 标定探头数据类
 */

public class CalibrationProbeDao extends BaseDao<CalibrationProbeModel> {


    @Override
    public void addData(CalibrationProbeModel model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        String selection = CalibrationProbeConstant.COLUMN_PROBE_ID + " LIKE ?";
        new Delete().from(CalibrationProbeModel.class).where(selection, (Object[]) args).execute();
    }

    @Override
    public void modifyData(CalibrationProbeModel model) {

    }

    @Override
    public void getData(DataLoadCallBack<CalibrationProbeModel> dataLoadCallBack, String... args) {
        if (args.length == 0) {
            List<CalibrationProbeModel> calibrationProbeModels = new Select().all().from(CalibrationProbeModel.class).execute();
            if (calibrationProbeModels != null && calibrationProbeModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(calibrationProbeModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        } else {
            String selection = CalibrationProbeConstant.COLUMN_PROBE_ID + " LIKE ?";
            List<CalibrationProbeModel> calibrationProbeModels =
                    new Select().from(CalibrationProbeModel.class)
                            .where(selection, (Object[]) args).execute();

            if (calibrationProbeModels != null && calibrationProbeModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(calibrationProbeModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        }


    }


}

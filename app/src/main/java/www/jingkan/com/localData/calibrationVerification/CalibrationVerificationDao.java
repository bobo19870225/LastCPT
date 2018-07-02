/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.calibrationVerification;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import www.jingkan.com.localData.dataFactory.BaseDao;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2018/1/3.
 * 标定数据类
 */

public class CalibrationVerificationDao extends BaseDao<CalibrationVerificationModel> {


    @Override
    public void addData(CalibrationVerificationModel model) {
        model.save();
    }

    @Override
    public void deleteData(String... args) {
        String selection = CalibrationVerificationConstant.COLUMN_NAME_PROBE_NO + " LIKE ?"
                + " AND " + CalibrationVerificationConstant.COLUMN_NAME_TYPE + " LIKE ?";
        new Delete().from(CalibrationVerificationModel.class).where(selection, (Object[]) args).execute();
    }

    @Override
    public void modifyData(CalibrationVerificationModel model) {

    }

    @Override
    public void getData(DataLoadCallBack<CalibrationVerificationModel> dataLoadCallBack, String... args) {
        if (args.length == 2) {
            String selection = CalibrationVerificationConstant.COLUMN_NAME_PROBE_NO + " LIKE ?"
                    + " AND " + CalibrationVerificationConstant.COLUMN_NAME_TYPE + " LIKE ?";
            List<CalibrationVerificationModel> calibrationVerificationModels =
                    new Select().from(CalibrationVerificationModel.class)
                            .where(selection, (Object[]) args).execute();

            if (calibrationVerificationModels != null && calibrationVerificationModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(calibrationVerificationModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }
        } else if (args.length == 3) {
            String selection = CalibrationVerificationConstant.COLUMN_NAME_PROBE_NO + " LIKE ?"
                    + " AND " + CalibrationVerificationConstant.COLUMN_NAME_TYPE + " LIKE ?"
                    + " AND " + CalibrationVerificationConstant.COLUMN_NAME_FORCE_TYPE + " LIKE ?";
            List<CalibrationVerificationModel> calibrationVerificationModels =
                    new Select().from(CalibrationVerificationModel.class)
                            .where(selection, (Object[]) args).execute();

            if (calibrationVerificationModels != null && calibrationVerificationModels.size() > 0) {
                dataLoadCallBack.onDataLoaded(calibrationVerificationModels);
            } else {
                dataLoadCallBack.onDataNotAvailable();
            }

        }

    }


}

package www.jingkan.com.db.dao;

import www.jingkan.com.db.dao.dao_factory.BaseDao;
import www.jingkan.com.db.dao.dao_factory.DataBaseCallBack;
import www.jingkan.com.db.entity.CalibrationProbeEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sampson on 2018/12/14.
 * CPTTest
 */
@Singleton
public class CalibrationProbeDaoHelper extends BaseDao<CalibrationProbeEntity> {
    @Inject
    CalibrationProbeDao calibrationProbeDao;

    @Inject
    public CalibrationProbeDaoHelper() {
    }

    private static class MyDataBaseAsyncTask extends DataBaseAsyncTask<CalibrationProbeEntity> {
        private CalibrationProbeDao calibrationProbeDao;
        private int action;

        MyDataBaseAsyncTask(int action, CalibrationProbeDao calibrationProbeDao, DataBaseCallBack dataBaseCallBack) {
            super(dataBaseCallBack);
            this.calibrationProbeDao = calibrationProbeDao;
            this.action = action;
        }


        @Override
        protected Void doInBackground(CalibrationProbeEntity... calibrationProbeEntities) {
            switch (action) {
                case 0:
                    calibrationProbeDao.insertCalibrationProbe(calibrationProbeEntities[0]);
                    break;
                case 1:
                    calibrationProbeDao.deleteCPEntityByProbeId(calibrationProbeEntities[0].probeID);
                    break;

            }
            return null;
        }
    }


    @Override
    public void addData(CalibrationProbeEntity entity, DataBaseCallBack dataBaseCallBack) {
        new MyDataBaseAsyncTask(0, calibrationProbeDao, dataBaseCallBack).execute(entity);
    }

    @Override
    public void deleteData(String[] strings, DataBaseCallBack dataBaseCallBack) {
        CalibrationProbeEntity calibrationProbeEntity = new CalibrationProbeEntity();
        calibrationProbeEntity.probeID = strings[0];
        new MyDataBaseAsyncTask(1, calibrationProbeDao, dataBaseCallBack).execute(calibrationProbeEntity);

    }

    @Override
    public void modifyData(CalibrationProbeEntity entity, DataBaseCallBack dataBaseCallBack) {
        new MyDataBaseAsyncTask(2, calibrationProbeDao, dataBaseCallBack).execute(entity);
    }


}

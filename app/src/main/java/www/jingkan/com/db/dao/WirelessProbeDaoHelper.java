package www.jingkan.com.db.dao;

import www.jingkan.com.db.dao.dao_factory.BaseDao;
import www.jingkan.com.db.dao.dao_factory.DataBaseCallBack;
import www.jingkan.com.db.entity.WirelessProbeEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sampson on 2018/12/14.
 * CPTTest
 */
@Singleton
public class WirelessProbeDaoHelper extends BaseDao<WirelessProbeEntity> {
    @Inject
    WirelessProbeDao wirelessProbeDao;

    @Inject
    public WirelessProbeDaoHelper() {
    }

    private static class MyDataBaseAsyncTask extends DataBaseAsyncTask<WirelessProbeEntity> {
        private WirelessProbeDao wirelessProbeDao;
        private int action;

        MyDataBaseAsyncTask(int action, WirelessProbeDao wirelessProbeDao, DataBaseCallBack dataBaseCallBack) {
            super(dataBaseCallBack);
            this.wirelessProbeDao = wirelessProbeDao;
            this.action = action;
        }


        @Override
        protected Void doInBackground(WirelessProbeEntity... wirelessProbeEntities) {
            switch (action) {
                case 0:
                    wirelessProbeDao.insertWirelessProbeEntity(wirelessProbeEntities[0]);
                    break;
                case 1:
                    wirelessProbeDao.deleteWirelessProbeEntityByProbeId(wirelessProbeEntities[0].probeID);
                    break;
                case 2:
                    wirelessProbeDao.upDataWirelessProbeEntity(wirelessProbeEntities[0]);
                    break;
            }
            return null;
        }
    }


    @Override
    public void addData(WirelessProbeEntity entity, DataBaseCallBack dataBaseCallBack) {
        new MyDataBaseAsyncTask(0, wirelessProbeDao, dataBaseCallBack).execute(entity);
    }

    @Override
    public void deleteData(String[] strings, DataBaseCallBack dataBaseCallBack) {
        WirelessProbeEntity wirelessProbeEntity = new WirelessProbeEntity();
        wirelessProbeEntity.probeID = strings[0];
        new MyDataBaseAsyncTask(1, wirelessProbeDao, dataBaseCallBack).execute(wirelessProbeEntity);

    }

    @Override
    public void modifyData(WirelessProbeEntity entity, DataBaseCallBack dataBaseCallBack) {
        new MyDataBaseAsyncTask(2, wirelessProbeDao, dataBaseCallBack).execute(entity);

    }


}

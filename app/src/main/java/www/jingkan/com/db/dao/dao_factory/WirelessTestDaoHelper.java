package www.jingkan.com.db.dao.dao_factory;

import javax.inject.Inject;
import javax.inject.Singleton;

import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.entity.WirelessTestEntity;

/**
 * Created by Sampson on 2018/12/14.
 * CPTTest
 */
@Singleton
public class WirelessTestDaoHelper extends BaseDao<WirelessTestEntity> {
    @Inject
    WirelessTestDao wirelessTestDao;

    @Inject
    public WirelessTestDaoHelper() {
    }

    private static class MyDataBaseAsyncTask extends DataBaseAsyncTask<WirelessTestEntity> {
        private WirelessTestDao wirelessTestDao;
        private int action;

        MyDataBaseAsyncTask(int action, WirelessTestDao wirelessTestDao, DataBaseCallBack dataBaseCallBack) {
            super(dataBaseCallBack);
            this.wirelessTestDao = wirelessTestDao;
            this.action = action;
        }


        @Override
        protected Void doInBackground(WirelessTestEntity... testEntities) {
            switch (action) {
                case 0:
                    wirelessTestDao.insertWirelessTestEntity(testEntities[0]);
                    break;
                case 1:
                    wirelessTestDao.deleteWirelessTestEntityByPrjNumberAndHoleNumber(testEntities[0].projectNumber, testEntities[0].holeNumber);
                    break;
                case 2:
                    break;
            }

            return null;
        }
    }

    @Override
    public void addData(WirelessTestEntity entity, DataBaseCallBack dataBaseCallBack) {
        new MyDataBaseAsyncTask(0, wirelessTestDao, dataBaseCallBack).execute(entity);
    }

    @Override
    public void deleteData(String[] strings, DataBaseCallBack dataBaseCallBack) {
        WirelessTestEntity testEntity = new WirelessTestEntity();
        testEntity.projectNumber = strings[0];
        testEntity.holeNumber = strings[1];
        new MyDataBaseAsyncTask(1, wirelessTestDao, dataBaseCallBack).execute(testEntity);

    }

    @Override
    public void modifyData(WirelessTestEntity entity, DataBaseCallBack dataBaseCallBack) {

    }


}

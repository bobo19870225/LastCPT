package www.jingkan.com.db.dao;

import www.jingkan.com.db.dao.dao_factory.BaseDao;
import www.jingkan.com.db.dao.dao_factory.DataBaseCallBack;
import www.jingkan.com.db.entity.TestDataEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sampson on 2018/12/14.
 * CPTTest
 */
@Singleton
public class TestDataDaoHelper extends BaseDao<TestDataEntity> {
    @Inject
    TestDataDao testDataDao;
    @Inject
    TestDataEntity testDataEntity;

    @Inject
    public TestDataDaoHelper() {
    }

    private static class MyDataBaseAsyncTask extends DataBaseAsyncTask<TestDataEntity> {
        private TestDataDao testDataDao;
        private int action;

        MyDataBaseAsyncTask(int action, TestDataDao testDataDao, DataBaseCallBack dataBaseCallBack) {
            super(dataBaseCallBack);
            this.testDataDao = testDataDao;
            this.action = action;
        }


        @Override
        protected Void doInBackground(TestDataEntity... testDataEntities) {
            switch (action) {
                case 0:
                    testDataDao.insertTestDataEntity(testDataEntities[0]);
                    break;
                case 1:
                    testDataDao.deleteTestDataEntityByTestId(testDataEntities[0].testDataID);
                    break;
                case 2:
                    break;
            }

            return null;
        }
    }


    @Override
    public void addData(TestDataEntity entity, DataBaseCallBack dataBaseCallBack) {
        new MyDataBaseAsyncTask(0, testDataDao, dataBaseCallBack).execute(entity);
    }

    @Override
    public void deleteData(String[] strings, DataBaseCallBack dataBaseCallBack) {
        testDataEntity.testDataID = strings[0];
        new MyDataBaseAsyncTask(1, testDataDao, dataBaseCallBack).execute(testDataEntity);

    }

    @Override
    public void modifyData(TestDataEntity entity, DataBaseCallBack dataBaseCallBack) {

    }


}

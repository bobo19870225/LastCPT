package www.jingkan.com.db.dao;

import www.jingkan.com.db.dao.dao_factory.BaseDao;
import www.jingkan.com.db.dao.dao_factory.DataBaseCallBack;
import www.jingkan.com.db.entity.TestEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sampson on 2018/12/14.
 * CPTTest
 */
@Singleton
public class TestDaoHelper extends BaseDao<TestEntity> {
    @Inject
    TestDao testDao;

    @Inject
    public TestDaoHelper() {
    }

    private static class MyDataBaseAsyncTask extends DataBaseAsyncTask<TestEntity> {
        private TestDao testDao;
        private int action;

        MyDataBaseAsyncTask(int action, TestDao testDao, DataBaseCallBack dataBaseCallBack) {
            super(dataBaseCallBack);
            this.testDao = testDao;
            this.action = action;
        }


        @Override
        protected Void doInBackground(TestEntity... testEntities) {
            switch (action) {
                case 0:
                    testDao.insertTestEntity(testEntities[0]);
                    break;
                case 1:
                    testDao.deleteTestEntityByPrjNumberAndHoleNumber(testEntities[0].projectNumber, testEntities[0].holeNumber);
                    break;
                case 2:
                    break;
            }

            return null;
        }
    }

    @Override
    public void addData(TestEntity entity, DataBaseCallBack dataBaseCallBack) {
        new MyDataBaseAsyncTask(0, testDao, dataBaseCallBack).execute(entity);
    }

    @Override
    public void deleteData(String[] strings, DataBaseCallBack dataBaseCallBack) {
        TestEntity testEntity = new TestEntity();
        testEntity.projectNumber = strings[0];
        testEntity.holeNumber = strings[1];
        new MyDataBaseAsyncTask(1, testDao, dataBaseCallBack).execute(testEntity);

    }

    @Override
    public void modifyData(TestEntity entity, DataBaseCallBack dataBaseCallBack) {

    }


}

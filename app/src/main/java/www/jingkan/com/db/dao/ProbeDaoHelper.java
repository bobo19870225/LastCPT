package www.jingkan.com.db.dao;

import www.jingkan.com.db.dao.dao_factory.BaseDao;
import www.jingkan.com.db.dao.dao_factory.DataBaseCallBack;
import www.jingkan.com.db.entity.ProbeEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sampson on 2018/12/14.
 * CPTTest
 */
@Singleton
public class ProbeDaoHelper extends BaseDao<ProbeEntity> {
    @Inject
    ProbeDao probeDao;

    @Inject
    public ProbeDaoHelper() {
    }

    private static class MyDataBaseAsyncTask extends DataBaseAsyncTask<ProbeEntity> {
        private ProbeDao probeDao;
        private int action;

        MyDataBaseAsyncTask(int action, ProbeDao probeDao, DataBaseCallBack dataBaseCallBack) {
            super(dataBaseCallBack);
            this.probeDao = probeDao;
            this.action = action;
        }


        @Override
        protected Void doInBackground(ProbeEntity... probeEntities) {
            switch (action) {
                case 0:
                    probeDao.insertProbeEntity(probeEntities[0]);
                    break;
                case 1:
                    probeDao.deleteProbeByProbeId(probeEntities[0].probeID);
                    break;
                case 2:
                    probeDao.upDateProbe(probeEntities[0]);
                    break;
            }
            return null;
        }
    }


    @Override
    public void addData(ProbeEntity entity, DataBaseCallBack dataBaseCallBack) {
        new MyDataBaseAsyncTask(0, probeDao, dataBaseCallBack).execute(entity);
    }

    @Override
    public void deleteData(String[] strings, DataBaseCallBack dataBaseCallBack) {
        ProbeEntity probeEntity = new ProbeEntity();
        probeEntity.probeID = strings[0];
        new MyDataBaseAsyncTask(1, probeDao, dataBaseCallBack).execute(probeEntity);

    }

    @Override
    public void modifyData(ProbeEntity entity, DataBaseCallBack dataBaseCallBack) {
        new MyDataBaseAsyncTask(2, probeDao, dataBaseCallBack).execute(entity);
    }


}

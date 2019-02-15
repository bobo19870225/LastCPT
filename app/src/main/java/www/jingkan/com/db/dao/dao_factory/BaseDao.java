
/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.db.dao.dao_factory;

import android.os.AsyncTask;

/**
 * Created by lushengbo on 2018/1/3.
 * 数据抽象类
 */

public abstract class BaseDao<T> {

    public abstract void addData(T entity, DataBaseCallBack dataBaseCallBack);

    public abstract void deleteData(String[] strings, DataBaseCallBack dataBaseCallBack);

    public abstract void modifyData(T entity, DataBaseCallBack dataBaseCallBack);

    public static abstract class DataBaseAsyncTask<T> extends AsyncTask<T, Void, Void> {

        private DataBaseCallBack dataBaseCallBack;

        protected DataBaseAsyncTask(DataBaseCallBack dataBaseCallBack) {

            this.dataBaseCallBack = dataBaseCallBack;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataBaseCallBack.onSuccess();
        }

    }
}

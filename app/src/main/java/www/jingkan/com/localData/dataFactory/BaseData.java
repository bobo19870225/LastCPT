/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.dataFactory;

import com.activeandroid.Model;

/**
 * Created by lushengbo on 2018/1/3.
 * 数据抽象类
 */

public abstract class BaseData {
    public abstract <T extends Model> void addData(T model);

    public abstract void deleteData(String... args);

    public abstract<T extends Model> void modifyData(T model);

    public abstract void getData(DataLoadCallBack dataLoadCallBack, String... args);

}

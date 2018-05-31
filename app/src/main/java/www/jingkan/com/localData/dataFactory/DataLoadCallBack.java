/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.dataFactory;

import com.activeandroid.Model;

import java.util.List;

/**
 * Created by lushengbo on 2018/1/3.
 * 加载数据回调接口
 */

public interface DataLoadCallBack {
    <T extends Model> void onDataLoaded(List<T> models);

    void onDataNotAvailable();
}

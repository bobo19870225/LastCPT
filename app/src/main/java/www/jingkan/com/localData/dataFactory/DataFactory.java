/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.dataFactory;

/**
 * Created by lushengbo on 2018/1/3.
 * 数据工厂
 */
@SuppressWarnings("unchecked")
public class DataFactory {
    public static <T extends BaseData> T getBaseData(Class<T> clz) {
        BaseData baseData = null;
        try {
            baseData = (BaseData) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) baseData;
    }

}

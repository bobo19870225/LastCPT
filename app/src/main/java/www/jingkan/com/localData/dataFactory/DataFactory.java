///*
// * Copyright (c) 2018. 代码著作权归卢声波所有。
// */
//
//package www.jingkan.com.localData.dataFactory;
//
///**
// * Created by lushengbo on 2018/1/3.
// * 数据工厂
// */
//@SuppressWarnings("unchecked")
//public class DataFactory {
//    public static <T extends BaseDao> T getBaseData(Class<T> clz) {
//        BaseDao baseDao = null;
//        try {
//            baseDao = (BaseDao) Class.forName(clz.getName()).newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return (T) baseDao;
//    }
//
//}

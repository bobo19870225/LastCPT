///*
// * Copyright (c) 2018. 代码著作权归卢声波所有。
// */
//
//package www.jingkan.com.localData.wirelessTestData;
//
//import com.activeandroid.query.Delete;
//import com.activeandroid.query.Select;
//
//import java.util.List;
//
//import www.jingkan.com.localData.dataFactory.BaseDao;
//import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
//
///**
// * Created by lushengbo on 2018/1/3.
// * 无缆试验数据类
// */
//
//public class WirelessTestDaoDao extends BaseDao<WirelessTestDataModel> {
//
//
//    @Override
//    public void addData(WirelessTestDataModel model) {
//        model.save();
//    }
//
//    @Override
//    public void deleteData(String... args) {
//        if (args.length == 1) {
//            String selection = WirelessTestDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
//
//            new Delete().from(WirelessTestDataModel.class).where(selection, (Object[]) args).execute();
//        }
//    }
//
//    @Override
//    public void modifyData(WirelessTestDataModel model) {
//
//    }
//
//    @Override
//    public void getData(DataLoadCallBack<WirelessTestDataModel> dataLoadCallBack, String... args) {
//        if (args.length == 1) {
//            String selection = WirelessTestDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
//            List<WirelessTestDataModel> wirelessTestDataModels =
//                    new Select().from(WirelessTestDataModel.class)
//                            .where(selection, (Object[]) args).execute();
//
//            if (wirelessTestDataModels != null && wirelessTestDataModels.size() > 0) {
//                dataLoadCallBack.onDataLoaded(wirelessTestDataModels);
//            } else {
//                dataLoadCallBack.onDataNotAvailable();
//            }
//        }
//    }
//
//
//}

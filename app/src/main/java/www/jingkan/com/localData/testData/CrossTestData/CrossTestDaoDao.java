///*
// * Copyright (c) 2018. 代码著作权归卢声波所有。
// */
//
//package www.jingkan.com.localData.testData.CrossTestData;
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
// * 试验数据
// */
//
//public class CrossTestDaoDao extends BaseDao<CrossTestDataModel> {
//
//    @Override
//    public void addData(CrossTestDataModel model) {
//        model.save();
//    }
//
//    @Override
//    public void deleteData(String... args) {
//        if (args.length == 1) {
//            String selection = CrossTestDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
//            new Delete().from(CrossTestDataModel.class).where(selection, (Object[]) args).execute();
//        }
//    }
//
//    @Override
//    public void modifyData(CrossTestDataModel model) {
//
//    }
//
//    @Override
//    public void getData(DataLoadCallBack<CrossTestDataModel> dataLoadCallBack, String... args) {
//
//        if (args.length == 1) {
//            String selection = CrossTestDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?";
//            List<CrossTestDataModel> crossTestDataModels =
//                    new Select().from(CrossTestDataModel.class)
//                            .where(selection, (Object[]) args).execute();
//
//            if (crossTestDataModels != null && crossTestDataModels.size() > 0) {
//                dataLoadCallBack.onDataLoaded(crossTestDataModels);
//            } else {
//                dataLoadCallBack.onDataNotAvailable();
//            }
//        } else if (args.length == 2) {
//            String selection = CrossTestDataConstant.COLUMN_TEST_DATA_ID + " LIKE ?" + " AND "
//                    + CrossTestDataConstant.COLUMN_NUMBER + " LIKE ?";
//            List<CrossTestDataModel> crossTestDataModels =
//                    new Select().from(CrossTestDataModel.class)
//                            .where(selection, (Object[]) args).execute();
//            if (crossTestDataModels != null && crossTestDataModels.size() > 0) {
//                dataLoadCallBack.onDataLoaded(crossTestDataModels);
//            } else {
//                dataLoadCallBack.onDataNotAvailable();
//            }
//        }
//    }
//
//
//}

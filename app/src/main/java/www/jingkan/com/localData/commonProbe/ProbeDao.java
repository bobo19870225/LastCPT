///*
// * Copyright (c) 2018. 代码著作权归卢声波所有。
// */
//
//package www.jingkan.com.localData.commonProbe;
//
//import com.activeandroid.query.Delete;
//import com.activeandroid.query.Select;
//import com.activeandroid.query.Update;
//
//import java.util.List;
//
//import www.jingkan.com.localData.dataFactory.BaseDao;
//import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
//
///**
// * Created by lushengbo on 2018/1/3.
// * 探头数据类
// */
//
//public class ProbeDao extends BaseDao<ProbeModel> {
//
//
//    @Override
//    public void addData(ProbeModel model) {
//        model.save();
//    }
//
//    @Override
//    public void deleteData(String... args) {
//        String selection = ProbeConstant.COLUMN_PROBE_ID + " LIKE ?";
//        new Delete().from(ProbeModel.class).where(selection, (Object[]) args).execute();
//    }
//
//    @Override
//    public void modifyData(ProbeModel model) {
//        Update update = new Update(ProbeModel.class);
//        String add = " = ? , ";
//        String toSet = ProbeConstant.COLUMN_NUMBER + add
//                + ProbeConstant.COLUMN_TYPE + add
//                + ProbeConstant.COLUMN_QC_AREA + add
//                + ProbeConstant.COLUMN_QC_COEFFICIENT + add
//                + ProbeConstant.COLUMN_QC_LIMIT + add
//                + ProbeConstant.COLUMN_FS_AREA + add
//                + ProbeConstant.COLUMN_FS_COEFFICIENT + add
//                + ProbeConstant.COLUMN_FS_LIMIT + " = ?";
//        String selection = ProbeConstant.COLUMN_PROBE_ID + " = ?";
//        update.set(toSet, model.number,
//                model.type,
//                model.qc_area,
//                model.qc_coefficient,
//                model.qc_limit,
//                model.fs_area,
//                model.fs_coefficient,
//                model.fs_limit).where(selection, model.probeID).execute();
//    }
//
//    @Override
//    public void getData(DataLoadCallBack<ProbeModel> dataLoadCallBack, String... args) {
//        if (args.length == 0) {
//            {
//                List<ProbeModel> probeModels = new Select().all().from(ProbeModel.class).execute();
//                if (probeModels != null && probeModels.size() > 0) {
//                    dataLoadCallBack.onDataLoaded(probeModels);
//                } else {
//                    dataLoadCallBack.onDataNotAvailable();
//                }
//            }
//
//        } else if (args.length == 1) {
//            String selection = ProbeConstant.COLUMN_PROBE_ID + " LIKE ?";
//            List<ProbeModel> probeModels =
//                    new Select().from(ProbeModel.class)
//                            .where(selection, (Object[]) args).execute();
//
//            if (probeModels != null && probeModels.size() > 0) {
//                dataLoadCallBack.onDataLoaded(probeModels);
//            } else {
//                dataLoadCallBack.onDataNotAvailable();
//            }
//        }
//    }
//
//
//}

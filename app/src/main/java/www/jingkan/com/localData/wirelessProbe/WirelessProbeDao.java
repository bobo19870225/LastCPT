///*
// * Copyright (c) 2018. 代码著作权归卢声波所有。
// */
//
//package www.jingkan.com.localData.wirelessProbe;
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
//public class WirelessProbeDao extends BaseDao<WirelessProbeModel> {
//
//
//    @Override
//    public void addData(WirelessProbeModel model) {
//        model.save();
//    }
//
//    @Override
//    public void deleteData(String... args) {
//        String selection = WirelessProbeConstant.COLUMN_PROBE_ID + " LIKE ?";
//        new Delete().from(WirelessProbeModel.class).where(selection, (Object[]) args).execute();
//    }
//
//    @Override
//    public void modifyData(WirelessProbeModel model) {
//        Update update = new Update(WirelessProbeModel.class);
//        String add = " = ? , ";
//        String toSet = WirelessProbeConstant.COLUMN_NUMBER + add
//                + WirelessProbeConstant.COLUMN_TYPE + add
//                + WirelessProbeConstant.COLUMN_QC_AREA + add
//                + WirelessProbeConstant.COLUMN_QC_COEFFICIENT + add
//                + WirelessProbeConstant.COLUMN_QC_LIMIT + add
//                + WirelessProbeConstant.COLUMN_FS_AREA + add
//                + WirelessProbeConstant.COLUMN_FS_COEFFICIENT + add
//                + WirelessProbeConstant.COLUMN_FS_LIMIT + " = ?";
//        String selection = WirelessProbeConstant.COLUMN_PROBE_ID + " = ?";
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
//    public void getData(DataLoadCallBack<WirelessProbeModel> dataLoadCallBack, String... args) {
//        if (args.length == 0) {
//            {
//                List<WirelessProbeModel> wirelessProbeModels = new Select().all().from(WirelessProbeModel.class).execute();
//                if (wirelessProbeModels != null && wirelessProbeModels.size() > 0) {
//                    dataLoadCallBack.onDataLoaded(wirelessProbeModels);
//                } else {
//                    dataLoadCallBack.onDataNotAvailable();
//                }
//            }
//
//        } else if (args.length == 1) {
//            String selection = WirelessProbeConstant.COLUMN_PROBE_ID + " LIKE ?";
//            List<WirelessProbeModel> wirelessProbeModels =
//                    new Select().from(WirelessProbeModel.class)
//                            .where(selection, (Object[]) args).execute();
//
//            if (wirelessProbeModels != null && wirelessProbeModels.size() > 0) {
//                dataLoadCallBack.onDataLoaded(wirelessProbeModels);
//            } else {
//                dataLoadCallBack.onDataNotAvailable();
//            }
//        }
//    }
//
//
//}

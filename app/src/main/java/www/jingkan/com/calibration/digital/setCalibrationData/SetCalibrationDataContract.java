/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.digital.setCalibrationData;

import java.util.List;

import www.jingkan.com.localData.memoryData.MemoryDataEntity;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface SetCalibrationDataContract {

    interface View {

        void showNotLinkError();

        void showInitialValue(String value);

        void showEffectiveValue(String value);

        void showProbeParameters(String strNumber,
                                 String strSn,
                                 String strDifferential,
                                 String workArea);

        void showRecordValue(String value);

        void showDifferential(int differential);

        void resetView(String strChannel, List<MemoryDataEntity> memoryDataEntities);
//        void resetView(String strChannel, List<MemoryDataModel> memoryDataModels);

        void showFaChannel();

        void showFaChannelValue(int x, int y, int z);
    }

    interface Presenter {
        void setDataToProbe();

        void resetDataToProbe(int which);

        void initProbeParameters(String sn, boolean isFs, boolean isFa);

        void doRecord();

        void setObliquityInitialValue(int x, int y, int z);

        void linkDevice(String mac);

        /**
         * 切换通道
         *
         * @param which 0：锥尖 1：侧壁 2：测斜
         */
        void switchingChannel(int which);

        void enableShock(boolean isEnable);

    }
}

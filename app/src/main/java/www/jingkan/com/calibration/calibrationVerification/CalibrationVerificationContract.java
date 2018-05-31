/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.calibrationVerification;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface CalibrationVerificationContract {

    interface View {

        void showNotLinkError();

        void showInitialValue(String value);

        void showEffectiveValue(String value);

        void showProbeParameters(String strNumber,
                                 String strSn,
                                 String strDifferential,
                                 String workArea);

        void showLoadingLine(int x, float loadingValue, String forceType);


    }

    interface Presenter {

        void initProbeParameters(String sn, boolean isFS);

        void getInitialValue();

        void doRecord();

        void saveData(String fileName);

        void linkDevice(String mac);

        void enableShock(boolean isEnable);

    }
}

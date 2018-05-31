/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.test;

/**
 * Created by lushengbo on 2017/10/25.
 * 无缆试验接口
 */

public interface WirelessTestContract {
    interface View {

        void showTestParameters(String projectNumber, String holeNumber);

        void showCurrentDepth(double depth);

        void showProbeParameters(double qcCoefficient,
                                 int qcLimit,
                                 double fsCoefficient,
                                 int fsLimit);

    }

    interface Presenter {

        void getTestParameters(String projectNumber, String holeNumber);

        void loadTestData(String testDataID);

        void setShock(boolean isShock);

        void doRecode();
    }
}

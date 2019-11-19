/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.chart;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by lushengbo on 2018/1/4.
 * 标定绘图上下文
 */
@Singleton
public class DrawCalibrationChartHelper extends DrawChartHelper {
    @Inject
    public DrawCalibrationChartHelper() {
    }

    private InterfaceDrawCalibrationChartStrategy mInterfaceDrawCalibrationChartStrategy;
    public void setStrategy(InterfaceDrawCalibrationChartStrategy interfaceDrawCalibrationChartStrategy) {
        mInterfaceDrawCalibrationChartStrategy = interfaceDrawCalibrationChartStrategy;
    }

    public void addOnePointToCalibrationChart(float x, float y, String faceType) {
        mInterfaceDrawCalibrationChartStrategy.addOnePointToCalibrationChart(x, y, faceType);
    }

}

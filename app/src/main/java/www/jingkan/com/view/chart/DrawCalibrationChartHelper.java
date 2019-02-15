/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.chart;

/**
 * Created by lushengbo on 2018/1/4.
 * 标定绘图上下文
 */

public class DrawCalibrationChartHelper extends DrawChartHelper {
    private InterfaceDrawCalibrationChartStrategy mInterfaceDrawCalibrationChartStrategy;

    public void setStrategy(InterfaceDrawCalibrationChartStrategy interfaceDrawCalibrationChartStrategy) {
        mInterfaceDrawCalibrationChartStrategy = interfaceDrawCalibrationChartStrategy;
    }

    public void addOnePointToCalibrationChart(float x, float y, String faceType) {
        mInterfaceDrawCalibrationChartStrategy.addOnePointToCalibrationChart(x, y, faceType);
    }

}

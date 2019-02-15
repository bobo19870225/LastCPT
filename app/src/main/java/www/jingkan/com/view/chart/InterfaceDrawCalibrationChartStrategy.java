/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.chart;

/**
 * Created by lushengbo on 2018/1/4.
 * 标定策略接口
 */

public interface InterfaceDrawCalibrationChartStrategy extends InterfaceDrawChartStrategy {
    void addOnePointToCalibrationChart(float x, float y, String faceType);
}

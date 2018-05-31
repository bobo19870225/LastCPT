/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.chart;

import java.util.List;

/**
 * Created by lushengbo on 2018/1/4.
 * 策略模式的context角色类
 */

public class DrawChartHelper {
    private InterfaceDrawChartStrategy mInterfaceDrawChartStrategy;

    public void setStrategy(InterfaceDrawChartStrategy interfaceDrawChartStrategy) {
        mInterfaceDrawChartStrategy = interfaceDrawChartStrategy;
    }

    public InterfaceDrawChartStrategy getStrategy() {
        return mInterfaceDrawChartStrategy;
    }

    public void addOnePointToChart(float[] data) {
        mInterfaceDrawChartStrategy.addOnePointToChart(data);
    }

    public void addPointsToChart(List<float[]> listData) {
        mInterfaceDrawChartStrategy.addPointsToChart(listData);
    }

    public void upDataSeriesQc(int index, double x, double y) {
        mInterfaceDrawChartStrategy.upDataSeriesQc(index, x, y);
    }

    public void upDataSeriesFs(int index, double x, double y) {
        mInterfaceDrawChartStrategy.upDataSeriesFs(index, x, y);
    }

    public void upDataSeriesFa(int index, double x, double y) {
        mInterfaceDrawChartStrategy.upDataSeriesFa(index, x, y);
    }

    public void cleanChart() {
        mInterfaceDrawChartStrategy.cleanChart();
    }
}

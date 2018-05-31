/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.chart;

import org.achartengine.util.IndexXYMap;

import java.util.List;

/**
 * Created by lushengbo on 2018/1/4.
 * 画图策略
 */

public interface InterfaceDrawChartStrategy {

    void addOnePointToChart(float[] data);

    void addPointsToChart(List<float[]> listData);

    void upDataSeriesQc(int index, double x, double y);

    void upDataSeriesFs(int index, double x, double y);

    void upDataSeriesFa(int index, double x, double y);

    IndexXYMap<Double, Double> getSeriesQcData();

    IndexXYMap<Double, Double> getSeriesFsData();

    IndexXYMap<Double, Double> getSeriesFaData();

    void cleanChart();
}

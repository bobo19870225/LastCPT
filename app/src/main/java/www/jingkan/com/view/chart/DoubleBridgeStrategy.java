/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.chart;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;



import java.util.List;

import www.jingkan.com.view.chart.achartengine.chart.PointStyle;
import www.jingkan.com.view.chart.achartengine.model.XYSeries;
import www.jingkan.com.view.chart.achartengine.renderer.XYSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.util.IndexXYMap;

/**
 * Created by lushengbo on 2018/1/4.
 * 双桥绘图类
 */

public class DoubleBridgeStrategy extends SingleBridgeStrategy {
    private XYSeries xySeriesFs;

    @Override
    public IndexXYMap<Double, Double> getSeriesFsData() {
        return xySeriesFs.getXYMap();

    }

    public DoubleBridgeStrategy(Context context, RelativeLayout relativeLayout) {
        super(context, relativeLayout);
    }

    @Override
    public void addOnePointToChart(float[] data) {
        addOneFsDataToSeries(data);
        super.addOnePointToChart(data);
    }


    private void addOneFsDataToSeries(float[] data) {
        xySeriesFs.add(data[3], data[1]);
    }

    @Override
    public void addPointsToChart(List<float[]> listData) {
        for (float[] points : listData) {
            addOneFsDataToSeries(points);
        }
        super.addPointsToChart(listData);
    }

    @Override
    public void upDataSeriesFs(int index, double x, double y) {
        xySeriesFs.remove(index);
        xySeriesFs.add(index, x, y);
        reDraw();

    }

    @Override
    protected void initChart() {

        super.initChart();
    }

    @Override
    protected void addSeries() {
        super.addSeries();
        XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
        xySeriesRenderer.setColor(Color.BLUE);
        xySeriesRenderer.setFillPoints(true);
        xySeriesRenderer.setPointStyle(PointStyle.POINT);
        xySeriesRenderer.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRenderer);
        xySeriesFs = new XYSeries("fs");
        xySeriesFs.add(0, 0);
        mDataSet.addSeries(xySeriesFs);
    }

    @Override
    protected void setXYTitle() {
        mRenderer.setXTitle("深度（m）");
        mRenderer.setYTitle("qc(MPa)/fs(kPa)");
    }
}

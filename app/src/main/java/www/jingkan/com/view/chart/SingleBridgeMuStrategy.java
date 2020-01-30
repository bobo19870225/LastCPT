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
 * 单桥多功能绘图
 */

public class SingleBridgeMuStrategy extends SingleBridgeStrategy {
    private XYSeries xySeriesFa;

    public SingleBridgeMuStrategy(Context context, RelativeLayout relativeLayout) {
        super(context, relativeLayout);
    }

    @Override
    protected void addSeries() {
        super.addSeries();
        XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
        xySeriesRenderer.setColor(Color.GREEN);
        xySeriesRenderer.setFillPoints(true);
        xySeriesRenderer.setPointStyle(PointStyle.POINT);
        xySeriesRenderer.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRenderer);
        xySeriesFa = new XYSeries("fa");
        xySeriesFa.add(0, 0);
        mDataSet.addSeries(xySeriesFa);
    }


    @Override
    public void addOnePointToChart(float[] data) {
        addOneFaDataToSeries(data);
        super.addOnePointToChart(data);
    }


    private void addOneFaDataToSeries(float[] data) {
        xySeriesFa.add(data[3], data[2]);
    }

    @Override
    public void addPointsToChart(List<float[]> listData) {
        xySeriesFa.clearSeriesValues();
        for (float[] points : listData) {
            addOneFaDataToSeries(points);
        }
//        IndexXYMap<Double, Double> xyMap = xySeriesFa.getXYMap();
        super.addPointsToChart(listData);
    }

    @Override
    public void upDataSeriesFa(int index, double x, double y) {
        xySeriesFa.remove(index);
        xySeriesFa.add(index, x, y);
        reDraw();
    }

    @Override
    public IndexXYMap<Double, Double> getSeriesFaData() {
        return xySeriesFa.getXYMap();
    }

    @Override
    protected void setXYTitle() {
        mRenderer.setXTitle("深度（m）");
        mRenderer.setYTitle("qc(MPa)/fa(Deg)");
    }
}

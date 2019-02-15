/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.chart;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * Created by lushengbo on 2018/1/4.
 * 标定绘图
 */

public class CalibrationStrategy extends SingleBridgeStrategy implements InterfaceDrawCalibrationChartStrategy {
    private XYSeries xySeriesAdd;
    private XYSeries xySeriesSubtract;
    private XYSeries xySerieSubtract1;

    CalibrationStrategy(Context context, RelativeLayout relativeLayout) {
        super(context, relativeLayout);
    }

    @Override
    public void addOnePointToChart(float[] data) {

    }

    @Override
    public void addPointsToChart(List<float[]> listData) {

    }

    @Override
    protected void initChart() {
        XYSeriesRenderer xySeriesRendererAdd = new XYSeriesRenderer();
        xySeriesRendererAdd.setFillPoints(true);
        xySeriesRendererAdd.setColor(Color.BLUE);
        xySeriesRendererAdd.setPointStyle(PointStyle.POINT);
        xySeriesRendererAdd.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRendererAdd);

        XYSeriesRenderer xySeriesRendererSubtract = new XYSeriesRenderer();
        xySeriesRendererSubtract.setFillPoints(true);
        xySeriesRendererAdd.setColor(Color.GREEN);
        xySeriesRendererSubtract.setPointStyle(PointStyle.POINT);
        xySeriesRendererSubtract.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRendererSubtract);

        XYSeriesRenderer xySeriesRendererSubtract1 = new XYSeriesRenderer();
        xySeriesRendererSubtract1.setFillPoints(true);
        xySeriesRendererAdd.setColor(Color.CYAN);
        xySeriesRendererSubtract1.setPointStyle(PointStyle.POINT);
        xySeriesRendererSubtract1.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRendererSubtract);

        xySeriesAdd = new XYSeries("卸荷1");
        xySeriesSubtract = new XYSeries("加荷2");
        xySerieSubtract1 = new XYSeries("卸荷2");

        mDataSet.addSeries(xySeriesAdd);
        mDataSet.addSeries(xySeriesSubtract);
        mDataSet.addSeries(xySerieSubtract1);

        super.initChart();
    }

    @Override
    protected void setXYSeriesTitle() {
        xySeriesQc = new XYSeries("加荷1");
    }

    @Override
    protected void setXYTitle() {
        mRenderer.setXTitle("单位面积荷载(MPa)");
        mRenderer.setYTitle("加卸荷读数(MPa)");
    }

    @Override
    public void addOnePointToCalibrationChart(float x, float y, String faceType) {
        switch (faceType) {
            case "加荷1":
                xySeriesQc.add(x, y);
                break;
            case "卸荷1":
                xySeriesAdd.add(x, y);
                break;
            case "加荷2":
                xySeriesSubtract.add(x, y);
                break;
            case "卸荷2":
                xySerieSubtract1.add(x, y);
                break;
        }
        reDraw();
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.chart;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;

import www.jingkan.com.view.chart.achartengine.chart.PointStyle;
import www.jingkan.com.view.chart.achartengine.model.XYSeries;
import www.jingkan.com.view.chart.achartengine.renderer.XYSeriesRenderer;


/**
 * Created by lushengbo on 2018/1/4.
 * 标定绘图
 */

public class CalibrationStrategy extends SingleBridgeStrategy implements InterfaceDrawCalibrationChartStrategy {
    private XYSeries xySeriesAdd;
    private XYSeries xySeriesSubtract;
    private XYSeries xySeriesSubtract1;

    public CalibrationStrategy(Context context, RelativeLayout relativeLayout) {
        super(context, relativeLayout);
    }

    @Override
    protected void addSeries() {
        super.addSeries();
        XYSeriesRenderer xySeriesRendererAdd = new XYSeriesRenderer();
        xySeriesRendererAdd.setFillPoints(true);
        xySeriesRendererAdd.setColor(Color.GREEN);
        xySeriesRendererAdd.setPointStyle(PointStyle.POINT);
        xySeriesRendererAdd.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRendererAdd);
        xySeriesAdd = new XYSeries("加荷2");
        xySeriesAdd.add(0, 0);
        mDataSet.addSeries(xySeriesAdd);

        XYSeriesRenderer xySeriesRendererSubtract = new XYSeriesRenderer();
        xySeriesRendererSubtract.setColor(Color.BLUE);
        xySeriesRendererSubtract.setFillPoints(true);
        xySeriesRendererSubtract.setPointStyle(PointStyle.POINT);
        xySeriesRendererSubtract.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRendererSubtract);
        xySeriesSubtract = new XYSeries("卸荷1");
        xySeriesSubtract.add(0, 0);
        mDataSet.addSeries(xySeriesSubtract);

        XYSeriesRenderer xySeriesRendererSubtract1 = new XYSeriesRenderer();
        xySeriesRendererSubtract1.setColor(Color.CYAN);
        xySeriesRendererSubtract1.setFillPoints(true);
        xySeriesRendererSubtract1.setPointStyle(PointStyle.POINT);
        xySeriesRendererSubtract1.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRendererSubtract1);
        xySeriesSubtract1 = new XYSeries("卸荷2");
        xySeriesSubtract1.add(0, 0);
        mDataSet.addSeries(xySeriesSubtract1);
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
                xySeriesSubtract.add(x, y);
                break;
            case "加荷2":
                xySeriesAdd.add(x, y);
                break;
            case "卸荷2":
                xySeriesSubtract1.add(x, y);
                break;
        }
        reDraw();
    }
}

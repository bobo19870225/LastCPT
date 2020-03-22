/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.chart;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.List;

import www.jingkan.com.view.chart.achartengine.model.XYSeries;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;

/**
 * Created by lushengbo on 2018/1/9.
 * 十字板绘图
 */

public class CrossStrategy extends SingleBridgeStrategy {
    public CrossStrategy(Context context, RelativeLayout relativeLayout) {
        super(context, relativeLayout);
    }

    @Override
    protected void setOrientation() {
        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
    }

    @Override
    public void addOnePointToChart(float[] data) {
        XYSeries[] series = mDataSet.getSeries();
        if (series.length == 0) {
            addSeries();
        }
        xySeriesQc.add(data[3], data[0]);
        reDraw();
    }

    @Override
    public void addPointsToChart(List<float[]> listData) {
        for (float[] points : listData) {
            xySeriesQc.add(points[3], points[0]);
        }
        reDraw();
    }


    @Override
    protected void setXYSeriesTitle() {
        xySeriesQc = new XYSeries("cu");
    }

    @Override
    protected void setXYTitle() {
        mRenderer.setXTitle("扭转角度（Deg）");
        mRenderer.setYTitle("cu(kPa)");
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.chart;

import android.content.Context;
import android.widget.RelativeLayout;

import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.List;

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

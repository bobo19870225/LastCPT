/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.chart;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.util.IndexXYMap;

import java.util.List;

/**
 * Created by lushengbo on 2018/1/4.
 * 算桥多功能试验绘图
 */

public class DoubleBridgeMultifunctionStrategy extends DoubleBridgeStrategy {

    private XYSeries xySeriesFa;

    @Override
    public IndexXYMap<Double, Double> getSeriesFaData() {
        return xySeriesFa.getXYMap();
    }

    public DoubleBridgeMultifunctionStrategy(Context context, RelativeLayout relativeLayout) {
        super(context, relativeLayout);
    }

    @Override
    protected void addSeries() {
        super.addSeries();
        XYSeriesRenderer xySeriesRendererFa = new XYSeriesRenderer();
        xySeriesRendererFa.setColor(Color.GREEN);
        xySeriesRendererFa.setFillPoints(true);
        xySeriesRendererFa.setPointStyle(PointStyle.POINT);
        xySeriesRendererFa.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRendererFa);
        xySeriesFa = new XYSeries("fa");
        xySeriesFa.add(0, 0);
        mDataSet.addSeries(xySeriesFa);
    }

    @Override
    protected void setXYTitle() {
        mRenderer.setXTitle("深度（m）");
        mRenderer.setYTitle("qc(MPa)/fs(kPa)/fa(Deg)");
    }

    /**
     * 增加一个记录
     *
     * @param data 锥尖，侧壁，斜度，深度。
     */
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
        for (float[] points : listData) {
            addOneFaDataToSeries(points);
        }
//        xySeriesFa.getXYMap();
        super.addPointsToChart(listData);
    }

    @Override
    public void upDataSeriesFa(int index, double x, double y) {
        xySeriesFa.remove(index);
        xySeriesFa.add(index, x, y);
        reDraw();
    }

}

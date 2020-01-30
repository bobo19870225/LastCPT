/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import www.jingkan.com.view.chart.achartengine.ChartFactory;
import www.jingkan.com.view.chart.achartengine.GraphicalView;
import www.jingkan.com.view.chart.achartengine.chart.PointStyle;
import www.jingkan.com.view.chart.achartengine.model.XYMultipleSeriesDataset;
import www.jingkan.com.view.chart.achartengine.model.XYSeries;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.util.IndexXYMap;

/**
 * Created by lushengbo on 2018/1/4.
 * 单桥绘图
 */

public class SingleBridgeStrategy implements InterfaceDrawChartStrategy {
    private Context mContext;
    private RelativeLayout mRelativeLayout;
    XYMultipleSeriesDataset mDataSet = new XYMultipleSeriesDataset();
    XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private GraphicalView mChartView;
    private int X_max = 3;
    private int Y_max = 6;
    XYSeries xySeriesQc;


    public SingleBridgeStrategy(Context context, RelativeLayout relativeLayout) {
        mContext = context;
        mRelativeLayout = relativeLayout;
        initChart();
    }

    @Override
    public void addOnePointToChart(float[] data) {
        addOneQcDataToSeries(data);
        reDraw();
    }

    private void addOneQcDataToSeries(float[] data) {
        float sd = data[data.length - 1];
        xySeriesQc.add(data[3], data[0]);
        if (sd > X_max) {
            X_max += 5;
            coordinateTransformation(X_max, Y_max, X_max - 5);
        } else if (data[0] > Y_max) {
            Y_max += 10;
            coordinateTransformation(X_max, Y_max, X_max - 5);
        }
    }

    @Override
    public void addPointsToChart(List<float[]> listData) {
        xySeriesQc.clearSeriesValues();
        for (float[] points : listData) {
            addOneQcDataToSeries(points);
        }
//        IndexXYMap<Double, Double> xyMap = xySeriesQc.getXYMap();
        reDraw();
    }

    @Override
    public void upDataSeriesQc(int index, double x, double y) {
//        IndexXYMap<Double, Double> xyMap = xySeriesQc.getXYMap();
        xySeriesQc.remove(index);
//        xyMap = xySeriesQc.getXYMap();
        xySeriesQc.add(index, x, y);
//        xyMap = xySeriesQc.getXYMap();
        reDraw();
    }

    @Override
    public void upDataSeriesFs(int index, double x, double y) {

    }

    @Override
    public void upDataSeriesFa(int index, double x, double y) {

    }

    @Override
    public IndexXYMap<Double, Double> getSeriesQcData() {
        return xySeriesQc.getXYMap();
    }

    @Override
    public IndexXYMap<Double, Double> getSeriesFsData() {
        return null;
    }

    @Override
    public IndexXYMap<Double, Double> getSeriesFaData() {
        return null;
    }

    @Override
    public void cleanChart() {
        removeSeries();
    }

    protected void initChart() {
        mRenderer.setAxisTitleTextSize(32);// 设置坐标轴标题文本大小
        mRenderer.setAntialiasing(true);//平滑
        mRenderer.setXLabelsColor(Color.rgb(255, 140, 0));// 设置X轴刻度颜色
        mRenderer.setYLabelsColor(0, Color.rgb(255, 140, 0));// 设置Y轴刻度颜色
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT, 0);// 刻度线与刻度标注之间的相对位置关系
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setLabelsTextSize(40);
        mRenderer.setYAxisMin(-1d);
        mRenderer.setXLabels(8);
        mRenderer.setYLabels(10);
        mRenderer.setShowGrid(true);
        mRenderer.setLegendTextSize(80);
        mRenderer.setFitLegend(true);
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setAxesColor(Color.BLACK);
        mRenderer.setXAxisMin(-1);
        mRenderer.setYAxisMax(15);
        mRenderer.setXAxisMax(10);
        setOrientation();
        mRenderer.setChartTitleTextSize(40);
        mRenderer.setMargins(new int[]{50, 50, 100, 50});//右，上，左，下
        mRenderer.setYLabelsPadding(-10);
        setXYTitle();
        setXYSeriesTitle();
        addSeries();
        reDraw();
    }

    protected void setOrientation() {
        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.VERTICAL);
    }

    protected void addSeries() {
        XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
        xySeriesRenderer.setColor(Color.RED);
        xySeriesRenderer.setFillPoints(true);
        xySeriesRenderer.setPointStyle(PointStyle.POINT);
        xySeriesRenderer.setLineWidth(2.0f);
        mRenderer.addSeriesRenderer(xySeriesRenderer);
        xySeriesQc.add(0, 0);
        mDataSet.addSeries(xySeriesQc);
    }


    /**
     * 清除图表
     */
    private void removeSeries() {
//        mDataSet.clear();
        xySeriesQc.clear();
        reDraw();
    }

    protected void setXYSeriesTitle() {
        xySeriesQc = new XYSeries("qc");
    }

    protected void setXYTitle() {
        mRenderer.setXTitle("深度（m）");
        mRenderer.setYTitle("qc(MPa)");
    }

    /**
     * 更新图表
     */
    void reDraw() {
        if (mChartView == null) {
            mChartView = ChartFactory.getLineChartView(mContext, mDataSet,
                    mRenderer);
            mRelativeLayout.addView(mChartView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            mChartView.repaint();
        }
    }

    /**
     * 坐标变换
     *
     * @param XAxisMax X轴最大值
     * @param YAxisMax Y轴最大值
     * @param XAxisMin X轴最小值
     */
    private void coordinateTransformation(double XAxisMax, double YAxisMax, double XAxisMin) {
        mRenderer.setXAxisMin(XAxisMin);
        mRenderer.setYAxisMax(YAxisMax);
        mRenderer.setXAxisMax(XAxisMax);
    }
}

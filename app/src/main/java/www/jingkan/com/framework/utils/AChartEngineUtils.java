/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

public class AChartEngineUtils {
    private XYMultipleSeriesDataset mDataSet = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private GraphicalView mChartView;
    private RelativeLayout relativeLayout;
    private Context context;
    private int X_max = 3;
    private int Y_max = 6;
    private XYSeries xySeries;
    private XYSeries xySeries1;
    private XYSeries xySeries2;
    private XYSeries xySeries3;

    public AChartEngineUtils(Context context, RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
        this.context = context;
    }

    public void initChart(String type) {
//        mRenderer.setChartTitle(type);
        mRenderer.setAxisTitleTextSize(20);// 设置坐标轴标题文本大小
        mRenderer.setAntialiasing(true);
        mRenderer.setXLabelsColor(Color.rgb(255, 140, 0));// 设置X轴刻度颜色
        mRenderer.setYLabelsColor(0, Color.rgb(255, 140, 0));// 设置Y轴刻度颜色
        mRenderer.setYLabelsAlign(Align.LEFT, 0);// 刻度线与刻度标注之间的相对位置关系
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setLabelsTextSize(20);
        mRenderer.setYAxisMin(-1d);
        mRenderer.setXLabels(8);
        mRenderer.setYLabels(10);
        mRenderer.setShowGrid(true);
        mRenderer.setLegendTextSize(30);
        mRenderer.setFitLegend(true);
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setAxesColor(Color.BLACK);
        mRenderer.setXAxisMin(-1);
        mRenderer.setYAxisMax(15);
        mRenderer.setXAxisMax(10);

        XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
        xySeriesRenderer.setColor(Color.RED);
        xySeriesRenderer.setFillPoints(true);
        xySeriesRenderer.setPointStyle(PointStyle.POINT);
        xySeriesRenderer.setLineWidth(2.0f);

        XYSeriesRenderer xySeriesRenderer1 = new XYSeriesRenderer();
        xySeriesRenderer1.setFillPoints(true);
        xySeriesRenderer1.setPointStyle(PointStyle.POINT);
        xySeriesRenderer1.setLineWidth(2.0f);

        XYSeriesRenderer xySeriesRenderer2 = new XYSeriesRenderer();
        xySeriesRenderer2.setFillPoints(true);
        xySeriesRenderer2.setPointStyle(PointStyle.POINT);
        xySeriesRenderer2.setLineWidth(2.0f);

        XYSeriesRenderer xySeriesRenderer3 = new XYSeriesRenderer();
        xySeriesRenderer3.setFillPoints(true);
        xySeriesRenderer3.setPointStyle(PointStyle.POINT);
        xySeriesRenderer3.setLineWidth(2.0f);

        switch (type) {
            case "单桥试验":
                xySeries = new XYSeries("ps");
                xySeries.add(0, 0);
                mDataSet.addSeries(xySeries);
                mRenderer.setOrientation(Orientation.VERTICAL);
//                mRenderer.setChartTitleTextSize(20);
                mRenderer.setMargins(new int[]{8, 8, 72, 8});//右，上，左，下
                mRenderer.setYLabelsPadding(-20);
                mRenderer.setXTitle("深度（m）");
                mRenderer.setYTitle("ps(MPa)");
                mRenderer.addSeriesRenderer(xySeriesRenderer);
                break;
            case "十字板剪切试验":
                xySeries = new XYSeries("cu");
                xySeries.add(0, 0);
                mDataSet.addSeries(xySeries);
                mRenderer.setOrientation(Orientation.HORIZONTAL);
                mRenderer.setChartTitleTextSize(30);
                mRenderer.setMargins(new int[]{50, 50, 50, 10});
                mRenderer.setXTitle("扭转角度(°)");
                mRenderer.setYTitle("cu(kPa)");
                mRenderer.addSeriesRenderer(xySeriesRenderer);
                break;
            case "双桥试验":
                xySeries = new XYSeries("qc");
                xySeries.add(0, 0);
                xySeries1 = new XYSeries("fs");
                xySeries1.add(0, 0);
                mDataSet.addSeries(xySeries);
                mDataSet.addSeries(xySeries1);
                mRenderer.setOrientation(Orientation.VERTICAL);
                mRenderer.setChartTitleTextSize(20);
                mRenderer.setMargins(new int[]{50, 50, 50, 50});
                mRenderer.setYLabelsPadding(-30);
                mRenderer.setXTitle("深度（m）");
                mRenderer.setYTitle("qc(MPa)/fs(kPa)");
                mRenderer.addSeriesRenderer(xySeriesRenderer);
                xySeriesRenderer1.setColor(Color.BLUE);
                mRenderer.addSeriesRenderer(xySeriesRenderer1);
                break;
            case "单桥测斜试验":
                xySeries = new XYSeries("ps");
                xySeries.add(0, 0);
                xySeries1 = new XYSeries("fa");
                xySeries1.add(0, 0);
                mDataSet.addSeries(xySeries);
                mDataSet.addSeries(xySeries1);
                mRenderer.setOrientation(Orientation.VERTICAL);
                mRenderer.setChartTitleTextSize(20);
                mRenderer.setMargins(new int[]{50, 50, 50, 50});
                mRenderer.setYLabelsPadding(-30);
                mRenderer.setXTitle("深度（m）");
                mRenderer.setYTitle("ps(MPa)/fa(°)");
                mRenderer.addSeriesRenderer(xySeriesRenderer);
                xySeriesRenderer1.setColor(Color.GREEN);
                mRenderer.addSeriesRenderer(xySeriesRenderer1);
                break;
            case "双桥测斜试验":
                xySeries = new XYSeries("qc");
                xySeries.add(0, 0);
                xySeries1 = new XYSeries("fs");
                xySeries1.add(0, 0);
                xySeries2 = new XYSeries("fa");
                xySeries2.add(0, 0);
                mDataSet.addSeries(xySeries);
                mDataSet.addSeries(xySeries1);
                mDataSet.addSeries(xySeries2);
                mRenderer.setOrientation(Orientation.VERTICAL);
                mRenderer.setChartTitleTextSize(20);
                mRenderer.setMargins(new int[]{50, 50, 50, 50});
                mRenderer.setYLabelsPadding(-30);
                mRenderer.setXTitle("深度（m）");
                mRenderer.setYTitle("qc(MPa)/fs(kPa)/fa(°)");
                mRenderer.addSeriesRenderer(xySeriesRenderer);
                xySeriesRenderer1.setColor(Color.BLUE);
                mRenderer.addSeriesRenderer(xySeriesRenderer1);
                xySeriesRenderer2.setColor(Color.GREEN);
                mRenderer.addSeriesRenderer(xySeriesRenderer2);
                break;
            default://标定时
                mRenderer.setXAxisMin(0);
                mRenderer.setYAxisMax(90);
                mRenderer.setXAxisMax(90);

                xySeries = new XYSeries("加荷1");
                xySeries1 = new XYSeries("卸荷1");
                xySeries2 = new XYSeries("加荷2");
                xySeries3 = new XYSeries("卸荷2");

                mDataSet.addSeries(xySeries);
                mDataSet.addSeries(xySeries1);
                mDataSet.addSeries(xySeries2);
                mDataSet.addSeries(xySeries3);

                mRenderer.setOrientation(Orientation.HORIZONTAL);
                mRenderer.setChartTitleTextSize(20);
                mRenderer.setMargins(new int[]{50, 50, 50, 50});
                mRenderer.setYLabelsPadding(-30);
                mRenderer.setXTitle("单位面积荷载(MPa)");
                mRenderer.setYTitle("加卸荷读数(MPa)");
                mRenderer.addSeriesRenderer(xySeriesRenderer);
                xySeriesRenderer1.setColor(Color.BLUE);
                mRenderer.addSeriesRenderer(xySeriesRenderer1);
                xySeriesRenderer2.setColor(Color.GREEN);
                mRenderer.addSeriesRenderer(xySeriesRenderer2);
                xySeriesRenderer3.setColor(Color.YELLOW);
                mRenderer.addSeriesRenderer(xySeriesRenderer3);
                break;
        }
        redraw();
    }

    /**
     * 增加一个记录
     *
     * @param listPoints 锥尖，侧壁，斜度点
     */
    public void addData(List<float[]> listPoints) {
        for (float[] points : listPoints) {
            addOneData(points);
        }
    }

    /**
     * 增加一个记录
     *
     * @param points 锥尖，侧壁，斜度，深度。
     */
    public void addOneData(float[] points) {

        float sd = points[points.length - 1];
        if (sd > X_max) {
            X_max += 5;
            coordinateTransformation(X_max, Y_max, X_max - 5);
        } else if (points[0] > Y_max) {
            Y_max += 10;
            coordinateTransformation(X_max, Y_max, X_max - 5);
        }
        switch (points.length) {
            case 2:
                xySeries.add(points[1], points[0]);
                redraw();
                break;
            case 3:
                xySeries.add(points[2], points[0]);
                xySeries1.add(points[2], points[1]);
                redraw();
                break;
            case 4:
                xySeries.add(points[3], points[0]);
                xySeries1.add(points[3], points[1]);
                xySeries2.add(points[3], points[2]);
                redraw();
                break;
            default:
                break;
        }

    }

    /**
     * 增加一个标定点
     *
     * @param x        单位面积荷载
     * @param y        加卸荷读数
     * @param faceType 受力类型
     */
    public void addOneData(float x, float y, String faceType) {
        switch (faceType) {
            case "加荷1":
                xySeries.add(x, y);
                break;
            case "卸荷1":
                xySeries1.add(x, y);
                break;
            case "加荷2":
                xySeries2.add(x, y);
                break;
            case "卸荷2":
                xySeries3.add(x, y);
                break;
        }
        redraw();
    }


    /**
     * 坐标变换
     *
     * @param XAxisMax X轴最大值
     * @param YAxisMax Y轴最大值
     * @param XAxisMin X轴最小值
     */
    public void coordinateTransformation(double XAxisMax, double YAxisMax, double XAxisMin) {
        mRenderer.setXAxisMin(XAxisMin);
        mRenderer.setYAxisMax(YAxisMax);
        mRenderer.setXAxisMax(XAxisMax);
    }

    /**
     * 更新图表
     */
    private void redraw() {
        if (mChartView == null) {
            mChartView = ChartFactory.getLineChartView(context, mDataSet,
                    mRenderer);
            relativeLayout.addView(mChartView, new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        } else {
            mChartView.repaint();
        }
    }
}

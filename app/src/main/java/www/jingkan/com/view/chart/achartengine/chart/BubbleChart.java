//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import java.util.List;

import www.jingkan.com.view.chart.achartengine.model.XYMultipleSeriesDataset;
import www.jingkan.com.view.chart.achartengine.model.XYValueSeries;
import www.jingkan.com.view.chart.achartengine.renderer.SimpleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYSeriesRenderer;

public class BubbleChart extends XYChart {
    public static final String TYPE = "Bubble";
    private static final int SHAPE_WIDTH = 10;
    private static final int MIN_BUBBLE_SIZE = 2;
    private static final int MAX_BUBBLE_SIZE = 20;

    BubbleChart() {
    }

    public BubbleChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        super(dataset, renderer);
    }

    public void drawSeries(Canvas canvas, Paint paint, List<Float> points, XYSeriesRenderer renderer, float yAxisValue, int seriesIndex, int startIndex) {
        paint.setColor(renderer.getColor());
        paint.setStyle(Style.FILL);
        int length = points.size();
        XYValueSeries series = (XYValueSeries) this.mDataset.getSeriesAt(seriesIndex);
        double max = series.getMaxValue();
        double coef = 20.0D / max;

        for (int i = 0; i < length; i += 2) {
            double size = series.getValue(startIndex + i / 2) * coef + 2.0D;
            this.drawCircle(canvas, paint, (Float) points.get(i), (Float) points.get(i + 1), (float) size);
        }

    }

    protected ClickableArea[] clickableAreasForPoints(List<Float> points, List<Double> values, float yAxisValue, int seriesIndex, int startIndex) {
        int length = points.size();
        XYValueSeries series = (XYValueSeries) this.mDataset.getSeriesAt(seriesIndex);
        double max = series.getMaxValue();
        double coef = 20.0D / max;
        ClickableArea[] ret = new ClickableArea[length / 2];

        for (int i = 0; i < length; i += 2) {
            double size = series.getValue(startIndex + i / 2) * coef + 2.0D;
            ret[i / 2] = new ClickableArea(new RectF((Float) points.get(i) - (float) size, (Float) points.get(i + 1) - (float) size, (Float) points.get(i) + (float) size, (Float) points.get(i + 1) + (float) size), (Double) values.get(i), (Double) values.get(i + 1));
        }

        return ret;
    }

    public int getLegendShapeWidth(int seriesIndex) {
        return 10;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer renderer, float x, float y, int seriesIndex, Paint paint) {
        paint.setStyle(Style.FILL);
        this.drawCircle(canvas, paint, x + 10.0F, y, 3.0F);
    }

    private void drawCircle(Canvas canvas, Paint paint, float x, float y, float radius) {
        canvas.drawCircle(x, y, radius, paint);
    }

    public String getChartType() {
        return "Bubble";
    }
}

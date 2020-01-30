//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import java.util.List;

import www.jingkan.com.view.chart.achartengine.model.XYMultipleSeriesDataset;
import www.jingkan.com.view.chart.achartengine.model.XYSeries;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYSeriesRenderer;

public class RangeBarChart extends BarChart {
    public static final String TYPE = "RangeBar";

    RangeBarChart() {
    }

    RangeBarChart(Type type) {
        super(type);
    }

    public RangeBarChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, Type type) {
        super(dataset, renderer, type);
    }

    public void drawSeries(Canvas canvas, Paint paint, List<Float> points, XYSeriesRenderer seriesRenderer, float yAxisValue, int seriesIndex, int startIndex) {
        int seriesNr = this.mDataset.getSeriesCount();
        int length = points.size();
        paint.setColor(seriesRenderer.getColor());
        paint.setStyle(Style.FILL);
        float halfDiffX = this.getHalfDiffX(points, length, seriesNr);
        int start = 0;
        if (startIndex > 0) {
            start = 2;
        }

        for (int i = start; i < length; i += 4) {
            if (points.size() > i + 3) {
                float xMin = (Float) points.get(i);
                float yMin = (Float) points.get(i + 1);
                float xMax = (Float) points.get(i + 2);
                float yMax = (Float) points.get(i + 3);
                this.drawBar(canvas, xMin, yMin, xMax, yMax, halfDiffX, seriesNr, seriesIndex, paint);
            }
        }

        paint.setColor(seriesRenderer.getColor());
    }

    protected void drawChartValuesText(Canvas canvas, XYSeries series, XYSeriesRenderer renderer, Paint paint, List<Float> points, int seriesIndex, int startIndex) {
        int seriesNr = this.mDataset.getSeriesCount();
        float halfDiffX = this.getHalfDiffX(points, points.size(), seriesNr);
        int start = 0;
        if (startIndex > 0) {
            start = 2;
        }

        for (int i = start; i < points.size(); i += 4) {
            int index = startIndex + i / 2;
            float x = (Float) points.get(i);
            if (this.mType == Type.DEFAULT) {
                x += (float) (seriesIndex * 2) * halfDiffX - ((float) seriesNr - 1.5F) * halfDiffX;
            }

            if (!this.isNullValue(series.getY(index + 1)) && points.size() > i + 3) {
                this.drawText(canvas, this.getLabel(renderer.getChartValuesFormat(), series.getY(index + 1)), x, (Float) points.get(i + 3) - renderer.getChartValuesSpacing(), paint, 0.0F);
            }

            if (!this.isNullValue(series.getY(index)) && points.size() > i + 1) {
                this.drawText(canvas, this.getLabel(renderer.getChartValuesFormat(), series.getY(index)), x, (Float) points.get(i + 1) + renderer.getChartValuesTextSize() + renderer.getChartValuesSpacing() - 3.0F, paint, 0.0F);
            }
        }

    }

    protected float getCoeficient() {
        return 0.5F;
    }

    public String getChartType() {
        return "RangeBar";
    }
}

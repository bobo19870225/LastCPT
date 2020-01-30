//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;

import java.util.List;

import www.jingkan.com.view.chart.achartengine.model.XYMultipleSeriesDataset;
import www.jingkan.com.view.chart.achartengine.model.XYSeries;
import www.jingkan.com.view.chart.achartengine.renderer.SimpleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYSeriesRenderer;

public class BarChart extends XYChart {
    public static final String TYPE = "Bar";
    private static final int SHAPE_WIDTH = 12;
    protected BarChart.Type mType;
    private List<Float> mPreviousSeriesPoints;

    BarChart() {
        this.mType = BarChart.Type.DEFAULT;
    }

    BarChart(BarChart.Type type) {
        this.mType = BarChart.Type.DEFAULT;
        this.mType = type;
    }

    public BarChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, BarChart.Type type) {
        super(dataset, renderer);
        this.mType = BarChart.Type.DEFAULT;
        this.mType = type;
    }

    protected ClickableArea[] clickableAreasForPoints(List<Float> points, List<Double> values, float yAxisValue, int seriesIndex, int startIndex) {
        int seriesNr = this.mDataset.getSeriesCount();
        int length = points.size();
        ClickableArea[] ret = new ClickableArea[length / 2];
        float halfDiffX = this.getHalfDiffX(points, length, seriesNr);

        for (int i = 0; i < length; i += 2) {
            float x = (Float) points.get(i);
            float y = (Float) points.get(i + 1);
            if (this.mType != BarChart.Type.STACKED && this.mType != BarChart.Type.HEAPED) {
                float startX = x - (float) seriesNr * halfDiffX + (float) (seriesIndex * 2) * halfDiffX;
                ret[i / 2] = new ClickableArea(new RectF(startX, Math.min(y, yAxisValue), startX + 2.0F * halfDiffX, Math.max(y, yAxisValue)), (Double) values.get(i), (Double) values.get(i + 1));
            } else {
                ret[i / 2] = new ClickableArea(new RectF(x - halfDiffX, Math.min(y, yAxisValue), x + halfDiffX, Math.max(y, yAxisValue)), (Double) values.get(i), (Double) values.get(i + 1));
            }
        }

        return ret;
    }

    public void drawSeries(Canvas canvas, Paint paint, List<Float> points, XYSeriesRenderer seriesRenderer, float yAxisValue, int seriesIndex, int startIndex) {
        int seriesNr = this.mDataset.getSeriesCount();
        int length = points.size();
        paint.setColor(seriesRenderer.getColor());
        paint.setStyle(Style.FILL);
        float halfDiffX = this.getHalfDiffX(points, length, seriesNr);

        for (int i = 0; i < length; i += 2) {
            float x = (Float) points.get(i);
            float y = (Float) points.get(i + 1);
            if (this.mType == BarChart.Type.HEAPED && seriesIndex > 0) {
                float lastY = (Float) this.mPreviousSeriesPoints.get(i + 1);
                y += lastY - yAxisValue;
                points.set(i + 1, y);
                this.drawBar(canvas, x, lastY, x, y, halfDiffX, seriesNr, seriesIndex, paint);
            } else {
                this.drawBar(canvas, x, yAxisValue, x, y, halfDiffX, seriesNr, seriesIndex, paint);
            }
        }

        paint.setColor(seriesRenderer.getColor());
        this.mPreviousSeriesPoints = points;
    }

    protected void drawBar(Canvas canvas, float xMin, float yMin, float xMax, float yMax, float halfDiffX, int seriesNr, int seriesIndex, Paint paint) {
        int scale = this.mDataset.getSeriesAt(seriesIndex).getScaleNumber();
        if (this.mType != BarChart.Type.STACKED && this.mType != BarChart.Type.HEAPED) {
            float startX = xMin - (float) seriesNr * halfDiffX + (float) (seriesIndex * 2) * halfDiffX;
            this.drawBar(canvas, startX, yMax, startX + 2.0F * halfDiffX, yMin, scale, seriesIndex, paint);
        } else {
            this.drawBar(canvas, xMin - halfDiffX, yMax, xMax + halfDiffX, yMin, scale, seriesIndex, paint);
        }

    }

    protected void drawBar(Canvas canvas, float xMin, float yMin, float xMax, float yMax, int scale, int seriesIndex, Paint paint) {
        float temp;
        if (xMin > xMax) {
            temp = xMin;
            xMin = xMax;
            xMax = temp;
        }

        if (yMin > yMax) {
            temp = yMin;
            yMin = yMax;
            yMax = temp;
        }

        SimpleSeriesRenderer renderer = this.mRenderer.getSeriesRendererAt(seriesIndex);
        if (renderer.isGradientEnabled()) {
            float minY = (float) this.toScreenPoint(new double[]{0.0D, renderer.getGradientStopValue()}, scale)[1];
            float maxY = (float) this.toScreenPoint(new double[]{0.0D, renderer.getGradientStartValue()}, scale)[1];
            float gradientMinY = Math.max(minY, Math.min(yMin, yMax));
            float gradientMaxY = Math.min(maxY, Math.max(yMin, yMax));
            int gradientMinColor = renderer.getGradientStopColor();
            int gradientMaxColor = renderer.getGradientStartColor();
            int gradientStartColor = gradientMaxColor;
            int gradientStopColor = gradientMinColor;
            if (yMin < minY) {
                paint.setColor(gradientMinColor);
                canvas.drawRect((float) Math.round(xMin), (float) Math.round(yMin), (float) Math.round(xMax), (float) Math.round(gradientMinY), paint);
            } else {
                gradientStopColor = this.getGradientPartialColor(gradientMinColor, gradientMaxColor, (maxY - gradientMinY) / (maxY - minY));
            }

            if (yMax > maxY) {
                paint.setColor(gradientMaxColor);
                canvas.drawRect((float) Math.round(xMin), (float) Math.round(gradientMaxY), (float) Math.round(xMax), (float) Math.round(yMax), paint);
            } else {
                gradientStartColor = this.getGradientPartialColor(gradientMaxColor, gradientMinColor, (gradientMaxY - minY) / (maxY - minY));
            }

            GradientDrawable gradient = new GradientDrawable(Orientation.BOTTOM_TOP, new int[]{gradientStartColor, gradientStopColor});
            gradient.setBounds(Math.round(xMin), Math.round(gradientMinY), Math.round(xMax), Math.round(gradientMaxY));
            gradient.draw(canvas);
        } else {
            if (Math.abs(yMin - yMax) < 1.0F) {
                if (yMin < yMax) {
                    yMax = yMin + 1.0F;
                } else {
                    yMax = yMin - 1.0F;
                }
            }

            canvas.drawRect((float) Math.round(xMin), (float) Math.round(yMin), (float) Math.round(xMax), (float) Math.round(yMax), paint);
        }

    }

    protected int getGradientPartialColor(int minColor, int maxColor, float fraction) {
        int alpha = Math.round(fraction * (float) Color.alpha(minColor) + (1.0F - fraction) * (float) Color.alpha(maxColor));
        int r = Math.round(fraction * (float) Color.red(minColor) + (1.0F - fraction) * (float) Color.red(maxColor));
        int g = Math.round(fraction * (float) Color.green(minColor) + (1.0F - fraction) * (float) Color.green(maxColor));
        int b = Math.round(fraction * (float) Color.blue(minColor) + (1.0F - fraction) * (float) Color.blue(maxColor));
        return Color.argb(alpha, r, g, b);
    }

    protected void drawChartValuesText(Canvas canvas, XYSeries series, XYSeriesRenderer renderer, Paint paint, List<Float> points, int seriesIndex, int startIndex) {
        int seriesNr = this.mDataset.getSeriesCount();
        int length = points.size();
        float halfDiffX = this.getHalfDiffX(points, length, seriesNr);

        for (int i = 0; i < length; i += 2) {
            int index = startIndex + i / 2;
            double value = series.getY(index);
            if (!this.isNullValue(value)) {
                float x = (Float) points.get(i);
                if (this.mType == BarChart.Type.DEFAULT) {
                    x += (float) (seriesIndex * 2) * halfDiffX - ((float) seriesNr - 1.5F) * halfDiffX;
                }

                if (value >= 0.0D) {
                    this.drawText(canvas, this.getLabel(renderer.getChartValuesFormat(), value), x, (Float) points.get(i + 1) - renderer.getChartValuesSpacing(), paint, 0.0F);
                } else {
                    this.drawText(canvas, this.getLabel(renderer.getChartValuesFormat(), value), x, (Float) points.get(i + 1) + renderer.getChartValuesTextSize() + renderer.getChartValuesSpacing() - 3.0F, paint, 0.0F);
                }
            }
        }

    }

    public int getLegendShapeWidth(int seriesIndex) {
        return 12;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer renderer, float x, float y, int seriesIndex, Paint paint) {
        float halfShapeWidth = 6.0F;
        canvas.drawRect(x, y - halfShapeWidth, x + 12.0F, y + halfShapeWidth, paint);
    }

    protected float getHalfDiffX(List<Float> points, int length, int seriesNr) {
        float barWidth = this.mRenderer.getBarWidth();
        if (barWidth > 0.0F) {
            return barWidth / 2.0F;
        } else {
            int div = length;
            if (length > 2) {
                div = length - 2;
            }

            float halfDiffX = ((Float) points.get(length - 2) - (Float) points.get(0)) / (float) div;
            if (halfDiffX == 0.0F) {
                halfDiffX = 10.0F;
            }

            if (this.mType != BarChart.Type.STACKED && this.mType != BarChart.Type.HEAPED) {
                halfDiffX /= (float) seriesNr;
            }

            return (float) ((double) halfDiffX / ((double) this.getCoeficient() * (1.0D + this.mRenderer.getBarSpacing())));
        }
    }

    protected float getCoeficient() {
        return 1.0F;
    }

    protected boolean isRenderNullValues() {
        return true;
    }

    public double getDefaultMinimum() {
        return 0.0D;
    }

    public String getChartType() {
        return "Bar";
    }

    public static enum Type {
        DEFAULT,
        STACKED,
        HEAPED;

        private Type() {
        }
    }
}

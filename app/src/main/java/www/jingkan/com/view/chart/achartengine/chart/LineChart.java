//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.view.chart.achartengine.model.XYMultipleSeriesDataset;
import www.jingkan.com.view.chart.achartengine.renderer.SimpleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYSeriesRenderer;

public class LineChart extends XYChart {
    public static final String TYPE = "Line";
    private static final int SHAPE_WIDTH = 30;
    private ScatterChart pointsChart;

    LineChart() {
    }

    public LineChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        super(dataset, renderer);
        this.pointsChart = new ScatterChart(dataset, renderer);
    }

    protected void setDatasetRenderer(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        super.setDatasetRenderer(dataset, renderer);
        this.pointsChart = new ScatterChart(dataset, renderer);
    }

    public void drawSeries(Canvas canvas, Paint paint, List<Float> points, XYSeriesRenderer renderer, float yAxisValue, int seriesIndex, int startIndex) {
        float lineWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(renderer.getLineWidth());
        XYSeriesRenderer.FillOutsideLine[] fillOutsideLine = renderer.getFillOutsideLine();
        XYSeriesRenderer.FillOutsideLine[] arr$ = fillOutsideLine;
        int len$ = fillOutsideLine.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            XYSeriesRenderer.FillOutsideLine fill = arr$[i$];
            if (fill.getType() != XYSeriesRenderer.FillOutsideLine.Type.NONE) {
                paint.setColor(fill.getColor());
                List<Float> fillPoints = new ArrayList();
                int[] range = fill.getFillRange();
                if (range == null) {
                    fillPoints.addAll(points);
                } else if (points.size() > range[0] * 2 && points.size() > range[1] * 2) {
                    fillPoints.addAll(points.subList(range[0] * 2, range[1] * 2));
                }

                float referencePoint;
                switch (fill.getType()) {
                    case BOUNDS_ALL:
                        referencePoint = yAxisValue;
                        break;
                    case BOUNDS_BELOW:
                        referencePoint = yAxisValue;
                        break;
                    case BOUNDS_ABOVE:
                        referencePoint = yAxisValue;
                        break;
                    case BELOW:
                        referencePoint = (float) canvas.getHeight();
                        break;
                    case ABOVE:
                        referencePoint = 0.0F;
                        break;
                    default:
                        throw new RuntimeException("You have added a new type of filling but have not implemented.");
                }

                if (fill.getType() == XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ABOVE || fill.getType() == XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_BELOW) {
                    List<Float> boundsPoints = new ArrayList();
                    boolean add = false;
                    int length = fillPoints.size();
                    if (length > 0 && fill.getType() == XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ABOVE && (Float) fillPoints.get(1) < referencePoint || fill.getType() == XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_BELOW && (Float) fillPoints.get(1) > referencePoint) {
                        boundsPoints.add(fillPoints.get(0));
                        boundsPoints.add(fillPoints.get(1));
                        add = true;
                    }

                    for (int i = 3; i < length; i += 2) {
                        float prevValue = (Float) fillPoints.get(i - 2);
                        float value = (Float) fillPoints.get(i);
                        if (prevValue < referencePoint && value > referencePoint || prevValue > referencePoint && value < referencePoint) {
                            float prevX = (Float) fillPoints.get(i - 3);
                            float x = (Float) fillPoints.get(i - 1);
                            boundsPoints.add(prevX + (x - prevX) * (referencePoint - prevValue) / (value - prevValue));
                            boundsPoints.add(referencePoint);
                            if ((fill.getType() != XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ABOVE || value <= referencePoint) && (fill.getType() != XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_BELOW || value >= referencePoint)) {
                                boundsPoints.add(x);
                                boundsPoints.add(value);
                                add = true;
                            } else {
                                i += 2;
                                add = false;
                            }
                        } else if (add || fill.getType() == XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ABOVE && value < referencePoint || fill.getType() == XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_BELOW && value > referencePoint) {
                            boundsPoints.add(fillPoints.get(i - 1));
                            boundsPoints.add(value);
                        }
                    }

                    fillPoints.clear();
                    fillPoints.addAll(boundsPoints);
                }

                int length = fillPoints.size();
                if (length > 0) {
                    fillPoints.set(0, fillPoints.get(0) + 1.0F);
                    fillPoints.add(fillPoints.get(length - 2));
                    fillPoints.add(referencePoint);
                    fillPoints.add(fillPoints.get(0));
                    fillPoints.add(fillPoints.get(length + 1));

                    for (int i = 0; i < length + 4; i += 2) {
                        if (fillPoints.get(i + 1) < 0.0F) {
                            fillPoints.set(i + 1, 0.0F);
                        }
                    }

                    paint.setStyle(Style.FILL);
                    this.drawPath(canvas, fillPoints, paint, true);
                }
            }
        }

        paint.setColor(renderer.getColor());
        paint.setStyle(Style.STROKE);
        this.drawPath(canvas, points, paint, false);
        paint.setStrokeWidth(lineWidth);
    }

    protected ClickableArea[] clickableAreasForPoints(List<Float> points, List<Double> values, float yAxisValue, int seriesIndex, int startIndex) {
        int length = points.size();
        ClickableArea[] ret = new ClickableArea[length / 2];

        for (int i = 0; i < length; i += 2) {
            int selectableBuffer = this.mRenderer.getSelectableBuffer();
            ret[i / 2] = new ClickableArea(new RectF((Float) points.get(i) - (float) selectableBuffer, (Float) points.get(i + 1) - (float) selectableBuffer, (Float) points.get(i) + (float) selectableBuffer, (Float) points.get(i + 1) + (float) selectableBuffer), (Double) values.get(i), (Double) values.get(i + 1));
        }

        return ret;
    }

    public int getLegendShapeWidth(int seriesIndex) {
        return 30;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer renderer, float x, float y, int seriesIndex, Paint paint) {
        float oldWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(((XYSeriesRenderer) renderer).getLineWidth());
        canvas.drawLine(x, y, x + 30.0F, y, paint);
        paint.setStrokeWidth(oldWidth);
        if (this.isRenderPoints(renderer)) {
            this.pointsChart.drawLegendShape(canvas, renderer, x + 5.0F, y, seriesIndex, paint);
        }

    }

    public boolean isRenderPoints(SimpleSeriesRenderer renderer) {
        return ((XYSeriesRenderer) renderer).getPointStyle() != PointStyle.POINT;

    }

    public ScatterChart getPointsChart() {
        return this.pointsChart;
    }

    public String getChartType() {
        return "Line";
    }
}

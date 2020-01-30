//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.SortedMap;

import www.jingkan.com.view.chart.achartengine.model.Point;
import www.jingkan.com.view.chart.achartengine.model.SeriesSelection;
import www.jingkan.com.view.chart.achartengine.model.XYMultipleSeriesDataset;
import www.jingkan.com.view.chart.achartengine.model.XYSeries;
import www.jingkan.com.view.chart.achartengine.renderer.BasicStroke;
import www.jingkan.com.view.chart.achartengine.renderer.SimpleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.util.MathHelper;


public abstract class XYChart extends AbstractChart {
    protected XYMultipleSeriesDataset mDataset;
    protected XYMultipleSeriesRenderer mRenderer;
    private float mScale;
    private float mTranslate;
    private Point mCenter;
    private Rect mScreenR;
    private final Map<Integer, double[]> mCalcRange = new HashMap();
    private Map<Integer, List<ClickableArea>> clickableAreas = new HashMap();

    protected XYChart() {
    }

    public XYChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        this.mDataset = dataset;
        this.mRenderer = renderer;
    }

    protected void setDatasetRenderer(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        this.mDataset = dataset;
        this.mRenderer = renderer;
    }

    public void draw(Canvas canvas, int x, int y, int width, int height, Paint paint) {
        paint.setAntiAlias(this.mRenderer.isAntialiasing());
        int legendSize = this.getLegendSize(this.mRenderer, height / 5, this.mRenderer.getAxisTitleTextSize());
        int[] margins = this.mRenderer.getMargins();
        int left = x + margins[1];
        int top = y + margins[0];
        int right = x + width - margins[3];
        int sLength = this.mDataset.getSeriesCount();
        String[] titles = new String[sLength];

        int bottom;
        for (bottom = 0; bottom < sLength; ++bottom) {
            titles[bottom] = this.mDataset.getSeriesAt(bottom).getTitle();
        }

        if (this.mRenderer.isFitLegend() && this.mRenderer.isShowLegend()) {
            legendSize = this.drawLegend(canvas, this.mRenderer, titles, left, right, y, width, height, legendSize, paint, true);
        }

        bottom = y + height - margins[2] - legendSize;
        if (this.mScreenR == null) {
            this.mScreenR = new Rect();
        }

        this.mScreenR.set(left, top, right, bottom);
        this.drawBackground(this.mRenderer, canvas, x, y, width, height, paint, false, 0);
        if (paint.getTypeface() == null || this.mRenderer.getTextTypeface() != null && paint.getTypeface().equals(this.mRenderer.getTextTypeface()) || !paint.getTypeface().toString().equals(this.mRenderer.getTextTypefaceName()) || paint.getTypeface().getStyle() != this.mRenderer.getTextTypefaceStyle()) {
            if (this.mRenderer.getTextTypeface() != null) {
                paint.setTypeface(this.mRenderer.getTextTypeface());
            } else {
                paint.setTypeface(Typeface.create(this.mRenderer.getTextTypefaceName(), this.mRenderer.getTextTypefaceStyle()));
            }
        }

        XYMultipleSeriesRenderer.Orientation or = this.mRenderer.getOrientation();
        if (or == XYMultipleSeriesRenderer.Orientation.VERTICAL) {
            right -= legendSize;
            bottom += legendSize - 20;
        }

        int angle = or.getAngle();
        boolean rotate = angle == 90;
        this.mScale = (float) height / (float) width;
        this.mTranslate = (float) (Math.abs(width - height) / 2);
        if (this.mScale < 1.0F) {
            this.mTranslate *= -1.0F;
        }

        this.mCenter = new Point((float) ((x + width) / 2), (float) ((y + height) / 2));
        if (rotate) {
            this.transform(canvas, (float) angle, false);
        }

        int maxScaleNumber = -2147483647;

        for (int i = 0; i < sLength; ++i) {
            maxScaleNumber = Math.max(maxScaleNumber, this.mDataset.getSeriesAt(i).getScaleNumber());
        }

        ++maxScaleNumber;
        if (maxScaleNumber >= 0) {
            double[] minX = new double[maxScaleNumber];
            double[] maxX = new double[maxScaleNumber];
            double[] minY = new double[maxScaleNumber];
            double[] maxY = new double[maxScaleNumber];
            boolean[] isMinXSet = new boolean[maxScaleNumber];
            boolean[] isMaxXSet = new boolean[maxScaleNumber];
            boolean[] isMinYSet = new boolean[maxScaleNumber];
            boolean[] isMaxYSet = new boolean[maxScaleNumber];

            for (int i = 0; i < maxScaleNumber; ++i) {
                minX[i] = this.mRenderer.getXAxisMin(i);
                maxX[i] = this.mRenderer.getXAxisMax(i);
                minY[i] = this.mRenderer.getYAxisMin(i);
                maxY[i] = this.mRenderer.getYAxisMax(i);
                isMinXSet[i] = this.mRenderer.isMinXSet(i);
                isMaxXSet[i] = this.mRenderer.isMaxXSet(i);
                isMinYSet[i] = this.mRenderer.isMinYSet(i);
                isMaxYSet[i] = this.mRenderer.isMaxYSet(i);
                if (this.mCalcRange.get(i) == null) {
                    this.mCalcRange.put(i, new double[4]);
                }
            }

            double[] xPixelsPerUnit = new double[maxScaleNumber];
            double[] yPixelsPerUnit = new double[maxScaleNumber];

            int i;
            for (i = 0; i < sLength; ++i) {
                XYSeries series = this.mDataset.getSeriesAt(i);
                int scale = series.getScaleNumber();
                if (series.getItemCount() != 0) {
                    double maximumY;
                    if (!isMinXSet[scale]) {
                        maximumY = series.getMinX();
                        minX[scale] = Math.min(minX[scale], maximumY);
                        this.mCalcRange.get(scale)[0] = minX[scale];
                    }

                    if (!isMaxXSet[scale]) {
                        maximumY = series.getMaxX();
                        maxX[scale] = Math.max(maxX[scale], maximumY);
                        Objects.requireNonNull(this.mCalcRange.get(scale))[1] = maxX[scale];
                    }

                    if (!isMinYSet[scale]) {
                        maximumY = series.getMinY();
                        minY[scale] = Math.min(minY[scale], (double) ((float) maximumY));
                        this.mCalcRange.get(scale)[2] = minY[scale];
                    }

                    if (!isMaxYSet[scale]) {
                        maximumY = series.getMaxY();
                        maxY[scale] = Math.max(maxY[scale], (double) ((float) maximumY));
                        Objects.requireNonNull(this.mCalcRange.get(scale))[3] = maxY[scale];
                    }
                }
            }

            for (i = 0; i < maxScaleNumber; ++i) {
                if (maxX[i] - minX[i] != 0.0D) {
                    xPixelsPerUnit[i] = (double) (right - left) / (maxX[i] - minX[i]);
                }

                if (maxY[i] - minY[i] != 0.0D) {
                    yPixelsPerUnit[i] = (double) ((float) ((double) (bottom - top) / (maxY[i] - minY[i])));
                }

                if (i > 0) {
                    xPixelsPerUnit[i] = xPixelsPerUnit[0];
                    minX[i] = minX[0];
                    maxX[i] = maxX[0];
                }
            }

            boolean hasValues = false;
            this.clickableAreas = new HashMap();

            float yLabel;
            int len$;
            for (i = 0; i < sLength; ++i) {
                XYSeries series = this.mDataset.getSeriesAt(i);
                int scale = series.getScaleNumber();
                if (series.getItemCount() != 0) {
                    hasValues = true;
                    XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) this.mRenderer.getSeriesRendererAt(i);
                    List<Float> points = new ArrayList();
                    List<Double> values = new ArrayList();
                    float yAxisValue = Math.min((float) bottom, (float) ((double) bottom + yPixelsPerUnit[scale] * minY[scale]));
                    LinkedList<ClickableArea> clickableArea = new LinkedList();
                    this.clickableAreas.put(i, clickableArea);
                    synchronized (series) {
                        SortedMap<Double, Double> range = series.getRange(minX[scale], maxX[scale], seriesRenderer.isDisplayBoundingPoints());
                        int startIndex = -1;
                        Iterator i$ = range.entrySet().iterator();

                        while (i$.hasNext()) {
                            Entry<Double, Double> value = (Entry) i$.next();
                            double xValue = (Double) value.getKey();
                            double yValue = (Double) value.getValue();
                            if (startIndex < 0 && (!this.isNullValue(yValue) || this.isRenderNullValues())) {
                                startIndex = series.getIndexForKey(xValue);
                            }

                            values.add(value.getKey());
                            values.add(value.getValue());
                            if (!this.isNullValue(yValue)) {
                                points.add((float) ((double) left + xPixelsPerUnit[scale] * (xValue - minX[scale])));
                                points.add((float) ((double) bottom - yPixelsPerUnit[scale] * (yValue - minY[scale])));
                            } else if (this.isRenderNullValues()) {
                                points.add((float) ((double) left + xPixelsPerUnit[scale] * (xValue - minX[scale])));
                                points.add((float) ((double) bottom - yPixelsPerUnit[scale] * -minY[scale]));
                            } else {
                                if (points.size() > 0) {
                                    this.drawSeries(series, canvas, paint, points, seriesRenderer, yAxisValue, i, or, startIndex);
                                    ClickableArea[] clickableAreasForSubSeries = this.clickableAreasForPoints(points, values, yAxisValue, i, startIndex);
                                    clickableArea.addAll(Arrays.asList(clickableAreasForSubSeries));
                                    points.clear();
                                    values.clear();
                                    startIndex = -1;
                                }

                                clickableArea.add(null);
                            }
                        }

                        len$ = series.getAnnotationCount();
                        if (len$ > 0) {
                            paint.setColor(seriesRenderer.getAnnotationsColor());
                            paint.setTextSize(seriesRenderer.getAnnotationsTextSize());
                            paint.setTextAlign(seriesRenderer.getAnnotationsTextAlign());
                            Rect bound = new Rect();

                            for (int j = 0; j < len$; ++j) {
                                yLabel = (float) ((double) left + xPixelsPerUnit[scale] * (series.getAnnotationX(j) - minX[scale]));
                                float yS = (float) ((double) bottom - yPixelsPerUnit[scale] * (series.getAnnotationY(j) - minY[scale]));
                                paint.getTextBounds(series.getAnnotationAt(j), 0, series.getAnnotationAt(j).length(), bound);
                                if (yLabel < yLabel + (float) bound.width() && yS < (float) canvas.getHeight()) {
                                    this.drawString(canvas, series.getAnnotationAt(j), yLabel, yS, paint);
                                }
                            }
                        }

                        if (points.size() > 0) {
                            this.drawSeries(series, canvas, paint, points, seriesRenderer, yAxisValue, i, or, startIndex);
                            ClickableArea[] clickableAreasForSubSeries = this.clickableAreasForPoints(points, values, yAxisValue, i, startIndex);
                            clickableArea.addAll(Arrays.asList(clickableAreasForSubSeries));
                        }
                    }
                }
            }

            this.drawBackground(this.mRenderer, canvas, x, bottom, width, height - bottom, paint, true, this.mRenderer.getMarginsColor());
            this.drawBackground(this.mRenderer, canvas, x, y, width, margins[0], paint, true, this.mRenderer.getMarginsColor());
            if (or == XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
                this.drawBackground(this.mRenderer, canvas, x, y, left - x, height - y, paint, true, this.mRenderer.getMarginsColor());
                this.drawBackground(this.mRenderer, canvas, right, y, margins[3], height - y, paint, true, this.mRenderer.getMarginsColor());
            } else if (or == XYMultipleSeriesRenderer.Orientation.VERTICAL) {
                this.drawBackground(this.mRenderer, canvas, right, y, width - right, height - y, paint, true, this.mRenderer.getMarginsColor());
                this.drawBackground(this.mRenderer, canvas, x, y, left - x, height - y, paint, true, this.mRenderer.getMarginsColor());
            }

            boolean showLabels = this.mRenderer.isShowLabels() && hasValues;
            boolean showGridX = this.mRenderer.isShowGridX();
            boolean showTickMarks = this.mRenderer.isShowTickMarks();
            boolean showCustomTextGridY = this.mRenderer.isShowCustomTextGridY();
            if (showLabels || showGridX) {
                List<Double> xLabels = this.getValidLabels(this.getXLabels(minX[0], maxX[0], this.mRenderer.getXLabels()));
                Map<Integer, List<Double>> allYLabels = this.getYLabels(minY, maxY, maxScaleNumber);
                if (showLabels) {
                    paint.setColor(this.mRenderer.getXLabelsColor());
                    paint.setTextSize(this.mRenderer.getLabelsTextSize());
                    paint.setTextAlign(this.mRenderer.getXLabelsAlign());
                }

                this.drawXLabels(xLabels, this.mRenderer.getXTextLabelLocations(), canvas, paint, left, top, bottom, xPixelsPerUnit[0], minX[0], maxX[0]);
                this.drawYLabels(allYLabels, canvas, paint, maxScaleNumber, left, right, bottom, yPixelsPerUnit, minY);
                if (showLabels) {
                    paint.setColor(this.mRenderer.getLabelsColor());

                    for (i = 0; i < maxScaleNumber; ++i) {
                        Align axisAlign = this.mRenderer.getYAxisAlign(i);
                        Double[] yTextLabelLocations = this.mRenderer.getYTextLabelLocations(i);
                        Double[] arr$ = yTextLabelLocations;
                        len$ = yTextLabelLocations.length;

                        for (int i$ = 0; i$ < len$; ++i$) {
                            Double location = arr$[i$];
                            if (minY[i] <= location && location <= maxY[i]) {
                                yLabel = (float) ((double) bottom - yPixelsPerUnit[i] * (location - minY[i]));
                                String label = this.mRenderer.getYTextLabel(location, i);
                                paint.setColor(this.mRenderer.getYLabelsColor(i));
                                paint.setTextAlign(this.mRenderer.getYLabelsAlign(i));
                                if (or == XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
                                    if (axisAlign == Align.LEFT) {
                                        if (showTickMarks) {
                                            canvas.drawLine((float) (left + this.getLabelLinePos(axisAlign)), yLabel, (float) left, yLabel, paint);
                                        }

                                        this.drawText(canvas, label, (float) left - this.mRenderer.getYLabelsPadding(), yLabel - this.mRenderer.getYLabelsVerticalPadding(), paint, this.mRenderer.getYLabelsAngle());
                                    } else {
                                        if (showTickMarks) {
                                            canvas.drawLine((float) right, yLabel, (float) (right + this.getLabelLinePos(axisAlign)), yLabel, paint);
                                        }

                                        this.drawText(canvas, label, (float) right - this.mRenderer.getYLabelsPadding(), yLabel - this.mRenderer.getYLabelsVerticalPadding(), paint, this.mRenderer.getYLabelsAngle());
                                    }

                                    if (showCustomTextGridY) {
                                        paint.setColor(this.mRenderer.getGridColor(i));
                                        canvas.drawLine((float) left, yLabel, (float) right, yLabel, paint);
                                    }
                                } else {
                                    if (showTickMarks) {
                                        canvas.drawLine((float) (right - this.getLabelLinePos(axisAlign)), yLabel, (float) right, yLabel, paint);
                                    }

                                    this.drawText(canvas, label, (float) (right + 10), yLabel - this.mRenderer.getYLabelsVerticalPadding(), paint, this.mRenderer.getYLabelsAngle());
                                    if (showCustomTextGridY) {
                                        paint.setColor(this.mRenderer.getGridColor(i));
                                        canvas.drawLine((float) right, yLabel, (float) left, yLabel, paint);
                                    }
                                }
                            }
                        }
                    }
                }

                if (showLabels) {
                    paint.setColor(this.mRenderer.getLabelsColor());
                    float size = this.mRenderer.getAxisTitleTextSize();
                    paint.setTextSize(size);
                    paint.setTextAlign(Align.CENTER);
                    if (or == XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
                        this.drawText(canvas, this.mRenderer.getXTitle(), (float) (x + width / 2), (float) bottom + this.mRenderer.getLabelsTextSize() * 4.0F / 3.0F + this.mRenderer.getXLabelsPadding() + size, paint, 0.0F);

                        for (i = 0; i < maxScaleNumber; ++i) {
                            Align axisAlign = this.mRenderer.getYAxisAlign(i);
                            if (axisAlign == Align.LEFT) {
                                this.drawText(canvas, this.mRenderer.getYTitle(i), (float) x + size, (float) (y + height / 2), paint, -90.0F);
                            } else {
                                this.drawText(canvas, this.mRenderer.getYTitle(i), (float) (x + width), (float) (y + height / 2), paint, -90.0F);
                            }
                        }

                        paint.setTextSize(this.mRenderer.getChartTitleTextSize());
                        this.drawText(canvas, this.mRenderer.getChartTitle(), (float) (x + width / 2), (float) y + this.mRenderer.getChartTitleTextSize(), paint, 0.0F);
                    } else if (or == XYMultipleSeriesRenderer.Orientation.VERTICAL) {
                        this.drawText(canvas, this.mRenderer.getXTitle(), (float) (x + width / 2), (float) (y + height) - size + this.mRenderer.getXLabelsPadding(), paint, -90.0F);
                        this.drawText(canvas, this.mRenderer.getYTitle(), (float) (right + 20), (float) (y + height / 2), paint, 0.0F);
                        paint.setTextSize(this.mRenderer.getChartTitleTextSize());
                        this.drawText(canvas, this.mRenderer.getChartTitle(), (float) x + size, (float) (top + height / 2), paint, 0.0F);
                    }
                }
            }

            if (or == XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
                this.drawLegend(canvas, this.mRenderer, titles, left, right, y + (int) this.mRenderer.getXLabelsPadding(), width, height, legendSize, paint, false);
            } else if (or == XYMultipleSeriesRenderer.Orientation.VERTICAL) {
                this.transform(canvas, (float) angle, true);
                this.drawLegend(canvas, this.mRenderer, titles, left, right, y + (int) this.mRenderer.getXLabelsPadding(), width, height, legendSize, paint, false);
                this.transform(canvas, (float) angle, false);
            }

            if (this.mRenderer.isShowAxes()) {
                paint.setColor(this.mRenderer.getXAxisColor());
                canvas.drawLine((float) left, (float) bottom, (float) right, (float) bottom, paint);
                paint.setColor(this.mRenderer.getYAxisColor());
                boolean rightAxis = false;

                for (i = 0; i < maxScaleNumber && !rightAxis; ++i) {
                    rightAxis = this.mRenderer.getYAxisAlign(i) == Align.RIGHT;
                }

                if (or == XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
                    canvas.drawLine((float) left, (float) top, (float) left, (float) bottom, paint);
                    if (rightAxis) {
                        canvas.drawLine((float) right, (float) top, (float) right, (float) bottom, paint);
                    }
                } else if (or == XYMultipleSeriesRenderer.Orientation.VERTICAL) {
                    canvas.drawLine((float) right, (float) top, (float) right, (float) bottom, paint);
                }
            }

            if (rotate) {
                this.transform(canvas, (float) angle, true);
            }

        }
    }

    protected List<Double> getXLabels(double min, double max, int count) {
        return MathHelper.getLabels(min, max, count);
    }

    protected Map<Integer, List<Double>> getYLabels(double[] minY, double[] maxY, int maxScaleNumber) {
        Map<Integer, List<Double>> allYLabels = new HashMap();

        for (int i = 0; i < maxScaleNumber; ++i) {
            allYLabels.put(i, this.getValidLabels(MathHelper.getLabels(minY[i], maxY[i], this.mRenderer.getYLabels())));
        }

        return allYLabels;
    }

    protected Rect getScreenR() {
        return this.mScreenR;
    }

    protected void setScreenR(Rect screenR) {
        this.mScreenR = screenR;
    }

    private List<Double> getValidLabels(List<Double> labels) {
        List<Double> result = new ArrayList(labels);
        Iterator i$ = labels.iterator();

        while (i$.hasNext()) {
            Double label = (Double) i$.next();
            if (label.isNaN()) {
                result.remove(label);
            }
        }

        return result;
    }

    protected void drawSeries(XYSeries series, Canvas canvas, Paint paint, List<Float> pointsList, XYSeriesRenderer seriesRenderer, float yAxisValue, int seriesIndex, XYMultipleSeriesRenderer.Orientation or, int startIndex) {
        BasicStroke stroke = seriesRenderer.getStroke();
        Cap cap = paint.getStrokeCap();
        Join join = paint.getStrokeJoin();
        float miter = paint.getStrokeMiter();
        PathEffect pathEffect = paint.getPathEffect();
        Style style = paint.getStyle();
        if (stroke != null) {
            PathEffect effect = null;
            if (stroke.getIntervals() != null) {
                effect = new DashPathEffect(stroke.getIntervals(), stroke.getPhase());
            }

            this.setStroke(stroke.getCap(), stroke.getJoin(), stroke.getMiter(), Style.FILL_AND_STROKE, effect, paint);
        }

        this.drawSeries(canvas, paint, pointsList, seriesRenderer, yAxisValue, seriesIndex, startIndex);
        this.drawPoints(canvas, paint, pointsList, seriesRenderer, yAxisValue, seriesIndex, startIndex);
        paint.setTextSize(seriesRenderer.getChartValuesTextSize());
        if (or == XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
            paint.setTextAlign(Align.CENTER);
        } else {
            paint.setTextAlign(Align.LEFT);
        }

        if (seriesRenderer.isDisplayChartValues()) {
            paint.setTextAlign(seriesRenderer.getChartValuesTextAlign());
            this.drawChartValuesText(canvas, series, seriesRenderer, paint, pointsList, seriesIndex, startIndex);
        }

        if (stroke != null) {
            this.setStroke(cap, join, miter, style, pathEffect, paint);
        }

    }

    protected void drawPoints(Canvas canvas, Paint paint, List<Float> pointsList, XYSeriesRenderer seriesRenderer, float yAxisValue, int seriesIndex, int startIndex) {
        if (this.isRenderPoints(seriesRenderer)) {
            ScatterChart pointsChart = this.getPointsChart();
            if (pointsChart != null) {
                pointsChart.drawSeries(canvas, paint, pointsList, seriesRenderer, yAxisValue, seriesIndex, startIndex);
            }
        }

    }

    private void setStroke(Cap cap, Join join, float miter, Style style, PathEffect pathEffect, Paint paint) {
        paint.setStrokeCap(cap);
        paint.setStrokeJoin(join);
        paint.setStrokeMiter(miter);
        paint.setPathEffect(pathEffect);
        paint.setStyle(style);
    }

    protected void drawChartValuesText(Canvas canvas, XYSeries series, XYSeriesRenderer renderer, Paint paint, List<Float> points, int seriesIndex, int startIndex) {
        if (points.size() > 2) {
            float previousPointX = (Float) points.get(0);
            float previousPointY = (Float) points.get(1);

            for (int k = 0; k < points.size(); k += 2) {
                if (k == 2) {
                    if (Math.abs((Float) points.get(2) - (Float) points.get(0)) > (float) renderer.getDisplayChartValuesDistance() || Math.abs((Float) points.get(3) - (Float) points.get(1)) > (float) renderer.getDisplayChartValuesDistance()) {
                        this.drawText(canvas, this.getLabel(renderer.getChartValuesFormat(), series.getY(startIndex)), (Float) points.get(0), (Float) points.get(1) - renderer.getChartValuesSpacing(), paint, 0.0F);
                        this.drawText(canvas, this.getLabel(renderer.getChartValuesFormat(), series.getY(startIndex + 1)), (Float) points.get(2), (Float) points.get(3) - renderer.getChartValuesSpacing(), paint, 0.0F);
                        previousPointX = (Float) points.get(2);
                        previousPointY = (Float) points.get(3);
                    }
                } else if (k > 2 && (Math.abs((Float) points.get(k) - previousPointX) > (float) renderer.getDisplayChartValuesDistance() || Math.abs((Float) points.get(k + 1) - previousPointY) > (float) renderer.getDisplayChartValuesDistance())) {
                    this.drawText(canvas, this.getLabel(renderer.getChartValuesFormat(), series.getY(startIndex + k / 2)), (Float) points.get(k), (Float) points.get(k + 1) - renderer.getChartValuesSpacing(), paint, 0.0F);
                    previousPointX = (Float) points.get(k);
                    previousPointY = (Float) points.get(k + 1);
                }
            }
        } else {
            for (int k = 0; k < points.size(); k += 2) {
                this.drawText(canvas, this.getLabel(renderer.getChartValuesFormat(), series.getY(startIndex + k / 2)), (Float) points.get(k), (Float) points.get(k + 1) - renderer.getChartValuesSpacing(), paint, 0.0F);
            }
        }

    }

    protected void drawText(Canvas canvas, String text, float x, float y, Paint paint, float extraAngle) {
        float angle = (float) (-this.mRenderer.getOrientation().getAngle()) + extraAngle;
        if (angle != 0.0F) {
            canvas.rotate(angle, x, y);
        }

        this.drawString(canvas, text, x, y, paint);
        if (angle != 0.0F) {
            canvas.rotate(-angle, x, y);
        }

    }

    private void transform(Canvas canvas, float angle, boolean inverse) {
        if (inverse) {
            canvas.scale(1.0F / this.mScale, this.mScale);
            canvas.translate(this.mTranslate, -this.mTranslate);
            canvas.rotate(-angle, this.mCenter.getX(), this.mCenter.getY());
        } else {
            canvas.rotate(angle, this.mCenter.getX(), this.mCenter.getY());
            canvas.translate(-this.mTranslate, this.mTranslate);
            canvas.scale(this.mScale, 1.0F / this.mScale);
        }

    }

    protected void drawXLabels(List<Double> xLabels, Double[] xTextLabelLocations, Canvas canvas, Paint paint, int left, int top, int bottom, double xPixelsPerUnit, double minX, double maxX) {
        int length = xLabels.size();
        boolean showLabels = this.mRenderer.isShowLabels();
        boolean showGridY = this.mRenderer.isShowGridY();
        boolean showTickMarks = this.mRenderer.isShowTickMarks();

        for (int i = 0; i < length; ++i) {
            double label = (Double) xLabels.get(i);
            float xLabel = (float) ((double) left + xPixelsPerUnit * (label - minX));
            if (showLabels) {
                paint.setColor(this.mRenderer.getXLabelsColor());
                if (showTickMarks) {
                    canvas.drawLine(xLabel, (float) bottom, xLabel, (float) bottom + this.mRenderer.getLabelsTextSize() / 3.0F, paint);
                }

                this.drawText(canvas, this.getLabel(this.mRenderer.getXLabelFormat(), label), xLabel, (float) bottom + this.mRenderer.getLabelsTextSize() * 4.0F / 3.0F + this.mRenderer.getXLabelsPadding(), paint, this.mRenderer.getXLabelsAngle());
            }

            if (showGridY) {
                paint.setColor(this.mRenderer.getGridColor(0));
                canvas.drawLine(xLabel, (float) bottom, xLabel, (float) top, paint);
            }
        }

        this.drawXTextLabels(xTextLabelLocations, canvas, paint, showLabels, left, top, bottom, xPixelsPerUnit, minX, maxX);
    }

    protected void drawYLabels(Map<Integer, List<Double>> allYLabels, Canvas canvas, Paint paint, int maxScaleNumber, int left, int right, int bottom, double[] yPixelsPerUnit, double[] minY) {
        XYMultipleSeriesRenderer.Orientation or = this.mRenderer.getOrientation();
        boolean showGridX = this.mRenderer.isShowGridX();
        boolean showLabels = this.mRenderer.isShowLabels();
        boolean showTickMarks = this.mRenderer.isShowTickMarks();

        for (int i = 0; i < maxScaleNumber; ++i) {
            paint.setTextAlign(this.mRenderer.getYLabelsAlign(i));
            List<Double> yLabels = (List) allYLabels.get(i);
            int length = yLabels.size();

            for (int j = 0; j < length; ++j) {
                double label = (Double) yLabels.get(j);
                Align axisAlign = this.mRenderer.getYAxisAlign(i);
                boolean textLabel = this.mRenderer.getYTextLabel(label, i) != null;
                float yLabel = (float) ((double) bottom - yPixelsPerUnit[i] * (label - minY[i]));
                if (or == XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
                    if (showLabels && !textLabel) {
                        paint.setColor(this.mRenderer.getYLabelsColor(i));
                        if (axisAlign == Align.LEFT) {
                            if (showTickMarks) {
                                canvas.drawLine((float) (left + this.getLabelLinePos(axisAlign)), yLabel, (float) left, yLabel, paint);
                            }

                            this.drawText(canvas, this.getLabel(this.mRenderer.getYLabelFormat(i), label), (float) left - this.mRenderer.getYLabelsPadding(), yLabel - this.mRenderer.getYLabelsVerticalPadding(), paint, this.mRenderer.getYLabelsAngle());
                        } else {
                            if (showTickMarks) {
                                canvas.drawLine((float) right, yLabel, (float) (right + this.getLabelLinePos(axisAlign)), yLabel, paint);
                            }

                            this.drawText(canvas, this.getLabel(this.mRenderer.getYLabelFormat(i), label), (float) right + this.mRenderer.getYLabelsPadding(), yLabel - this.mRenderer.getYLabelsVerticalPadding(), paint, this.mRenderer.getYLabelsAngle());
                        }
                    }

                    if (showGridX) {
                        paint.setColor(this.mRenderer.getGridColor(i));
                        canvas.drawLine((float) left, yLabel, (float) right, yLabel, paint);
                    }
                } else if (or == XYMultipleSeriesRenderer.Orientation.VERTICAL) {
                    if (showLabels && !textLabel) {
                        paint.setColor(this.mRenderer.getYLabelsColor(i));
                        if (showTickMarks) {
                            canvas.drawLine((float) (right - this.getLabelLinePos(axisAlign)), yLabel, (float) right, yLabel, paint);
                        }

                        this.drawText(canvas, this.getLabel(this.mRenderer.getLabelFormat(), label), (float) (right + 10) + this.mRenderer.getYLabelsPadding(), yLabel - this.mRenderer.getYLabelsVerticalPadding(), paint, this.mRenderer.getYLabelsAngle());
                    }

                    if (showGridX) {
                        paint.setColor(this.mRenderer.getGridColor(i));
                        if (showTickMarks) {
                            canvas.drawLine((float) right, yLabel, (float) left, yLabel, paint);
                        }
                    }
                }
            }
        }

    }

    protected void drawXTextLabels(Double[] xTextLabelLocations, Canvas canvas, Paint paint, boolean showLabels, int left, int top, int bottom, double xPixelsPerUnit, double minX, double maxX) {
        boolean showCustomTextGridX = this.mRenderer.isShowCustomTextGridX();
        boolean showTickMarks = this.mRenderer.isShowTickMarks();
        if (showLabels) {
            paint.setColor(this.mRenderer.getXLabelsColor());
            Double[] arr$ = xTextLabelLocations;
            int len$ = xTextLabelLocations.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                Double location = arr$[i$];
                if (minX <= location && location <= maxX) {
                    float xLabel = (float) ((double) left + xPixelsPerUnit * (location - minX));
                    paint.setColor(this.mRenderer.getXLabelsColor());
                    if (showTickMarks) {
                        canvas.drawLine(xLabel, (float) bottom, xLabel, (float) bottom + this.mRenderer.getLabelsTextSize() / 3.0F, paint);
                    }

                    this.drawText(canvas, this.mRenderer.getXTextLabel(location), xLabel, (float) bottom + this.mRenderer.getLabelsTextSize() * 4.0F / 3.0F + this.mRenderer.getXLabelsPadding(), paint, this.mRenderer.getXLabelsAngle());
                    if (showCustomTextGridX) {
                        paint.setColor(this.mRenderer.getGridColor(0));
                        canvas.drawLine(xLabel, (float) bottom, xLabel, (float) top, paint);
                    }
                }
            }
        }

    }

    public XYMultipleSeriesRenderer getRenderer() {
        return this.mRenderer;
    }

    public XYMultipleSeriesDataset getDataset() {
        return this.mDataset;
    }

    public double[] getCalcRange(int scale) {
        return (double[]) this.mCalcRange.get(scale);
    }

    public void setCalcRange(double[] range, int scale) {
        this.mCalcRange.put(scale, range);
    }

    public double[] toRealPoint(float screenX, float screenY) {
        return this.toRealPoint(screenX, screenY, 0);
    }

    public double[] toScreenPoint(double[] realPoint) {
        return this.toScreenPoint(realPoint, 0);
    }

    private int getLabelLinePos(Align align) {
        int pos = 4;
        if (align == Align.LEFT) {
            pos = -pos;
        }

        return pos;
    }

    public double[] toRealPoint(float screenX, float screenY, int scale) {
        double realMinX = this.mRenderer.getXAxisMin(scale);
        double realMaxX = this.mRenderer.getXAxisMax(scale);
        double realMinY = this.mRenderer.getYAxisMin(scale);
        double realMaxY = this.mRenderer.getYAxisMax(scale);
        if (!this.mRenderer.isMinXSet(scale) || !this.mRenderer.isMaxXSet(scale) || !this.mRenderer.isMinYSet(scale) || !this.mRenderer.isMaxYSet(scale)) {
            double[] calcRange = this.getCalcRange(scale);
            if (calcRange != null) {
                realMinX = calcRange[0];
                realMaxX = calcRange[1];
                realMinY = calcRange[2];
                realMaxY = calcRange[3];
            }
        }

        return this.mScreenR != null ? new double[]{(double) (screenX - (float) this.mScreenR.left) * (realMaxX - realMinX) / (double) this.mScreenR.width() + realMinX, (double) ((float) (this.mScreenR.top + this.mScreenR.height()) - screenY) * (realMaxY - realMinY) / (double) this.mScreenR.height() + realMinY} : new double[]{(double) screenX, (double) screenY};
    }

    public double[] toScreenPoint(double[] realPoint, int scale) {
        double realMinX = this.mRenderer.getXAxisMin(scale);
        double realMaxX = this.mRenderer.getXAxisMax(scale);
        double realMinY = this.mRenderer.getYAxisMin(scale);
        double realMaxY = this.mRenderer.getYAxisMax(scale);
        if (!this.mRenderer.isMinXSet(scale) || !this.mRenderer.isMaxXSet(scale) || !this.mRenderer.isMinYSet(scale) || !this.mRenderer.isMaxYSet(scale)) {
            double[] calcRange = this.getCalcRange(scale);
            realMinX = calcRange[0];
            realMaxX = calcRange[1];
            realMinY = calcRange[2];
            realMaxY = calcRange[3];
        }

        return this.mScreenR != null ? new double[]{(realPoint[0] - realMinX) * (double) this.mScreenR.width() / (realMaxX - realMinX) + (double) this.mScreenR.left, (realMaxY - realPoint[1]) * (double) this.mScreenR.height() / (realMaxY - realMinY) + (double) this.mScreenR.top} : realPoint;
    }

    public SeriesSelection getSeriesAndPointForScreenCoordinate(Point screenPoint) {
        if (this.clickableAreas != null) {
            for (int seriesIndex = this.clickableAreas.size() - 1; seriesIndex >= 0; --seriesIndex) {
                int pointIndex = 0;
                if (this.clickableAreas.get(seriesIndex) != null) {
                    for (Iterator i$ = ((List) this.clickableAreas.get(seriesIndex)).iterator(); i$.hasNext(); ++pointIndex) {
                        ClickableArea area = (ClickableArea) i$.next();
                        if (area != null) {
                            RectF rectangle = area.getRect();
                            if (rectangle != null && rectangle.contains(screenPoint.getX(), screenPoint.getY())) {
                                return new SeriesSelection(seriesIndex, pointIndex, area.getX(), area.getY());
                            }
                        }
                    }
                }
            }
        }

        return super.getSeriesAndPointForScreenCoordinate(screenPoint);
    }

    public abstract void drawSeries(Canvas var1, Paint var2, List<Float> var3, XYSeriesRenderer var4, float var5, int var6, int var7);

    protected abstract ClickableArea[] clickableAreasForPoints(List<Float> var1, List<Double> var2, float var3, int var4, int var5);

    protected boolean isRenderNullValues() {
        return false;
    }

    public boolean isRenderPoints(SimpleSeriesRenderer renderer) {
        return false;
    }

    public double getDefaultMinimum() {
        return 1.7976931348623157E308D;
    }

    public ScatterChart getPointsChart() {
        return null;
    }

    public abstract String getChartType();
}

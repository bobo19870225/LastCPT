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
import www.jingkan.com.view.chart.achartengine.renderer.SimpleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYSeriesRenderer;

public class ScatterChart extends XYChart {
    public static final String TYPE = "Scatter";
    private static final float SIZE = 3.0F;
    private static final int SHAPE_WIDTH = 10;
    private float size = 3.0F;

    ScatterChart() {
    }

    public ScatterChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        super(dataset, renderer);
        this.size = renderer.getPointSize();
    }

    protected void setDatasetRenderer(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        super.setDatasetRenderer(dataset, renderer);
        this.size = renderer.getPointSize();
    }

    public void drawSeries(Canvas canvas, Paint paint, List<Float> points, XYSeriesRenderer renderer, float yAxisValue, int seriesIndex, int startIndex) {
        paint.setColor(renderer.getColor());
        float stroke = paint.getStrokeWidth();
        if (renderer.isFillPoints()) {
            paint.setStyle(Style.FILL);
        } else {
            paint.setStrokeWidth(renderer.getPointStrokeWidth());
            paint.setStyle(Style.STROKE);
        }

        int length = points.size();
        float[] path;
        int i;
        label63:
        switch (renderer.getPointStyle()) {
            case X:
                paint.setStrokeWidth(renderer.getPointStrokeWidth());
                i = 0;

                while (true) {
                    if (i >= length) {
                        break label63;
                    }

                    this.drawX(canvas, paint, (Float) points.get(i), (Float) points.get(i + 1));
                    i += 2;
                }
            case CIRCLE:
                i = 0;

                while (true) {
                    if (i >= length) {
                        break label63;
                    }

                    this.drawCircle(canvas, paint, (Float) points.get(i), (Float) points.get(i + 1));
                    i += 2;
                }
            case TRIANGLE:
                path = new float[6];
                i = 0;

                while (true) {
                    if (i >= length) {
                        break label63;
                    }

                    this.drawTriangle(canvas, paint, path, (Float) points.get(i), (Float) points.get(i + 1));
                    i += 2;
                }
            case SQUARE:
                i = 0;

                while (true) {
                    if (i >= length) {
                        break label63;
                    }

                    this.drawSquare(canvas, paint, (Float) points.get(i), (Float) points.get(i + 1));
                    i += 2;
                }
            case DIAMOND:
                path = new float[8];
                i = 0;

                while (true) {
                    if (i >= length) {
                        break label63;
                    }

                    this.drawDiamond(canvas, paint, path, (Float) points.get(i), (Float) points.get(i + 1));
                    i += 2;
                }
            case POINT:
                for (i = 0; i < length; i += 2) {
                    canvas.drawPoint((Float) points.get(i), (Float) points.get(i + 1), paint);
                }
        }

        paint.setStrokeWidth(stroke);
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
        return 10;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer renderer, float x, float y, int seriesIndex, Paint paint) {
        if (((XYSeriesRenderer) renderer).isFillPoints()) {
            paint.setStyle(Style.FILL);
        } else {
            paint.setStyle(Style.STROKE);
        }

        switch (((XYSeriesRenderer) renderer).getPointStyle()) {
            case X:
                this.drawX(canvas, paint, x + 10.0F, y);
                break;
            case CIRCLE:
                this.drawCircle(canvas, paint, x + 10.0F, y);
                break;
            case TRIANGLE:
                this.drawTriangle(canvas, paint, new float[6], x + 10.0F, y);
                break;
            case SQUARE:
                this.drawSquare(canvas, paint, x + 10.0F, y);
                break;
            case DIAMOND:
                this.drawDiamond(canvas, paint, new float[8], x + 10.0F, y);
                break;
            case POINT:
                canvas.drawPoint(x + 10.0F, y, paint);
        }

    }

    private void drawX(Canvas canvas, Paint paint, float x, float y) {
        canvas.drawLine(x - this.size, y - this.size, x + this.size, y + this.size, paint);
        canvas.drawLine(x + this.size, y - this.size, x - this.size, y + this.size, paint);
    }

    private void drawCircle(Canvas canvas, Paint paint, float x, float y) {
        canvas.drawCircle(x, y, this.size, paint);
    }

    private void drawTriangle(Canvas canvas, Paint paint, float[] path, float x, float y) {
        path[0] = x;
        path[1] = y - this.size - this.size / 2.0F;
        path[2] = x - this.size;
        path[3] = y + this.size;
        path[4] = x + this.size;
        path[5] = path[3];
        this.drawPath(canvas, path, paint, true);
    }

    private void drawSquare(Canvas canvas, Paint paint, float x, float y) {
        canvas.drawRect(x - this.size, y - this.size, x + this.size, y + this.size, paint);
    }

    private void drawDiamond(Canvas canvas, Paint paint, float[] path, float x, float y) {
        path[0] = x;
        path[1] = y - this.size;
        path[2] = x - this.size;
        path[3] = y;
        path[4] = x;
        path[5] = y + this.size;
        path[6] = x + this.size;
        path[7] = y;
        this.drawPath(canvas, path, paint, true);
    }

    public String getChartType() {
        return "Scatter";
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import www.jingkan.com.view.chart.achartengine.model.CategorySeries;
import www.jingkan.com.view.chart.achartengine.renderer.DefaultRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.SimpleSeriesRenderer;

public abstract class RoundChart extends AbstractChart {
    protected static final int SHAPE_WIDTH = 10;
    protected CategorySeries mDataset;
    protected DefaultRenderer mRenderer;
    protected static final int NO_VALUE = 2147483647;
    protected int mCenterX = 2147483647;
    protected int mCenterY = 2147483647;

    public RoundChart(CategorySeries dataset, DefaultRenderer renderer) {
        this.mDataset = dataset;
        this.mRenderer = renderer;
    }

    public void drawTitle(Canvas canvas, int x, int y, int width, Paint paint) {
        if (this.mRenderer.isShowLabels()) {
            paint.setColor(this.mRenderer.getLabelsColor());
            paint.setTextAlign(Align.CENTER);
            paint.setTextSize(this.mRenderer.getChartTitleTextSize());
            this.drawString(canvas, this.mRenderer.getChartTitle(), (float) (x + width / 2), (float) y + this.mRenderer.getChartTitleTextSize(), paint);
        }

    }

    public int getLegendShapeWidth(int seriesIndex) {
        return 10;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer renderer, float x, float y, int seriesIndex, Paint paint) {
        canvas.drawRect(x, y - 5.0F, x + 10.0F, y + 5.0F, paint);
    }

    public DefaultRenderer getRenderer() {
        return this.mRenderer;
    }

    public int getCenterX() {
        return this.mCenterX;
    }

    public int getCenterY() {
        return this.mCenterY;
    }

    public void setCenterX(int centerX) {
        this.mCenterX = centerX;
    }

    public void setCenterY(int centerY) {
        this.mCenterY = centerY;
    }
}

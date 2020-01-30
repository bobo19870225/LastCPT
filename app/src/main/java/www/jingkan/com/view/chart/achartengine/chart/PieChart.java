//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;


import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.view.chart.achartengine.model.CategorySeries;
import www.jingkan.com.view.chart.achartengine.model.Point;
import www.jingkan.com.view.chart.achartengine.model.SeriesSelection;
import www.jingkan.com.view.chart.achartengine.renderer.DefaultRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.SimpleSeriesRenderer;

public class PieChart extends RoundChart {
    private PieMapper mPieMapper = new PieMapper();

    public PieChart(CategorySeries dataset, DefaultRenderer renderer) {
        super(dataset, renderer);
    }

    public void draw(Canvas canvas, int x, int y, int width, int height, Paint paint) {
        paint.setAntiAlias(this.mRenderer.isAntialiasing());
        paint.setStyle(Style.FILL);
        paint.setTextSize(this.mRenderer.getLabelsTextSize());
        int legendSize = this.getLegendSize(this.mRenderer, height / 5, 0.0F);
        int left = x;
        int right = x + width;
        int sLength = this.mDataset.getItemCount();
        double total = 0.0D;
        String[] titles = new String[sLength];

        int bottom;
        for (bottom = 0; bottom < sLength; ++bottom) {
            total += this.mDataset.getValue(bottom);
            titles[bottom] = this.mDataset.getCategory(bottom);
        }

        if (this.mRenderer.isFitLegend()) {
            legendSize = this.drawLegend(canvas, this.mRenderer, titles, x, right, y, width, height, legendSize, paint, true);
        }

        bottom = y + height - legendSize;
        this.drawBackground(this.mRenderer, canvas, x, y, width, height, paint, false, 0);
        float currentAngle = this.mRenderer.getStartAngle();
        int mRadius = Math.min(Math.abs(right - x), Math.abs(bottom - y));
        int radius = (int) ((double) mRadius * 0.35D * (double) this.mRenderer.getScale());
        if (this.mCenterX == 2147483647) {
            this.mCenterX = (x + right) / 2;
        }

        if (this.mCenterY == 2147483647) {
            this.mCenterY = (bottom + y) / 2;
        }

        this.mPieMapper.setDimensions(radius, this.mCenterX, this.mCenterY);
        boolean loadPieCfg = !this.mPieMapper.areAllSegmentPresent(sLength);
        if (loadPieCfg) {
            this.mPieMapper.clearPieSegments();
        }

        float shortRadius = (float) radius * 0.9F;
        float longRadius = (float) radius * 1.1F;
        RectF oval = new RectF((float) (this.mCenterX - radius), (float) (this.mCenterY - radius), (float) (this.mCenterX + radius), (float) (this.mCenterY + radius));
        List<RectF> prevLabelsBounds = new ArrayList();

        for (int i = 0; i < sLength; ++i) {
            SimpleSeriesRenderer seriesRenderer = this.mRenderer.getSeriesRendererAt(i);
            if (seriesRenderer.isGradientEnabled()) {
                RadialGradient grad = new RadialGradient((float) this.mCenterX, (float) this.mCenterY, longRadius, seriesRenderer.getGradientStartColor(), seriesRenderer.getGradientStopColor(), TileMode.MIRROR);
                paint.setShader(grad);
            } else {
                paint.setColor(seriesRenderer.getColor());
            }

            float value = (float) this.mDataset.getValue(i);
            float angle = (float) ((double) value / total * 360.0D);
            if (seriesRenderer.isHighlighted()) {
                double rAngle = Math.toRadians((double) (90.0F - (currentAngle + angle / 2.0F)));
                float translateX = (float) ((double) radius * 0.1D * Math.sin(rAngle));
                float translateY = (float) ((double) radius * 0.1D * Math.cos(rAngle));
                oval.offset(translateX, translateY);
                canvas.drawArc(oval, currentAngle, angle, true, paint);
                oval.offset(-translateX, -translateY);
            } else {
                canvas.drawArc(oval, currentAngle, angle, true, paint);
            }

            paint.setColor(seriesRenderer.getColor());
            paint.setShader((Shader) null);
            this.drawLabel(canvas, this.mDataset.getCategory(i), this.mRenderer, prevLabelsBounds, this.mCenterX, this.mCenterY, shortRadius, longRadius, currentAngle, angle, left, right, this.mRenderer.getLabelsColor(), paint, true, false);
            if (this.mRenderer.isDisplayValues()) {
                this.drawLabel(canvas, this.getLabel(this.mRenderer.getSeriesRendererAt(i).getChartValuesFormat(), this.mDataset.getValue(i)), this.mRenderer, prevLabelsBounds, this.mCenterX, this.mCenterY, shortRadius / 2.0F, longRadius / 2.0F, currentAngle, angle, left, right, this.mRenderer.getLabelsColor(), paint, false, true);
            }

            if (loadPieCfg) {
                this.mPieMapper.addPieSegment(i, value, currentAngle, angle);
            }

            currentAngle += angle;
        }

        prevLabelsBounds.clear();
        this.drawLegend(canvas, this.mRenderer, titles, left, right, y, width, height, legendSize, paint, false);
        this.drawTitle(canvas, x, y, width, paint);
    }

    public SeriesSelection getSeriesAndPointForScreenCoordinate(Point screenPoint) {
        return this.mPieMapper.getSeriesAndPointForScreenCoordinate(screenPoint);
    }
}

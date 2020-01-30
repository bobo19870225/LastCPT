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

import www.jingkan.com.view.chart.achartengine.model.CategorySeries;
import www.jingkan.com.view.chart.achartengine.model.MultipleCategorySeries;
import www.jingkan.com.view.chart.achartengine.renderer.DefaultRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.SimpleSeriesRenderer;

public class DoughnutChart extends RoundChart {
    private MultipleCategorySeries mDataset;
    private int mStep;

    public DoughnutChart(MultipleCategorySeries dataset, DefaultRenderer renderer) {
        super((CategorySeries) null, renderer);
        this.mDataset = dataset;
    }

    public void draw(Canvas canvas, int x, int y, int width, int height, Paint paint) {
        paint.setAntiAlias(this.mRenderer.isAntialiasing());
        paint.setStyle(Style.FILL);
        paint.setTextSize(this.mRenderer.getLabelsTextSize());
        int legendSize = this.getLegendSize(this.mRenderer, height / 5, 0.0F);
        int left = x;
        int right = x + width;
        int cLength = this.mDataset.getCategoriesCount();
        String[] categories = new String[cLength];

        int bottom;
        for (bottom = 0; bottom < cLength; ++bottom) {
            categories[bottom] = this.mDataset.getCategory(bottom);
        }

        if (this.mRenderer.isFitLegend()) {
            legendSize = this.drawLegend(canvas, this.mRenderer, categories, x, right, y, width, height, legendSize, paint, true);
        }

        bottom = y + height - legendSize;
        this.drawBackground(this.mRenderer, canvas, x, y, width, height, paint, false, 0);
        this.mStep = 7;
        int mRadius = Math.min(Math.abs(right - x), Math.abs(bottom - y));
        double rCoef = 0.35D * (double) this.mRenderer.getScale();
        double decCoef = 0.2D / (double) cLength;
        int radius = (int) ((double) mRadius * rCoef);
        if (this.mCenterX == 2147483647) {
            this.mCenterX = (x + right) / 2;
        }

        if (this.mCenterY == 2147483647) {
            this.mCenterY = (bottom + y) / 2;
        }

        float shortRadius = (float) radius * 0.9F;
        float longRadius = (float) radius * 1.1F;
        List<RectF> prevLabelsBounds = new ArrayList();

        for (int category = 0; category < cLength; ++category) {
            int sLength = this.mDataset.getItemCount(category);
            double total = 0.0D;
            String[] titles = new String[sLength];

            for (int i = 0; i < sLength; ++i) {
                total += this.mDataset.getValues(category)[i];
                titles[i] = this.mDataset.getTitles(category)[i];
            }

            float currentAngle = this.mRenderer.getStartAngle();
            RectF oval = new RectF((float) (this.mCenterX - radius), (float) (this.mCenterY - radius), (float) (this.mCenterX + radius), (float) (this.mCenterY + radius));

            for (int i = 0; i < sLength; ++i) {
                paint.setColor(this.mRenderer.getSeriesRendererAt(i).getColor());
                float value = (float) this.mDataset.getValues(category)[i];
                float angle = (float) ((double) value / total * 360.0D);
                canvas.drawArc(oval, currentAngle, angle, true, paint);
                this.drawLabel(canvas, this.mDataset.getTitles(category)[i], this.mRenderer, prevLabelsBounds, this.mCenterX, this.mCenterY, shortRadius, longRadius, currentAngle, angle, left, right, this.mRenderer.getLabelsColor(), paint, true, false);
                currentAngle += angle;
            }

            radius = (int) ((double) radius - (double) mRadius * decCoef);
            shortRadius = (float) ((double) shortRadius - ((double) mRadius * decCoef - 2.0D));
            if (this.mRenderer.getBackgroundColor() != 0) {
                paint.setColor(this.mRenderer.getBackgroundColor());
            } else {
                paint.setColor(-1);
            }

            paint.setStyle(Style.FILL);
            oval = new RectF((float) (this.mCenterX - radius), (float) (this.mCenterY - radius), (float) (this.mCenterX + radius), (float) (this.mCenterY + radius));
            canvas.drawArc(oval, 0.0F, 360.0F, true, paint);
            --radius;
        }

        prevLabelsBounds.clear();
        this.drawLegend(canvas, this.mRenderer, categories, left, right, y, width, height, legendSize, paint, false);
        this.drawTitle(canvas, x, y, width, paint);
    }

    public int getLegendShapeWidth(int seriesIndex) {
        return 10;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer renderer, float x, float y, int seriesIndex, Paint paint) {
        --this.mStep;
        canvas.drawCircle(x + 10.0F - (float) this.mStep, y, (float) this.mStep, paint);
    }
}

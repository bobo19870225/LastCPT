//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.renderer;

import android.graphics.Color;
import android.graphics.Paint.Align;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.view.chart.achartengine.chart.PointStyle;

public class XYSeriesRenderer extends SimpleSeriesRenderer {
    private boolean mFillPoints = false;
    private List<XYSeriesRenderer.FillOutsideLine> mFillBelowLine = new ArrayList();
    private PointStyle mPointStyle;
    private float mPointStrokeWidth;
    private float mLineWidth;
    private boolean mDisplayChartValues;
    private int mDisplayChartValuesDistance;
    private float mChartValuesTextSize;
    private Align mChartValuesTextAlign;
    private float mChartValuesSpacing;
    private float mAnnotationsTextSize;
    private Align mAnnotationsTextAlign;
    private int mAnnotationsColor;

    public XYSeriesRenderer() {
        this.mPointStyle = PointStyle.POINT;
        this.mPointStrokeWidth = 1.0F;
        this.mLineWidth = 1.0F;
        this.mDisplayChartValuesDistance = 100;
        this.mChartValuesTextSize = 10.0F;
        this.mChartValuesTextAlign = Align.CENTER;
        this.mChartValuesSpacing = 5.0F;
        this.mAnnotationsTextSize = 10.0F;
        this.mAnnotationsTextAlign = Align.CENTER;
        this.mAnnotationsColor = -3355444;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public boolean isFillBelowLine() {
        return this.mFillBelowLine.size() > 0;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setFillBelowLine(boolean fill) {
        this.mFillBelowLine.clear();
        if (fill) {
            this.mFillBelowLine.add(new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL));
        } else {
            this.mFillBelowLine.add(new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.NONE));
        }

    }

    public XYSeriesRenderer.FillOutsideLine[] getFillOutsideLine() {
        return (XYSeriesRenderer.FillOutsideLine[]) this.mFillBelowLine.toArray(new XYSeriesRenderer.FillOutsideLine[0]);
    }

    public void addFillOutsideLine(XYSeriesRenderer.FillOutsideLine fill) {
        this.mFillBelowLine.add(fill);
    }

    public boolean isFillPoints() {
        return this.mFillPoints;
    }

    public void setFillPoints(boolean fill) {
        this.mFillPoints = fill;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setFillBelowLineColor(int color) {
        if (this.mFillBelowLine.size() > 0) {
            ((XYSeriesRenderer.FillOutsideLine) this.mFillBelowLine.get(0)).setColor(color);
        }

    }

    public PointStyle getPointStyle() {
        return this.mPointStyle;
    }

    public void setPointStyle(PointStyle style) {
        this.mPointStyle = style;
    }

    public float getPointStrokeWidth() {
        return this.mPointStrokeWidth;
    }

    public void setPointStrokeWidth(float strokeWidth) {
        this.mPointStrokeWidth = strokeWidth;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.mLineWidth = lineWidth;
    }

    public boolean isDisplayChartValues() {
        return this.mDisplayChartValues;
    }

    public void setDisplayChartValues(boolean display) {
        this.mDisplayChartValues = display;
    }

    public int getDisplayChartValuesDistance() {
        return this.mDisplayChartValuesDistance;
    }

    public void setDisplayChartValuesDistance(int distance) {
        this.mDisplayChartValuesDistance = distance;
    }

    public float getChartValuesTextSize() {
        return this.mChartValuesTextSize;
    }

    public void setChartValuesTextSize(float textSize) {
        this.mChartValuesTextSize = textSize;
    }

    public Align getChartValuesTextAlign() {
        return this.mChartValuesTextAlign;
    }

    public void setChartValuesTextAlign(Align align) {
        this.mChartValuesTextAlign = align;
    }

    public float getChartValuesSpacing() {
        return this.mChartValuesSpacing;
    }

    public void setChartValuesSpacing(float spacing) {
        this.mChartValuesSpacing = spacing;
    }

    public float getAnnotationsTextSize() {
        return this.mAnnotationsTextSize;
    }

    public void setAnnotationsTextSize(float textSize) {
        this.mAnnotationsTextSize = textSize;
    }

    public Align getAnnotationsTextAlign() {
        return this.mAnnotationsTextAlign;
    }

    public void setAnnotationsTextAlign(Align align) {
        this.mAnnotationsTextAlign = align;
    }

    public int getAnnotationsColor() {
        return this.mAnnotationsColor;
    }

    public void setAnnotationsColor(int color) {
        this.mAnnotationsColor = color;
    }

    public static class FillOutsideLine implements Serializable {
        private final XYSeriesRenderer.FillOutsideLine.Type mType;
        private int mColor = Color.argb(125, 0, 0, 200);
        private int[] mFillRange;

        public FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type type) {
            this.mType = type;
        }

        public int getColor() {
            return this.mColor;
        }

        public void setColor(int color) {
            this.mColor = color;
        }

        public XYSeriesRenderer.FillOutsideLine.Type getType() {
            return this.mType;
        }

        public int[] getFillRange() {
            return this.mFillRange;
        }

        public void setFillRange(int[] range) {
            this.mFillRange = range;
        }

        public static enum Type {
            NONE,
            BOUNDS_ALL,
            BOUNDS_BELOW,
            BOUNDS_ABOVE,
            BELOW,
            ABOVE;

            private Type() {
            }
        }
    }
}

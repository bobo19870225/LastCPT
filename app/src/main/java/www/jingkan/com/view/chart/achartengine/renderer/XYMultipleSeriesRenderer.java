//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.renderer;

import android.graphics.Color;
import android.graphics.Paint.Align;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class XYMultipleSeriesRenderer extends DefaultRenderer {
    private String mXTitle;
    private String[] mYTitle;
    private float mAxisTitleTextSize;
    private double[] mMinX;
    private double[] mMaxX;
    private double[] mMinY;
    private double[] mMaxY;
    private int mXLabels;
    private int mYLabels;
    private XYMultipleSeriesRenderer.Orientation mOrientation;
    private Map<Double, String> mXTextLabels;
    private Map<Integer, Map<Double, String>> mYTextLabels;
    private boolean mPanXEnabled;
    private boolean mPanYEnabled;
    private boolean mZoomXEnabled;
    private boolean mZoomYEnabled;
    private double mBarSpacing;
    private int mMarginsColor;
    private double[] mPanLimits;
    private double[] mZoomLimits;
    private float mXLabelsAngle;
    private float mYLabelsAngle;
    private Map<Integer, double[]> initialRange;
    private float mPointSize;
    private int[] mGridColors;
    private int scalesCount;
    private Align xLabelsAlign;
    private Align[] yLabelsAlign;
    private float mXLabelsPadding;
    private float mYLabelsPadding;
    private float mYLabelsVerticalPadding;
    private Align[] yAxisAlign;
    private int mXLabelsColor;
    private int[] mYLabelsColor;
    private boolean mXRoundedLabels;
    private NumberFormat mXLabelFormat;
    private NumberFormat[] mYLabelFormat;
    private float mBarWidth;
    private double mZoomInLimitX;
    private double mZoomInLimitY;

    public XYMultipleSeriesRenderer() {
        this(1);
    }

    public XYMultipleSeriesRenderer(int scaleNumber) {
        this.mXTitle = "";
        this.mAxisTitleTextSize = 12.0F;
        this.mXLabels = 5;
        this.mYLabels = 5;
        this.mOrientation = XYMultipleSeriesRenderer.Orientation.HORIZONTAL;
        this.mXTextLabels = new HashMap();
        this.mYTextLabels = new LinkedHashMap();
        this.mPanXEnabled = true;
        this.mPanYEnabled = true;
        this.mZoomXEnabled = true;
        this.mZoomYEnabled = true;
        this.mBarSpacing = 0.0D;
        this.mMarginsColor = 0;
        this.initialRange = new LinkedHashMap();
        this.mPointSize = 3.0F;
        this.xLabelsAlign = Align.CENTER;
        this.mXLabelsPadding = 0.0F;
        this.mYLabelsPadding = 0.0F;
        this.mYLabelsVerticalPadding = 2.0F;
        this.mXLabelsColor = -3355444;
        this.mYLabelsColor = new int[]{-3355444};
        this.mXRoundedLabels = true;
        this.mBarWidth = -1.0F;
        this.mZoomInLimitX = 0.0D;
        this.mZoomInLimitY = 0.0D;
        this.scalesCount = scaleNumber;
        this.initAxesRange(scaleNumber);
    }

    public void initAxesRange(int scales) {
        this.mYTitle = new String[scales];
        this.yLabelsAlign = new Align[scales];
        this.yAxisAlign = new Align[scales];
        this.mYLabelsColor = new int[scales];
        this.mYLabelFormat = new NumberFormat[scales];
        this.mMinX = new double[scales];
        this.mMaxX = new double[scales];
        this.mMinY = new double[scales];
        this.mMaxY = new double[scales];
        this.mGridColors = new int[scales];

        for (int i = 0; i < scales; ++i) {
            this.mYLabelsColor[i] = -3355444;
            this.mYLabelFormat[i] = NumberFormat.getNumberInstance();
            this.mGridColors[i] = Color.argb(75, 200, 200, 200);
            this.initAxesRangeForScale(i);
        }

    }

    public void initAxesRangeForScale(int i) {
        this.mMinX[i] = 1.7976931348623157E308D;
        this.mMaxX[i] = -1.7976931348623157E308D;
        this.mMinY[i] = 1.7976931348623157E308D;
        this.mMaxY[i] = -1.7976931348623157E308D;
        double[] range = new double[]{this.mMinX[i], this.mMaxX[i], this.mMinY[i], this.mMaxY[i]};
        this.initialRange.put(i, range);
        this.mYTitle[i] = "";
        this.mYTextLabels.put(i, new HashMap());
        this.yLabelsAlign[i] = Align.CENTER;
        this.yAxisAlign[i] = Align.LEFT;
    }

    public XYMultipleSeriesRenderer.Orientation getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(XYMultipleSeriesRenderer.Orientation orientation) {
        this.mOrientation = orientation;
    }

    public String getXTitle() {
        return this.mXTitle;
    }

    public void setXTitle(String title) {
        this.mXTitle = title;
    }

    public String getYTitle() {
        return this.getYTitle(0);
    }

    public String getYTitle(int scale) {
        return this.mYTitle[scale];
    }

    public void setYTitle(String title) {
        this.setYTitle(title, 0);
    }

    public void setYTitle(String title, int scale) {
        this.mYTitle[scale] = title;
    }

    public float getAxisTitleTextSize() {
        return this.mAxisTitleTextSize;
    }

    public void setAxisTitleTextSize(float textSize) {
        this.mAxisTitleTextSize = textSize;
    }

    public double getXAxisMin() {
        return this.getXAxisMin(0);
    }

    public void setXAxisMin(double min) {
        this.setXAxisMin(min, 0);
    }

    public boolean isMinXSet() {
        return this.isMinXSet(0);
    }

    public double getXAxisMax() {
        return this.getXAxisMax(0);
    }

    public void setXAxisMax(double max) {
        this.setXAxisMax(max, 0);
    }

    public boolean isMaxXSet() {
        return this.isMaxXSet(0);
    }

    public double getYAxisMin() {
        return this.getYAxisMin(0);
    }

    public void setYAxisMin(double min) {
        this.setYAxisMin(min, 0);
    }

    public boolean isMinYSet() {
        return this.isMinYSet(0);
    }

    public double getYAxisMax() {
        return this.getYAxisMax(0);
    }

    public void setYAxisMax(double max) {
        this.setYAxisMax(max, 0);
    }

    public boolean isMaxYSet() {
        return this.isMaxYSet(0);
    }

    public double getXAxisMin(int scale) {
        return this.mMinX[scale];
    }

    public void setXAxisMin(double min, int scale) {
        if (!this.isMinXSet(scale)) {
            ((double[]) this.initialRange.get(scale))[0] = min;
        }

        this.mMinX[scale] = min;
    }

    public boolean isMinXSet(int scale) {
        return this.mMinX[scale] != 1.7976931348623157E308D;
    }

    public double getXAxisMax(int scale) {
        return this.mMaxX[scale];
    }

    public void setXAxisMax(double max, int scale) {
        if (!this.isMaxXSet(scale)) {
            ((double[]) this.initialRange.get(scale))[1] = max;
        }

        this.mMaxX[scale] = max;
    }

    public boolean isMaxXSet(int scale) {
        return this.mMaxX[scale] != -1.7976931348623157E308D;
    }

    public double getYAxisMin(int scale) {
        return this.mMinY[scale];
    }

    public void setYAxisMin(double min, int scale) {
        if (!this.isMinYSet(scale)) {
            ((double[]) this.initialRange.get(scale))[2] = min;
        }

        this.mMinY[scale] = min;
    }

    public boolean isMinYSet(int scale) {
        return this.mMinY[scale] != 1.7976931348623157E308D;
    }

    public double getYAxisMax(int scale) {
        return this.mMaxY[scale];
    }

    public void setYAxisMax(double max, int scale) {
        if (!this.isMaxYSet(scale)) {
            ((double[]) this.initialRange.get(scale))[3] = max;
        }

        this.mMaxY[scale] = max;
    }

    public boolean isMaxYSet(int scale) {
        return this.mMaxY[scale] != -1.7976931348623157E308D;
    }

    public int getXLabels() {
        return this.mXLabels;
    }

    public void setXLabels(int xLabels) {
        this.mXLabels = xLabels;
    }

    /**
     * @deprecated
     */
    public void addTextLabel(double x, String text) {
        this.addXTextLabel(x, text);
    }

    public synchronized void addXTextLabel(double x, String text) {
        this.mXTextLabels.put(x, text);
    }

    public synchronized void removeXTextLabel(double x) {
        this.mXTextLabels.remove(x);
    }

    public synchronized String getXTextLabel(Double x) {
        return (String) this.mXTextLabels.get(x);
    }

    public synchronized Double[] getXTextLabelLocations() {
        return (Double[]) this.mXTextLabels.keySet().toArray(new Double[0]);
    }

    /**
     * @deprecated
     */
    public void clearTextLabels() {
        this.clearXTextLabels();
    }

    public synchronized void clearXTextLabels() {
        this.mXTextLabels.clear();
    }

    public boolean isXRoundedLabels() {
        return this.mXRoundedLabels;
    }

    public void setXRoundedLabels(boolean rounded) {
        this.mXRoundedLabels = rounded;
    }

    public void addYTextLabel(double y, String text) {
        this.addYTextLabel(y, text, 0);
    }

    public void removeYTextLabel(double y) {
        this.removeYTextLabel(y, 0);
    }

    public synchronized void addYTextLabel(double y, String text, int scale) {
        ((Map) this.mYTextLabels.get(scale)).put(y, text);
    }

    public synchronized void removeYTextLabel(double y, int scale) {
        ((Map) this.mYTextLabels.get(scale)).remove(y);
    }

    public String getYTextLabel(Double y) {
        return this.getYTextLabel(y, 0);
    }

    public synchronized String getYTextLabel(Double y, int scale) {
        return (String) ((Map) this.mYTextLabels.get(scale)).get(y);
    }

    public Double[] getYTextLabelLocations() {
        return this.getYTextLabelLocations(0);
    }

    public synchronized Double[] getYTextLabelLocations(int scale) {
        return (Double[]) ((Map) this.mYTextLabels.get(scale)).keySet().toArray(new Double[0]);
    }

    public void clearYTextLabels() {
        this.clearYTextLabels(0);
    }

    public synchronized void clearYTextLabels(int scale) {
        ((Map) this.mYTextLabels.get(scale)).clear();
    }

    public int getYLabels() {
        return this.mYLabels;
    }

    public void setYLabels(int yLabels) {
        this.mYLabels = yLabels;
    }

    public float getBarWidth() {
        return this.mBarWidth;
    }

    public void setBarWidth(float width) {
        this.mBarWidth = width;
    }

    public boolean isPanEnabled() {
        return this.isPanXEnabled() || this.isPanYEnabled();
    }

    public boolean isPanXEnabled() {
        return this.mPanXEnabled;
    }

    public boolean isPanYEnabled() {
        return this.mPanYEnabled;
    }

    public void setPanEnabled(boolean enabledX, boolean enabledY) {
        this.mPanXEnabled = enabledX;
        this.mPanYEnabled = enabledY;
    }

    public void setPanEnabled(boolean enabled) {
        this.setPanEnabled(enabled, enabled);
    }

    public boolean isZoomEnabled() {
        return this.isZoomXEnabled() || this.isZoomYEnabled();
    }

    public boolean isZoomXEnabled() {
        return this.mZoomXEnabled;
    }

    public boolean isZoomYEnabled() {
        return this.mZoomYEnabled;
    }

    public void setZoomEnabled(boolean enabledX, boolean enabledY) {
        this.mZoomXEnabled = enabledX;
        this.mZoomYEnabled = enabledY;
    }

    /**
     * @deprecated
     */
    public double getBarsSpacing() {
        return this.getBarSpacing();
    }

    public double getBarSpacing() {
        return this.mBarSpacing;
    }

    public void setBarSpacing(double spacing) {
        this.mBarSpacing = spacing;
    }

    public int getMarginsColor() {
        return this.mMarginsColor;
    }

    public void setMarginsColor(int color) {
        this.mMarginsColor = color;
    }

    public int getGridColor(int scale) {
        return this.mGridColors[scale];
    }

    public void setGridColor(int color) {
        this.setGridColor(color, 0);
    }

    public void setGridColor(int color, int scale) {
        this.mGridColors[scale] = color;
    }

    public double[] getPanLimits() {
        return this.mPanLimits;
    }

    public void setPanLimits(double[] panLimits) {
        this.mPanLimits = panLimits;
    }

    public double[] getZoomLimits() {
        return this.mZoomLimits;
    }

    public void setZoomLimits(double[] zoomLimits) {
        this.mZoomLimits = zoomLimits;
    }

    public float getXLabelsAngle() {
        return this.mXLabelsAngle;
    }

    public void setXLabelsAngle(float angle) {
        this.mXLabelsAngle = angle;
    }

    public float getYLabelsAngle() {
        return this.mYLabelsAngle;
    }

    public void setYLabelsAngle(float angle) {
        this.mYLabelsAngle = angle;
    }

    public float getPointSize() {
        return this.mPointSize;
    }

    public void setPointSize(float size) {
        this.mPointSize = size;
    }

    public void setRange(double[] range) {
        this.setRange(range, 0);
    }

    public void setRange(double[] range, int scale) {
        this.setXAxisMin(range[0], scale);
        this.setXAxisMax(range[1], scale);
        this.setYAxisMin(range[2], scale);
        this.setYAxisMax(range[3], scale);
    }

    public boolean isInitialRangeSet() {
        return this.isInitialRangeSet(0);
    }

    public boolean isInitialRangeSet(int scale) {
        return this.initialRange.get(scale) != null;
    }

    public double[] getInitialRange() {
        return this.getInitialRange(0);
    }

    public double[] getInitialRange(int scale) {
        return (double[]) this.initialRange.get(scale);
    }

    public void setInitialRange(double[] range) {
        this.setInitialRange(range, 0);
    }

    public void setInitialRange(double[] range, int scale) {
        this.initialRange.put(scale, range);
    }

    public int getXLabelsColor() {
        return this.mXLabelsColor;
    }

    public int getYLabelsColor(int scale) {
        return this.mYLabelsColor[scale];
    }

    public void setXLabelsColor(int color) {
        this.mXLabelsColor = color;
    }

    public void setYLabelsColor(int scale, int color) {
        this.mYLabelsColor[scale] = color;
    }

    public Align getXLabelsAlign() {
        return this.xLabelsAlign;
    }

    public void setXLabelsAlign(Align align) {
        this.xLabelsAlign = align;
    }

    public Align getYLabelsAlign(int scale) {
        return this.yLabelsAlign[scale];
    }

    public void setYLabelsAlign(Align align) {
        this.setYLabelsAlign(align, 0);
    }

    public Align getYAxisAlign(int scale) {
        return this.yAxisAlign[scale];
    }

    public void setYAxisAlign(Align align, int scale) {
        this.yAxisAlign[scale] = align;
    }

    public void setYLabelsAlign(Align align, int scale) {
        this.yLabelsAlign[scale] = align;
    }

    public float getXLabelsPadding() {
        return this.mXLabelsPadding;
    }

    public void setXLabelsPadding(float padding) {
        this.mXLabelsPadding = padding;
    }

    public float getYLabelsPadding() {
        return this.mYLabelsPadding;
    }

    public void setYLabelsVerticalPadding(float padding) {
        this.mYLabelsVerticalPadding = padding;
    }

    public float getYLabelsVerticalPadding() {
        return this.mYLabelsVerticalPadding;
    }

    public void setYLabelsPadding(float padding) {
        this.mYLabelsPadding = padding;
    }

    /**
     * @deprecated
     */
    public NumberFormat getLabelFormat() {
        return this.getXLabelFormat();
    }

    /**
     * @deprecated
     */
    public void setLabelFormat(NumberFormat format) {
        this.setXLabelFormat(format);
    }

    public NumberFormat getXLabelFormat() {
        return this.mXLabelFormat;
    }

    public void setXLabelFormat(NumberFormat format) {
        this.mXLabelFormat = format;
    }

    public NumberFormat getYLabelFormat(int scale) {
        return this.mYLabelFormat[scale];
    }

    public void setYLabelFormat(NumberFormat format, int scale) {
        this.mYLabelFormat[scale] = format;
    }

    public double getZoomInLimitX() {
        return this.mZoomInLimitX;
    }

    public void setZoomInLimitX(double zoomInLimitX) {
        this.mZoomInLimitX = zoomInLimitX;
    }

    public double getZoomInLimitY() {
        return this.mZoomInLimitY;
    }

    public void setZoomInLimitY(double zoomInLimitY) {
        this.mZoomInLimitY = zoomInLimitY;
    }

    public int getScalesCount() {
        return this.scalesCount;
    }

    public static enum Orientation {
        HORIZONTAL(0),
        VERTICAL(90);

        private int mAngle = 0;

        private Orientation(int angle) {
            this.mAngle = angle;
        }

        public int getAngle() {
            return this.mAngle;
        }
    }
}

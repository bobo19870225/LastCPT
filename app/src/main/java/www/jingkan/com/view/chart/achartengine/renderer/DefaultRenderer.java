//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.renderer;

import android.graphics.Typeface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Typeface.NORMAL;

public class DefaultRenderer implements Serializable {
    private String mChartTitle = "";
    private float mChartTitleTextSize = 15.0F;
    public static final int NO_COLOR = 0;
    public static final int BACKGROUND_COLOR = -16777216;
    public static final int TEXT_COLOR = -3355444;
    private static final Typeface REGULAR_TEXT_FONT;
    private String mTextTypefaceName;
    private int mTextTypefaceStyle;
    private Typeface mTextTypeface;
    private int mBackgroundColor;
    private boolean mApplyBackgroundColor;
    private boolean mShowAxes;
    private int mYAxisColor;
    private int mXAxisColor;
    private boolean mShowLabels;
    private boolean mShowTickMarks;
    private int mLabelsColor;
    private float mLabelsTextSize;
    private boolean mShowLegend;
    private float mLegendTextSize;
    private boolean mFitLegend;
    private boolean mShowGridX;
    private boolean mShowGridY;
    private boolean mShowCustomTextGridX;
    private boolean mShowCustomTextGridY;
    private List<SimpleSeriesRenderer> mRenderers;
    private boolean mAntialiasing;
    private int mLegendHeight;
    private int[] mMargins;
    private float mScale;
    private boolean mPanEnabled;
    private boolean mZoomEnabled;
    private boolean mZoomButtonsVisible;
    private float mZoomRate;
    private boolean mExternalZoomEnabled;
    private float mOriginalScale;
    private boolean mClickEnabled;
    private int selectableBuffer;
    private boolean mDisplayValues;
    private boolean mInScroll;
    private float mStartAngle;

    public DefaultRenderer() {
        this.mTextTypefaceName = REGULAR_TEXT_FONT.toString();
        this.mTextTypefaceStyle = 0;
        this.mShowAxes = true;
        this.mYAxisColor = -3355444;
        this.mXAxisColor = -3355444;
        this.mShowLabels = true;
        this.mShowTickMarks = true;
        this.mLabelsColor = -3355444;
        this.mLabelsTextSize = 10.0F;
        this.mShowLegend = true;
        this.mLegendTextSize = 12.0F;
        this.mFitLegend = false;
        this.mShowGridX = false;
        this.mShowGridY = false;
        this.mShowCustomTextGridX = false;
        this.mShowCustomTextGridY = false;
        this.mRenderers = new ArrayList();
        this.mAntialiasing = true;
        this.mLegendHeight = 0;
        this.mMargins = new int[]{20, 30, 10, 20};
        this.mScale = 1.0F;
        this.mPanEnabled = true;
        this.mZoomEnabled = true;
        this.mZoomButtonsVisible = false;
        this.mZoomRate = 1.5F;
        this.mExternalZoomEnabled = false;
        this.mOriginalScale = this.mScale;
        this.mClickEnabled = false;
        this.selectableBuffer = 15;
        this.mStartAngle = 0.0F;
    }

    public String getChartTitle() {
        return this.mChartTitle;
    }

    public void setChartTitle(String title) {
        this.mChartTitle = title;
    }

    public float getChartTitleTextSize() {
        return this.mChartTitleTextSize;
    }

    public void setChartTitleTextSize(float textSize) {
        this.mChartTitleTextSize = textSize;
    }

    public void addSeriesRenderer(SimpleSeriesRenderer renderer) {
        this.mRenderers.add(renderer);
    }

    public void addSeriesRenderer(int index, SimpleSeriesRenderer renderer) {
        this.mRenderers.add(index, renderer);
    }

    public void removeSeriesRenderer(SimpleSeriesRenderer renderer) {
        this.mRenderers.remove(renderer);
    }

    public void removeAllRenderers() {
        this.mRenderers.clear();
    }

    public SimpleSeriesRenderer getSeriesRendererAt(int index) {
        return (SimpleSeriesRenderer) this.mRenderers.get(index);
    }

    public int getSeriesRendererCount() {
        return this.mRenderers.size();
    }

    public SimpleSeriesRenderer[] getSeriesRenderers() {
        return (SimpleSeriesRenderer[]) this.mRenderers.toArray(new SimpleSeriesRenderer[0]);
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public void setBackgroundColor(int color) {
        this.mBackgroundColor = color;
    }

    public boolean isApplyBackgroundColor() {
        return this.mApplyBackgroundColor;
    }

    public void setApplyBackgroundColor(boolean apply) {
        this.mApplyBackgroundColor = apply;
    }

    public int getAxesColor() {
        return this.mXAxisColor != -3355444 ? this.mXAxisColor : this.mYAxisColor;
    }

    public void setAxesColor(int color) {
        this.setXAxisColor(color);
        this.setYAxisColor(color);
    }

    public int getYAxisColor() {
        return this.mYAxisColor;
    }

    public void setYAxisColor(int color) {
        this.mYAxisColor = color;
    }

    public int getXAxisColor() {
        return this.mXAxisColor;
    }

    public void setXAxisColor(int color) {
        this.mXAxisColor = color;
    }

    public int getLabelsColor() {
        return this.mLabelsColor;
    }

    public void setLabelsColor(int color) {
        this.mLabelsColor = color;
    }

    public float getLabelsTextSize() {
        return this.mLabelsTextSize;
    }

    public void setLabelsTextSize(float textSize) {
        this.mLabelsTextSize = textSize;
    }

    public boolean isShowAxes() {
        return this.mShowAxes;
    }

    public void setShowAxes(boolean showAxes) {
        this.mShowAxes = showAxes;
    }

    public boolean isShowLabels() {
        return this.mShowLabels;
    }

    public void setShowLabels(boolean showLabels) {
        this.mShowLabels = showLabels;
    }

    public boolean isShowTickMarks() {
        return this.mShowTickMarks;
    }

    public void setShowTickMarks(boolean mShowTickMarks) {
        this.mShowTickMarks = mShowTickMarks;
    }

    public boolean isShowGridX() {
        return this.mShowGridX;
    }

    public boolean isShowGridY() {
        return this.mShowGridY;
    }

    public void setShowGridX(boolean showGrid) {
        this.mShowGridX = showGrid;
    }

    public void setShowGridY(boolean showGrid) {
        this.mShowGridY = showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.setShowGridX(showGrid);
        this.setShowGridY(showGrid);
    }

    public boolean isShowCustomTextGridX() {
        return this.mShowCustomTextGridX;
    }

    public boolean isShowCustomTextGridY() {
        return this.mShowCustomTextGridY;
    }

    public void setShowCustomTextGridX(boolean showGrid) {
        this.mShowCustomTextGridX = showGrid;
    }

    public void setShowCustomTextGridY(boolean showGrid) {
        this.mShowCustomTextGridY = showGrid;
    }

    public void setShowCustomTextGrid(boolean showGrid) {
        this.setShowCustomTextGridX(showGrid);
        this.setShowCustomTextGridY(showGrid);
    }

    public boolean isShowLegend() {
        return this.mShowLegend;
    }

    public void setShowLegend(boolean showLegend) {
        this.mShowLegend = showLegend;
    }

    public boolean isFitLegend() {
        return this.mFitLegend;
    }

    public void setFitLegend(boolean fit) {
        this.mFitLegend = fit;
    }

    public String getTextTypefaceName() {
        return this.mTextTypefaceName;
    }

    public int getTextTypefaceStyle() {
        return this.mTextTypefaceStyle;
    }

    public Typeface getTextTypeface() {
        return this.mTextTypeface;
    }

    public float getLegendTextSize() {
        return this.mLegendTextSize;
    }

    public void setLegendTextSize(float textSize) {
        this.mLegendTextSize = textSize;
    }

    public void setTextTypeface(String typefaceName, int style) {
        this.mTextTypefaceName = typefaceName;
        this.mTextTypefaceStyle = style;
    }

    public void setTextTypeface(Typeface typeface) {
        this.mTextTypeface = typeface;
    }

    public boolean isAntialiasing() {
        return this.mAntialiasing;
    }

    public void setAntialiasing(boolean antialiasing) {
        this.mAntialiasing = antialiasing;
    }

    public float getScale() {
        return this.mScale;
    }

    public float getOriginalScale() {
        return this.mOriginalScale;
    }

    public void setScale(float scale) {
        this.mScale = scale;
    }

    public boolean isZoomEnabled() {
        return this.mZoomEnabled;
    }

    public void setZoomEnabled(boolean enabled) {
        this.mZoomEnabled = enabled;
    }

    public boolean isZoomButtonsVisible() {
        return this.mZoomButtonsVisible;
    }

    public void setZoomButtonsVisible(boolean visible) {
        this.mZoomButtonsVisible = visible;
    }

    public boolean isExternalZoomEnabled() {
        return this.mExternalZoomEnabled;
    }

    public void setExternalZoomEnabled(boolean enabled) {
        this.mExternalZoomEnabled = enabled;
    }

    public float getZoomRate() {
        return this.mZoomRate;
    }

    public boolean isPanEnabled() {
        return this.mPanEnabled;
    }

    public void setPanEnabled(boolean enabled) {
        this.mPanEnabled = enabled;
    }

    public void setZoomRate(float rate) {
        this.mZoomRate = rate;
    }

    public boolean isClickEnabled() {
        return this.mClickEnabled;
    }

    public void setClickEnabled(boolean enabled) {
        this.mClickEnabled = enabled;
    }

    public int getSelectableBuffer() {
        return this.selectableBuffer;
    }

    public void setSelectableBuffer(int buffer) {
        this.selectableBuffer = buffer;
    }

    public int getLegendHeight() {
        return this.mLegendHeight;
    }

    public void setLegendHeight(int height) {
        this.mLegendHeight = height;
    }

    public int[] getMargins() {
        return this.mMargins;
    }

    public void setMargins(int[] margins) {
        this.mMargins = margins;
    }

    public boolean isInScroll() {
        return this.mInScroll;
    }

    public void setInScroll(boolean inScroll) {
        this.mInScroll = inScroll;
    }

    public float getStartAngle() {
        return this.mStartAngle;
    }

    public void setStartAngle(float startAngle) {
        while (startAngle < 0.0F) {
            startAngle += 360.0F;
        }

        this.mStartAngle = startAngle;
    }

    public boolean isDisplayValues() {
        return this.mDisplayValues;
    }

    public void setDisplayValues(boolean display) {
        this.mDisplayValues = display;
    }

    static {
        REGULAR_TEXT_FONT = Typeface.create(Typeface.SERIF, NORMAL);
    }
}

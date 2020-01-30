//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialRenderer extends DefaultRenderer {
    private double mAngleMin = 330.0D;
    private double mAngleMax = 30.0D;
    private double mMinValue = 1.7976931348623157E308D;
    private double mMaxValue = -1.7976931348623157E308D;
    private double mMinorTickSpacing = 1.7976931348623157E308D;
    private double mMajorTickSpacing = 1.7976931348623157E308D;
    private List<DialRenderer.Type> mVisualTypes = new ArrayList();

    public DialRenderer() {
    }

    public double getAngleMin() {
        return this.mAngleMin;
    }

    public void setAngleMin(double min) {
        this.mAngleMin = min;
    }

    public double getAngleMax() {
        return this.mAngleMax;
    }

    public void setAngleMax(double max) {
        this.mAngleMax = max;
    }

    public double getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(double min) {
        this.mMinValue = min;
    }

    public boolean isMinValueSet() {
        return this.mMinValue != 1.7976931348623157E308D;
    }

    public double getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(double max) {
        this.mMaxValue = max;
    }

    public boolean isMaxValueSet() {
        return this.mMaxValue != -1.7976931348623157E308D;
    }

    public double getMinorTicksSpacing() {
        return this.mMinorTickSpacing;
    }

    public void setMinorTicksSpacing(double spacing) {
        this.mMinorTickSpacing = spacing;
    }

    public double getMajorTicksSpacing() {
        return this.mMajorTickSpacing;
    }

    public void setMajorTicksSpacing(double spacing) {
        this.mMajorTickSpacing = spacing;
    }

    public DialRenderer.Type getVisualTypeForIndex(int index) {
        return index < this.mVisualTypes.size() ? (DialRenderer.Type) this.mVisualTypes.get(index) : DialRenderer.Type.NEEDLE;
    }

    public void setVisualTypes(DialRenderer.Type[] types) {
        this.mVisualTypes.clear();
        this.mVisualTypes.addAll(Arrays.asList(types));
    }

    public static enum Type {
        NEEDLE,
        ARROW;

        private Type() {
        }
    }
}

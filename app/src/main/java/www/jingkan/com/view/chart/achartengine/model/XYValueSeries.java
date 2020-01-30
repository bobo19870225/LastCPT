//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.model;

import java.util.ArrayList;
import java.util.List;

public class XYValueSeries extends XYSeries {
    private List<Double> mValue = new ArrayList();
    private double mMinValue = 1.7976931348623157E308D;
    private double mMaxValue = -1.7976931348623157E308D;

    public XYValueSeries(String title) {
        super(title);
    }

    public synchronized void add(double x, double y, double value) {
        super.add(x, y);
        this.mValue.add(value);
        this.updateRange(value);
    }

    private void initRange() {
        this.mMinValue = 1.7976931348623157E308D;
        this.mMaxValue = -1.7976931348623157E308D;
        int length = this.getItemCount();

        for (int k = 0; k < length; ++k) {
            this.updateRange(this.getValue(k));
        }

    }

    private void updateRange(double value) {
        this.mMinValue = Math.min(this.mMinValue, value);
        this.mMaxValue = Math.max(this.mMaxValue, value);
    }

    public synchronized void add(double x, double y) {
        this.add(x, y, 0.0D);
    }

    public synchronized void remove(int index) {
        super.remove(index);
        double removedValue = (Double) this.mValue.remove(index);
        if (removedValue == this.mMinValue || removedValue == this.mMaxValue) {
            this.initRange();
        }

    }

    public synchronized void clear() {
        super.clear();
        this.mValue.clear();
        this.initRange();
    }

    public synchronized double getValue(int index) {
        return (Double) this.mValue.get(index);
    }

    public double getMinValue() {
        return this.mMinValue;
    }

    public double getMaxValue() {
        return this.mMaxValue;
    }
}

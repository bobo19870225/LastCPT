//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.model;

public class SeriesSelection {
    private int mSeriesIndex;
    private int mPointIndex;
    private double mXValue;
    private double mValue;

    public SeriesSelection(int seriesIndex, int pointIndex, double xValue, double value) {
        this.mSeriesIndex = seriesIndex;
        this.mPointIndex = pointIndex;
        this.mXValue = xValue;
        this.mValue = value;
    }

    public int getSeriesIndex() {
        return this.mSeriesIndex;
    }

    public int getPointIndex() {
        return this.mPointIndex;
    }

    public double getXValue() {
        return this.mXValue;
    }

    public double getValue() {
        return this.mValue;
    }
}

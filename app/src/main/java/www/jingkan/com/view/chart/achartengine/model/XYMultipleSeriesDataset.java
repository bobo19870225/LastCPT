//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XYMultipleSeriesDataset implements Serializable {
    private List<XYSeries> mSeries = new ArrayList();

    public XYMultipleSeriesDataset() {
    }

    public synchronized void addSeries(XYSeries series) {
        this.mSeries.add(series);
    }

    public synchronized void addSeries(int index, XYSeries series) {
        this.mSeries.add(index, series);
    }

    public synchronized void addAllSeries(List<XYSeries> series) {
        this.mSeries.addAll(series);
    }

    public synchronized void removeSeries(int index) {
        this.mSeries.remove(index);
    }

    public synchronized void removeSeries(XYSeries series) {
        this.mSeries.remove(series);
    }

    public synchronized void clear() {
        this.mSeries.clear();
    }

    public synchronized XYSeries getSeriesAt(int index) {
        return (XYSeries) this.mSeries.get(index);
    }

    public synchronized int getSeriesCount() {
        return this.mSeries.size();
    }

    public synchronized XYSeries[] getSeries() {
        return (XYSeries[]) this.mSeries.toArray(new XYSeries[0]);
    }
}

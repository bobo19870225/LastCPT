//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CategorySeries implements Serializable {
    private String mTitle;
    private List<String> mCategories = new ArrayList();
    private List<Double> mValues = new ArrayList();

    public CategorySeries(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public synchronized void add(double value) {
        this.add(this.mCategories.size() + "", value);
    }

    public synchronized void add(String category, double value) {
        this.mCategories.add(category);
        this.mValues.add(value);
    }

    public synchronized void set(int index, String category, double value) {
        this.mCategories.set(index, category);
        this.mValues.set(index, value);
    }

    public synchronized void remove(int index) {
        this.mCategories.remove(index);
        this.mValues.remove(index);
    }

    public synchronized void clear() {
        this.mCategories.clear();
        this.mValues.clear();
    }

    public synchronized double getValue(int index) {
        return (Double) this.mValues.get(index);
    }

    public synchronized String getCategory(int index) {
        return (String) this.mCategories.get(index);
    }

    public synchronized int getItemCount() {
        return this.mCategories.size();
    }

    public XYSeries toXYSeries() {
        XYSeries xySeries = new XYSeries(this.mTitle);
        int k = 0;
        Iterator i$ = this.mValues.iterator();

        while (i$.hasNext()) {
            double value = (Double) i$.next();
            ++k;
            xySeries.add((double) k, value);
        }

        return xySeries;
    }
}

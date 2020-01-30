//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class IndexXYMap<K, V> extends TreeMap<K, V> {
    private final List<K> indexList = new ArrayList();
    private double maxXDifference = 0.0D;

    public IndexXYMap() {
    }

    public V put(K key, V value) {
        this.indexList.add(key);
        this.updateMaxXDifference();
        return super.put(key, value);
    }

    public V put(int index, K key, V value) {
        this.indexList.add(index, key);
        this.updateMaxXDifference();
        return super.put(key, value);
    }

    private void updateMaxXDifference() {
        if (this.indexList.size() < 2) {
            this.maxXDifference = 0.0D;
        } else {
            if (Math.abs((Double) this.indexList.get(this.indexList.size() - 1) - (Double) this.indexList.get(this.indexList.size() - 2)) > this.maxXDifference) {
                this.maxXDifference = Math.abs((Double) this.indexList.get(this.indexList.size() - 1) - (Double) this.indexList.get(this.indexList.size() - 2));
            }

        }
    }

    public double getMaxXDifference() {
        return this.maxXDifference;
    }

    public void clear() {
        this.updateMaxXDifference();
        super.clear();
        this.indexList.clear();
    }

    public K getXByIndex(int index) {
        return this.indexList.get(index);
    }

    public V getYByIndex(int index) {
        K key = this.indexList.get(index);
        return this.get(key);
    }

    public XYEntry<K, V> getByIndex(int index) {
        K key = this.indexList.get(index);
        return new XYEntry(key, this.get(key));
    }

    public XYEntry<K, V> removeByIndex(int index) {
        K key = this.indexList.remove(index);
        return new XYEntry(key, this.remove(key));
    }

    public int getIndexForKey(K key) {
        return Collections.binarySearch(this.indexList, key, (Comparator) null);
    }
}

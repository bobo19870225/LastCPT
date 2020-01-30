//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.util;

import java.util.Map.Entry;

public class XYEntry<K, V> implements Entry<K, V> {
    private final K key;
    private V value;

    public XYEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public V setValue(V object) {
        this.value = object;
        return this.value;
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine;

import android.view.MotionEvent;

import www.jingkan.com.view.chart.achartengine.tools.PanListener;
import www.jingkan.com.view.chart.achartengine.tools.ZoomListener;

public interface ITouchHandler {
    boolean handleTouch(MotionEvent var1);

    void addZoomListener(ZoomListener var1);

    void removeZoomListener(ZoomListener var1);

    void addPanListener(PanListener var1);

    void removePanListener(PanListener var1);
}

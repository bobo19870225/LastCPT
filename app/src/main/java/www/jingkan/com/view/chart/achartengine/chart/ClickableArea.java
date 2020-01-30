//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import android.graphics.RectF;

public class ClickableArea {
    private RectF rect;
    private double x;
    private double y;

    public ClickableArea(RectF rect, double x, double y) {
        this.rect = rect;
        this.x = x;
        this.y = y;
    }

    public RectF getRect() {
        return this.rect;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}

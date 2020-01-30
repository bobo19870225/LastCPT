//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.model;

import java.io.Serializable;

public final class Point implements Serializable {
    private float mX;
    private float mY;

    public Point() {
    }

    public Point(float x, float y) {
        this.mX = x;
        this.mY = y;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public void setX(float x) {
        this.mX = x;
    }

    public void setY(float y) {
        this.mY = y;
    }
}

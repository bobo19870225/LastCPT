//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.tools;

public class ZoomEvent {
    private boolean mZoomIn;
    private float mZoomRate;

    public ZoomEvent(boolean in, float rate) {
        this.mZoomIn = in;
        this.mZoomRate = rate;
    }

    public boolean isZoomIn() {
        return this.mZoomIn;
    }

    public float getZoomRate() {
        return this.mZoomRate;
    }
}

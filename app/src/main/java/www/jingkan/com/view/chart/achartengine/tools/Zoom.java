//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.tools;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import www.jingkan.com.view.chart.achartengine.chart.AbstractChart;
import www.jingkan.com.view.chart.achartengine.chart.RoundChart;
import www.jingkan.com.view.chart.achartengine.chart.XYChart;
import www.jingkan.com.view.chart.achartengine.renderer.DefaultRenderer;

public class Zoom extends AbstractTool {
    private boolean mZoomIn;
    private float mZoomRate;
    private List<ZoomListener> mZoomListeners = new ArrayList();
    private boolean limitsReachedX = false;
    private boolean limitsReachedY = false;
    public static final int ZOOM_AXIS_XY = 0;
    public static final int ZOOM_AXIS_X = 1;
    public static final int ZOOM_AXIS_Y = 2;

    public Zoom(AbstractChart chart, boolean in, float rate) {
        super(chart);
        this.mZoomIn = in;
        this.setZoomRate(rate);
    }

    public void setZoomRate(float rate) {
        this.mZoomRate = rate;
    }

    public void apply(int zoom_axis) {
        if (this.mChart instanceof XYChart) {
            int scales = this.mRenderer.getScalesCount();

            for (int i = 0; i < scales; ++i) {
                double[] range = this.getRange(i);
                this.checkRange(range, i);
                double[] limits = this.mRenderer.getZoomLimits();
                double centerX = (range[0] + range[1]) / 2.0D;
                double centerY = (range[2] + range[3]) / 2.0D;
                double newWidth = range[1] - range[0];
                double newHeight = range[3] - range[2];
                double newXMin = centerX - newWidth / 2.0D;
                double newXMax = centerX + newWidth / 2.0D;
                double newYMin = centerY - newHeight / 2.0D;
                double newYMax = centerY + newHeight / 2.0D;
                if (i == 0) {
                    this.limitsReachedX = limits != null && (newXMin <= limits[0] || newXMax >= limits[1]);
                    this.limitsReachedY = limits != null && (newYMin <= limits[2] || newYMax >= limits[3]);
                }

                if (this.mZoomIn) {
                    if (this.mRenderer.isZoomXEnabled() && (zoom_axis == 1 || zoom_axis == 0) && (!this.limitsReachedX || this.mZoomRate >= 1.0F)) {
                        newWidth /= (double) this.mZoomRate;
                    }

                    if (this.mRenderer.isZoomYEnabled() && (zoom_axis == 2 || zoom_axis == 0) && (!this.limitsReachedY || this.mZoomRate >= 1.0F)) {
                        newHeight /= (double) this.mZoomRate;
                    }
                } else {
                    if (this.mRenderer.isZoomXEnabled() && !this.limitsReachedX && (zoom_axis == 1 || zoom_axis == 0)) {
                        newWidth *= (double) this.mZoomRate;
                    }

                    if (this.mRenderer.isZoomYEnabled() && !this.limitsReachedY && (zoom_axis == 2 || zoom_axis == 0)) {
                        newHeight *= (double) this.mZoomRate;
                    }
                }

                double minX;
                double minY;
                if (limits != null) {
                    minX = Math.min(this.mRenderer.getZoomInLimitX(), limits[1] - limits[0]);
                    minY = Math.min(this.mRenderer.getZoomInLimitY(), limits[3] - limits[2]);
                } else {
                    minX = this.mRenderer.getZoomInLimitX();
                    minY = this.mRenderer.getZoomInLimitY();
                }

                newWidth = Math.max(newWidth, minX);
                newHeight = Math.max(newHeight, minY);
                if (this.mRenderer.isZoomXEnabled() && (zoom_axis == 1 || zoom_axis == 0)) {
                    newXMin = centerX - newWidth / 2.0D;
                    newXMax = centerX + newWidth / 2.0D;
                    this.setXRange(newXMin, newXMax, i);
                }

                if (this.mRenderer.isZoomYEnabled() && (zoom_axis == 2 || zoom_axis == 0)) {
                    newYMin = centerY - newHeight / 2.0D;
                    newYMax = centerY + newHeight / 2.0D;
                    this.setYRange(newYMin, newYMax, i);
                }
            }
        } else {
            DefaultRenderer renderer = ((RoundChart) this.mChart).getRenderer();
            if (this.mZoomIn) {
                renderer.setScale(renderer.getScale() * this.mZoomRate);
            } else {
                renderer.setScale(renderer.getScale() / this.mZoomRate);
            }
        }

        this.notifyZoomListeners(new ZoomEvent(this.mZoomIn, this.mZoomRate));
    }

    private synchronized void notifyZoomListeners(ZoomEvent e) {
        Iterator i$ = this.mZoomListeners.iterator();

        while (i$.hasNext()) {
            ZoomListener listener = (ZoomListener) i$.next();
            listener.zoomApplied(e);
        }

    }

    public synchronized void notifyZoomResetListeners() {
        Iterator i$ = this.mZoomListeners.iterator();

        while (i$.hasNext()) {
            ZoomListener listener = (ZoomListener) i$.next();
            listener.zoomReset();
        }

    }

    public synchronized void addZoomListener(ZoomListener listener) {
        this.mZoomListeners.add(listener);
    }

    public synchronized void removeZoomListener(ZoomListener listener) {
        this.mZoomListeners.remove(listener);
    }
}

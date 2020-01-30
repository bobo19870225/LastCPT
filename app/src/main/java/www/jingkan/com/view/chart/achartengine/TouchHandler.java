//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine;

import android.graphics.RectF;
import android.view.MotionEvent;

import www.jingkan.com.view.chart.achartengine.chart.AbstractChart;
import www.jingkan.com.view.chart.achartengine.chart.RoundChart;
import www.jingkan.com.view.chart.achartengine.chart.XYChart;
import www.jingkan.com.view.chart.achartengine.renderer.DefaultRenderer;
import www.jingkan.com.view.chart.achartengine.tools.Pan;
import www.jingkan.com.view.chart.achartengine.tools.PanListener;
import www.jingkan.com.view.chart.achartengine.tools.Zoom;
import www.jingkan.com.view.chart.achartengine.tools.ZoomListener;


public class TouchHandler implements ITouchHandler {
    private DefaultRenderer mRenderer;
    private float oldX;
    private float oldY;
    private float oldX2;
    private float oldY2;
    private RectF zoomR;
    private Pan mPan;
    private Zoom mPinchZoom;
    private GraphicalView graphicalView;

    public TouchHandler(GraphicalView view, AbstractChart chart) {
        this.graphicalView = view;
        this.zoomR = this.graphicalView.getZoomRectangle();
        if (chart instanceof XYChart) {
            this.mRenderer = ((XYChart) chart).getRenderer();
        } else {
            this.mRenderer = ((RoundChart) chart).getRenderer();
        }

        if (this.mRenderer.isPanEnabled()) {
            this.mPan = new Pan(chart);
        }

        if (this.mRenderer.isZoomEnabled()) {
            this.mPinchZoom = new Zoom(chart, true, 1.0F);
        }

    }

    public boolean handleTouch(MotionEvent event) {
        int action = event.getAction();
        if (this.mRenderer != null && action == 2) {
            if (this.oldX >= 0.0F || this.oldY >= 0.0F) {
                float newX = event.getX(0);
                float newY = event.getY(0);
                if (event.getPointerCount() > 1 && (this.oldX2 >= 0.0F || this.oldY2 >= 0.0F) && this.mRenderer.isZoomEnabled()) {
                    float newX2 = event.getX(1);
                    float newY2 = event.getY(1);
                    float newDeltaX = Math.abs(newX - newX2);
                    float newDeltaY = Math.abs(newY - newY2);
                    float oldDeltaX = Math.abs(this.oldX - this.oldX2);
                    float oldDeltaY = Math.abs(this.oldY - this.oldY2);
                    float zoomRate;
                    float tan1 = Math.abs(newY - this.oldY) / Math.abs(newX - this.oldX);
                    float tan2 = Math.abs(newY2 - this.oldY2) / Math.abs(newX2 - this.oldX2);
                    if ((double) tan1 <= 0.25D && (double) tan2 <= 0.25D) {
                        zoomRate = newDeltaX / oldDeltaX;
                        this.applyZoom(zoomRate, 1);
                    } else if ((double) tan1 >= 3.73D && (double) tan2 >= 3.73D) {
                        zoomRate = newDeltaY / oldDeltaY;
                        this.applyZoom(zoomRate, 2);
                    } else {
                        if (Math.abs(newX - this.oldX) >= Math.abs(newY - this.oldY)) {
                            zoomRate = newDeltaX / oldDeltaX;
                        } else {
                            zoomRate = newDeltaY / oldDeltaY;
                        }

                        this.applyZoom(zoomRate, 0);
                    }

                    this.oldX2 = newX2;
                    this.oldY2 = newY2;
                } else if (this.mRenderer.isPanEnabled()) {
                    this.mPan.apply(this.oldX, this.oldY, newX, newY);
                    this.oldX2 = 0.0F;
                    this.oldY2 = 0.0F;
                }

                this.oldX = newX;
                this.oldY = newY;
                this.graphicalView.repaint();
                return true;
            }
        } else if (action == 0) {
            this.oldX = event.getX(0);
            this.oldY = event.getY(0);
            if (this.mRenderer != null && this.mRenderer.isZoomEnabled() && this.zoomR.contains(this.oldX, this.oldY)) {
                if (this.oldX < this.zoomR.left + this.zoomR.width() / 3.0F) {
                    this.graphicalView.zoomIn();
                } else if (this.oldX < this.zoomR.left + this.zoomR.width() * 2.0F / 3.0F) {
                    this.graphicalView.zoomOut();
                } else {
                    this.graphicalView.zoomReset();
                }

                return true;
            }
        } else if (action == 1 || action == 6) {
            this.oldX = 0.0F;
            this.oldY = 0.0F;
            this.oldX2 = 0.0F;
            this.oldY2 = 0.0F;
            if (action == 6) {
                this.oldX = -1.0F;
                this.oldY = -1.0F;
            }
        }

        return !this.mRenderer.isClickEnabled();
    }

    private void applyZoom(float zoomRate, int axis) {
        zoomRate = Math.max(zoomRate, 0.9F);
        zoomRate = Math.min(zoomRate, 1.1F);
        if (this.mPinchZoom != null && (double) zoomRate > 0.9D && (double) zoomRate < 1.1D) {
            this.mPinchZoom.setZoomRate(zoomRate);
            this.mPinchZoom.apply(axis);
        }

    }

    public void addZoomListener(ZoomListener listener) {
        if (this.mPinchZoom != null) {
            this.mPinchZoom.addZoomListener(listener);
        }

    }

    public void removeZoomListener(ZoomListener listener) {
        if (this.mPinchZoom != null) {
            this.mPinchZoom.removeZoomListener(listener);
        }

    }

    public void addPanListener(PanListener listener) {
        if (this.mPan != null) {
            this.mPan.addPanListener(listener);
        }

    }

    public void removePanListener(PanListener listener) {
        if (this.mPan != null) {
            this.mPan.removePanListener(listener);
        }

    }
}

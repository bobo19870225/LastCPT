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
import www.jingkan.com.view.chart.achartengine.tools.ZoomListener;


public class TouchHandlerOld implements ITouchHandler {
    private DefaultRenderer mRenderer;
    private float oldX;
    private float oldY;
    private RectF zoomR = new RectF();
    private Pan mPan;
    private GraphicalView graphicalView;

    public TouchHandlerOld(GraphicalView view, AbstractChart chart) {
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

    }

    public boolean handleTouch(MotionEvent event) {
        int action = event.getAction();
        if (this.mRenderer != null && action == 2) {
            if (this.oldX >= 0.0F || this.oldY >= 0.0F) {
                float newX = event.getX();
                float newY = event.getY();
                if (this.mRenderer.isPanEnabled()) {
                    this.mPan.apply(this.oldX, this.oldY, newX, newY);
                }

                this.oldX = newX;
                this.oldY = newY;
                this.graphicalView.repaint();
                return true;
            }
        } else if (action == 0) {
            this.oldX = event.getX();
            this.oldY = event.getY();
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
        } else if (action == 1) {
            this.oldX = 0.0F;
            this.oldY = 0.0F;
        }

        return !this.mRenderer.isClickEnabled();
    }

    public void addZoomListener(ZoomListener listener) {
    }

    public void removeZoomListener(ZoomListener listener) {
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

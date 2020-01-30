//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import www.jingkan.com.view.chart.achartengine.chart.AbstractChart;
import www.jingkan.com.view.chart.achartengine.chart.RoundChart;
import www.jingkan.com.view.chart.achartengine.chart.XYChart;
import www.jingkan.com.view.chart.achartengine.model.Point;
import www.jingkan.com.view.chart.achartengine.model.SeriesSelection;
import www.jingkan.com.view.chart.achartengine.renderer.DefaultRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;
import www.jingkan.com.view.chart.achartengine.tools.FitZoom;
import www.jingkan.com.view.chart.achartengine.tools.PanListener;
import www.jingkan.com.view.chart.achartengine.tools.Zoom;
import www.jingkan.com.view.chart.achartengine.tools.ZoomListener;


public class GraphicalView extends View {
    private AbstractChart mChart;
    private DefaultRenderer mRenderer;
    private Rect mRect = new Rect();
    private Handler mHandler;
    private RectF mZoomR = new RectF();
    private Bitmap zoomInImage;
    private Bitmap zoomOutImage;
    private Bitmap fitZoomImage;
    private int zoomSize = 50;
    private static final int ZOOM_BUTTONS_COLOR = Color.argb(175, 150, 150, 150);
    private Zoom mZoomIn;
    private Zoom mZoomOut;
    private FitZoom mFitZoom;
    private Paint mPaint = new Paint();
    private ITouchHandler mTouchHandler;
    private float oldX;
    private float oldY;
    private boolean mDrawn;

    public GraphicalView(Context context, AbstractChart chart) {
        super(context);
        this.mChart = chart;
        this.mHandler = new Handler();
        if (this.mChart instanceof XYChart) {
            this.mRenderer = ((XYChart) this.mChart).getRenderer();
        } else {
            this.mRenderer = ((RoundChart) this.mChart).getRenderer();
        }

        if (this.mRenderer.isZoomButtonsVisible()) {
            this.zoomInImage = BitmapFactory.decodeStream(GraphicalView.class.getResourceAsStream("image/zoom_in.png"));
            this.zoomOutImage = BitmapFactory.decodeStream(GraphicalView.class.getResourceAsStream("image/zoom_out.png"));
            this.fitZoomImage = BitmapFactory.decodeStream(GraphicalView.class.getResourceAsStream("image/zoom-1.png"));
        }

        if (this.mRenderer instanceof XYMultipleSeriesRenderer && ((XYMultipleSeriesRenderer) this.mRenderer).getMarginsColor() == 0) {
            ((XYMultipleSeriesRenderer) this.mRenderer).setMarginsColor(this.mPaint.getColor());
        }

        if (this.mRenderer.isZoomEnabled() && this.mRenderer.isZoomButtonsVisible() || this.mRenderer.isExternalZoomEnabled()) {
            this.mZoomIn = new Zoom(this.mChart, true, this.mRenderer.getZoomRate());
            this.mZoomOut = new Zoom(this.mChart, false, this.mRenderer.getZoomRate());
            this.mFitZoom = new FitZoom(this.mChart);
        }

        int version = 7;

        try {
            version = Integer.valueOf(VERSION.SDK);
        } catch (Exception var5) {
        }

        if (version < 7) {
            this.mTouchHandler = new TouchHandlerOld(this, this.mChart);
        } else {
            this.mTouchHandler = new TouchHandler(this, this.mChart);
        }

    }

    public SeriesSelection getCurrentSeriesAndPoint() {
        return this.mChart.getSeriesAndPointForScreenCoordinate(new Point(this.oldX, this.oldY));
    }

    public boolean isChartDrawn() {
        return this.mDrawn;
    }

    public double[] toRealPoint(int scale) {
        if (this.mChart instanceof XYChart) {
            XYChart chart = (XYChart) this.mChart;
            return chart.toRealPoint(this.oldX, this.oldY, scale);
        } else {
            return null;
        }
    }

    public AbstractChart getChart() {
        return this.mChart;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.getClipBounds(this.mRect);
        int top = this.mRect.top;
        int left = this.mRect.left;
        int width = this.mRect.width();
        int height = this.mRect.height();
        if (this.mRenderer.isInScroll()) {
            top = 0;
            left = 0;
            width = this.getMeasuredWidth();
            height = this.getMeasuredHeight();
        }

        this.mChart.draw(canvas, left, top, width, height, this.mPaint);
        if (this.mRenderer != null && this.mRenderer.isZoomEnabled() && this.mRenderer.isZoomButtonsVisible()) {
            this.mPaint.setColor(ZOOM_BUTTONS_COLOR);
            this.zoomSize = Math.max(this.zoomSize, Math.min(width, height) / 7);
            this.mZoomR.set((float) (left + width - this.zoomSize * 3), (float) (top + height) - (float) this.zoomSize * 0.775F, (float) (left + width), (float) (top + height));
            canvas.drawRoundRect(this.mZoomR, (float) (this.zoomSize / 3), (float) (this.zoomSize / 3), this.mPaint);
            float buttonY = (float) (top + height) - (float) this.zoomSize * 0.625F;
            canvas.drawBitmap(this.zoomInImage, (float) (left + width) - (float) this.zoomSize * 2.75F, buttonY, (Paint) null);
            canvas.drawBitmap(this.zoomOutImage, (float) (left + width) - (float) this.zoomSize * 1.75F, buttonY, (Paint) null);
            canvas.drawBitmap(this.fitZoomImage, (float) (left + width) - (float) this.zoomSize * 0.75F, buttonY, (Paint) null);
        }

        this.mDrawn = true;
    }

    public void setZoomRate(float rate) {
        if (this.mZoomIn != null && this.mZoomOut != null) {
            this.mZoomIn.setZoomRate(rate);
            this.mZoomOut.setZoomRate(rate);
        }

    }

    public void zoomIn() {
        if (this.mZoomIn != null) {
            this.mZoomIn.apply(0);
            this.repaint();
        }

    }

    public void zoomOut() {
        if (this.mZoomOut != null) {
            this.mZoomOut.apply(0);
            this.repaint();
        }

    }

    public void zoomReset() {
        if (this.mFitZoom != null) {
            this.mFitZoom.apply();
            this.mZoomIn.notifyZoomResetListeners();
            this.repaint();
        }

    }

    public void addZoomListener(ZoomListener listener, boolean onButtons, boolean onPinch) {
        if (onButtons && this.mZoomIn != null) {
            this.mZoomIn.addZoomListener(listener);
            this.mZoomOut.addZoomListener(listener);
        }

        if (onPinch) {
            this.mTouchHandler.addZoomListener(listener);
        }

    }

    public synchronized void removeZoomListener(ZoomListener listener) {
        if (this.mZoomIn != null) {
            this.mZoomIn.removeZoomListener(listener);
            this.mZoomOut.removeZoomListener(listener);
        }

        this.mTouchHandler.removeZoomListener(listener);
    }

    public void addPanListener(PanListener listener) {
        this.mTouchHandler.addPanListener(listener);
    }

    public void removePanListener(PanListener listener) {
        this.mTouchHandler.removePanListener(listener);
    }

    protected RectF getZoomRectangle() {
        return this.mZoomR;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            this.oldX = event.getX();
            this.oldY = event.getY();
        }

        return this.mRenderer != null && this.mDrawn && (this.mRenderer.isPanEnabled() || this.mRenderer.isZoomEnabled()) && this.mTouchHandler.handleTouch(event) ? true : super.onTouchEvent(event);
    }

    public void repaint() {
        this.mHandler.post(new Runnable() {
            public void run() {
                GraphicalView.this.invalidate();
            }
        });
    }

    public void repaint(final int left, final int top, final int right, final int bottom) {
        this.mHandler.post(new Runnable() {
            public void run() {
                GraphicalView.this.invalidate(left, top, right, bottom);
            }
        });
    }

    public Bitmap toBitmap() {
        this.setDrawingCacheEnabled(false);
        if (!this.isDrawingCacheEnabled()) {
            this.setDrawingCacheEnabled(true);
        }

        if (this.mRenderer.isApplyBackgroundColor()) {
            this.setDrawingCacheBackgroundColor(this.mRenderer.getBackgroundColor());
        }

        this.setDrawingCacheQuality(DRAWING_CACHE_QUALITY_AUTO);
        return this.getDrawingCache(true);
    }
}

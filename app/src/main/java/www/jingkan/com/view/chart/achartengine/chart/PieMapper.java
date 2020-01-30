//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import www.jingkan.com.view.chart.achartengine.model.Point;
import www.jingkan.com.view.chart.achartengine.model.SeriesSelection;

public class PieMapper implements Serializable {
    private List<PieSegment> mPieSegmentList = new ArrayList();
    private int mPieChartRadius;
    private int mCenterX;
    private int mCenterY;

    public PieMapper() {
    }

    public void setDimensions(int pieRadius, int centerX, int centerY) {
        this.mPieChartRadius = pieRadius;
        this.mCenterX = centerX;
        this.mCenterY = centerY;
    }

    public boolean areAllSegmentPresent(int datasetSize) {
        return this.mPieSegmentList.size() == datasetSize;
    }

    public void addPieSegment(int dataIndex, float value, float startAngle, float angle) {
        this.mPieSegmentList.add(new PieSegment(dataIndex, value, startAngle, angle));
    }

    public void clearPieSegments() {
        this.mPieSegmentList.clear();
    }

    public double getAngle(Point screenPoint) {
        double dx = (double) (screenPoint.getX() - (float) this.mCenterX);
        double dy = (double) (-(screenPoint.getY() - (float) this.mCenterY));
        double inRads = Math.atan2(dy, dx);
        if (inRads < 0.0D) {
            inRads = Math.abs(inRads);
        } else {
            inRads = 6.283185307179586D - inRads;
        }

        return Math.toDegrees(inRads);
    }

    public boolean isOnPieChart(Point screenPoint) {
        double sqValue = Math.pow((double) ((float) this.mCenterX - screenPoint.getX()), 2.0D) + Math.pow((double) ((float) this.mCenterY - screenPoint.getY()), 2.0D);
        double radiusSquared = (double) (this.mPieChartRadius * this.mPieChartRadius);
        boolean isOnPieChart = sqValue <= radiusSquared;
        return isOnPieChart;
    }

    public SeriesSelection getSeriesAndPointForScreenCoordinate(Point screenPoint) {
        if (this.isOnPieChart(screenPoint)) {
            double angleFromPieCenter = this.getAngle(screenPoint);
            Iterator i$ = this.mPieSegmentList.iterator();

            while (i$.hasNext()) {
                PieSegment pieSeg = (PieSegment) i$.next();
                if (pieSeg.isInSegment(angleFromPieCenter)) {
                    return new SeriesSelection(0, pieSeg.getDataIndex(), (double) pieSeg.getValue(), (double) pieSeg.getValue());
                }
            }
        }

        return null;
    }
}

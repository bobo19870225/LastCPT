//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import java.io.Serializable;

public class PieSegment implements Serializable {
    private float mStartAngle;
    private float mEndAngle;
    private int mDataIndex;
    private float mValue;

    public PieSegment(int dataIndex, float value, float startAngle, float angle) {
        this.mStartAngle = startAngle;
        this.mEndAngle = angle + startAngle;
        this.mDataIndex = dataIndex;
        this.mValue = value;
    }

    public boolean isInSegment(double angle) {
        if (angle >= (double) this.mStartAngle && angle <= (double) this.mEndAngle) {
            return true;
        } else {
            double cAngle = angle % 360.0D;
            double startAngle = (double) this.mStartAngle;

            double stopAngle;
            for (stopAngle = (double) this.mEndAngle; stopAngle > 360.0D; stopAngle -= 360.0D) {
                startAngle -= 360.0D;
            }

            return cAngle >= startAngle && cAngle <= stopAngle;
        }
    }

    protected float getStartAngle() {
        return this.mStartAngle;
    }

    protected float getEndAngle() {
        return this.mEndAngle;
    }

    protected int getDataIndex() {
        return this.mDataIndex;
    }

    protected float getValue() {
        return this.mValue;
    }

    public String toString() {
        return "mDataIndex=" + this.mDataIndex + ",mValue=" + this.mValue + ",mStartAngle=" + this.mStartAngle + ",mEndAngle=" + this.mEndAngle;
    }
}

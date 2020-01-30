//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.renderer;

import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;

import java.io.Serializable;

public class BasicStroke implements Serializable {
    public static final BasicStroke SOLID;
    public static final BasicStroke DASHED;
    public static final BasicStroke DOTTED;
    private Cap mCap;
    private Join mJoin;
    private float mMiter;
    private float[] mIntervals;
    private float mPhase;

    public BasicStroke(Cap cap, Join join, float miter, float[] intervals, float phase) {
        this.mCap = cap;
        this.mJoin = join;
        this.mMiter = miter;
        this.mIntervals = intervals;
    }

    public Cap getCap() {
        return this.mCap;
    }

    public Join getJoin() {
        return this.mJoin;
    }

    public float getMiter() {
        return this.mMiter;
    }

    public float[] getIntervals() {
        return this.mIntervals;
    }

    public float getPhase() {
        return this.mPhase;
    }

    static {
        SOLID = new BasicStroke(Cap.BUTT, Join.MITER, 4.0F, (float[]) null, 0.0F);
        DASHED = new BasicStroke(Cap.ROUND, Join.BEVEL, 10.0F, new float[]{10.0F, 10.0F}, 1.0F);
        DOTTED = new BasicStroke(Cap.ROUND, Join.BEVEL, 5.0F, new float[]{2.0F, 10.0F}, 1.0F);
    }
}

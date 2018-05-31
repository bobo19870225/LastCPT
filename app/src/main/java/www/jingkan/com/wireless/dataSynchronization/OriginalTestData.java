/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.dataSynchronization;

/**
 * Created by lushengbo on 2018/1/18.
 * 原始试验数据类
 */

public class OriginalTestData {
    private long sj;
    private float qc;
    private float fs;
    private float fa;

    public OriginalTestData(long sj, float qc, float fs, float fa) {
        this.sj = sj;
        this.qc = qc;
        this.fs = fs;
        this.fa = fa;
    }

    public long getSj() {
        return sj;
    }

    public void setSj(long sj) {
        this.sj = sj;
    }

    public float getQc() {
        return qc;
    }

    public void setQc(float qc) {
        this.qc = qc;
    }

    public float getFs() {
        return fs;
    }

    public void setFs(float fs) {
        this.fs = fs;
    }

    public float getFa() {
        return fa;
    }

    public void setFa(float fa) {
        this.fa = fa;
    }
}

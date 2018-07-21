package www.jingkan.com.testDataDetails.crossTestDataDetails;

/**
 * Created by Sampson on 2018/7/21.
 * LastCPT
 */
public class DataItem {


    private float cu;
    private int deg;

    DataItem(float cu, int deg) {
        this.cu = cu;
        this.deg = deg;
    }

    public float getCu() {
        return cu;
    }

    public void setCu(float cu) {
        this.cu = cu;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}

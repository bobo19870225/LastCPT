package www.jingkan.com.view.adapter.crosstestdatalistadapter;

/**
 * Created by Sampson on 2018/7/21.
 * LastCPT
 */
public class DataGroup {


    private float deep;
    private String type;
    private int number;

    public DataGroup(float deep, String type, int number) {
        this.deep = deep;
        this.type = type;
        this.number = number;
    }

    public float getDeep() {
        return deep;
    }

    public void setDeep(float deep) {
        this.deep = deep;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

package www.jingkan.com.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import www.jingkan.com.view.adapter.ItemWirelessResultDataDetails;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "wirelessResultData")
public class WirelessResultDataEntity implements ItemWirelessResultDataDetails {
    //strProjectNumber + "_" + strHoleNumber.
    @PrimaryKey
    @NonNull
    public String testDataID = "";
    public String probeNumber;
    public float deep;
    public float qc;
    public float fs;
    public float fa;

    @Override
    public String getDeep() {
        return String.valueOf(deep);
    }

    @Override
    public String getQc() {
        return String.valueOf(qc);
    }

    @Override
    public String getFs() {
        return String.valueOf(fs);
    }

    @Override
    public String getFa() {
        return String.valueOf(fa);
    }

    @Override
    public Object getId() {
        return testDataID;
    }
}

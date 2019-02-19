package www.jingkan.com.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import www.jingkan.com.view.adapter.ItemMarkFileDetails;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "wirelessTestData")
public class WirelessTestDataEntity implements ItemMarkFileDetails {
    //strProjectNumber + "_" + strHoleNumber.
    @PrimaryKey
    @NonNull
    public String testDataID = "";
    public String probeNumber;
    public float deep;
    public long rtc;

    @Override
    public String getDeep() {
        return String.valueOf(deep);
    }

    @Override
    public String getSRC() {
        return String.valueOf(rtc);
    }

    @Override
    public Object getId() {
        return testDataID;
    }
}

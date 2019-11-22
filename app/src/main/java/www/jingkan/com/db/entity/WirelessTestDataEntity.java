package www.jingkan.com.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import www.jingkan.com.util.StringUtil;
import www.jingkan.com.view.adapter.ItemMarkFileDetails;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "wirelessTestData", primaryKeys = {"testDataID", "deep"})
public class WirelessTestDataEntity implements ItemMarkFileDetails {
    //strProjectNumber + "_" + strHoleNumber.
    @NonNull
    public String testDataID = "";
    public String probeNumber;
    public float deep;
    public long rtc;

    @Override
    public String getDeep() {
        return StringUtil.format(deep, 1);
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

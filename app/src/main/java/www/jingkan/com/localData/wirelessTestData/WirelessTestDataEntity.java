package www.jingkan.com.localData.wirelessTestData;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = WirelessTestDataConstant.TABLE_NAME)
public class WirelessTestDataEntity {
    //strProjectNumber + "_" + strHoleNumber.
    @PrimaryKey
    @NonNull
    public String testDataID;
    public String probeNumber;
    public float deep;
    public long rtc;
}

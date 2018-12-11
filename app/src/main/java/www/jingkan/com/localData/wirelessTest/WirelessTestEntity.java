package www.jingkan.com.localData.wirelessTest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = WirelessTestConstant.TABLE_NAME)
public class WirelessTestEntity {
    @PrimaryKey
    public String testID;//projectNumber_holeNumber
    public String testDate;
    public String projectNumber;
    public String holeNumber;
    public float holeHigh;
    public float waterLevel;
    public String location;
    public String tester;
    public String testType;
    public String testDataID;//projectNumber_holeNumber
}

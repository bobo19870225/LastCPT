package www.jingkan.com.localData.test;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
@Entity(tableName = TestConstant.TABLE_NAME)
public class TestEntity {
    @PrimaryKey
    public String testID;
    public String testDate;
    public String projectNumber;
    public String holeNumber;
    public float holeHigh;
    public float waterLevel;
    public String location;
    public String tester;
    public String testType;
    public String testProbeType;
    public String testDataID;
}

package www.jingkan.com.db.entity;

import com.jinkan.www.cpttest.view.adapter.ItemMarkupFile;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "wirelessTest")
public class WirelessTestEntity implements ItemMarkupFile {
    @PrimaryKey
    @NonNull
    public String testID = "";//projectNumber_holeNumber
    public String testDate;
    public String projectNumber;
    public String holeNumber;
    public float holeHigh;
    public float waterLevel;
    public String location;
    public String tester;
    public String testType;
    public String testDataID;//projectNumber_holeNumber


    @Override
    public String getTestId() {
        return testID;
    }

    @Override
    public String getTestData() {
        return testDate;
    }

    @Override
    public Object getId() {
        return testID;
    }
}

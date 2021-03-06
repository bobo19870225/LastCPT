package www.jingkan.com.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import www.jingkan.com.view.adapter.ItemMarkupFile;

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
    public Object getId() {
        return testID;
    }

    @Override
    public String getProjectNumber() {
        return projectNumber;
    }

    @Override
    public String getTestType() {
        return testType;
    }

    @Override
    public String getHoleNumber() {
        return holeNumber;
    }

    @Override
    public String getTestDate() {
        return testDate;
    }
}

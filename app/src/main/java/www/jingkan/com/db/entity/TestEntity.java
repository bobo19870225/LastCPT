package www.jingkan.com.db.entity;

import www.jingkan.com.view.adapter.ItemHistoryData;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
@Entity(tableName = "test")
public class TestEntity implements ItemHistoryData {
    @PrimaryKey
    @NonNull
    public String testID = "1";
    public String testDate = "1";
    public String projectNumber = "1";
    public String holeNumber = "1";
    public float holeHigh = 33;
    public float waterLevel = 44;
    public String location = "1";
    public String tester = "1";
    public String testType = "1";
    public String testProbeType = "1";
    public String testDataID = "1";

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


    @Override
    public Object getId() {
        return testID;
    }
}

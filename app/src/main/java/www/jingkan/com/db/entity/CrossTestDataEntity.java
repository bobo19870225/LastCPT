package www.jingkan.com.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "crossTestData", primaryKeys = {"testDataID", "number", "deg"})
public class CrossTestDataEntity {

    @NonNull
    //strProjectNumber + "_" + strHoleNumber.
    public String testDataID = "";
    public String probeID;
    public float deep;
    public String type;
    public int number;
    public float cu;
    public int deg;
}

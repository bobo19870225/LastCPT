package www.jingkan.com.localData.testData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
@Entity(tableName = TestDataConstant.TABLE_NAME)
public class TestDataEntity {
    @PrimaryKey
    public String testDataID;
    public String probeID;
    public float deep;
    public float qc;
    public float fs;
    public float fa;
}

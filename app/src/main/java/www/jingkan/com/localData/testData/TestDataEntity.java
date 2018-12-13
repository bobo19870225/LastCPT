package www.jingkan.com.localData.testData;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = TestDataConstant.TABLE_NAME)
public class TestDataEntity {
    @PrimaryKey
    @NonNull
    public String testDataID;
    public String probeID;
    public float deep;
    public float qc;
    public float fs;
    public float fa;
}

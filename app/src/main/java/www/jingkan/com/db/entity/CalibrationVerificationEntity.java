package www.jingkan.com.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "calibrationVerification")
public class CalibrationVerificationEntity {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String probeNo;
    public String type;
    public String standardValue;//标准值
    public String forceType;
    public float loadValue;
}

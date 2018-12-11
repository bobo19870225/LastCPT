package www.jingkan.com.localData.calibrationVerification;

import androidx.room.Entity;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = CalibrationVerificationConstant.TABLE_NAME)
public class CalibrationVerificationEntity {
    public String probeNo;
    public String type;
    public String standardValue;//标准值
    public String forceType;
    public float loadValue;
}

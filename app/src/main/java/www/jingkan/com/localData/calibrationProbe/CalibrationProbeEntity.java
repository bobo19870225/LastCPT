package www.jingkan.com.localData.calibrationProbe;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = CalibrationProbeConstant.TABLE_NAME)
public class CalibrationProbeEntity {
    @PrimaryKey
    @NonNull
    public String probeID = "";//其实就是sn
    public String number;
    public String work_area;
    public String differential;
}

package www.jingkan.com.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "calibrationProbe")
public class CalibrationProbeEntity {
    @PrimaryKey
    @NonNull
    public String probeID = "";//其实就是sn
    public String number;
    public String work_area;
    public String differential;
}

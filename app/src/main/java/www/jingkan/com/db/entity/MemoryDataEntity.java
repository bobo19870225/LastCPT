package www.jingkan.com.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "memoryData")
public class MemoryDataEntity {
    @NonNull
    @PrimaryKey
    public String probeID;
    public String probeNo;
    public String type;
    public String forceType;
    public int ADValue;
}

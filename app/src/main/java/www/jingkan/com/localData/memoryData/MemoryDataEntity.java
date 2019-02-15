package www.jingkan.com.localData.memoryData;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = MemoryDataConstant.TABLE_NAME)
public class MemoryDataEntity {
    @PrimaryKey
    @NonNull
    public String probeID = "";
    public String probeNo;
    public String type;
    public String forceType;
    public int ADValue;
}

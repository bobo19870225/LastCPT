package www.jingkan.com.localData.memoryData;

import androidx.room.Entity;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = MemoryDataConstant.TABLE_NAME)
public class MemoryDataEntity {
    public String probeID;
    public String probeNo;
    public String type;
    public String forceType;
    public int ADValue;
}

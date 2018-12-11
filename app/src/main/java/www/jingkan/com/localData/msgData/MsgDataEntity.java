package www.jingkan.com.localData.msgData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = MsgDataConstant.TABLE_NAME)
public class MsgDataEntity {
    @PrimaryKey
    public int msgID;
    public String title;
    public String time;
}

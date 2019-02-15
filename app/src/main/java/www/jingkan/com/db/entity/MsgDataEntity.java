package www.jingkan.com.db.entity;

import www.jingkan.com.view.adapter.ItemMyMsg;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "msgData")
public class MsgDataEntity implements ItemMyMsg {
    @PrimaryKey
    public int msgID = 0;
    public String title;
    public String time;

    @Override
    public String getMsgTime() {
        return time;
    }

    @Override
    public Object getId() {
        return msgID;
    }
}

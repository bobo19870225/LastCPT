package www.jingkan.com.db.dao;

import www.jingkan.com.db.entity.MsgDataEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Dao
public interface MsgDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMsgDataEntity(MsgDataEntity msgDataEntity);

    @Query("DELETE FROM msgData WHERE msgID = :msgId")
    void deleteMsgDataEntityByMsgId(int msgId);

    @Query("SELECT * FROM msgData")
    LiveData<List<MsgDataEntity>> getAllMsgDataEntity();

    @Query("SELECT * FROM msgData WHERE msgID = :msgId")
    LiveData<List<MsgDataEntity>> getMsgDataEntityByMsgId(String msgId);

}

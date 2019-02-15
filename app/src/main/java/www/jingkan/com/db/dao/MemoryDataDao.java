package www.jingkan.com.db.dao;

import com.jinkan.www.cpttest.db.entity.MemoryDataEntity;

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
public interface MemoryDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMemoryDataEntity(MemoryDataEntity memoryDataEntity);

    @Query("DELETE FROM memoryData WHERE probeID = :probeId")
    void deleteMemoryDataEntityByProbeId(String probeId);

    @Query("DELETE FROM memoryData WHERE probeID = :probeId " +
            "AND type = :type")
    void deleteMemoryDataEntityByProbeIdAndType(String probeId, String type);

    @Query("SELECT * FROM memoryData WHERE probeID = :probeId")
    LiveData<List<MemoryDataEntity>> getMemoryDataByProbeId(String probeId);

    @Query("SELECT * FROM memoryData WHERE probeID = :probeId AND type = :type")
    LiveData<List<MemoryDataEntity>>
    getMemoryDataByProbeIdAndType(String probeId, String type);
}

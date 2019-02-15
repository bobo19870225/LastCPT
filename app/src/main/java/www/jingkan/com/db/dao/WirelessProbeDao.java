package www.jingkan.com.db.dao;

import com.jinkan.www.cpttest.db.entity.WirelessProbeEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Dao
public interface WirelessProbeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWirelessProbeEntity(WirelessProbeEntity wirelessProbeEntity);

    @Query("DELETE FROM wirelessProbe WHERE probeID = :probeId")
    void deleteWirelessProbeEntityByProbeId(String probeId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upDataWirelessProbeEntity(WirelessProbeEntity wirelessProbeEntity);

    @Query("SELECT * FROM wirelessProbe")
    LiveData<List<WirelessProbeEntity>> getAllWirelessProbeEntity();

    @Query("SELECT * FROM wirelessProbe WHERE probeID = :probeId")
    LiveData<List<WirelessProbeEntity>> getWirelessProbeEntityByProbeId(String probeId);
}

package www.jingkan.com.db.dao;

import www.jingkan.com.db.entity.ProbeEntity;

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
public interface ProbeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProbeEntity(ProbeEntity probeEntity);

    @Query("DELETE FROM probe WHERE probeID = :probeId")
    void deleteProbeByProbeId(String probeId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upDateProbe(ProbeEntity probeEntity);

    @Query("SELECT * FROM probe")
    LiveData<List<ProbeEntity>> getAllProbe();

    @Query("SELECT * FROM probe WHERE probeID = :probeId")
    LiveData<List<ProbeEntity>> getProbeByProbeId(String probeId);

}

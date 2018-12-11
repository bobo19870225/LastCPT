package www.jingkan.com.localData.calibrationProbe;

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
public interface CalibrationProbeDaoForRoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCalibrationProbe(CalibrationProbeEntity calibrationProbeEntity);

    @Query("DELETE FROM calibrationProbe WHERE probeID = :probeId")
    void deleteCPEntityByProbeId(String probeId);

    @Query("SELECT * FROM calibrationProbe")
    LiveData<List<CalibrationProbeEntity>> getAllCalbrationProbeEntity();

    @Query("SELECT * FROM calibrationProbe WHERE probeID = :probeId")
    LiveData<List<CalibrationProbeEntity>> getCalbrationProbeEntityByProbeId(String probeId);

}

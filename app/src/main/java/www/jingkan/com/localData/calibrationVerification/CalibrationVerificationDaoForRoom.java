package www.jingkan.com.localData.calibrationVerification;

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
public interface CalibrationVerificationDaoForRoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCVEntity(CalibrationVerificationEntity calibrationVerificationEntity);

    @Query("DELETE FROM CalibrationVerification " +
            "WHERE probeNo LIKE :probeNo AND type LIKE :type")
    void deleteCVEntityByProbeNoAndType(String probeNo, String type);

    @Query("SELECT * FROM CalibrationVerification " +
            "WHERE probeNo = :probeNo AND type = :type")
    LiveData<List<CalibrationVerificationEntity>> getCVEntityByProbeNoAndType(String probeNo, String type);

    @Query("SELECT * FROM CalibrationVerification " +
            "WHERE probeNo = :probeNo AND type = :type AND forceType = :forceType")
    LiveData<List<CalibrationVerificationEntity>> getCVEntityByProbeNoAndType(String probeNo, String type, String forceType);
}

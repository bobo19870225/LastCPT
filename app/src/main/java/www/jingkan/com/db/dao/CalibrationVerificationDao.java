package www.jingkan.com.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import www.jingkan.com.db.entity.CalibrationVerificationEntity;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Dao
public interface CalibrationVerificationDao {
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
    LiveData<List<CalibrationVerificationEntity>> getCVEntityByProbeNoAndTypeAndForceType(String probeNo, String type, String forceType);

    @Query("SELECT * FROM CalibrationVerification ")
    LiveData<List<CalibrationVerificationEntity>> getAllCVEntity();

}

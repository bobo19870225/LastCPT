package www.jingkan.com.db.dao;

import www.jingkan.com.db.entity.WirelessTestEntity;

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
public interface WirelessTestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWirelessTestEntity(WirelessTestEntity wirelessTestEntity);

    @Query("DELETE FROM wirelessTest WHERE projectNumber = :projectNumber " +
            "AND holeNumber = :holeNumber")
    void deleteWirelessTestEntityByPrjNumberAndHoleNumber
            (String projectNumber, String holeNumber);

    @Query("SELECT * FROM wirelessTest")
    LiveData<List<WirelessTestEntity>> getAllWirelessTestEntity();

    @Query("SELECT * FROM wirelessTest WHERE testID = :testId")
    LiveData<List<WirelessTestEntity>> getWirelessTestEntityByTestId(String testId);

    @Query("SELECT * FROM WIRELESSTEST WHERE projectNumber = :projectNumber " +
            "AND holeNumber = :holeNumber")
    LiveData<List<WirelessTestEntity>>
    getWirelessTestEntityByPrjNumberAndHoleNumber(String projectNumber, String holeNumber);
}

package www.jingkan.com.localData.wirelessResultData;

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
public interface WirelessResultDaoForRoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWirelessResultDataEntity(WirelessResultDataEntity wirelessResultDataEntity);

    @Query("DELETE FROM wirelessResultData WHERE testDataID = :testDataId")
    void deleteWirelessResultDataEntityByTestDataId(String testDataId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upDateWirelessResultDataEntity(WirelessResultDataEntity wirelessResultDataEntity);

    @Query("SELECT * FROM wirelessResultData WHERE testDataID = :testDataId")
    LiveData<List<WirelessResultDataEntity>> getWirelessResultDataEntityByTestDataId(String testDataId);


}

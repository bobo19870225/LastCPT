package www.jingkan.com.db.dao;

import com.jinkan.www.cpttest.db.entity.WirelessTestDataEntity;

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
public interface WirelessTestDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWirelessTestDataEntity(WirelessTestDataEntity wirelessTestDataEntity);

    @Query("DELETE FROM WIRELESSTESTDATA WHERE testDataID = :testDataId")
    void deleteWirelessTestDataEntityByTestDataId(String testDataId);

    @Query("SELECT * FROM WIRELESSTESTDATA WHERE testDataID = :testDataId")
    LiveData<List<WirelessTestDataEntity>> getWTDEByTestDataId(String testDataId);

}

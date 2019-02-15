package www.jingkan.com.db.dao;

import com.jinkan.www.cpttest.db.entity.CrossTestDataEntity;

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
public interface CrossTestDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCrossTestDataEntity(CrossTestDataEntity crossTestDataEntity);

    @Query("DELETE FROM crossTestData WHERE testDataID = :testDataId")
    void deleteCrossTestDataEntityByTestDataId(String testDataId);

    @Query("SELECT * FROM crossTestData WHERE testDataID = :testDataId")
    LiveData<List<CrossTestDataEntity>> getCrossTestDataByTestDataId(String testDataId);

    @Query("SELECT * FROM crossTestData WHERE testDataID = :testDataId AND number = :number")
    LiveData<List<CrossTestDataEntity>>
    getCrossTestDataByTestDataIdAndNumber(String testDataId, int number);

}

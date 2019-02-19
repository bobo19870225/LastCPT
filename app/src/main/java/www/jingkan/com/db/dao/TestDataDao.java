package www.jingkan.com.db.dao;

import www.jingkan.com.db.entity.TestDataEntity;

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
public interface TestDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTestDataEntity(TestDataEntity testDataEntity);

    @Query("DELETE FROM testData WHERE testDataID = :testDataId")
    void deleteTestDataEntityByTestId(String testDataId);

    @Query("SELECT * FROM testData WHERE testDataID = :testDataId")
    LiveData<List<TestDataEntity>> getTestDataByTestDataId(String testDataId);

}

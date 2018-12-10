package www.jingkan.com.localData.testData;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
@Dao
public interface TestDataDaoForRoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TestDataEntity> testDataEntities);

    @Query("DELETE FROM TESTDATA WHERE testDataID = :testDataID")
    void deleteByTestDataId(String testDataID);

    @Query("SELECT * FROM TESTDATA WHERE testDataID = :testDataID")
    LiveData<List<TestDataEntity>> getTestDataByTestDataId(String testDataID);
}

package www.jingkan.com.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import www.jingkan.com.db.entity.TestEntity;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
@Dao
public interface TestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTestEntity(TestEntity testEntity);

    @Query("DELETE FROM TEST WHERE projectNumber = :projectNumber AND holeNumber = :holeNumber")
    void deleteTestEntityByPrjNumberAndHoleNumber(String projectNumber, String holeNumber);

    @Query("SELECT * FROM TEST ORDER BY testDate DESC")
    LiveData<List<TestEntity>> getAllTestes();

    @Query("SELECT * FROM TEST WHERE testDataID = :testDataId")
    LiveData<List<TestEntity>> getTestEntityByTestDataId(String testDataId);

    @Query("SELECT * FROM TEST WHERE testID = :testId")
    LiveData<List<TestEntity>> getTestEntityByTestId(String testId);

    @Query("SELECT * FROM TEST WHERE projectNumber = :projectNumber AND holeNumber = :holeNumber")
    LiveData<List<TestEntity>> getTestEntityByPrjNumberAndHoleNumber(String projectNumber, String holeNumber);

}


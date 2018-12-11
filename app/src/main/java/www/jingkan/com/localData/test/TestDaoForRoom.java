package www.jingkan.com.localData.test;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
@Dao
public interface TestDaoForRoom {
    @Query("SELECT * FROM TEST ORDER BY testDate DESC")
    LiveData<List<TestEntity>> getAllTestes();
}


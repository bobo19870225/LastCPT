package www.jingkan.com.localData;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import www.jingkan.com.localData.calibrationProbe.CalibrationProbeDaoForRoom;
import www.jingkan.com.localData.calibrationProbe.CalibrationProbeEntity;
import www.jingkan.com.localData.calibrationVerification.CalibrationVerificationDaoForRoom;
import www.jingkan.com.localData.calibrationVerification.CalibrationVerificationEntity;
import www.jingkan.com.localData.commonProbe.ProbeDaoForRoom;
import www.jingkan.com.localData.commonProbe.ProbeEntity;
import www.jingkan.com.localData.memoryData.MemoryDataDaoForRoom;
import www.jingkan.com.localData.memoryData.MemoryDataEntity;
import www.jingkan.com.localData.msgData.MsgDaoForRoom;
import www.jingkan.com.localData.msgData.MsgDataEntity;
import www.jingkan.com.localData.test.TestDaoForRoom;
import www.jingkan.com.localData.test.TestEntity;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataDaoForRoom;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataEntity;
import www.jingkan.com.localData.testData.TestDataDaoForRoom;
import www.jingkan.com.localData.testData.TestDataEntity;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeDaoForRoom;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeEntity;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataDaoForRoom;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataEntity;
import www.jingkan.com.localData.wirelessTest.WirelessTestDaoForRoom;
import www.jingkan.com.localData.wirelessTest.WirelessTestEntity;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataDaoForRoom;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataEntity;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
@Database(entities = {
        CalibrationProbeEntity.class,
        CalibrationVerificationEntity.class,
        ProbeEntity.class,
        MemoryDataEntity.class,
        MsgDataEntity.class,
        TestEntity.class,
        TestDataEntity.class,
        CrossTestDataEntity.class,
        WirelessProbeEntity.class,
        WirelessResultDataEntity.class,
        WirelessTestEntity.class,
        WirelessTestDataEntity.class
}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "jk_data";


    public abstract CalibrationProbeDaoForRoom calibrationProbeDaoForRoom();

    public abstract CalibrationVerificationDaoForRoom calibrationVerificationDaoForRoom();

    public abstract ProbeDaoForRoom probeDaoForRoom();

    public abstract MemoryDataDaoForRoom memoryDataDaoForRoom();

    public abstract MsgDaoForRoom msgDaoForRoom();

    public abstract TestDaoForRoom testDaoForRoom();

    public abstract TestDataDaoForRoom testDataDaoForRoom();

    public abstract CrossTestDataDaoForRoom crossTestDataDaoForRoom();

    public abstract WirelessProbeDaoForRoom wirelessProbeDaoForRoom();

    public abstract WirelessResultDataDaoForRoom wirelessResultDataDaoForRoom();

    public abstract WirelessTestDaoForRoom wirelessTestDaoForRoom();

    public abstract WirelessTestDataDaoForRoom wirelessTestDataDaoForRoom();
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }


    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `productsFts` USING FTS4("
                    + "`name` TEXT, `description` TEXT, content=`products`)");
            database.execSQL("INSERT INTO productsFts (`rowid`, `name`, `description`) "
                    + "SELECT `id`, `name`, `description` FROM products");

        }
    };
}

package www.jingkan.com.db;

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

import www.jingkan.com.db.dao.CalibrationProbeDao;
import www.jingkan.com.db.dao.CalibrationVerificationDao;
import www.jingkan.com.db.dao.CrossTestDataDao;
import www.jingkan.com.db.dao.MemoryDataDao;
import www.jingkan.com.db.dao.MsgDao;
import www.jingkan.com.db.dao.ProbeDao;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.dao.TestDataDao;
import www.jingkan.com.db.dao.WirelessProbeDao;
import www.jingkan.com.db.dao.WirelessResultDataDao;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.dao.WirelessTestDataDao;
import www.jingkan.com.db.entity.CalibrationProbeEntity;
import www.jingkan.com.db.entity.CalibrationVerificationEntity;
import www.jingkan.com.db.entity.CrossTestDataEntity;
import www.jingkan.com.db.entity.MemoryDataEntity;
import www.jingkan.com.db.entity.MsgDataEntity;
import www.jingkan.com.db.entity.ProbeEntity;
import www.jingkan.com.db.entity.TestDataEntity;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.db.entity.WirelessProbeEntity;
import www.jingkan.com.db.entity.WirelessResultDataEntity;
import www.jingkan.com.db.entity.WirelessTestDataEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;

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
}, version = 4)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "jk_data";


    public abstract CalibrationProbeDao calibrationProbeDao();

    public abstract CalibrationVerificationDao calibrationVerificationDao();

    public abstract ProbeDao probeDao();

    public abstract MemoryDataDao memoryDataDao();

    public abstract MsgDao msgDao();

    public abstract TestDao testDao();

    public abstract TestDataDao testDataDao();

    public abstract CrossTestDataDao crossTestDataDao();

    public abstract WirelessProbeDao wirelessProbeDao();

    public abstract WirelessResultDataDao wirelessResultDataDao();

    public abstract WirelessTestDao wirelessTestDao();

    public abstract WirelessTestDataDao wirelessTestDataDao();


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
//                .addMigrations(MIGRATION_2_3)
                .fallbackToDestructiveMigration()
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

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `testData` (`testDataID` TEXT NOT NULL, `probeID` TEXT, `deep` REAL NOT NULL, `qc` REAL NOT NULL, `fs` REAL NOT NULL, `fa` REAL NOT NULL, PRIMARY KEY(`testDataID`))");
        }
    };
}

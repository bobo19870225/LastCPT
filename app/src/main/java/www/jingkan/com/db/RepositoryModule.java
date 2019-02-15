package www.jingkan.com.db;

import android.app.Application;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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

/**
 * This is used by Dagger to inject the required arguments into the {@link }.
 */
@Module
abstract public class RepositoryModule {

    private static final int THREAD_COUNT = 3;


    @Singleton
    @Provides
    static AppDatabase provideDb(Application context) {
        return AppDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    static TestDao provideTasksDao(AppDatabase db) {
        return db.testDao();
    }

    @Singleton
    @Provides
    static TestDataDao provideTestDataDao(AppDatabase db) {
        return db.testDataDao();
    }

    @Singleton
    @Provides
    static ProbeDao provideProbeDao(AppDatabase db) {
        return db.probeDao();
    }

    @Singleton
    @Provides
    static CalibrationProbeDao provideCalibrationProbeDao(AppDatabase db) {
        return db.calibrationProbeDao();
    }

    @Singleton
    @Provides
    static CalibrationVerificationDao provideCalibrationVerificationDao(AppDatabase db) {
        return db.calibrationVerificationDao();
    }

    @Singleton
    @Provides
    static WirelessProbeDao provideWirelessProbeDao(AppDatabase db) {
        return db.wirelessProbeDao();
    }

    @Singleton
    @Provides
    static WirelessResultDataDao provideWirelessResultDataDao(AppDatabase db) {
        return db.wirelessResultDataDao();
    }

    @Singleton
    @Provides
    static WirelessTestDao provideWirelessTestDao(AppDatabase db) {
        return db.wirelessTestDao();
    }

    @Singleton
    @Provides
    static WirelessTestDataDao provideWirelessTestDataDao(AppDatabase db) {
        return db.wirelessTestDataDao();
    }

    @Singleton
    @Provides
    static MsgDao provideMsgDao(AppDatabase db) {
        return db.msgDao();
    }

    @Singleton
    @Provides
    static MemoryDataDao provideMemoryDataDao(AppDatabase db) {
        return db.memoryDataDao();
    }

    @Singleton
    @Provides
    static CrossTestDataDao provideCrossTestDataDao(AppDatabase db) {
        return db.crossTestDataDao();
    }
}

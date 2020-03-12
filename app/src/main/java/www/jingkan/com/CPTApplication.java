package www.jingkan.com;

import com.tencent.bugly.crashreport.CrashReport;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import www.jingkan.com.di.DaggerAppComponent;

/**
 * Created by Sampson on 2018/12/15.
 * CPTTest
 */
public class CPTApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "abfdb1bc23", true);
    }
}

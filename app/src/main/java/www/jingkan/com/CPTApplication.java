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
        CrashReport.initCrashReport(getApplicationContext(), "a283dada06", true);
        super.onCreate();

    }
}

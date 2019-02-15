package www.jingkan.com.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import www.jingkan.com.activity.NewTestActivity;
import www.jingkan.com.view.AddProbeActivity;
import www.jingkan.com.view.AddProbeInfoActivity;
import www.jingkan.com.view.DataSyncActivity;
import www.jingkan.com.view.HistoryDataActivity;
import www.jingkan.com.view.LinkBluetoothActivity;
import www.jingkan.com.view.MarkFileActivity;
import www.jingkan.com.view.MyLinkerActivity;
import www.jingkan.com.view.MyMsgActivity;
import www.jingkan.com.view.OpenFileActivity;
import www.jingkan.com.view.OrdinaryProbeActivity;
import www.jingkan.com.view.SingleBridgeTestActivity;
import www.jingkan.com.view.TestDataDetailsActivity;
import www.jingkan.com.view.TimeSynchronizationActivity;
import www.jingkan.com.view.VersionInfoActivity;
import www.jingkan.com.view.VideoActivity;
import www.jingkan.com.view.WirelessProbeActivity;
import www.jingkan.com.view.WirelessTestActivity;
import www.jingkan.com.view.base.BaseTestActivity;
import www.jingkan.com.view.main.CalibrationParameterActivity;
import www.jingkan.com.view.main.MainActivity;
import www.jingkan.com.view_model.main.MainModule;
import www.jingkan.com.view_model.new_test.NewTestModule;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subcomponents for us.
 */
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = NewTestModule.class)
    abstract NewTestActivity newTestDaggerActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SingleBridgeTestActivity singleBridgeTestActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract BaseTestActivity baseTestDaggerActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract LinkBluetoothActivity linkBluetoothActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract HistoryDataActivity historyDataActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract OrdinaryProbeActivity ordinaryProbeActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract TestDataDetailsActivity testDataDetailsActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract MyLinkerActivity myLinkerActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract MyMsgActivity myMsgActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract VideoActivity videoActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract VersionInfoActivity versionInfoActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract MarkFileActivity markFileActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract AddProbeActivity addProbeActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddProbeInfoModule.class)
    abstract AddProbeInfoActivity addProbeInfoActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddProbeInfoModule.class)
    abstract WirelessProbeActivity wirelessProbeActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract TimeSynchronizationActivity timeSynchronizationActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract OpenFileActivity openWFileActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract DataSyncActivity dataSyncActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract WirelessTestActivity wirelessTestActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract CalibrationParameterActivity calibrationParameterActivity();

}

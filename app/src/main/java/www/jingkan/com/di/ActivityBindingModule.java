package www.jingkan.com.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import www.jingkan.com.view.AddProbeActivity;
import www.jingkan.com.view.AddProbeInfoActivity;
import www.jingkan.com.view.AnalogCaCalibrationVerificationActivity;
import www.jingkan.com.view.CalibrationParameterActivity;
import www.jingkan.com.view.CalibrationVerificationActivity;
import www.jingkan.com.view.CrossTestActivity;
import www.jingkan.com.view.DataSyncActivity;
import www.jingkan.com.view.DoubleBridgeMultifunctionTestActivity;
import www.jingkan.com.view.DoubleBridgeTestActivity;
import www.jingkan.com.view.HistoryDataActivity;
import www.jingkan.com.view.LinkBluetoothActivity;
import www.jingkan.com.view.MarkFileActivity;
import www.jingkan.com.view.MarkFileDetailActivity;
import www.jingkan.com.view.MyLinkerActivity;
import www.jingkan.com.view.MyMsgActivity;
import www.jingkan.com.view.NewTestActivity;
import www.jingkan.com.view.OldSetCalibrationDataActivity;
import www.jingkan.com.view.OpenFileActivity;
import www.jingkan.com.view.OrdinaryProbeActivity;
import www.jingkan.com.view.SetCalibrationDataActivity;
import www.jingkan.com.view.SetEmailActivity;
import www.jingkan.com.view.ShowDataCharActivity;
import www.jingkan.com.view.SingleBridgeMultifunctionTestActivity;
import www.jingkan.com.view.SingleBridgeTestActivity;
import www.jingkan.com.view.TestDataDetailsActivity;
import www.jingkan.com.view.TestingActivity;
import www.jingkan.com.view.TimeSynchronizationActivity;
import www.jingkan.com.view.VersionInfoActivity;
import www.jingkan.com.view.VideoActivity;
import www.jingkan.com.view.WirelessProbeActivity;
import www.jingkan.com.view.WirelessResultDataDetailActivity;
import www.jingkan.com.view.WirelessTestActivity;
import www.jingkan.com.view.WirelessTestResultDataActivity;
import www.jingkan.com.view.base.BaseTestActivity;
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
    abstract MarkFileDetailActivity markFileDetailActivity();

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
    abstract ShowDataCharActivity showDataCharActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract TestingActivity testingActivity();

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

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SetEmailActivity setEmailActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract WirelessTestResultDataActivity wirelessTestResultDataActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract WirelessResultDataDetailActivity wirelessResultDataDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract DoubleBridgeMultifunctionTestActivity doubleBridgeMultifunctionTestActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract DoubleBridgeTestActivity doubleBridgeTestActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SingleBridgeTestActivity singleBridgeTestActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SingleBridgeMultifunctionTestActivity singleBridgeMultifunctionTestActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract CrossTestActivity crossTestActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SetCalibrationDataActivity setCalibrationDataActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract OldSetCalibrationDataActivity oldSetCalibrationDataActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract CalibrationVerificationActivity calibrationVerificationActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract AnalogCaCalibrationVerificationActivity analogCaCalibrationVerificationActivity();

}

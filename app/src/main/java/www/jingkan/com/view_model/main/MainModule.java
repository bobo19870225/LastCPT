package www.jingkan.com.view_model.main;

import www.jingkan.com.di.FragmentScoped;
import www.jingkan.com.view.InstrumentCalibrationFragment;
import www.jingkan.com.view.MeFragment;
import www.jingkan.com.view.OrdinaryTestFragment;
import www.jingkan.com.view.WirelessTestFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Sampson on 2018/12/15.
 * CPTTest
 */
@Module
public abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract OrdinaryTestFragment ordinaryTestFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MeFragment meFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract WirelessTestFragment wirelessTestFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract InstrumentCalibrationFragment instrumentCalibrationFragment();

}

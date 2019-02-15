package www.jingkan.com.view_model.main;

import com.jinkan.www.cpttest.di.FragmentScoped;
import com.jinkan.www.cpttest.view.InstrumentCalibrationFragment;
import com.jinkan.www.cpttest.view.MeFragment;
import com.jinkan.www.cpttest.view.OrdinaryTestFragment;
import com.jinkan.www.cpttest.view.WirelessTestFragment;

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

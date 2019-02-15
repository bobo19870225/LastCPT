package www.jingkan.com.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import www.jingkan.com.view.CrossFragment;
import www.jingkan.com.view.DoubleBridgeFragment;
import www.jingkan.com.view.SingleBridgeFragment;

/**
 * Created by Sampson on 2018/12/29.
 * CPTTest
 */
@Module
public abstract class AddProbeInfoModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract SingleBridgeFragment singleBridgeFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DoubleBridgeFragment doubleBridgeFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract CrossFragment crossFragment();
}

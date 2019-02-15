package www.jingkan.com.view_model.new_test;

import www.jingkan.com.di.ActivityScoped;
import www.jingkan.com.view.NewTestActivity;

import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Sampson on 2018/12/17.
 * CPTTest
 */
@Module
public class NewTestModule {
    @ActivityScoped
    @Provides
    NewTestViewModel providesNewTestViewModel(NewTestActivity newTestActivity) {
        return ViewModelProviders.of(newTestActivity).get(NewTestViewModel.class);
    }

}

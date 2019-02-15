package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityTestingBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.DialogMVVMDaggerActivity;
import www.jingkan.com.view_model.TestingViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class TestingActivity extends DialogMVVMDaggerActivity<TestingViewModel, ActivityTestingBinding> {
    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {

    }

    @Override
    public TestingViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(TestingViewModel.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_testing;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

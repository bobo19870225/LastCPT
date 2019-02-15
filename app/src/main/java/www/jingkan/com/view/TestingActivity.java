package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityTestingBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.base.DialogMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.TestingViewModel;

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
    public void callback(CallbackMessage callbackMessage) {

    }
}

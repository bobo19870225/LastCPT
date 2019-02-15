package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityCrossTestBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.base.DialogMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.CrossTestViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class CrossTestActivity extends DialogMVVMDaggerActivity<CrossTestViewModel, ActivityCrossTestBinding> {
    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {

    }

    @Override
    public CrossTestViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(CrossTestViewModel.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_cross_test;
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

    }
}

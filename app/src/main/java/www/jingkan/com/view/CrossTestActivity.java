package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityCrossTestBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.DialogMVVMDaggerActivity;
import www.jingkan.com.view_model.CrossTestViewModel;

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
    public void action(CallbackMessage callbackMessage) {

    }
}

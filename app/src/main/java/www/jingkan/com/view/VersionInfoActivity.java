package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityVersionInfoBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.VersionInfoVM;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class VersionInfoActivity extends BaseMVVMDaggerActivity<VersionInfoVM, ActivityVersionInfoBinding> {
    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {

    }

    @Override
    public int initView() {
        return R.layout.activity_version_info;
    }


    @Override
    public VersionInfoVM createdViewModel() {
        return ViewModelProviders.of(this).get(VersionInfoVM.class);
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

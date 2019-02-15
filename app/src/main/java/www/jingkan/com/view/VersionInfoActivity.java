package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityVersionInfoBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.VersionInfoVM;

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
    public void callback(CallbackMessage callbackMessage) {

    }
}

package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityCrossTestDataDetailsBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.CrossTestDataDetailsMV;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class CrossTestDataDetailsActivity extends BaseMVVMDaggerActivity<CrossTestDataDetailsMV, ActivityCrossTestDataDetailsBinding> {
    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {

    }

    @Override
    public int initView() {
        return R.layout.activity_cross_test_data_details;
    }


    @Override
    public CrossTestDataDetailsMV createdViewModel() {
        return ViewModelProviders.of(this).get(CrossTestDataDetailsMV.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

    }
}

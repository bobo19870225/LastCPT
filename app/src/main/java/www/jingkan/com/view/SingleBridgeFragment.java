package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.FragmentSingleBridgeBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerFragment;
import com.jinkan.www.cpttest.view_model.AddProbeInfoVM;

import java.util.Objects;

import javax.inject.Inject;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Sampson on 2018/12/29.
 * CPTTest
 */
public class SingleBridgeFragment extends BaseMVVMDaggerFragment<AddProbeInfoVM, FragmentSingleBridgeBinding> {
    @Inject
    public SingleBridgeFragment() {

    }

    @Override
    protected Object[] injectToViewModel() {
        return ((AddProbeInfoActivity) Objects.requireNonNull(getActivity())).injectToViewModel();
    }

    @Override
    protected int setLayOutId() {
        return R.layout.fragment_single_bridge;
    }

    @Override
    protected void setView() {

    }

    @Override
    public AddProbeInfoVM createdViewModel() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            return ViewModelProviders.of(activity).get(AddProbeInfoVM.class);
        }
        return null;
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {
        ((AddProbeInfoActivity) Objects.requireNonNull(getActivity())).callback(callbackMessage);
    }
}

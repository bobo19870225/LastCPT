package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.FragmentCrossBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerFragment;
import www.jingkan.com.view_model.AddProbeInfoVM;

import java.util.Objects;

import javax.inject.Inject;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Sampson on 2018/12/29.
 * CPTTest
 */
public class CrossFragment extends BaseMVVMDaggerFragment<AddProbeInfoVM, FragmentCrossBinding> {
    @Inject
    public CrossFragment() {

    }


    @Override
    protected Object[] injectToViewModel() {
        return ((AddProbeInfoActivity) Objects.requireNonNull(getActivity())).injectToViewModel();
    }

    @Override
    protected int setLayOutId() {
        return R.layout.fragment_cross;
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
    public void action(CallbackMessage callbackMessage) {
        ((AddProbeInfoActivity) Objects.requireNonNull(getActivity())).action(callbackMessage);
    }
}

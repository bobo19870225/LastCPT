/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.FragmentMeBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerFragment;
import com.jinkan.www.cpttest.view_model.MeViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;


public class MeFragment extends BaseMVVMDaggerFragment<MeViewModel, FragmentMeBinding> {

    @Inject
    public MeFragment() {

    }

    @Override
    protected void setView() {

    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected int setLayOutId() {
        return R.layout.fragment_me;
    }

    @Override
    public MeViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(MeViewModel.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

        switch (callbackMessage.what) {
            case 0:
                goTo(MyLinkerActivity.class, "设置连接器");
                break;
            case 1:
                goTo(SetEmailActivity.class, null);
                break;
            case 2:
                goTo(VideoActivity.class, null);
                break;
            case 3:
                goTo(VersionInfoActivity.class, null);
                break;
            case 4:
                goTo(MyMsgActivity.class, null);
                break;
        }

    }
}

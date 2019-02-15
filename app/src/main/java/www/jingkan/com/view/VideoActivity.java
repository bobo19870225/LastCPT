package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityVideoBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.VideoViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class VideoActivity extends BaseMVVMDaggerActivity<VideoViewModel, ActivityVideoBinding> {
    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {

    }

    @Override
    public int initView() {
        return R.layout.activity_video;
    }

    @Override
    public VideoViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(VideoViewModel.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

    }
}

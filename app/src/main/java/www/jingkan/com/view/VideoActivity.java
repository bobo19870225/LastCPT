package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityVideoBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.VideoViewModel;

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
    public void action(CallbackMessage callbackMessage) {

    }
}

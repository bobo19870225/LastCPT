/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.me;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import www.jingkan.com.R;
import www.jingkan.com.activity.GuideActivity;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseFragment;
import www.jingkan.com.me.myMsg.MyMsgActivity;
import www.jingkan.com.me.versionInfo.VersionInfoActivity;


public class MeFragment extends BaseFragment {
    @BindView(id = R.id.my_linker, click = true)
    private RelativeLayout my_linker;
    @BindView(id = R.id.my_email, click = true)
    private RelativeLayout my_email;
    @BindView(id = R.id.check_version, click = true)
    private RelativeLayout check_version;
    @BindView(id = R.id.my_msg, click = true)
    private RelativeLayout my_msg;
    @BindView(id = R.id.my_video, click = true)
    private RelativeLayout my_video;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    protected void setView() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_linker:
                goTo(MyLinkerActivity.class, "设置连接器");
                break;
            case R.id.my_email:
                goTo(SetEmailActivity.class, null);
                break;
            case R.id.my_video:
                goTo(GuideActivity.class, null);
                break;
            case R.id.check_version:
                goTo(VersionInfoActivity.class, null);
                break;
            case R.id.my_msg:
                goTo(MyMsgActivity.class, null);
                break;
            default:
                break;
        }
    }

}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.me.myMsg;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.BaseMVVMActivity;
import www.jingkan.com.databinding.ActivityMyMsgDetailBinding;

/**
 * Created by lushengbo on 2018/1/
 * 我的信息详情
 */

public class MyMsgDetailActivity extends BaseMVVMActivity<MyMsgDetailViewModel, ActivityMyMsgDetailBinding> {
    @Override
    protected MyMsgDetailViewModel createdViewModel() {
        return new MyMsgDetailViewModel();
    }


    @Override
    protected void setView() {
        setToolBar("消息详情");
    }

    @Override
    public int initView() {
        return R.layout.activity_my_msg_detail;
    }
}

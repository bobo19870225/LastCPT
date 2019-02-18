/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityMyMsgDetailBinding;
import www.jingkan.com.db.dao.MsgDao;
import www.jingkan.com.db.entity.MsgDataEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.MyMsgDetailViewModel;

/**
 * Created by lushengbo on 2018/1/
 * 我的信息详情
 */

public class MyMsgDetailActivity extends BaseMVVMDaggerActivity<MyMsgDetailViewModel, ActivityMyMsgDetailBinding> {

    @Inject
    MsgDao msgDao;

    @Override
    public MyMsgDetailViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(MyMsgDetailViewModel.class);
    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, msgDao};
    }

    @Override
    protected void setMVVMView() {
        setToolBar("消息详情");
        mViewModel.ldMsgDataEntities.observe(this, msgDataEntities -> {
            if (msgDataEntities != null && !msgDataEntities.isEmpty()) {
                MsgDataEntity msgDataEntity = msgDataEntities.get(0);
                mViewModel.msg.set(msgDataEntity.title);
                mViewModel.time.set(msgDataEntity.time);
            }
        });
    }

    @Override
    public int initView() {
        return R.layout.activity_my_msg_detail;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

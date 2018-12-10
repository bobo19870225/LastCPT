/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.me.myMsg;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMListActivity;
import www.jingkan.com.databinding.ActivityMyMsgBinding;
import www.jingkan.com.localData.msgData.MsgDataModel;

/**
 * Created by lushengbo on 2018/1/23.
 * 我的信息界面
 */

public class MyMsgActivity extends MVVMListActivity<MyMsgViewModel, ActivityMyMsgBinding> {
    @Override
    public void setListView(final List list) {
        ListView listView = mViewDataBinding.list;
        listView.setEmptyView(mViewDataBinding.hint);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            MsgDataModel msgDataModel = (MsgDataModel) list.get(i);
            goTo(MyMsgDetailActivity.class, msgDataModel.msgID);
        });
    }

    @Override
    protected MyMsgViewModel createdViewModel() {
        return new MyMsgViewModel();
    }


    @Override
    protected void setViewWithOutListView() {
        setToolBar("我的消息列表");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return mViewDataBinding.srl;
    }

    @Override
    public int initView() {
        return R.layout.activity_my_msg;
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityMyMsgBinding;
import com.jinkan.www.cpttest.db.dao.MsgDao;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.adapter.MyMsgAdapter;
import com.jinkan.www.cpttest.view.base.ListMVVMActivity;
import com.jinkan.www.cpttest.view_model.MyMsgViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by lushengbo on 2018/1/23.
 * 我的信息界面
 */

public class MyMsgActivity extends ListMVVMActivity<MyMsgViewModel, ActivityMyMsgBinding, MyMsgAdapter> {
//    @Override
//    public void setListView(final List list) {
//        ListView listView = mViewDataBinding.list;
//        listView.setEmptyView(mViewDataBinding.hint);
//        listView.setOnItemClickListener((adapterView, view, i, l) -> {
//            MsgDataEntity msgDataModel = (MsgDataEntity) list.get(i);
//            goTo(MyMsgDetailActivity.class, msgDataModel.msgID);
//        });
//    }

    @Inject
    MsgDao msgDao;

    @Override
    protected RecyclerView setRecyclerView() {
        return mViewDataBinding.listView;
    }

    @Override
    protected MyMsgAdapter setAdapter() {
        return new MyMsgAdapter(R.layout.item_my_msg, null);
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

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, msgDao};
    }

    @Override
    public MyMsgViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(MyMsgViewModel.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

    }
}

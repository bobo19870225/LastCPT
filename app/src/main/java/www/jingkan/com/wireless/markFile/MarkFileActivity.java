/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.markFile;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMListActivity;
import www.jingkan.com.databinding.ActivityMarkFileBinding;
import www.jingkan.com.localData.wirelessTest.WirelessTestModel;

/**
 * Created by lushengbo on 2018/1/15.
 * 标记文件View
 */

public class MarkFileActivity extends MVVMListActivity<MarkFileViewModel, ActivityMarkFileBinding> {


    @Override
    protected MarkFileViewModel createdViewModel() {
        return new MarkFileViewModel();
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("标记数据");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return mViewDataBinding.srl;
    }


    @Override
    public int initView() {
        return R.layout.activity_mark_file;
    }

    @Override
    public void setListView(final List list) {
        ListView listView = mViewDataBinding.list;
        listView.setEmptyView(mViewDataBinding.hint);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goTo(MarkFileDetailActivity.class, ((WirelessTestModel) list.get(i)).testDataID);
            }
        });
    }
}

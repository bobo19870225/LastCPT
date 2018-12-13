/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.testData;

import android.widget.ListView;

import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMListActivity;
import www.jingkan.com.databinding.ActivityWirelessTestDataBinding;
import www.jingkan.com.localData.wirelessTest.WirelessTestEntity;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验数据列表
 */

public class WirelessTestDataActivity extends MVVMListActivity<WirelessTestDataViewModel, ActivityWirelessTestDataBinding> {
    @Override
    public void setListView(final List list) {
        ListView listView = mViewDataBinding.list;
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            WirelessTestEntity wirelessTestEntity = (WirelessTestEntity) list.get(i);
            goTo(WirelessResultDataDetailActivity.class, wirelessTestEntity.testDataID);
        });
        listView.setEmptyView(mViewDataBinding.hint);
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("无缆试验数据列表");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return mViewDataBinding.srl;
    }

    @Override
    public int initView() {
        return R.layout.activity_wireless_test_data;
    }

    @Override
    protected WirelessTestDataViewModel createdViewModel() {
        return new WirelessTestDataViewModel();
    }
}

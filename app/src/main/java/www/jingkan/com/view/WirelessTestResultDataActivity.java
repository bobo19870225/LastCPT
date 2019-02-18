/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityWirelessTestResultDataBinding;
import www.jingkan.com.db.dao.WirelessResultDataDao;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.adapter.WirelessTestDataAdapter;
import www.jingkan.com.view.base.ListMVVMActivity;
import www.jingkan.com.view_model.WirelessTestResultDataViewModel;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验数据列表
 */

public class WirelessTestResultDataActivity extends ListMVVMActivity<WirelessTestResultDataViewModel, ActivityWirelessTestResultDataBinding, WirelessTestDataAdapter> {
    //    @Override
//    public void setListView(final List list) {
//        ListView listView = mViewDataBinding.list;
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                WirelessTestModel wirelessTestModel = (WirelessTestModel) list.get(i);
//                goTo(WirelessResultDataDetailActivity.class, wirelessTestModel.testDataID);
//            }
//        });
//        listView.setEmptyView(mViewDataBinding.hint);
//    }
    @Inject
    WirelessResultDataDao wirelessResultDataDao;

    @Override
    protected RecyclerView setRecyclerView() {
        return mViewDataBinding.listView;
    }

    @Override
    protected WirelessTestDataAdapter setAdapter() {
        return new WirelessTestDataAdapter(R.layout.item_wireless_test_result_data, null);
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
        return R.layout.activity_wireless_test_result_data;
    }

    @Override
    public WirelessTestResultDataViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(WirelessTestResultDataViewModel.class);
    }

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, wirelessResultDataDao};
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

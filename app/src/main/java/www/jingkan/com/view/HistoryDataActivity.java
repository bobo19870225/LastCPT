/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityHistoryDataBinding;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.dao.TestDaoHelper;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.adapter.HistoryDataAdapter;
import www.jingkan.com.view.adapter.ItemHistoryData;
import www.jingkan.com.view.adapter.ItemHistoryDataClickCallback;
import www.jingkan.com.view.base.ListMVVMActivity;
import www.jingkan.com.view_model.HistoryDataViewModel;

import static www.jingkan.com.util.SystemConstant.VANE_TEST;

public class HistoryDataActivity extends ListMVVMActivity<HistoryDataViewModel, ActivityHistoryDataBinding, HistoryDataAdapter> {
    @Inject
    TestDao testDao;
    @Inject
    TestDaoHelper testDaoHelper;
    @SuppressWarnings("unchecked")
    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return mViewDataBinding.srl;
    }



    @Override
    public int initView() {
        return R.layout.activity_history_data;
    }

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, testDao, testDaoHelper};
    }


    @Override
    protected RecyclerView setRecyclerView() {
        return mViewDataBinding.listView;
    }

    @Override
    protected HistoryDataAdapter setAdapter() {

        return new HistoryDataAdapter(R.layout.item_history_data, new ItemHistoryDataClickCallback() {
            @Override
            public void onClick(ItemHistoryData itemHistoryData) {

                if (itemHistoryData.getTestType().equals(VANE_TEST)) {
                    goTo(CrossTestDataDetailsActivity.class, itemHistoryData.getId());
                } else {
                    goTo(TestDataDetailsActivity.class, itemHistoryData.getId());
                }
            }

            @Override
            public void onDeleteClick(ItemHistoryData itemHistoryData) {
                showDeleteDialog(itemHistoryData);
            }
        });
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("历史数据");
        mViewModel.action.observe(this, s -> {
            if (s.equals("刷新")) {
                toRefresh();
            }
        });
    }


    @Override
    public HistoryDataViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(HistoryDataViewModel.class);
    }

    private void showDeleteDialog(ItemHistoryData itemHistoryData) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("确定要删除该试验孔的数据吗？删除后无法恢复！")
                .setPositiveButton("确定", (dialog, which) -> mViewModel.deleteOneHistoryData(itemHistoryData))
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).create();
        alertDialog.show();
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

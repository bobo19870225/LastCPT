/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.base.baseMVVM;

import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import www.jingkan.com.R;

/**
 * Created by lushengbo on 2018/1/16.
 * 列表页面基类
 */

public abstract class MVVMListActivity<VM extends MVVMListViewModel, VDB extends ViewDataBinding> extends BaseMVVMActivity<VM, VDB> {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = () -> mViewModel.loadListViewData();

    @Override
    protected void setView() {
        mSwipeRefreshLayout = setSwipeRefreshLayout();
        if (mSwipeRefreshLayout != null) {
            // 设置下拉进度的主题颜色
            mSwipeRefreshLayout.setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_blue_bright,
                    R.color.colorPrimaryDark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_red_dark,
                    android.R.color.holo_purple);
        }
        setViewWithOutListView();
    }

    protected abstract void setViewWithOutListView();

    /**
     * 停止加载数据，一般在加载结束时调用
     */
    public void stopLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 设置刷新控件
     *
     * @param <SRL> 刷新控件
     * @return 刷新控件
     */
    protected abstract <SRL extends SwipeRefreshLayout> SRL setSwipeRefreshLayout();

    /**
     * 一般在请求完数据以后调用，让Activity去处理点击事件
     *
     * @param list 数据集合
     */
    public abstract void setListView(List list);

    @Override
    protected void toRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
            mSwipeRefreshLayout.setRefreshing(true);//不会出发onRefresh
            onRefreshListener.onRefresh();//强制刷新
        }
    }
}

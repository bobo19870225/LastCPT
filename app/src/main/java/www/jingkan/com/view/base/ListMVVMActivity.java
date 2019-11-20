package www.jingkan.com.view.base;

import android.annotation.SuppressLint;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.view.adapter.MyBaseAdapter;
import www.jingkan.com.view_model.base.BaseListViewModel;

/**
 * Created by Sampson on 2018/12/24.
 * CPTTest
 */
@SuppressLint("Registered")
public abstract class ListMVVMActivity<VM extends BaseListViewModel, VDB extends ViewDataBinding, A extends MyBaseAdapter> extends BaseMVVMDaggerActivity<VM, VDB> {
    private A mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = this::loadListData;
    private List list;

    public List getList() {
        return list;
    }

    /**
     * 设置刷新控件
     *
     * @param <SRL> 刷新控件
     * @return 刷新控件
     */
    protected abstract <SRL extends SwipeRefreshLayout> SRL setSwipeRefreshLayout();

    @Override
    protected final void setMVVMView() {
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
        mAdapter = setAdapter();
        RecyclerView recyclerView = setRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        setViewWithOutListView();
    }

    protected abstract RecyclerView setRecyclerView();

    protected abstract A setAdapter();

    protected abstract void setViewWithOutListView();

    /**
     * 停止加载数据，一般在加载结束时调用
     */
    public void stopLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void toRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
            mSwipeRefreshLayout.setRefreshing(true);//不会出发onRefresh
            onRefreshListener.onRefresh();//强制刷新
        } else {
            loadListData();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadListData() {

        stopLoading();
        LiveData liveData = mViewModel.loadListViewData();
        liveData.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                list = null;
                liveData.removeObserver(this);//避免重复刷新
                if (o == null) {
                    mViewModel.isEmpty.setValue(true);
                } else if (o instanceof List && ((List) o).size() != 0) {
                    list = (List) o;
                    mViewModel.isEmpty.setValue(false);
                } else {
                    mViewModel.isEmpty.setValue(true);
                }
                mAdapter.setList(list);
            }
        });
        mViewModel.afterLoadListViewData();
    }


}

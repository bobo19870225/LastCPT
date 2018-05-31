/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.base.baseMVVM;

import www.jingkan.com.adapter.BaseDataBindingAdapter;

/**
 * Created by lushengbo on 2018/1/25.
 * 列表VM
 */

public abstract class MVVMListViewModel<V extends MVVMListActivity> extends BaseViewModel<V> {
    protected BaseDataBindingAdapter adapter;

    @Override
    protected final void init(Object data) {
        adapter = setUpAdapter();
        initData(data);
    }

    /**
     * 加载列表以外的数据
     * @param data 列表以外的数据
     */
    protected abstract void initData(Object data);

    public abstract BaseDataBindingAdapter setUpAdapter();

    public BaseDataBindingAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseDataBindingAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * 加载列表数据
     */
    public abstract void loadListViewData();
}

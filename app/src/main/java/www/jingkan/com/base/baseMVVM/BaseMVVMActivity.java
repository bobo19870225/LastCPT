/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.base.baseMVVM;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import www.jingkan.com.BR;
import www.jingkan.com.base.BaseActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by lushengbo on 2018/1/12.
 * MVVM View基类
 */

public abstract class BaseMVVMActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseActivity {
    protected VM mViewModel;
    protected VDB mViewDataBinding;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mViewModel = checkNotNull(createdViewModel());
        super.onCreate(savedInstanceState);
        mViewModel.attachView(this);
        mViewModel.init(mData);
    }

    protected abstract VM createdViewModel();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.clear();
        mViewModel.detachView();
    }

    @Override
    protected void init(int viewId) {
        mViewDataBinding = DataBindingUtil.setContentView(this, viewId);
        mViewDataBinding.setVariable(BR.model, mViewModel);
        mRootView = mViewDataBinding.getRoot();
        mFragmentManager = getSupportFragmentManager();
    }


    @Override
    public void onClick(View view) {

    }
    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode 请求码
     * @param resultCode  应答码
     * @param data        数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }
}

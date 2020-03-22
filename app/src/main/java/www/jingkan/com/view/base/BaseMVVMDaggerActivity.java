/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.base;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import www.jingkan.com.BR;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by lushengbo on 2018/1/12.
 * MVVM View基类
 */

public abstract class BaseMVVMDaggerActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseDaggerActivity
        implements MVVMView<VM, VDB>, ViewCallback {
    protected VM mViewModel;
    protected VDB mViewDataBinding;

    @Inject
    CallbackMessage callbackMessage;
    @Override
    protected final void setView() {
        VM vm = createdViewModel();
        if (vm == null) {
            throw new RuntimeException("ViewModel can't be null!");
        } else {
            mViewModel = vm;
            mViewModel.setLifecycleOwner(this);
            mViewModel.attachView(this, callbackMessage);
            mViewDataBinding.setVariable(BR.model, mViewModel);
        }
        mViewModel.inject(injectToViewModel());
        setMVVMView();
    }

    @Deprecated
    protected abstract Object[] injectToViewModel();

    protected abstract void setMVVMView();

    @Override
    public VDB setViewDataBinding(int layOutId) {
        VDB viewDataBinding = DataBindingUtil.setContentView(this, layOutId);
//        viewDataBinding.setVariable(BR.model, mViewModel);
        viewDataBinding.setLifecycleOwner(this);
        return viewDataBinding;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.clear();
//        mViewModel.detachView();
    }

    @Override
    protected void init(int viewId) {
        WeakReference<VDB> weakReferenceVDB = new WeakReference<>(setViewDataBinding(viewId));
        mViewDataBinding = weakReferenceVDB.get();
//         ViewModelProviders.of(this, new ViewModelProvider.NewInstanceFactory()).get(NewTestViewModel.class);
        mRootView = new WeakReference<>(mViewDataBinding.getRoot());
        mFragmentManager = getSupportFragmentManager();
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

    @Override
    public final void toast(String msg) {
        showToast(msg);
    }
}

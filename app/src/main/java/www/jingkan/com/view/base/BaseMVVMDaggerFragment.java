package www.jingkan.com.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.jingkan.com.BR;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view_model.base.BaseViewModel;

import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by Sampson on 2018/12/16.
 * CPTTest
 */
public abstract class BaseMVVMDaggerFragment<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseDaggerFragment
        implements MVVMView<VM, VDB>, ViewCallback {
    protected VM mViewModel;
    protected VDB mViewDataBinding;
    @Inject
    CallbackMessage callbackMessage;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = createdViewModel();
        if (mViewModel == null) {
            throw new RuntimeException("ViewModel can't be null!");
        } else {
            mViewModel.attachView(this, callbackMessage);
        }

    }

    @Override
    public final View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int viewId = setLayOutId();
        mViewDataBinding = DataBindingUtil.inflate(inflater, viewId, container, false);
        mViewDataBinding.setVariable(BR.model, mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
//        ViewModelProviders.of(this).get();
        mRootView = mViewDataBinding.getRoot();
        mViewModel.inject(injectToViewModel());
        return mRootView;
    }

    protected abstract Object[] injectToViewModel();

    @Override
    public VDB setViewDataBinding(int layOutId) {
        return mViewDataBinding;
    }

    protected abstract @LayoutRes
    int setLayOutId();

    @Override
    public final void toast(String msg) {
        showToast(msg);
    }
}

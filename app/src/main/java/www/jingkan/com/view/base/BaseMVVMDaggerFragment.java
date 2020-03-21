package www.jingkan.com.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import www.jingkan.com.BR;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2018/12/16.
 * CPTTest
 */
public abstract class BaseMVVMDaggerFragment<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseDaggerFragment
        implements MVVMView<VM, VDB>, ViewCallback {
    protected VM mViewModel;
    protected WeakReference<VDB> mViewDataBinding;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int viewId = setLayOutId();
        mViewDataBinding = new WeakReference<>(DataBindingUtil.inflate(inflater, viewId, container, false));
        mViewDataBinding.get().setVariable(BR.model, mViewModel);
        mViewDataBinding.get().setLifecycleOwner(this);
//        ViewModelProviders.of(this).get();
        mRootView = new WeakReference<>(mViewDataBinding.get().getRoot());
        mViewModel.inject(injectToViewModel());
        return mRootView.get();
    }

    protected abstract Object[] injectToViewModel();

    @Override
    public VDB setViewDataBinding(int layOutId) {
        return mViewDataBinding.get();
    }

    protected abstract @LayoutRes
    int setLayOutId();

    @Override
    public final void toast(String msg) {
        showToast(msg);
    }
}

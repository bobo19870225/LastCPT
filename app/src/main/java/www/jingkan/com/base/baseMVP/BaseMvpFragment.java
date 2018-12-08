/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.base.baseMVP;

import android.os.Bundle;
import androidx.annotation.Nullable;

import www.jingkan.com.base.BaseFragment;
import www.jingkan.com.mInterface.MvpView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by lushengbo on 2017/4/23.
 * Mvp 碎片基类
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment
        implements MvpView {
    protected P mPresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
        mPresenter.detachView();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = checkNotNull((P) createdPresenter());
        mPresenter.attachView(this);
        mPresenter.init(getContext(), mData);
    }

    @Override
    public void onResume() {
        super.onResume();
        toRefresh();
    }

    /**
     * 需要每次进入时刷新的覆盖此方法
     */
    protected void toRefresh() {

    }


}

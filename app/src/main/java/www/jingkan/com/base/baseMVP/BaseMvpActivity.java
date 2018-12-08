/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.base.baseMVP;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.mInterface.MvpView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by bobo on 2017/3/26.
 * MVP活动基类
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements MvpView {
    protected P mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (P) checkNotNull( createdPresenter());
        mPresenter.attachView(this);
        mPresenter.init(getApplicationContext(), mData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
        mPresenter.detachView();
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
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

}

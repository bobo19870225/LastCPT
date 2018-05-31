/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.base.baseMVP;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import www.jingkan.com.mInterface.MvpPresenter;
import www.jingkan.com.mInterface.MvpView;

/**
 * Created by bobo on 2017/3/12.
 * 中介基类
 */

public abstract class BasePresenter<V extends MvpView> implements MvpPresenter {
    protected Reference<V> myView;

    @SuppressWarnings("unchecked")
    @Override
    public void attachView(MvpView view) {
        myView = (Reference<V>) new WeakReference<>(view);
    }


    @Override
    public V getView() {
        return myView.get();
    }


    @Override
    public void detachView() {
        myView = null;
    }


}

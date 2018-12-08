/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.base.baseMVVM;

import androidx.lifecycle.ViewModel;
import android.content.Intent;
import androidx.databinding.BaseObservable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by lushengbo on 2018/1/12.
 * MVVM ViewModel 基类
 */

public abstract class BaseViewModel<V extends BaseMVVMActivity> extends ViewModel {
    protected Reference<V> myView;


    public void attachView(V view) {
        myView = new WeakReference<>(view);
    }

    public V getView() {
        return myView.get();
    }


    public void detachView() {
        myView = null;
    }

    protected abstract void init(Object data);

    protected abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    protected abstract void clear();


}

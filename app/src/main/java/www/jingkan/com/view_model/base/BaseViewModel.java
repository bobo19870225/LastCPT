/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view_model.base;

import android.app.Application;
import android.content.Intent;

import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.ViewCallback;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * Created by lushengbo on 2018/1/12.
 * MVVM ViewModel 基类
 * 这种写法存在问题，ViewModel不应该持有Activity
 */

public abstract class BaseViewModel extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected Reference<ViewCallback> mViewCallback;

    protected CallbackMessage callbackMessage;

    public void attachView(ViewCallback viewCallback, CallbackMessage callbackMessage) {
        this.callbackMessage = callbackMessage;
        mViewCallback = new WeakReference<>(viewCallback);
    }

    public ViewCallback getView() {
        return mViewCallback.get();
    }

    protected void toast(String msg) {
        getView().toast(msg);
    }

    protected void action(int what, Object object) {
        callbackMessage.setValue(what, object);
        getView().action(callbackMessage);
    }

    protected void goTo(Class mClass, Object data, boolean isTop) {
        getView().goTo(mClass, data, isTop);
    }

    protected void goTo(Class mClass, Object data) {
        getView().goTo(mClass, data);
    }
    @Override
    protected void onCleared() {
        mViewCallback = null;
    }

    public abstract void inject(Object... objects);

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public abstract void clear();


}

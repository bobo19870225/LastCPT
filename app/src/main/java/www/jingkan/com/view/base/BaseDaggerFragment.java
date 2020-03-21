/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Map;

import dagger.android.support.DaggerFragment;

/**
 * Created by bobo on 2017/3/5.
 * 碎片基类
 */

public abstract class BaseDaggerFragment extends DaggerFragment {
    protected WeakReference<View> mRootView;
    protected Object mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = new WeakReference<>(initView(inflater, container));
//        AnnotateUtils.initBindView(this, mRootView);
        return mRootView.get();

    }

    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 跳转
     *
     * @param data   参数
     * @param mClass 类名
     */
    public void goTo(Class mClass, Object data) {
        goTo(mClass, data, false);
//        Intent intent = new Intent();
//        intent.setClass(getContext(), mClass);
//        if (data != null) {
//            Bundle bundle = new Bundle();
//            if (data instanceof String) {
//                bundle.putString("DATA", String.valueOf(data));
//            } else if (data instanceof Integer) {
//                bundle.putInt("DATA", (Integer) data);
//            } else if (data instanceof String[]) {
//                bundle.putStringArray("DATA", (String[]) data);
//            }
//            intent.putExtra("BUNDLE", bundle);
//        }
//        startActivity(intent);
    }

    /**
     * 跳转
     *
     * @param data   参数
     * @param mClass 类名
     * @param isTop  是否关闭其它页面
     */
    public void goTo(Class mClass, Object data, boolean isTop) {
        Intent intent = new Intent();
        intent.setClass(getContext(), mClass);
        if (isTop) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        if (data != null) {
            Bundle bundle = new Bundle();
            if (data instanceof String) {
                bundle.putString("DATA", String.valueOf(data));
            } else if (data instanceof Integer) {
                bundle.putInt("DATA", (Integer) data);
            } else if (data instanceof String[]) {
                bundle.putStringArray("DATA", (String[]) data);
            } else if (data instanceof Parcelable) {
                bundle.putParcelable("DATA", (Parcelable) data);
            } else if (data instanceof Map) {
                bundle.putSerializable("DATA", (Serializable) data);
            }
            intent.putExtra("BUNDLE", bundle);
        }
        startActivity(intent);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        AnnotateUtils.initBindView(this, view);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mData = arguments.get("DATA");
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView();
    }

    protected abstract void setView();

    public void showToast(String msg) {
        if (null != mRootView.get())
            Snackbar.make(mRootView.get(), msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

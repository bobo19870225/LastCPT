/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.jingkan.com.annotation.AnnotateUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by bobo on 2017/3/5.
 * 碎片基类
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View mRootView;
    protected Object mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = checkNotNull(initView(inflater, container));
        AnnotateUtils.initBindView(this, mRootView);
        return mRootView;

    }

    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 跳转
     *
     * @param data   参数
     * @param mClass 类名
     */
    public void goTo(Class mClass, Object data) {
        Intent intent = new Intent();
        intent.setClass(getContext(), mClass);
        if (data != null) {
            Bundle bundle = new Bundle();
            if (data instanceof String) {
                bundle.putString("DATA", String.valueOf(data));
            } else if (data instanceof Integer) {
                bundle.putInt("DATA", (Integer) data);
            } else if (data instanceof String[]) {
                bundle.putStringArray("DATA", (String[]) data);
            }
            intent.putExtra("BUNDLE", bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnnotateUtils.initBindView(this, view);
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
        Snackbar.make(mRootView, msg, Snackbar.LENGTH_LONG).show();
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

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter;

import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import www.jingkan.com.BR;

/**
 * Created by lushengbo on 2018/1/16.
 * 使用DataBinding的适配器基类
 */

public class BaseDataBindingAdapter<VDB extends ViewDataBinding, VM extends BaseObservable> extends BaseAdapter {
    protected List<VM> list;
    private int intRsc;

    public BaseDataBindingAdapter(List<VM> list, int intRsc) {
        this.list = list;
        this.intRsc = intRsc;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        VDB viewDataBinding;
        if (view == null) {
            //创建一个dataBinding
            viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), intRsc, viewGroup, false);
            //获取convertView
            view = viewDataBinding.getRoot();
        } else {
            //去除convertView中的dataBinding
            viewDataBinding = DataBindingUtil.getBinding(view);
        }
        viewDataBinding.setVariable(BR.model, list.get(i));
        return view;
    }

}

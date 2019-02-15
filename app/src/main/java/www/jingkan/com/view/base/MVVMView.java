/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.base;


import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

/**
 * Created by bobo on 2017/3/12.
 * 接口
 */

public interface MVVMView<VM extends ViewModel, VDB extends ViewDataBinding> {

    VM createdViewModel();

    VDB setViewDataBinding(@LayoutRes int layOutId);
}

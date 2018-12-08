/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.openFile;

import androidx.databinding.BaseObservable;
import androidx.databinding.ViewDataBinding;

import java.util.List;

import www.jingkan.com.adapter.BaseDataBindingAdapter;

public class FileExplorerAdapter<VDB extends ViewDataBinding, VM extends BaseObservable>
        extends BaseDataBindingAdapter<VDB, VM> {

    public FileExplorerAdapter(List<VM> list, int intRsc) {
        super(list, intRsc);
    }

    public void update(List<VM> listItemViewModel) {
        list = listItemViewModel;
        notifyDataSetChanged();
    }

}

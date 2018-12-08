/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter.markFileDetailAdapter;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

/**
 * Created by lushengbo on 2018/1/17.
 * 标记文件详情
 */

public class MarkFileDetailItemViewModel extends BaseObservable {
    public final ObservableField<String> strDeep = new ObservableField<>();
    public final ObservableField<String> strSRC = new ObservableField<>();
}

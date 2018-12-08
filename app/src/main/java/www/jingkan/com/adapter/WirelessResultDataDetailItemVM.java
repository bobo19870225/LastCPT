/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验数据详情列表项
 */

public class WirelessResultDataDetailItemVM extends BaseObservable {
    public final ObservableField<String> strDeep = new ObservableField<>();
    public final ObservableField<String> strQc = new ObservableField<>();
    public final ObservableField<String> strFs = new ObservableField<>();
    public final ObservableField<String> strFa = new ObservableField<>();

}

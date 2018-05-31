/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验数据列表项
 */

public class WirelessTestDataItemVM extends BaseObservable {
    public final ObservableField<String> strProjectNumber = new ObservableField<>();
    public final ObservableField<String> strHoleNumber = new ObservableField<>();
    public final ObservableField<String> strTestDate = new ObservableField<>();
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter.markFileAdapter;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

/**
 * Created by lushengbo on 2018/1/17.
 * 标记文件
 */

public class MarkFileItemViewMode extends BaseObservable {
    public final ObservableField<String> testID = new ObservableField<>();
    public final ObservableField<String> testData = new ObservableField<>();
}

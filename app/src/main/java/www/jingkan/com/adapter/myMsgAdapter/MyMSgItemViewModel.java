/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter.myMsgAdapter;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * Created by lushengbo on 2018/1/23.
 * 我的信息列表
 */

public class MyMSgItemViewModel extends BaseObservable {
    public final ObservableField<String>
            msgTime = new ObservableField<>();

}

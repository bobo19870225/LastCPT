/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.mInterface;

import www.jingkan.com.base.baseMVP.BasePresenter;

/**
 * Created by bobo on 2017/3/12.
 * 接口
 */

public interface MvpView<P extends BasePresenter> {
    P createdPresenter();
}

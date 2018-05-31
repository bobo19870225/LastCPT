/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.mInterface;

import android.content.Context;
import android.content.Intent;

/**
 * Created by bobo on 2017/4/3.
 * 中介根接口
 */

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    V getView();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void detachView();

    void clear();

    void init(Context context, Object data);
}

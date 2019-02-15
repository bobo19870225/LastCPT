package www.jingkan.com.view.base;

import www.jingkan.com.util.CallbackMessage;

/**
 * Created by Sampson on 2018/12/28.
 * CPTTest
 */
public interface ViewCallback {
    void action(CallbackMessage callbackMessage);

    void goTo(Class mClass, Object data);

    void goTo(Class mClass, Object data, boolean isTop);

    void toast(String msg);
}

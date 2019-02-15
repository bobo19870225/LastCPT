package www.jingkan.com.view.base;

import com.jinkan.www.cpttest.util.CallbackMessage;

/**
 * Created by Sampson on 2018/12/28.
 * CPTTest
 */
public interface ViewCallback {
    void callback(CallbackMessage callbackMessage);

    void skipTo(Class c, Object object, Boolean isTop);

    void toast(CallbackMessage callbackMessage);
}

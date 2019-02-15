package www.jingkan.com.util;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sampson on 2018/12/19.
 * CPTTest
 */
@Singleton
public class CallbackMessage {
    @Inject
    public CallbackMessage() {
    }

    public int what;
    public int arg1;
    public int arg2;
    public Object obj;

    private void clean() {
        what = -1;
        arg1 = -1;
        arg2 = -1;
        obj = null;
    }

    public void setValue(int what, int arg1, int arg2, Object obj) {
        clean();
        this.what = what;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.obj = obj;
    }

    public void setValue(int what, int arg1, int arg2) {
        clean();
        this.what = what;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public void setValue(int what, int arg1) {
        clean();
        this.what = what;
        this.arg1 = arg1;
    }

    public void setValue(int what) {
        clean();
        this.what = what;
    }

    public void setValue(int what, Object obj) {
        clean();
        this.what = what;
        this.obj = obj;
    }
}

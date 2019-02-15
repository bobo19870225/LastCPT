package www.jingkan.com.view_model;

import android.content.Intent;

/**
 * Created by Sampson on 2018/3/20.
 * LastCPT
 */

public interface ISkip {
    void skipForResult(Intent intent, int requestCode);

    void skip(Intent intent);

    void sendToastMsg(String msg);
}
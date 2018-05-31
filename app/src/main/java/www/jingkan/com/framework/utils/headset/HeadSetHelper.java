/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.utils.headset;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;

/**
 * 耳机线控管理助手类
 * 单例
 */
public class HeadSetHelper {

    private static HeadSetHelper headSetHelper;

    private OnHeadSetListener headSetListener = null;

    public static HeadSetHelper getInstance() {
        if (headSetHelper == null) {
            headSetHelper = new HeadSetHelper();
        }
        return headSetHelper;
    }

    /**
     * 设置耳机单击双击监听接口
     * 必须在open前设置此接口，否则设置无效
     */
    public void setOnHeadSetListener(OnHeadSetListener headSetListener) {
        this.headSetListener = headSetListener;
    }

    /**
     * 开启耳机线控监听,
     * 请务必在设置接口监听之后再调用此方法，否则接口无效
     */
    public void open(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        ComponentName name = new ComponentName(context.getPackageName(),
                HeadSetReceiver.class.getName());
        if (audioManager != null) {
            audioManager.registerMediaButtonEventReceiver(name);
        }
    }

    /**
     * 关闭耳机线控监听
     */
    public void close(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        ComponentName name = new ComponentName(context.getPackageName(),
                HeadSetReceiver.class.getName());
        if (audioManager != null) {
            audioManager.unregisterMediaButtonEventReceiver(name);
        }
    }

    /**
     * 删除耳机单机双击监听接口
     */
    public void delHeadSetListener() {
        this.headSetListener = null;
    }

    /**
     * 获取耳机单击双击接口
     */
    protected OnHeadSetListener getOnHeadSetListener() {
        return headSetListener;
    }

    /**
     * 耳机按钮单双击监听
     */
    public interface OnHeadSetListener {
        /**
         * 单击触发,主线程。
         * 此接口真正触发是在单击操作1秒后
         * 因为需要判断1秒内是否仍监听到点击，有的话那就是双击了。
         */
        void onClick();

        //双击触发，此接口在主线程，可以放心使用

        //public void onDoubleClick();
    }
}

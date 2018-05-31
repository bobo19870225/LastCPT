/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.utils;

import android.os.Handler;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lushengbo on 2017/6/6.
 * 时间工具类
 */

public class TimeUtils {
    private Timer timer;
    private Handler mHandler;

    public TimeUtils(Handler timedTaskHandler) {
        mHandler = timedTaskHandler;
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    public static String getCurrentTimeYYYY_MM_DD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }


    public void timedTask(long delay, long period) {
        if (timer == null) {
            timer = new Timer(true);
        }
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, delay, period);

    }

    public void stopTimedTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


}

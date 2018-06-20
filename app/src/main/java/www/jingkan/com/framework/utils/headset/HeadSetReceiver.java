/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.utils.headset;

//import java.util.Timer;
//import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

//import android.os.Handler;
//import android.os.Message;

public class HeadSetReceiver extends BroadcastReceiver {

    //Timer timer = null;
    HeadSetHelper.OnHeadSetListener headSetListener;

    //private static boolean isTimerStart = false;
    //private static MyTimer myTimer = null;
    //重写构造方法，将接口绑定。因为此类的初始化的特殊性。
    public HeadSetReceiver() {
        //timer = new Timer(true);
        this.headSetListener = HeadSetHelper.getInstance().getOnHeadSetListener();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            //获得KeyEvent对象
            KeyEvent keyEvent = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (headSetListener != null) {
                try {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
	        				/*if(isTimerStart){
	        					myTimer.cancel();
	        					isTimerStart = false;
	        					headSetListener.onDoubleClick();
	        				}else{
	        					myTimer = new MyTimer();
	        					//timer.schedule(myTimer,1000);
	        					//isTimerStart = true;
	        				}*/
                        headSetListener.onClick();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
        //终止广播(不让别的程序收到此广播，免受干扰)
        abortBroadcast();
    }
    /*
     * 定时器，用于延迟1秒，内若无操作则为单击
     */
	/*class MyTimer extends TimerTask{

			@Override
			public void run() {
				try {
					myHandle.sendEmptyMessage(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
	};*/
    /*
     * 此handle的目的主要是为了将接口在主线程中触发
     * ，为了安全起见把接口放到主线程触发
     */
	/*Handler myHandle = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			headSetListener.onClick();
			isTimerStart = false;
		}

	};*/

}


/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.probeTest;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.bluetooth.BluetoothCommService;
import www.jingkan.com.framework.utils.BluetoothUtils;

/**
 * Created by lushengbo on 2018/1/29.
 * LastCPT
 */

public class TestingViewModel extends BaseViewModel<TestingActivity> {
    public final ObservableField<String> strData = new ObservableField<>();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (myView != null) {
                switch (msg.what) {

                    case BluetoothCommService.MESSAGE_READ://读数据
                        byte[] b = (byte[]) msg.obj;
                        String mDate = new String(b);
                        if (mDate.length() > 40) {
                            if (mDate.contains("\r")) {
                                mDate = mDate.substring(0, mDate.indexOf("\r"));
                            }
                            strData.set(mDate);
                        }
                        break;
                    case BluetoothCommService.MESSAGE_TOAST://提示信息
                        Bundle bundle = msg.getData();
                        String s = bundle.getString(BluetoothCommService.TOAST);
                        myView.get().showToast(s);
                        break;
                    case BluetoothCommService.MESSAGE_STATE_CHANGE:
                        if (msg.arg1 == BluetoothCommService.STATE_CONNECTED) {
                            getView().closeWaitDialog();
                            getView().showToast("连接成功");
                        } else if (msg.arg1 == BluetoothCommService.STATE_CONNECT_FAILED) {
                            getView().closeWaitDialog();
                        }
                        break;
                }
            }
        }
    };
    private BluetoothCommService bluetoothCommService = new BluetoothCommService(mHandler);
    private String mac;

    @Override
    protected void init(Object data) {
        mac = (String) data;
        link();
        bluetoothCommService.mContent.observe(getView(), (String content) -> {
            String content1 = content;
        });
    }

    public void link() {
        BluetoothDevice bluetoothDevice = BluetoothUtils.getInstance().
                getBluetoothAdapter().getRemoteDevice(mac);
        bluetoothCommService.connect(bluetoothDevice);
        getView().showWaitDialog("正在连接蓝牙设备...", false, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        bluetoothCommService.stop();
        bluetoothCommService = null;
    }


}

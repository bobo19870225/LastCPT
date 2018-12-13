/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.timeSynchronization;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.ObservableField;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.List;

import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.bluetooth.BluetoothCommService;
import www.jingkan.com.framework.utils.BluetoothUtils;
import www.jingkan.com.framework.utils.TimeUtils;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeDao;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeModel;
import www.jingkan.com.localData.wirelessTest.WirelessTestDao;
import www.jingkan.com.localData.wirelessTest.WirelessTestEntity;

/**
 * Created by lushengbo on 2018/1/12.
 * 时间同步VM
 */

public class TimeSynchronizationViewModel extends BaseViewModel<TimeSynchronizationActivity> {
    public final ObservableField<String> strProjectNumber = new ObservableField<>();
    public final ObservableField<String> strHoleNumber = new ObservableField<>();
    public final ObservableField<String> strQcCoefficient = new ObservableField<>();
    public final ObservableField<String> strQcLimit = new ObservableField<>();
    public final ObservableField<String> strFsCoefficient = new ObservableField<>();
    public final ObservableField<String> strFsLimit = new ObservableField<>();
    public final ObservableField<String> markingTime = new ObservableField<>();
    public final ObservableField<String> obsProbeNumber = new ObservableField<>();
    public final ObservableField<Boolean> linked = new ObservableField<>();
    public final ObservableField<Boolean> isDoubleBridge = new ObservableField<>();

    private BluetoothCommService bluetoothCommService;
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
                            mDate = mDate.replace(" ", "");
                            if (mDate.contains("Sn:")) {
                                String sn = mDate.substring(mDate.indexOf("Sn:") + 3, mDate.indexOf("Sn:") + 11);
                                identificationProbe(sn);
                            }
                            if (mDate.contains("Bt:")) {
                                String bt = mDate.substring(mDate.indexOf("Bt:") + 3, mDate.indexOf("Sn:"));
                                markingTime.set(bt);
                            }
                        }
                        break;
                    case BluetoothCommService.MESSAGE_TOAST://提示信息
                        Bundle bundle = msg.getData();
                        String s = bundle.getString(BluetoothCommService.TOAST);
                        getView().showToast(s);
                        break;
                    case BluetoothCommService.MESSAGE_STATE_CHANGE:
                        if (msg.arg1 == BluetoothCommService.STATE_CONNECTED) {
                            getView().showToast("连接成功");
                            linked.set(true);
                        } else if (msg.arg1 == BluetoothCommService.STATE_CONNECTING) {
                            myView.get().showToast("正在连接");
                            linked.set(false);
                        } else {
                            linked.set(false);
                        }
                }
            }
        }
    };
    private boolean isIdentification;

    private void getTestParameters(String projectNumber, String holeNumber) {
        WirelessTestDao wirelessTestDao = DataFactory.getBaseData(WirelessTestDao.class);
        wirelessTestDao.getData(new DataLoadCallBack<WirelessTestEntity>() {

            @Override
            public void onDataLoaded(List<WirelessTestEntity> models) {
                WirelessTestEntity wirelessTestEntity = models.get(0);
                strProjectNumber.set(wirelessTestEntity.projectNumber);
                strHoleNumber.set(wirelessTestEntity.holeNumber);
            }

            @Override
            public void onDataNotAvailable() {
                myView.get().showToast("找不到该孔信息");
            }
        }, projectNumber, holeNumber);

    }

    private void identificationProbe(String sn) {
        if (!isIdentification) {
            isIdentification = true;
            WirelessProbeDao wirelessProbeDao = DataFactory.getBaseData(WirelessProbeDao.class);
            wirelessProbeDao.getData(new DataLoadCallBack<WirelessProbeModel>() {

                @Override
                public void onDataLoaded(List<WirelessProbeModel> models) {
                    WirelessProbeModel wirelessProbeModel = models.get(0);
                    strQcCoefficient.set(String.valueOf(wirelessProbeModel.qc_coefficient));
                    strQcLimit.set(String.valueOf(wirelessProbeModel.qc_limit));
                    strFsCoefficient.set(String.valueOf(wirelessProbeModel.fs_coefficient));
                    strFsLimit.set(String.valueOf(wirelessProbeModel.fs_limit));
                    obsProbeNumber.set(wirelessProbeModel.number);
                }

                @Override
                public void onDataNotAvailable() {
                    myView.get().showToast("该探头未添加到探头列表中，暂时不能使用，请在探头列表里添加该探头");
                }
            }, sn);
        }

    }

    public void doSynchronization() {
        getView().showMyDialog("注意！",
                "请确定探头处于工作状态，切换开关置于B！",
                false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        char a = 13;
                        char b = 10;
                        String str = "@";
                        str = str + TimeUtils.getCurrentTimeYYYY_MM_DD() + a + b;
                        // 没有连接设备，不能发送
                        if (bluetoothCommService.getState() != BluetoothCommService.STATE_CONNECTED) {
                            myView.get().showToast("未连接设备");
                            return;
                        }
                        if (str.length() > 0) {// Check that there's actually something to send
                            // Get the message bytes and tell the BluetoothChatService to write
                            byte[] send = str.getBytes();
                            bluetoothCommService.write(send);
                            //Reset out string buffer to zero and clear the edit text field
                            //mOutStringBuffer.setLength(0);
                        }
                    }
                });

    }

    @Override
    protected void init(Object data) {
        bluetoothCommService = new BluetoothCommService(mHandler);
        String[] strings = (String[]) data;
        getTestParameters(strings[1], strings[2]);//载入试验参数
        if (strings[3].contains("双桥")) {
            isDoubleBridge.set(true);
        } else {
            isDoubleBridge.set(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {
        bluetoothCommService.stop();
    }

    public void linkDevice(String strMac) {
        BluetoothAdapter bluetoothAdapter = BluetoothUtils.getInstance().
                getBluetoothAdapter();
        if (bluetoothAdapter.isEnabled()) {// 蓝牙已打开
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(strMac);
            bluetoothCommService.connect(bluetoothDevice);
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            myView.get().startActivityForResult(intent, 0);
        }
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.linkBluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import www.jingkan.com.localData.BluetoothDeviceModel;
import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.framework.acp.Acp;
import www.jingkan.com.framework.acp.AcpListener;
import www.jingkan.com.framework.acp.AcpOptions;
import www.jingkan.com.framework.utils.BluetoothUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by bobo on 2017/3/26.
 * 中介
 */

public class LinkBluetoothPresenter extends BasePresenter<LinkBluetoothActivity> {
    private ArrayList<BluetoothDeviceModel> newDeviceList = new ArrayList<>();
    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 查找新设备
            if (action != null) {
                switch (action) {
                    case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                        myView.get().onSearchDevice();
                        myView.get().showToast("正在搜索");
                        break;
                    case BluetoothDevice.ACTION_FOUND:
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                            String newName = device.getName();
                            String address = device.getAddress();
                            BluetoothDeviceModel bluetoothDeviceModel = new BluetoothDeviceModel();
                            bluetoothDeviceModel.name = newName;
                            bluetoothDeviceModel.address = address;
                            newDeviceList.add(bluetoothDeviceModel);
                            myView.get().onSearchedDevice(newDeviceList);
                        }
                        break;
                    case BluetoothAdapter.ACTION_DISCOVERY_FINISHED: // 搜索结束
                        myView.get().showToast("搜索结束");
                        break;
                }
            }
        }
    };

    private BluetoothAdapter device;

    private void initBluetooth() {
        device = getDevice();
        // 注册广播
        if (device != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
            intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            myView.get().registerReceiver(bReceiver, intentFilter);
        }

    }

    private BluetoothAdapter getDevice() {
        if (BluetoothUtils.getInstance().isBluetoothAvailable()) {
            myView.get().showToast("蓝牙设备可用");
            return BluetoothUtils.getInstance().getBluetoothAdapter();
        } else {
            myView.get().showToast("无可用蓝牙设备");
            myView.get().finish();
            return null;
        }
    }


    /**
     * 清除资源
     */
    @Override
    public void clear() {
        BluetoothUtils.getInstance().cancelDiscovery();
        device = null;
        myView.get().unregisterReceiver(bReceiver);
    }

    @Override
    public void init(Context context, Object data) {
        initBluetooth();
    }



    /**
     * 活动回调
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        结果数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {// 判断activity请求
            case 0:// 询问用户是否打开蓝牙
                if (resultCode == Activity.RESULT_OK) {// 用户点击允许
                    getDeviceList();
                } else {
                    myView.get().showToast("您已拒绝打开蓝牙！");
                }
                break;
            default:
                break;
        }
    }

    void openBluetooth() {
        if (device.isEnabled()) {// 蓝牙已打开
            getDeviceList();
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            myView.get().startActivityForResult(intent, 0);
        }
    }

    private void getDeviceList() {
        Set<BluetoothDevice> devices = device.getBondedDevices();// 取出已配对的蓝牙设备
        ArrayList<BluetoothDeviceModel> list = new ArrayList<>();
        for (BluetoothDevice bluetoothDevices : devices) {
            BluetoothDeviceModel bluetoothDeviceModel = new BluetoothDeviceModel();
            bluetoothDeviceModel.name = bluetoothDevices.getName();
            bluetoothDeviceModel.address = bluetoothDevices.getAddress();
            list.add(bluetoothDeviceModel);
        }
        myView.get().onGetDeviceList(list);
    }

    void searchDevice(Context context) {
        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        BluetoothUtils.getInstance().doDiscovery();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        myView.get().showToast(permissions.toString() + "权限拒绝");
                    }
                });

    }
}

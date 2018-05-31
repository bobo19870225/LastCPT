/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.utils;

import android.bluetooth.BluetoothAdapter;

import java.io.Serializable;

/**
 * Created by bobo on 2017/3/19.
 * 蓝牙工具类
 */


public class BluetoothUtils implements Serializable {
    private BluetoothAdapter bluetoothAdapter;


    private static class SingletonHolder {
        //     单例对象实例  
        private static BluetoothUtils INSTANCE = new BluetoothUtils();
    }

    public static BluetoothUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     *  readResolve方法应对单例对象被序列化时候  
     */
    private Object readResolve() {
        return getInstance();
    }

    /**
     *  private的构造函数用于避免外界直接使用new来实例化对象  
     */
    private BluetoothUtils() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// 获取蓝牙适配器
    }


    public boolean isBluetoothAvailable() {
        return bluetoothAdapter != null;

    }

    public void cancelDiscovery() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public void doDiscovery() {
        cancelDiscovery();
        bluetoothAdapter.startDiscovery();
    }
}






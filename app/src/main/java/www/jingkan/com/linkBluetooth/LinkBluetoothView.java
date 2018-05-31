/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.linkBluetooth;


import www.jingkan.com.localData.BluetoothDeviceModel;
import www.jingkan.com.mInterface.MvpView;

import java.util.List;

/**
 * Created by bobo on 2017/3/26.
 * 蓝牙接口
 */

public interface LinkBluetoothView extends MvpView {
    void onSearchDevice();
    void onSearchedDevice(List<BluetoothDeviceModel> deviceModelList);
    void onGetDeviceList(List<BluetoothDeviceModel> deviceModelList);
}


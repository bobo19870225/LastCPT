/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.linkBluetooth;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.DeviceAdapter;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.baseMVP.BaseMvpActivity;
import www.jingkan.com.calibration.calibrationVerification.CalibrationVerificationActivity;
import www.jingkan.com.calibration.digital.setCalibrationData.SetCalibrationDataActivity;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.localData.BluetoothDeviceModel;
import www.jingkan.com.parameter.SystemConstant;
import www.jingkan.com.calibration.probeTest.TestingActivity;
import www.jingkan.com.common.DoubleBridgeMultifunctionTestActivity;
import www.jingkan.com.common.DoubleBridgeTestActivity;
import www.jingkan.com.common.SingleBridgeTestActivity;
import www.jingkan.com.common.SingleBridgeMultifunctionTestActivity;
import www.jingkan.com.common.crossTest.CrossTestActivity;
import www.jingkan.com.wireless.timeSynchronization.TimeSynchronizationActivity;

/**
 * Created by bobo on 2017/3/26.
 * 链接蓝牙
 */

public class LinkBluetoothActivity
        extends BaseMvpActivity<LinkBluetoothPresenter> implements LinkBluetoothView {
    @BindView(id = R.id.bt_search, click = true)
    private FloatingActionButton bt_search;
    @BindView(id = R.id.lv_new_devices)
    private ListView lv_new_devices;
    @BindView(id = R.id.lv_devices)
    private ListView lv_devices;
    private DeviceAdapter deviceAdapter;
    private ArrayList<BluetoothDeviceModel> newDeviceList;
    private ArrayList<BluetoothDeviceModel> bondedDeviceList;
    private DeviceAdapter bondedDeviceAdapter;

    @Override
    public LinkBluetoothPresenter createdPresenter() {
        return new LinkBluetoothPresenter();
    }

    @Override
    protected void setView() {
        initNewDeviceList();
        initBondedDeviceList();
    }

    private void initBondedDeviceList() {
        bondedDeviceList = new ArrayList<>();
        bondedDeviceAdapter = new DeviceAdapter(this, R.layout.device_item, bondedDeviceList);
        lv_devices.setAdapter(bondedDeviceAdapter);
        lv_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_item = view.findViewById(R.id.tv_new_address);
                String mac = tv_item.getText().toString();
                linkAndSaveBluetooth(mac);
            }
        });
    }

    private void linkAndSaveBluetooth(String mac) {
        PreferencesUtils preferencesUtils = new PreferencesUtils(LinkBluetoothActivity.this);
        preferencesUtils.saveLinker(mac);
        if (mData instanceof String[]) {
            String[] strings = (String[]) mData;
            switch (strings[2]) {
                case "设置":
                    //传递：1.蓝牙地址 2.探头序列号 3.标定类型
                    goTo(SetCalibrationDataActivity.class, new String[]{mac, strings[0], strings[1]});
                    break;
                case "验证":
                    //传递：1.蓝牙地址 2.探头序列号 3.标定类型
                    goTo(CalibrationVerificationActivity.class, new String[]{mac, strings[0], strings[1]});
                    break;
                case SystemConstant.SINGLE_BRIDGE_TEST:
                    //mac地址，工程编号，孔号。
                    goTo(SingleBridgeTestActivity.class, new String[]{mac, strings[0], strings[1]});
                    break;
                case SystemConstant.SINGLE_BRIDGE_MULTI_TEST:
                    goTo(SingleBridgeMultifunctionTestActivity.class, new String[]{mac, strings[0], strings[1]});
                    break;
                case SystemConstant.DOUBLE_BRIDGE_TEST:
                    goTo(DoubleBridgeTestActivity.class, new String[]{mac, strings[0], strings[1]});
                    break;
                case SystemConstant.DOUBLE_BRIDGE_MULTI_TEST:
                    goTo(DoubleBridgeMultifunctionTestActivity.class, new String[]{mac, strings[0], strings[1]});
                    break;
                case SystemConstant.VANE_TEST:
                    goTo(CrossTestActivity.class, new String[]{mac, strings[0], strings[1]});
                    break;
                case SystemConstant.SINGLE_BRIDGE_MULTI_WIRELESS_TEST:
                case SystemConstant.DOUBLE_BRIDGE_MULTI_WIRELESS_TEST:
                    goTo(TimeSynchronizationActivity.class, new String[]{mac, strings[0], strings[1], strings[2]});
                    break;
            }
        } else if (mData.equals("探头检测")) {
            goTo(TestingActivity.class, mac);
        }
        finish();
    }

    private void initNewDeviceList() {
        newDeviceList = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(this, R.layout.device_item, newDeviceList);
        lv_new_devices.setAdapter(deviceAdapter);
        lv_new_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_item = view.findViewById(R.id.tv_new_address);
                String mac = tv_item.getText().toString();
                linkAndSaveBluetooth(mac);
            }
        });
    }

    @Override
    public int initView() {
        return R.layout.activity_link_bluetooth;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.openBluetooth();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                mPresenter.searchDevice(this);
                break;
        }
    }


    @Override
    public void onSearchDevice() {
        deviceAdapter.clear();
    }

    @Override
    public void onSearchedDevice(List<BluetoothDeviceModel> list) {
        newDeviceList.addAll(list);
        deviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetDeviceList(List<BluetoothDeviceModel> deviceModelList) {
        bondedDeviceList.clear();
        bondedDeviceList.addAll(deviceModelList);
        bondedDeviceAdapter.notifyDataSetChanged();// 刷新
    }


}

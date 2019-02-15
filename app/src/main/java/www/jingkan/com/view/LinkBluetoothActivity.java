package www.jingkan.com.view;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityLinkBluetoothBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.util.PreferencesUtil;
import com.jinkan.www.cpttest.util.SystemConstant;
import com.jinkan.www.cpttest.util.bluetooth.BluetoothUtil;
import com.jinkan.www.cpttest.view.adapter.DeviceAdapter;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.LinkBluetoothViewModel;
import com.jinkan.www.cpttest.view_model.MyBluetoothDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class LinkBluetoothActivity extends BaseMVVMDaggerActivity<LinkBluetoothViewModel, ActivityLinkBluetoothBinding> {
    @Inject
    BluetoothUtil bluetoothUtil;
    @Inject
    PreferencesUtil preferencesUtil;
    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String sAction = intent.getAction();
            // 查找新设备
            if (sAction != null) {
                switch (sAction) {
                    case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                        deviceAdapter.clear();
                        showToast("正在搜索");
                        break;
                    case BluetoothDevice.ACTION_FOUND:
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                            String newName = device.getName();
                            String address = device.getAddress();
                            MyBluetoothDevice myBluetoothDevice = new MyBluetoothDevice();
                            myBluetoothDevice.name = newName;
                            myBluetoothDevice.address = address;
                            newDeviceList.add(myBluetoothDevice);
                        }
                        break;
                    case BluetoothAdapter.ACTION_DISCOVERY_FINISHED: // 搜索结束
                        deviceAdapter.notifyDataSetChanged();
                        showToast("搜索结束");
                        break;
                }
            }
        }
    };
    private BluetoothAdapter device;
    private DeviceAdapter deviceAdapter;
    private ArrayList<MyBluetoothDevice> newDeviceList;
    private ArrayList<MyBluetoothDevice> bondedDeviceList;
    private DeviceAdapter bondedDeviceAdapter;


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, bluetoothUtil};
    }

    @Override
    protected void setMVVMView() {
        initBluetooth();
        initNewDeviceList();
        initBondedDeviceList();


    }

    private void initBluetooth() {
        if (bluetoothUtil.isBluetoothAvailable()) {
            showToast("蓝牙设备可用");
            device = bluetoothUtil.getBluetoothAdapter();
            if (device != null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
                intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                // 注册广播
                registerReceiver(bReceiver, intentFilter);
            }
        } else {
            showToast("无可用蓝牙设备");
            finish();
        }


    }

    @Override
    protected void toRefresh() {
        if (device.isEnabled()) {// 蓝牙已打开
            Set<BluetoothDevice> devices = device.getBondedDevices();// 取出已配对的蓝牙设备
            ArrayList<MyBluetoothDevice> list = new ArrayList<>();
            for (BluetoothDevice bluetoothDevices : devices) {
                MyBluetoothDevice bluetoothDeviceModel = new MyBluetoothDevice();
                bluetoothDeviceModel.name = bluetoothDevices.getName();
                bluetoothDeviceModel.address = bluetoothDevices.getAddress();
                list.add(bluetoothDeviceModel);
            }
            bondedDeviceList.clear();
            bondedDeviceList.addAll(list);
            bondedDeviceAdapter.notifyDataSetChanged();// 刷新
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 0);
        }
    }

    private void initBondedDeviceList() {
        bondedDeviceList = new ArrayList<>();
        bondedDeviceAdapter = new DeviceAdapter(this, R.layout.item_device, bondedDeviceList);
        mViewDataBinding.lvDevices.setAdapter(bondedDeviceAdapter);
        mViewDataBinding.lvDevices.setOnItemClickListener((parent, view, position, id) -> {
            TextView tv_item = view.findViewById(R.id.tv_new_address);
            String mac = tv_item.getText().toString();
            linkAndSaveBluetooth(mac);
        });
    }

    private void initNewDeviceList() {
        newDeviceList = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(this, R.layout.item_device, newDeviceList);
        mViewDataBinding.lvNewDevices.setAdapter(deviceAdapter);
        mViewDataBinding.lvNewDevices.setOnItemClickListener((parent, view, position, id) -> {
            TextView tv_item = view.findViewById(R.id.tv_new_address);
            String mac = tv_item.getText().toString();
            linkAndSaveBluetooth(mac);
        });
    }

    private void linkAndSaveBluetooth(String mac) {
        if (mData instanceof HashMap) {
            HashMap hashMap = (HashMap) this.mData;
            String action = (String) hashMap.get("action");
            if (action != null) {
                switch (action) {
                    case "选择数字连接器":
                        preferencesUtil.saveLinker(mac);
                        break;
                    case "选择模拟连接器":
                        preferencesUtil.saveAnalogLinker(mac);
                        break;
                    case "设置探头内部数据":
                        preferencesUtil.saveLinker(mac);
                        //传递：1.蓝牙地址 2.探头序列号 3.标定类型
                        goTo(SetCalibrationDataActivity.class, new String[]{mac, (String) hashMap.get("Sn"), (String) hashMap.get("type")});
                        break;
                    case "数字探头标定":
                        preferencesUtil.saveLinker(mac);
                        //传递：1.蓝牙地址 2.探头序列号 3.标定类型
                        goTo(CalibrationVerificationActivity.class, new String[]{mac, (String) hashMap.get("Sn"), (String) hashMap.get("type")});
                        break;
                    case "模拟探头标定":
                        preferencesUtil.saveAnalogLinker(mac);
                        //传递：1.蓝牙地址 2.探头序列号 3.标定类型
                        goTo(CalibrationVerificationActivity.class, new String[]{mac, (String) hashMap.get("Sn"), (String) hashMap.get("type")});
                        break;
                    case "数字探头检测":
                        preferencesUtil.saveLinker(mac);
                        goTo(TestingActivity.class, mac);
                        break;
                }
            }
        }

        if (mData instanceof String[]) {
            String[] strings = (String[]) mData;
            String[] dataToSend = {mac, strings[0], strings[1], strings[2]};
            preferencesUtil.saveLinker(mac);
            switch (strings[2]) {
                case SystemConstant.SINGLE_BRIDGE_TEST:
                    //mac地址，工程编号，孔号。
                    goTo(SingleBridgeTestActivity.class, dataToSend);
                    break;
                case SystemConstant.SINGLE_BRIDGE_MULTI_TEST:
                    goTo(SingleBridgeMultifunctionTestActivity.class, dataToSend);
                    break;
                case SystemConstant.DOUBLE_BRIDGE_TEST:
                    goTo(DoubleBridgeTestActivity.class, dataToSend);
                    break;
                case SystemConstant.DOUBLE_BRIDGE_MULTI_TEST:
                    goTo(DoubleBridgeMultifunctionTestActivity.class, dataToSend);
                    break;
                case SystemConstant.VANE_TEST:
                    goTo(CrossTestActivity.class, dataToSend);
                    break;
                case SystemConstant.SINGLE_BRIDGE_MULTI_WIRELESS_TEST:
                case SystemConstant.DOUBLE_BRIDGE_MULTI_WIRELESS_TEST:
                    goTo(TimeSynchronizationActivity.class, new String[]{mac, strings[0], strings[1], strings[2]});
                    break;
            }
        }
        finish();
    }

    @Override
    public LinkBluetoothViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(LinkBluetoothViewModel.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_link_bluetooth;
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

    }
}

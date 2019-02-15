package www.jingkan.com.view;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.MenuItem;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityTimeSynchronizationBinding;
import www.jingkan.com.db.dao.WirelessProbeDao;
import www.jingkan.com.db.entity.WirelessProbeEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.TimeUtil;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view.base.DialogMVVMDaggerActivity;
import www.jingkan.com.view_model.TimeSynchronizationVM;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import static www.jingkan.com.util.bluetooth.BluetoothCommService.MESSAGE_READ;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.MESSAGE_STATE_CHANGE;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECTED;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECTING;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECT_FAILED;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECT_LOST;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_LISTEN;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_NONE;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class TimeSynchronizationActivity extends DialogMVVMDaggerActivity<TimeSynchronizationVM, ActivityTimeSynchronizationBinding> {


    @Inject
    BluetoothUtil bluetoothUtil;
    @Inject
    BluetoothCommService bluetoothCommService;
    @Inject
    WirelessProbeDao wirelessProbeDao;

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, bluetoothUtil, bluetoothCommService, wirelessProbeDao};
    }


    @Override
    protected void setMVVMView() {
        setToolBar("时间同步", R.menu.time_synchronization);
        MutableLiveData<CallbackMessage> bluetoothMessageMutableLiveData = bluetoothCommService.getBluetoothMessageMutableLiveData();
        bluetoothMessageMutableLiveData.observe(this, callbackMessage -> {
            switch (callbackMessage.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (callbackMessage.arg1) {
                        case STATE_NONE:
                            break;
                        case STATE_LISTEN:// 监听连接
                            break;
                        case STATE_CONNECTING: // now initiating an outgoing connection
                            showToast("正在连接");
                            break;
                        case STATE_CONNECTED:   // 已连接上远程设备
                            closeWaitDialog();
                            showToast("连接成功");
                            break;
                        case STATE_CONNECT_FAILED: // 连接失败
                            closeWaitDialog();
                            showToast("连接失败");
                            break;
                        case STATE_CONNECT_LOST: // 失去连接
                            showToast("失去连接");
                            break;
                    }
                    break;
                case MESSAGE_READ:
                    byte[] b = (byte[]) callbackMessage.obj;
                    String mDate = new String(b);
                    if (mDate.length() > 40) {
                        if (mDate.contains("\r")) {
                            mDate = mDate.substring(0, mDate.indexOf("\r"));
                        }
                        mDate = mDate.replace(" ", "");
                        if (mDate.contains("Sn:")) {
                            String sn = mDate.substring(mDate.indexOf("Sn:") + 3, mDate.indexOf("Sn:") + 11);
                            mViewModel.identificationProbe(sn);
                        }
                        if (mDate.contains("Bt:")) {
                            String bt = mDate.substring(mDate.indexOf("Bt:") + 3, mDate.indexOf("Sn:"));
                            mViewModel.markingTime.setValue(bt);
                        }
                    }
                    break;
            }

        });
        mViewModel.liveDataWirelessProbeEntity.observe(this, wirelessProbeEntities -> {
            if (wirelessProbeEntities != null && !wirelessProbeEntities.isEmpty()) {
                WirelessProbeEntity wirelessProbeModel = wirelessProbeEntities.get(0);
                mViewModel.strQcCoefficient.setValue(String.valueOf(wirelessProbeModel.qc_coefficient));
                mViewModel.strQcLimit.setValue(String.valueOf(wirelessProbeModel.qc_limit));
                mViewModel.strFsCoefficient.setValue(String.valueOf(wirelessProbeModel.fs_coefficient));
                mViewModel.strFsLimit.setValue(String.valueOf(wirelessProbeModel.fs_limit));
                mViewModel.obsProbeNumber.setValue(wirelessProbeModel.number);
            } else {
                showToast("该探头未添加到探头列表中，暂时不能使用，请在探头列表里添加该探头");
            }
        });
        mViewModel.liveDataWirelessTestEntity.observe(this, wirelessTestEntities -> {
            if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
                WirelessTestEntity wirelessTestEntity = wirelessTestEntities.get(0);
                mViewModel.strProjectNumber.setValue(wirelessTestEntity.projectNumber);
                mViewModel.strHoleNumber.setValue(wirelessTestEntity.holeNumber);
            } else {
                showToast("找不到该孔信息");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                mViewModel.linkDevice();
                return false;
            case R.id.skip://跳过
                if (mViewDataBinding.probeNumber.getText().toString().contains("JK")) {
                    String[] strings = (String[]) mData;
                    goTo(WirelessTestActivity.class, new String[]{
                            strings[0],//蓝牙地址
                            strings[1],//工程编号
                            strings[2],//孔号
                            strings[3],//试验类型
                            mViewModel.obsProbeNumber.getValue()//探头编号
                    });
                } else {
                    showToast("请填写探头编号");
                }

                return false;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void toRefresh() {
        mViewModel.linkDevice();
    }
    @Override
    public TimeSynchronizationVM createdViewModel() {
        return ViewModelProviders.of(this).get(TimeSynchronizationVM.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_time_synchronization;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {
        switch (callbackMessage.what) {
            case 0:
                // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 0);
                break;
            case 1:
                showMyDialog("注意！",
                        "请确定探头处于工作状态，切换开关置于B！",
                        false, (dialogInterface, i) -> {
                            char a = 13;
                            char b = 10;
                            String str = "@";
                            str = str + TimeUtil.getCurrentTimeYYYY_MM_DD() + a + b;
                            // 没有连接设备，不能发送
                            if (bluetoothCommService.getState() != BluetoothCommService.STATE_CONNECTED) {
                                showToast("未连接设备");
                                return;
                            }
                            if (str.length() > 0) {// Check that there's actually something to send
                                // Get the message bytes and tell the BluetoothChatService to write
                                byte[] send = str.getBytes();
                                bluetoothCommService.write(send);
                                //Reset out string buffer to zero and clear the edit text field
                                //mOutStringBuffer.setLength(0);
                            }
                        });
                break;
        }

    }
}

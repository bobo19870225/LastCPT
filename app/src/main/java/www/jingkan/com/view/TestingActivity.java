package www.jingkan.com.view;

import android.view.MenuItem;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityTestingBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view.base.DialogMVVMDaggerActivity;
import www.jingkan.com.view_model.TestingViewModel;

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
public class TestingActivity extends DialogMVVMDaggerActivity<TestingViewModel, ActivityTestingBinding> {
    @Inject
    BluetoothUtil bluetoothUtil;
    @Inject
    BluetoothCommService bluetoothCommService;


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, bluetoothUtil, bluetoothCommService};
    }

    @Override
    protected void setMVVMView() {
        setToolBar("探头检测", R.menu.link);
        mViewModel.singleLiveEvent.observe(this, aVoid -> showWaitDialog("正在连接蓝牙设备...", false, false));

        bluetoothCommService.getBluetoothMessageMutableLiveData().observe(this, bluetoothMessage -> {
            switch (bluetoothMessage.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (bluetoothMessage.arg1) {
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
                    byte[] b = (byte[]) bluetoothMessage.obj;
                    String mDate = new String(b);
                    if (mDate.length() > 40) {
                        if (mDate.contains("\r")) {
                            mDate = mDate.substring(0, mDate.indexOf("\r"));
                            mViewModel.strData.setValue(mDate);
                        }

                    }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                mViewModel.link();
                return false;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public TestingViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(TestingViewModel.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_testing;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

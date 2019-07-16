package www.jingkan.com.view_model;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import www.jingkan.com.util.SingleLiveEvent;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 * {@link www.jingkan.com.view.TestingActivity}
 */
public class TestingViewModel extends BaseViewModel {
    public final MutableLiveData<String> strData = new MutableLiveData<>();
    public final SingleLiveEvent<String> singleLiveEvent = new SingleLiveEvent<>();
    private BluetoothUtil bluetoothUtil;
    private BluetoothCommService bluetoothCommService;
    private String mac;

    public TestingViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        mac = (String) objects[0];
        bluetoothUtil = (BluetoothUtil) objects[1];
        bluetoothCommService = (BluetoothCommService) objects[2];
        link();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {
        bluetoothCommService.stop();
        bluetoothCommService = null;
    }

    public void link() {
        BluetoothAdapter bluetoothAdapter = bluetoothUtil.getBluetoothAdapter();
        if (bluetoothAdapter.isEnabled()) {// 蓝牙已打开
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac);
            bluetoothCommService.connect(bluetoothDevice);
            singleLiveEvent.setValue("Link");
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            singleLiveEvent.setValue("OpenBT");
            getView().action(callbackMessage);
//            action.setValue("startActivityForResult");
//            myView.get().startActivityForResult(intent, 0);
        }

    }
}

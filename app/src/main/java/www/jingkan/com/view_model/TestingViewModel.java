package www.jingkan.com.view_model;

import android.app.Application;
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
 */
public class TestingViewModel extends BaseViewModel {
    public final MutableLiveData<String> strData = new MutableLiveData<>();
    public final SingleLiveEvent<Void> singleLiveEvent = new SingleLiveEvent<>();
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
        BluetoothDevice bluetoothDevice = bluetoothUtil.getBluetoothAdapter().getRemoteDevice(mac);
        bluetoothCommService.connect(bluetoothDevice);
        singleLiveEvent.call();
//        getView().showWaitDialog("正在连接蓝牙设备...", false, false);
    }
}

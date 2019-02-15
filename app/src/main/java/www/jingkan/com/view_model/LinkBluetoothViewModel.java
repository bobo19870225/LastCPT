package www.jingkan.com.view_model;

import android.Manifest;
import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.util.acp.Acp;
import com.jinkan.www.cpttest.util.acp.AcpListener;
import com.jinkan.www.cpttest.util.acp.AcpOptions;
import com.jinkan.www.cpttest.util.bluetooth.BluetoothUtil;
import com.jinkan.www.cpttest.view_model.base.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class LinkBluetoothViewModel extends BaseViewModel {
    private BluetoothUtil bluetoothUtil;

    public LinkBluetoothViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        bluetoothUtil = (BluetoothUtil) objects[1];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    public void searchDevice() {
        Acp.getInstance(getApplication()).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        bluetoothUtil.doDiscovery();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        toast(permissions.toString() + "权限拒绝");
                    }
                });
    }


}

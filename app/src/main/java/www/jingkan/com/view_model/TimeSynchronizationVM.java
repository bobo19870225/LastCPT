package www.jingkan.com.view_model;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import www.jingkan.com.db.dao.WirelessProbeDao;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.entity.WirelessProbeEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 * {@link www.jingkan.com.view.TimeSynchronizationActivity}
 */
public class TimeSynchronizationVM extends BaseViewModel {
    public final MutableLiveData<String> strProjectNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strHoleNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strQcCoefficient = new MutableLiveData<>();
    public final MutableLiveData<String> strQcLimit = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isDoubleBridge = new MutableLiveData<>();
    public final MutableLiveData<String> strFsCoefficient = new MutableLiveData<>();
    public final MutableLiveData<String> strFsLimit = new MutableLiveData<>();
    public final MutableLiveData<String> markingTime = new MutableLiveData<>();
    public final MutableLiveData<String> obsProbeNumber = new MutableLiveData<>();
    public final MutableLiveData<Boolean> linked = new MutableLiveData<>();
    public final MediatorLiveData<List<WirelessProbeEntity>> liveDataWirelessProbeEntity = new MediatorLiveData<>();
    public final MediatorLiveData<List<WirelessTestEntity>> liveDataWirelessTestEntity = new MediatorLiveData<>();


    private BluetoothUtil bluetoothUtil;
    private String strMac;
    private BluetoothCommService bluetoothCommService;
    private boolean isIdentification;
    private WirelessProbeDao wirelessProbeDao;
    private WirelessTestDao wirelessTestDao;
    public TimeSynchronizationVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        String[] strings = (String[]) objects[0];
        strMac = strings[0];
        if (strings[3].contains("双桥")) {
            isDoubleBridge.setValue(true);
        } else {
            isDoubleBridge.setValue(false);
        }
        bluetoothUtil = (BluetoothUtil) objects[1];
        bluetoothCommService = (BluetoothCommService) objects[2];
        wirelessProbeDao = (WirelessProbeDao) objects[3];
        wirelessTestDao = (WirelessTestDao) objects[4];
        getTestParameters(strings[1], strings[2]);//载入试验参数

    }

    private void getTestParameters(String projectNumber, String holeNumber) {
        liveDataWirelessTestEntity
                .addSource(wirelessTestDao.getWirelessTestEntityByPrjNumberAndHoleNumber(projectNumber, holeNumber),
                        liveDataWirelessTestEntity::setValue);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {
        bluetoothCommService.stop();
    }

    public void identificationProbe(String sn) {
        if (!isIdentification) {
            isIdentification = true;
            liveDataWirelessProbeEntity.addSource(wirelessProbeDao.getWirelessProbeEntityByProbeId(sn), liveDataWirelessProbeEntity::setValue);
        }
    }

    public void doSynchronization() {
        callbackMessage.setValue(1);
        getView().action(callbackMessage);

    }

    public void linkDevice() {
        BluetoothAdapter bluetoothAdapter = bluetoothUtil.getBluetoothAdapter();
        if (bluetoothAdapter.isEnabled()) {// 蓝牙已打开
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(strMac);
            bluetoothCommService.connect(bluetoothDevice);
        } else {
            callbackMessage.setValue(0);
            getView().action(callbackMessage);

        }
    }
}

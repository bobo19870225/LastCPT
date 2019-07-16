package www.jingkan.com.view_model;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import www.jingkan.com.db.dao.CrossTestDataDao;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.entity.CrossTestDataEntity;
import www.jingkan.com.util.SingleLiveEvent;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 * {@link www.jingkan.com.view.CrossTestActivity}
 */
public class CrossTestViewModel extends BaseViewModel {

    public final MutableLiveData<String> strProjectNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strHoleNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strCuCoefficient = new MutableLiveData<>();
    public final MutableLiveData<String> strCuLimit = new MutableLiveData<>();
    public final MutableLiveData<String> strCuInitial = new MutableLiveData<>();
    public final MutableLiveData<String> strCuEffective = new MutableLiveData<>();
    public final MutableLiveData<String> deg = new MutableLiveData<>();
    public final MutableLiveData<Integer> intTestNumber = new MutableLiveData<>();
    public final MutableLiveData<String> strDeep = new MutableLiveData<>();
    public final MutableLiveData<String> strSoilType = new MutableLiveData<>();
    public final MutableLiveData<Boolean> linked = new MutableLiveData<>();
    public final MutableLiveData<Boolean> start = new MutableLiveData<>();
    public final SingleLiveEvent<List<CrossTestDataEntity>> CrossTestDataEntities = new SingleLiveEvent<>();
    public String mac;
    private BluetoothUtil bluetoothUtil;
    private BluetoothCommService bluetoothCommService;
    private TestDao testDao;
    private CrossTestDataDao crossTestDataDao;

    public CrossTestViewModel(@NonNull Application application, BluetoothUtil bluetoothUtil, BluetoothCommService bluetoothCommService, TestDao testDao, CrossTestDataDao crossTestDataDao) {
        super(application);
        this.bluetoothUtil = bluetoothUtil;
        this.bluetoothCommService = bluetoothCommService;
        this.testDao = testDao;
        this.crossTestDataDao = crossTestDataDao;
    }

    public void getTestParameters() {
        testDao.getTestEntityByPrjNumberAndHoleNumber(strProjectNumber.getValue(), strHoleNumber.getValue()).observe(lifecycleOwner, testEntities -> {
            if (testEntities != null && testEntities.size() > 0) {
                crossTestDataDao.getCrossTestDataByTestDataId(strProjectNumber.getValue() + "_" + strHoleNumber.getValue()).observe(lifecycleOwner, crossTestDataEntities -> {
                    if (crossTestDataEntities != null && crossTestDataEntities.size() > 0) {
                        CrossTestDataEntity crossTestDataEntity = crossTestDataEntities.get(crossTestDataEntities.size() - 1);
                        deg.setValue(String.valueOf(crossTestDataEntity.deg));
                        strDeep.setValue(String.valueOf(crossTestDataEntity.deep));
                        intTestNumber.setValue(crossTestDataEntity.number);
                        strSoilType.setValue(crossTestDataEntity.type);
                        Integer intTestNumberValue = intTestNumber.getValue();
                        if (intTestNumberValue != null)
                            crossTestDataDao.getCrossTestDataByTestDataIdAndNumber(strProjectNumber.getValue() + "_" + strHoleNumber.getValue(), intTestNumberValue).observe(lifecycleOwner, crossTestDataEntities1 -> {
                                CrossTestDataEntities.setValue(crossTestDataEntities1);
//                                myView.get().showTestData(models);
                            });
                    } else {
                        toast("没有试验数据");
                    }
                });
            } else {
                toast("找不到该孔信息");
            }
        });
    }
    @Override
    public void inject(Object... objects) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    public void modify() {
    }

    public void doInitialValue() {

    }

    public void doStart() {
    }

    public void end() {
    }

    public void linkDevice() {
        action.setValue("LinkBT");
        BluetoothAdapter bluetoothAdapter = bluetoothUtil.getBluetoothAdapter();
        if (bluetoothAdapter.isEnabled()) {// 蓝牙已打开
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac);
            bluetoothCommService.connect(bluetoothDevice);
        } else {
            action.setValue("OpenBT");
        }
    }
}

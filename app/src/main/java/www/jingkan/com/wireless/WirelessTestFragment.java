/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import www.jingkan.com.R;
import www.jingkan.com.activity.NewTestActivity;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseFragment;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.linkBluetooth.LinkBluetoothActivity;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.wirelessTest.WirelessTestDaoForRoom;
import www.jingkan.com.localData.wirelessTest.WirelessTestEntity;
import www.jingkan.com.wireless.dataSynchronization.DataSyncActivity;
import www.jingkan.com.wireless.markFile.MarkFileActivity;
import www.jingkan.com.wireless.testData.WirelessTestDataActivity;
import www.jingkan.com.wireless.timeSynchronization.TimeSynchronizationActivity;
import www.jingkan.com.wireless.wirelessProbe.WirelessProbeActivity;


public class WirelessTestFragment extends BaseFragment {
    @BindView(id = R.id.markup_file, click = true)
    private RelativeLayout markup_file;
    @BindView(id = R.id.test_again, click = true)
    private RelativeLayout test_again;
    @BindView(id = R.id.new_test, click = true)
    private RelativeLayout new_test;
    @BindView(id = R.id.wireless_probe_list, click = true)
    private RelativeLayout wireless_probe_list;
    @BindView(id = R.id.data_syn, click = true)
    private RelativeLayout data_syn;
    @BindView(id = R.id.test_data, click = true)
    private RelativeLayout test_data;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_wireless_test, container, false);
    }

    @Override
    protected void setView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.new_test:
                goTo(NewTestActivity.class, "无缆试验");
                break;
            case R.id.test_again:
                WirelessTestDaoForRoom wirelessTestDaoForRoom = AppDatabase.getInstance(getContext()).wirelessTestDaoForRoom();
                LiveData<List<WirelessTestEntity>> liveData = wirelessTestDaoForRoom.getAllWirelessTestEntity();
                List<WirelessTestEntity> wirelessTestEntities = liveData.getValue();
                if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
                    PreferencesUtils preferencesUtils = new PreferencesUtils(getContext());
                    Map<String, String> linkerPreferences = preferencesUtils.getLinkerPreferences();
                    String add = linkerPreferences.get("add");
                    WirelessTestEntity wirelessTestEntity = wirelessTestEntities.get(0);
                    if (StringUtils.isEmpty(add)) {
                        goTo(LinkBluetoothActivity.class,
                                new String[]{wirelessTestEntity.projectNumber,
                                        wirelessTestEntity.holeNumber,
                                        wirelessTestEntity.testType});
                    } else {//mac地址，工程编号，孔号，试验类型。
                        goTo(TimeSynchronizationActivity.class,
                                new String[]{add,
                                        wirelessTestEntity.projectNumber,
                                        wirelessTestEntity.holeNumber,
                                        wirelessTestEntity.testType});
                    }
                } else {
                    showToast("暂无可进行二次测量的试验");
                }

//                WirelessTestDao wirelessTestDao = DataFactory.getBaseData(WirelessTestDao.class);
//                wirelessTestDao.getData(new DataLoadCallBack<WirelessTestEntity>() {
//
//                    @Override
//                    public void onDataLoaded(List<WirelessTestEntity> models) {
//                        PreferencesUtils preferencesUtils = new PreferencesUtils(getContext());
//                        Map<String, String> linkerPreferences = preferencesUtils.getLinkerPreferences();
//                        String add = linkerPreferences.get("add");
//                        WirelessTestEntity wirelessTestEntity = models.get(0);
//                        if (StringUtils.isEmpty(add)) {
//                            goTo(LinkBluetoothActivity.class,
//                                    new String[]{wirelessTestEntity.projectNumber,
//                                            wirelessTestEntity.holeNumber,
//                                            wirelessTestEntity.testType});
//                        } else {//mac地址，工程编号，孔号，试验类型。
//                            goTo(TimeSynchronizationActivity.class,
//                                    new String[]{add,
//                                            wirelessTestEntity.projectNumber,
//                                            wirelessTestEntity.holeNumber,
//                                            wirelessTestEntity.testType});
//                        }
//                    }
//
//                    @Override
//                    public void onDataNotAvailable() {
//                        showToast("暂无可进行二次测量的试验");
//                    }
//                });
                break;
            case R.id.markup_file:
                goTo(MarkFileActivity.class, null);
                break;
            case R.id.wireless_probe_list:
                goTo(WirelessProbeActivity.class, null);
                break;
            case R.id.data_syn:
                goTo(DataSyncActivity.class, null);
                break;
            case R.id.test_data:
                goTo(WirelessTestDataActivity.class, null);
                break;
        }

    }
}



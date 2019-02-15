/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.FragmentWirelessTestBinding;
import com.jinkan.www.cpttest.db.dao.WirelessTestDao;
import com.jinkan.www.cpttest.db.entity.WirelessTestEntity;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.util.PreferencesUtil;
import com.jinkan.www.cpttest.util.StringUtil;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerFragment;
import com.jinkan.www.cpttest.view_model.WirelessTestFragmentViewModel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class WirelessTestFragment extends BaseMVVMDaggerFragment<WirelessTestFragmentViewModel, FragmentWirelessTestBinding> {


    @Inject
    PreferencesUtil preferencesUtil;
    @Inject
    WirelessTestDao wirelessTestDao;

    @Inject
    public WirelessTestFragment() {

    }

    @Override
    protected void setView() {

        mViewModel.listMediatorLiveData.observe(this, new Observer<List<WirelessTestEntity>>() {
            @Override
            public void onChanged(List<WirelessTestEntity> wirelessTestEntities) {
                {
                    mViewModel.listMediatorLiveData.removeObserver(this);
                    if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
                        Map<String, String> linkerPreferences = preferencesUtil.getLinkerPreferences();
                        String add = linkerPreferences.get("add");
                        WirelessTestEntity wirelessTestEntity = wirelessTestEntities.get(0);
                        if (StringUtil.isEmpty(add)) {
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
                }
            }
        });
    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, wirelessTestDao};
    }

    @Override
    protected int setLayOutId() {
        return R.layout.fragment_wireless_test;
    }

    @Override
    public WirelessTestFragmentViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(WirelessTestFragmentViewModel.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

        switch (callbackMessage.what) {
            case 0:
                goTo(NewTestActivity.class, "无缆试验");
                break;
            case 1:
                goTo(MarkFileActivity.class, null);
                break;
            case 2:
                goTo(WirelessProbeActivity.class, null);
                break;
            case 3:
                goTo(DataSyncActivity.class, null);
                break;
            case 4:
//                    goTo(WirelessTestDataActivity.class, null);
                break;
        }

    }
}



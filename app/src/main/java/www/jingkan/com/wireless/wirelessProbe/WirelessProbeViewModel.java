/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.wirelessProbe;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.adapter.wirelessProbeAdapter.WirelessProbeItemViewModel;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemWirelessProbeListBinding;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeDaoForRoom;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeEntity;

/**
 * Created by lushengbo on 2018/1/24.
 * 无缆探头VM
 */

public class WirelessProbeViewModel extends MVVMListViewModel<WirelessProbeActivity> {
    List<WirelessProbeItemViewModel> wirelessProbeItemViewModels;

    @Override
    protected void initData(Object data) {

    }

    @Override
    public BaseDataBindingAdapter setUpAdapter() {
        wirelessProbeItemViewModels = new ArrayList<>();
        return new BaseDataBindingAdapter<ItemWirelessProbeListBinding, WirelessProbeItemViewModel>(wirelessProbeItemViewModels, R.layout.item_wireless_probe_list);
    }

    @Override
    public void loadListViewData() {
        getWirelessProbeList();
    }

    void getWirelessProbeList() {
        WirelessProbeDaoForRoom wirelessProbeDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).wirelessProbeDaoForRoom();
        wirelessProbeItemViewModels.clear();
        LiveData<List<WirelessProbeEntity>> liveData = wirelessProbeDaoForRoom.getAllWirelessProbeEntity();
        List<WirelessProbeEntity> wirelessProbeEntities = liveData.getValue();
        if (wirelessProbeEntities != null && !wirelessProbeEntities.isEmpty()) {
            for (WirelessProbeEntity wirelessProbeModel : wirelessProbeEntities
                    ) {
                WirelessProbeItemViewModel wirelessProbeItemViewModel = new WirelessProbeItemViewModel();
                wirelessProbeItemViewModel.probeNumber.set(wirelessProbeModel.number);
                wirelessProbeItemViewModels.add(wirelessProbeItemViewModel);
            }
            adapter.notifyDataSetChanged();
            getView().stopLoading();
            getView().setListView(wirelessProbeEntities);
        } else {
            adapter.notifyDataSetChanged();
            getView().stopLoading();
        }

//        WirelessProbeDao wirelessProbeDao = DataFactory.getBaseData(WirelessProbeDao.class);
//        wirelessProbeItemViewModels.clear();
//        wirelessProbeDao.getData(new DataLoadCallBack<WirelessProbeModel>() {
//
//            @Override
//            public void onDataLoaded(List<WirelessProbeModel> models) {
//                for (WirelessProbeModel wirelessProbeModel : models
//                        ) {
//                    WirelessProbeItemViewModel wirelessProbeItemViewModel = new WirelessProbeItemViewModel();
//                    wirelessProbeItemViewModel.probeNumber.set(wirelessProbeModel.number);
//                    wirelessProbeItemViewModels.add(wirelessProbeItemViewModel);
//                }
//                adapter.notifyDataSetChanged();
//                getView().stopLoading();
//                getView().setListView(models);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                adapter.notifyDataSetChanged();
//                getView().stopLoading();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }

    public void addProbe() {
        getView().goTo(AddWirelessProbeActivity.class, null);
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.testData;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.adapter.WirelessTestDataItemVM;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemWirelessTestDataBinding;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.wirelessTest.WirelessTestDaoForRoom;
import www.jingkan.com.localData.wirelessTest.WirelessTestEntity;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验数据列表
 */

public class WirelessTestDataViewModel extends MVVMListViewModel<WirelessTestDataActivity> {

    private List<WirelessTestDataItemVM> wirelessTestDataItemVMS;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }

    @Override
    protected void initData(Object data) {

    }

    @Override
    public BaseDataBindingAdapter setUpAdapter() {
        wirelessTestDataItemVMS = new ArrayList<>();
        return new BaseDataBindingAdapter
                <ItemWirelessTestDataBinding, WirelessTestDataItemVM>
                (wirelessTestDataItemVMS, R.layout.item_wireless_test_data);
    }

    @Override
    public void loadListViewData() {
        WirelessTestDaoForRoom wirelessTestDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).wirelessTestDaoForRoom();
        wirelessTestDataItemVMS.clear();
        LiveData<List<WirelessTestEntity>> liveData = wirelessTestDaoForRoom.getAllWirelessTestEntity();
        List<WirelessTestEntity> wirelessTestEntities = liveData.getValue();
        if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {

            for (WirelessTestEntity wirelessTestEntity : wirelessTestEntities
                    ) {
                WirelessTestDataItemVM wirelessTestDataItemVM = new WirelessTestDataItemVM();
                wirelessTestDataItemVM.strProjectNumber.set(wirelessTestEntity.projectNumber);
                wirelessTestDataItemVM.strHoleNumber.set(wirelessTestEntity.holeNumber);
                wirelessTestDataItemVM.strTestDate.set(wirelessTestEntity.testDate);
                wirelessTestDataItemVMS.add(wirelessTestDataItemVM);
            }
            adapter.notifyDataSetChanged();
            getView().stopLoading();
            getView().setListView(wirelessTestEntities);
        } else {
            adapter.notifyDataSetChanged();
            getView().stopLoading();
        }
//        WirelessTestDao wirelessTestDao = DataFactory.getBaseData(WirelessTestDao.class);
//        wirelessTestDao.getData(new DataLoadCallBack<WirelessTestEntity>() {
//
//            @Override
//            public void onDataLoaded(List<WirelessTestEntity> models) {
//
//                for (WirelessTestEntity wirelessTestEntity : models
//                        ) {
//                    WirelessTestDataItemVM wirelessTestDataItemVM = new WirelessTestDataItemVM();
//                    wirelessTestDataItemVM.strProjectNumber.set(wirelessTestEntity.projectNumber);
//                    wirelessTestDataItemVM.strHoleNumber.set(wirelessTestEntity.holeNumber);
//                    wirelessTestDataItemVM.strTestDate.set(wirelessTestEntity.testDate);
//                    wirelessTestDataItemVMS.add(wirelessTestDataItemVM);
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
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.testData;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.adapter.WirelessTestDataItemVM;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemWirelessTestDataBinding;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.wirelessTest.WirelessTestDao;
import www.jingkan.com.localData.wirelessTest.WirelessTestModel;

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
        WirelessTestDao wirelessTestDao = DataFactory.getBaseData(WirelessTestDao.class);
        wirelessTestDataItemVMS.clear();
        wirelessTestDao.getData(new DataLoadCallBack<WirelessTestModel>() {

            @Override
            public void onDataLoaded(List<WirelessTestModel> models) {

                for (WirelessTestModel wirelessTestModel : models
                        ) {
                    WirelessTestDataItemVM wirelessTestDataItemVM = new WirelessTestDataItemVM();
                    wirelessTestDataItemVM.strProjectNumber.set(wirelessTestModel.projectNumber);
                    wirelessTestDataItemVM.strHoleNumber.set(wirelessTestModel.holeNumber);
                    wirelessTestDataItemVM.strTestDate.set(wirelessTestModel.testDate);
                    wirelessTestDataItemVMS.add(wirelessTestDataItemVM);
                }
                adapter.notifyDataSetChanged();
                getView().stopLoading();
                getView().setListView(models);
            }

            @Override
            public void onDataNotAvailable() {
                adapter.notifyDataSetChanged();
                getView().stopLoading();
            }
        });
    }
}

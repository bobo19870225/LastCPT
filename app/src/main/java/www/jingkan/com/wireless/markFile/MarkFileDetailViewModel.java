/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.markFile;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.activeandroid.Model;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.adapter.markFileDetailAdapter.MarkFileDetailItemViewModel;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemMarkFileDetailBinding;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.wirelessTest.WirelessTestData;
import www.jingkan.com.localData.wirelessTest.WirelessTestModel;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataData;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataModel;

/**
 * Created by lushengbo on 2018/1/16.
 * 标记数据详情
 */

public class MarkFileDetailViewModel extends MVVMListViewModel<MarkFileDetailActivity> {
    public final ObservableField<String> strProjectNumber = new ObservableField<>();
    public final ObservableField<String> strHoleNumber = new ObservableField<>();
    public final ObservableField<String> strTestDate = new ObservableField<>();
    public final ObservableField<String> obsProbeNumber = new ObservableField<>();
    public final ObservableBoolean isHaveData = new ObservableBoolean(false);
    private String strTestDataID;

    @Override
    protected void initData(Object data) {
        strTestDataID = (String) data;
        String[] strings = strTestDataID.split("_");
        String strProjectNumber = strings[0];
        String strHoleNumber = strings[1];
        loadTestParameter(strProjectNumber, strHoleNumber);
    }

    private List<MarkFileDetailItemViewModel> markFileDetailItemViewModels;

    @Override
    public BaseDataBindingAdapter setUpAdapter() {
        markFileDetailItemViewModels = new ArrayList<>();
        return new BaseDataBindingAdapter<ItemMarkFileDetailBinding, MarkFileDetailItemViewModel>(markFileDetailItemViewModels, R.layout.item_mark_file_detail);
    }

    @Override
    public void loadListViewData() {
        loadMarkFileData(strTestDataID);
    }

    private void loadTestParameter(String projectNumber, String holeNumber) {
        WirelessTestData wirelessTestData = DataFactory.getBaseData(WirelessTestData.class);
        wirelessTestData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                WirelessTestModel wirelessTestModel = (WirelessTestModel) models.get(0);
                strProjectNumber.set(wirelessTestModel.projectNumber);
                strHoleNumber.set(wirelessTestModel.holeNumber);
                strTestDate.set(wirelessTestModel.testDate);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, projectNumber, holeNumber);
    }

    private void loadMarkFileData(String strTestDataID) {
        WirelessTestDataData wirelessTestDataData = DataFactory.getBaseData(WirelessTestDataData.class);
        markFileDetailItemViewModels.clear();
        wirelessTestDataData.getData(new DataLoadCallBack() {
            @Override
            @SuppressWarnings("unchecked")
            public <T extends Model> void onDataLoaded(List<T> models) {
                List<WirelessTestDataModel> wirelessTestDataModels = (List<WirelessTestDataModel>) models;
                obsProbeNumber.set(wirelessTestDataModels.get(0).probeNumber);
                for (WirelessTestDataModel wirelessTestDataModel : wirelessTestDataModels
                        ) {
                    MarkFileDetailItemViewModel markFileDetailItemViewModel = new MarkFileDetailItemViewModel();
                    markFileDetailItemViewModel.strDeep.set(String.valueOf(wirelessTestDataModel.deep));
                    markFileDetailItemViewModel.strSRC.set(String.valueOf(wirelessTestDataModel.rtc));
                    markFileDetailItemViewModels.add(markFileDetailItemViewModel);
                }
                adapter.notifyDataSetChanged();
                getView().stopLoading();
                isHaveData.set(true);
            }

            @Override
            public void onDataNotAvailable() {
                adapter.notifyDataSetChanged();
                getView().stopLoading();
                isHaveData.set(false);
            }
        }, strTestDataID);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }
}

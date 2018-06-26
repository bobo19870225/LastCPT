/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.markFile;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.adapter.markFileAdapter.MarkFileItemViewMode;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemMarkFileBinding;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.wirelessTest.WirelessTestData;
import www.jingkan.com.localData.wirelessTest.WirelessTestModel;

/**
 * Created by lushengbo on 2018/1/15.
 * 标记文件VM
 */

public class MarkFileViewModel extends MVVMListViewModel<MarkFileActivity> {

    @Override
    protected void initData(Object data) {

    }

    private List<MarkFileItemViewMode> markFileItemViewModes;

    @Override
    public BaseDataBindingAdapter setUpAdapter() {
        markFileItemViewModes = new ArrayList<>();
        return new BaseDataBindingAdapter<ItemMarkFileBinding, MarkFileItemViewMode>(markFileItemViewModes, R.layout.item_mark_file);

    }

    @Override
    public void loadListViewData() {
        loadMarkData();
    }

    private void loadMarkData() {
        WirelessTestData wirelessTestData = DataFactory.getBaseData(WirelessTestData.class);
        wirelessTestData.getData(new DataLoadCallBack<WirelessTestModel>() {

            @Override
            public void onDataLoaded(List<WirelessTestModel> models) {
                //items.addAll((Collection<? extends WirelessTestModel>) models);
                List<WirelessTestModel> wirelessTestModels = (List<WirelessTestModel>) models;
                markFileItemViewModes.clear();
                for (WirelessTestModel wirelessTestModel : wirelessTestModels) {
                    MarkFileItemViewMode markFileItemViewMode = new MarkFileItemViewMode();
                    markFileItemViewMode.testID.set(wirelessTestModel.testID);
                    markFileItemViewMode.testData.set(wirelessTestModel.testDate);
                    markFileItemViewModes.add(markFileItemViewMode);
                }
                adapter.notifyDataSetChanged();
                getView().stopLoading();
                getView().setListView(wirelessTestModels);

            }

            @Override
            public void onDataNotAvailable() {
                adapter.notifyDataSetChanged();
                getView().stopLoading();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.markFile;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.adapter.markFileAdapter.MarkFileItemViewMode;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemMarkFileBinding;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.wirelessTest.WirelessTestDaoForRoom;
import www.jingkan.com.localData.wirelessTest.WirelessTestEntity;

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
        WirelessTestDaoForRoom wirelessTestDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).wirelessTestDaoForRoom();
        LiveData<List<WirelessTestEntity>> liveData = wirelessTestDaoForRoom.getAllWirelessTestEntity();
        List<WirelessTestEntity> wirelessTestEntities = liveData.getValue();
        if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
            //items.addAll((Collection<? extends WirelessTestEntity>) models);
            markFileItemViewModes.clear();
            for (WirelessTestEntity wirelessTestEntity : wirelessTestEntities) {
                MarkFileItemViewMode markFileItemViewMode = new MarkFileItemViewMode();
                markFileItemViewMode.testID.set(wirelessTestEntity.testID);
                markFileItemViewMode.testData.set(wirelessTestEntity.testDate);
                markFileItemViewModes.add(markFileItemViewMode);
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
//                //items.addAll((Collection<? extends WirelessTestEntity>) models);
//                List<WirelessTestEntity> wirelessTestEntitys = (List<WirelessTestEntity>) models;
//                markFileItemViewModes.clear();
//                for (WirelessTestEntity wirelessTestEntity : wirelessTestEntitys) {
//                    MarkFileItemViewMode markFileItemViewMode = new MarkFileItemViewMode();
//                    markFileItemViewMode.testID.set(wirelessTestEntity.testID);
//                    markFileItemViewMode.testData.set(wirelessTestEntity.testDate);
//                    markFileItemViewModes.add(markFileItemViewMode);
//                }
//                adapter.notifyDataSetChanged();
//                getView().stopLoading();
//                getView().setListView(wirelessTestEntitys);
//
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
}

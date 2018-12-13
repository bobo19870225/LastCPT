/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.testData;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.adapter.WirelessResultDataDetailItemVM;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemResultDataDetailsBinding;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataDaoForRoom;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataEntity;
import www.jingkan.com.localData.wirelessTest.WirelessTestDaoForRoom;
import www.jingkan.com.localData.wirelessTest.WirelessTestEntity;
import www.jingkan.com.mInterface.ISkip;
import www.jingkan.com.saveUtils.DataUtils;

import static www.jingkan.com.saveUtils.DataUtils.SET_EMAIL;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验详情
 */

public class WirelessResultDataDetailVM extends MVVMListViewModel<WirelessResultDataDetailActivity> implements ISkip {

    public final ObservableField<String> strProjectNumber = new ObservableField<>();
    public final ObservableField<String> strHoleNumber = new ObservableField<>();
    public final ObservableField<String> strTestDate = new ObservableField<>();
    private String strTestID;
    private List<WirelessResultDataEntity> wirelessResultDataModels;

    @Override
    protected void initData(Object data) {
        strTestID = (String) data;
        getTestParameter((String) data);
    }

    private WirelessTestEntity wirelessTestEntity;

    private void getTestParameter(String data) {
        String[] split = data.split("_");
        WirelessTestDaoForRoom wirelessTestDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).wirelessTestDaoForRoom();
        LiveData<List<WirelessTestEntity>> liveData = wirelessTestDaoForRoom.getWirelessTestEntityByPrjNumberAndHoleNumber(split[0], split[1]);
        List<WirelessTestEntity> wirelessTestEntities = liveData.getValue();
        if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
            wirelessTestEntity = wirelessTestEntities.get(0);
            strProjectNumber.set(wirelessTestEntity.projectNumber);
            strHoleNumber.set(wirelessTestEntity.holeNumber);
            strTestDate.set(wirelessTestEntity.testDate);
        }


//        WirelessTestDao wirelessTestDao = DataFactory.getBaseData(WirelessTestDao.class);
//        wirelessTestDao.getData(new DataLoadCallBack<WirelessTestEntity>() {
//
//            @Override
//            public void onDataLoaded(List<WirelessTestEntity> models) {
//                wirelessTestEntity = models.get(0);
//                strProjectNumber.set(wirelessTestEntity.projectNumber);
//                strHoleNumber.set(wirelessTestEntity.holeNumber);
//                strTestDate.set(wirelessTestEntity.testDate);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        }, split[0], split[1]);
    }

    private List<WirelessResultDataDetailItemVM> wirelessResultDataDetailItemVMs;

    @Override
    public BaseDataBindingAdapter setUpAdapter() {
        wirelessResultDataDetailItemVMs = new ArrayList<>();
        return new BaseDataBindingAdapter<ItemResultDataDetailsBinding, WirelessResultDataDetailItemVM>(wirelessResultDataDetailItemVMs,
                R.layout.item_result_data_details);
    }

    @Override
    public void loadListViewData() {
        WirelessResultDataDaoForRoom wirelessResultDataDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).wirelessResultDataDaoForRoom();
        LiveData<List<WirelessResultDataEntity>> liveData = wirelessResultDataDaoForRoom.getWirelessResultDataEntityByTestDataId(strTestID);
        List<WirelessResultDataEntity> wirelessResultDataEntities = liveData.getValue();
        if (wirelessResultDataEntities != null && !wirelessResultDataEntities.isEmpty()) {
            wirelessResultDataModels = wirelessResultDataEntities;
            for (WirelessResultDataEntity wirelessResultDataModel : wirelessResultDataEntities) {
                WirelessResultDataDetailItemVM wirelessResultDataDetailItemVM = new WirelessResultDataDetailItemVM();
                wirelessResultDataDetailItemVM.strDeep.set(StringUtils.format(wirelessResultDataModel.deep, 1));
                wirelessResultDataDetailItemVM.strQc.set(StringUtils.format(wirelessResultDataModel.qc, 3));
                wirelessResultDataDetailItemVM.strFs.set(StringUtils.format(wirelessResultDataModel.fs, 3));
                wirelessResultDataDetailItemVM.strFa.set(StringUtils.format(wirelessResultDataModel.fa, 1));
                wirelessResultDataDetailItemVMs.add(wirelessResultDataDetailItemVM);
            }
            adapter.notifyDataSetChanged();
            getView().stopLoading();
            getView().setListView(wirelessResultDataEntities);
        } else {
            getView().stopLoading();
        }


//        WirelessResultDaoDao wirelessResultDataDao = DataFactory.getBaseData(WirelessResultDaoDao.class);
//        wirelessResultDataDetailItemVMs.clear();
//        wirelessResultDataDao.getData(new DataLoadCallBack<WirelessResultDataModel>() {
//
//            @Override
//            public void onDataLoaded(List<WirelessResultDataModel> models) {
//                wirelessResultDataModels = (List<WirelessResultDataModel>) models;
//                for (WirelessResultDataModel wirelessResultDataModel :
//                        (List<WirelessResultDataModel>) models) {
//                    WirelessResultDataDetailItemVM wirelessResultDataDetailItemVM = new WirelessResultDataDetailItemVM();
//                    wirelessResultDataDetailItemVM.strDeep.set(StringUtils.format(wirelessResultDataModel.deep, 1));
//                    wirelessResultDataDetailItemVM.strQc.set(StringUtils.format(wirelessResultDataModel.qc, 3));
//                    wirelessResultDataDetailItemVM.strFs.set(StringUtils.format(wirelessResultDataModel.fs, 3));
//                    wirelessResultDataDetailItemVM.strFa.set(StringUtils.format(wirelessResultDataModel.fa, 1));
//                    wirelessResultDataDetailItemVMs.add(wirelessResultDataDetailItemVM);
//                }
//                adapter.notifyDataSetChanged();
//                getView().stopLoading();
//                getView().setListView(models);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                getView().stopLoading();
//            }
//        }, strTestID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SET_EMAIL && resultCode == Activity.RESULT_OK) {
            emailTestData(mEmailType);
        }
    }

    @Override
    protected void clear() {

    }

    public void saveTestDataToSD(String saveType) {
        DataUtils.getInstance().saveDataToSd(
                getView().getApplicationContext(),
                wirelessResultDataModels,
                saveType,
                wirelessTestEntity,
                this);
    }

    private String mEmailType;

    public void emailTestData(String emailType) {
        mEmailType = emailType;
        DataUtils.getInstance().emailData(
                getView().getApplicationContext(),
                wirelessResultDataModels,
                emailType,
                wirelessTestEntity,
                this);
    }

    @Override
    public void skipForResult(Intent intent, int requestCode) {
        getView().startActivityForResult(intent, requestCode);
    }

    @Override
    public void skip(Intent intent) {

    }

    @Override
    public void sendToastMsg(String msg) {
        getView().showToast(msg);
    }
}

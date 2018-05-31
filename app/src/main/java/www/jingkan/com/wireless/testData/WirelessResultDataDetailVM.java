/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.testData;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;

import com.activeandroid.Model;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.adapter.WirelessResultDataDetailItemVM;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemResultDataDetailsBinding;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataData;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataModel;
import www.jingkan.com.localData.wirelessTest.WirelessTestData;
import www.jingkan.com.localData.wirelessTest.WirelessTestModel;
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
    private List<WirelessResultDataModel> wirelessResultDataModels;

    @Override
    protected void initData(Object data) {
        strTestID = (String) data;
        getTestParameter((String) data);
    }

    private WirelessTestModel wirelessTestModel;

    private void getTestParameter(String data) {
        String[] split = data.split("_");
        WirelessTestData wirelessTestData = DataFactory.getBaseData(WirelessTestData.class);
        wirelessTestData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                wirelessTestModel = (WirelessTestModel) models.get(0);
                strProjectNumber.set(wirelessTestModel.projectNumber);
                strHoleNumber.set(wirelessTestModel.holeNumber);
                strTestDate.set(wirelessTestModel.testDate);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, split[0], split[1]);
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
        WirelessResultDataData wirelessResultDataData = DataFactory.getBaseData(WirelessResultDataData.class);
        wirelessResultDataDetailItemVMs.clear();
        wirelessResultDataData.getData(new DataLoadCallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                wirelessResultDataModels = (List<WirelessResultDataModel>) models;
                for (WirelessResultDataModel wirelessResultDataModel :
                        (List<WirelessResultDataModel>) models) {
                    WirelessResultDataDetailItemVM wirelessResultDataDetailItemVM = new WirelessResultDataDetailItemVM();
                    wirelessResultDataDetailItemVM.strDeep.set(StringUtils.format(wirelessResultDataModel.deep, 1));
                    wirelessResultDataDetailItemVM.strQc.set(StringUtils.format(wirelessResultDataModel.qc, 3));
                    wirelessResultDataDetailItemVM.strFs.set(StringUtils.format(wirelessResultDataModel.fs, 3));
                    wirelessResultDataDetailItemVM.strFa.set(StringUtils.format(wirelessResultDataModel.fa, 1));
                    wirelessResultDataDetailItemVMs.add(wirelessResultDataDetailItemVM);
                }
                adapter.notifyDataSetChanged();
                getView().stopLoading();
                getView().setListView(models);
            }

            @Override
            public void onDataNotAvailable() {
                getView().stopLoading();
            }
        }, strTestID);
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

    public void saveTestDataToSD(int saveType) {
        DataUtils.getInstance().saveDataToSd(
                getView().getApplicationContext(),
                wirelessResultDataModels,
                saveType,
                wirelessTestModel,
                this);
    }

    private int mEmailType;

    public void emailTestData(int emailType) {
        mEmailType = emailType;
        DataUtils.getInstance().emailData(
                getView().getApplicationContext(),
                wirelessResultDataModels,
                emailType,
                wirelessTestModel,
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

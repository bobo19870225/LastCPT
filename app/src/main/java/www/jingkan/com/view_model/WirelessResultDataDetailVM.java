/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import www.jingkan.com.db.dao.WirelessResultDataDao;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.entity.WirelessResultDataEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.util.DataUtil;
import www.jingkan.com.view_model.base.BaseListViewModel;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验详情
 */

public class WirelessResultDataDetailVM extends BaseListViewModel<List<WirelessResultDataEntity>> {

    public final ObservableField<String> strProjectNumber = new ObservableField<>();
    public final ObservableField<String> strHoleNumber = new ObservableField<>();
    public final ObservableField<String> strTestDate = new ObservableField<>();
    public final MediatorLiveData<List<WirelessTestEntity>> lvWirelessTestEntities = new MediatorLiveData<>();
    private String strTestID;
    private WirelessTestDao wirelessTestDao;
    private WirelessResultDataDao wirelessResultDataDao;
    private DataUtil dataUtil;

    public WirelessResultDataDetailVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        strTestID = (String) objects[0];
        getTestParameter(strTestID);
        wirelessTestDao = (WirelessTestDao) objects[1];
        wirelessResultDataDao = (WirelessResultDataDao) objects[2];
        dataUtil = (DataUtil) objects[3];
    }


    private void getTestParameter(String data) {
        String[] split = data.split("_");
        lvWirelessTestEntities.addSource(wirelessTestDao.getWirelessTestEntityByPrjNumberAndHoleNumber(split[0], split[1]), lvWirelessTestEntities::setValue);
    }


    @Override
    public LiveData<List<WirelessResultDataEntity>> loadListViewData() {
        return wirelessResultDataDao.getWirelessResultDataEntityByTestDataId(strTestID);
    }

    @Override
    public void afterLoadListViewData() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @Override
    public void clear() {

    }

    public void saveTestDataToSD(List wirelessResultDataModels, String saveType, Object wirelessTestModel, ISkip iSkip) {
        dataUtil.saveDataToSd(wirelessResultDataModels, saveType, wirelessTestModel, iSkip);
    }


    public void emailTestData(List wirelessResultDataModels, String emailType, Object wirelessTestModel, ISkip iSkip) {
        dataUtil.emailData(wirelessResultDataModels, emailType, wirelessTestModel, iSkip);
    }


}

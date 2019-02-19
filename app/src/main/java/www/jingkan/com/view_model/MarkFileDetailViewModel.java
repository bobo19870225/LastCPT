/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.dao.WirelessTestDataDao;
import www.jingkan.com.db.entity.WirelessTestDataEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.view_model.base.BaseListViewModel;

/**
 * Created by lushengbo on 2018/1/16.
 * 标记数据详情
 */

public class MarkFileDetailViewModel extends BaseListViewModel<List<WirelessTestDataEntity>> {
    public final ObservableField<String> strProjectNumber = new ObservableField<>();
    public final ObservableField<String> strHoleNumber = new ObservableField<>();
    public final ObservableField<String> strTestDate = new ObservableField<>();
    public final ObservableField<String> obsProbeNumber = new ObservableField<>();
    public final ObservableBoolean isHaveData = new ObservableBoolean(false);
    public final MediatorLiveData<List<WirelessTestEntity>> ldWirelessTestEntities = new MediatorLiveData<>();

    private String strTestDataID;
    private WirelessTestDataDao wirelessTestDataDao;
    private WirelessTestDao wirelessTestDao;

    public MarkFileDetailViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        strTestDataID = (String) objects[0];
        wirelessTestDataDao = (WirelessTestDataDao) objects[1];
        wirelessTestDao = (WirelessTestDao) objects[2];
        String[] strings = strTestDataID.split("_");
        String strProjectNumber = strings[0];
        String strHoleNumber = strings[1];
        loadTestParameter(strProjectNumber, strHoleNumber);
    }


//    private List<MarkFileDetailItemViewModel> markFileDetailItemViewModels;


    @Override
    public LiveData<List<WirelessTestDataEntity>> loadListViewData() {
        return wirelessTestDataDao.getWTDEByTestDataId(strTestDataID);
    }

    @Override
    public void afterLoadListViewData() {

    }

    private void loadTestParameter(String projectNumber, String holeNumber) {
        ldWirelessTestEntities.addSource(wirelessTestDao.getWirelessTestEntityByPrjNumberAndHoleNumber(projectNumber, holeNumber), ldWirelessTestEntities::setValue);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

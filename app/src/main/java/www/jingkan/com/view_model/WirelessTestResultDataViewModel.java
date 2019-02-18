/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import www.jingkan.com.db.dao.WirelessResultDataDao;
import www.jingkan.com.db.entity.WirelessResultDataEntity;
import www.jingkan.com.view_model.base.BaseListViewModel;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验数据列表
 */

public class WirelessTestResultDataViewModel extends BaseListViewModel<List<WirelessResultDataEntity>> {

    //    private List<WirelessTestDataItemVM> wirelessTestDataItemVMS;
    private WirelessResultDataDao wirelessResultDataDao;

    public WirelessTestResultDataViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        wirelessResultDataDao = (WirelessResultDataDao) objects[1];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }


    @Override
    public LiveData<List<WirelessResultDataEntity>> loadListViewData() {
        return wirelessResultDataDao.getAllWirelessResultDataEntities();
    }

    @Override
    public void afterLoadListViewData() {

    }
}

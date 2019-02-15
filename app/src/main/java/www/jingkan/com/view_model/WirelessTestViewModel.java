/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.db.dao.WirelessTestDao;
import com.jinkan.www.cpttest.db.dao.WirelessTestDataDao;
import com.jinkan.www.cpttest.db.entity.WirelessTestDataEntity;
import com.jinkan.www.cpttest.db.entity.WirelessTestEntity;
import com.jinkan.www.cpttest.util.StringUtil;
import com.jinkan.www.cpttest.util.TimeUtil;
import com.jinkan.www.cpttest.util.VibratorUtil;
import com.jinkan.www.cpttest.view_model.base.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MediatorLiveData;

/**
 * Created by lushengbo on 2017/10/25.
 * 无缆试验代理
 */

public class WirelessTestViewModel extends BaseViewModel {
    public final ObservableField<String> projectNumber = new ObservableField<>();
    public final ObservableField<String> holeNumber = new ObservableField<>();
    public final ObservableBoolean shock = new ObservableBoolean();
    public final ObservableField<String> strQcCoefficient = new ObservableField<>();
    public final ObservableField<String> strQcLimit = new ObservableField<>();
    public final ObservableField<String> strFsCoefficient = new ObservableField<>();
    public final ObservableField<String> strFsLimit = new ObservableField<>();
    public final ObservableField<String> strDeep = new ObservableField<>("0");
    public final ObservableField<String> obsProbeNumber = new ObservableField<>();
    public final ObservableBoolean doubleBridge = new ObservableBoolean(false);
    public final MediatorLiveData<List<WirelessTestEntity>> liveDataWirelessTestEntity = new MediatorLiveData<>();
    public final MediatorLiveData<List<WirelessTestDataEntity>> liveDataWirelessTestDataEntity = new MediatorLiveData<>();

    private WirelessTestDataDao wirelessTestDataDao;
    private WirelessTestDao wirelessTestDao;
    private VibratorUtil vibratorUtil;
    public float dp = 0;

    public WirelessTestViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    public void inject(Object... objects) {
        String[] strings = (String[]) objects[0];
        projectNumber.set(strings[1]);
        holeNumber.set(strings[2]);
        if (strings[3].contains("双桥")) {
            doubleBridge.set(true);
        }
        obsProbeNumber.set(strings[4]);
        getTestParameters();
        loadTestData(projectNumber.get() + "_" + holeNumber.get());
        wirelessTestDataDao = (WirelessTestDataDao) objects[1];
        wirelessTestDao = (WirelessTestDao) objects[2];
        vibratorUtil = (VibratorUtil) objects[3];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }


    private void getTestParameters() {
        liveDataWirelessTestEntity.addSource(wirelessTestDao.getWirelessTestEntityByPrjNumberAndHoleNumber(projectNumber.get(), holeNumber.get()),
                liveDataWirelessTestEntity::setValue);
    }


    private void loadTestData(String testDataID) {
        liveDataWirelessTestDataEntity.addSource(wirelessTestDataDao.getWTDEByTestDataId(testDataID),
                liveDataWirelessTestDataEntity::setValue);
    }


    private int md[] = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334,
            0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};


    public void doRecord() {
        dp += 0.1;
        int year, month, day, hr, min, sec; // 表示sytM与5整除
        char a = 13;
        char b = 10;
        String str = "@";
        str = str + TimeUtil.getCurrentTimeYYYY_MM_DD() + a + b;
        String strDate = str.substring(str.indexOf('@') + 1, str.indexOf(' '));
        String strTime = str.substring(str.indexOf(' ') + 1, str.indexOf(13));
        String[] dates = strDate.split("-");
        String[] times = strTime.split(":");
        year = Integer.parseInt(dates[0]) - 2000;
        month = Integer.parseInt(dates[1]);
        day = Integer.parseInt(dates[2]);
        hr = Integer.parseInt(times[0]);
        min = Integer.parseInt(times[1]);
        sec = Integer.parseInt(times[2]);
        long RTC;
        if (year == 0) {
            RTC = 0;
        } else {
            RTC = (year + 3) / 4 + year * 365;
        }
        if (year % 4 == 0) {
            RTC = RTC + md[month + 11];
        } else {
            RTC = RTC + md[month - 1];
        }
        RTC = RTC + day - 1;
        RTC = RTC * 86400 + hr * 3600 + min * 60 + sec;
        RTC = RTC * 256 + 128; // sytM\5
        WirelessTestDataEntity wirelessTestDataModel = new WirelessTestDataEntity();
        wirelessTestDataModel.testDataID = projectNumber.get() + "-" + holeNumber.get();
        wirelessTestDataModel.probeNumber = obsProbeNumber.get();
        wirelessTestDataModel.deep = dp;
        wirelessTestDataModel.rtc = RTC;
        wirelessTestDataDao.insertWirelessTestDataEntity(wirelessTestDataModel);
        if (shock.get()) {
            vibratorUtil.Vibrate(200);
        }
        strDeep.set(StringUtil.format(dp, 2));
    }


}

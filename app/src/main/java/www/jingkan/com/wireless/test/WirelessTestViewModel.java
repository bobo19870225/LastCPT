/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.test;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.activeandroid.Model;

import java.util.List;

import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.framework.utils.TimeUtils;
import www.jingkan.com.framework.utils.VibratorUtils;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.wirelessTest.WirelessTestData;
import www.jingkan.com.localData.wirelessTest.WirelessTestModel;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataData;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataModel;

/**
 * Created by lushengbo on 2017/10/25.
 * 无缆试验代理
 */

public class WirelessTestViewModel extends BaseViewModel<WirelessTestActivity> {
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


    private WirelessTestDataData wirelessTestDataData = DataFactory.getBaseData(WirelessTestDataData.class);


    @Override
    public void init(Object data) {
        String[] strings = (String[]) data;
        projectNumber.set(strings[1]);
        holeNumber.set(strings[2]);
        if (strings[3].contains("双桥")) {
            doubleBridge.set(true);
        }
        obsProbeNumber.set(strings[4]);
        getTestParameters();
        loadTestData(projectNumber.get() + "_" + holeNumber.get());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }


    private void getTestParameters() {
        final WirelessTestData wirelessTestData = DataFactory.getBaseData(WirelessTestData.class);
        wirelessTestData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                WirelessTestModel wirelessTestModel = (WirelessTestModel) models.get(0);
                projectNumber.set(wirelessTestModel.projectNumber);
                holeNumber.set(wirelessTestModel.holeNumber);
            }

            @Override
            public void onDataNotAvailable() {
                myView.get().showToast("找不到该孔信息");
            }
        }, projectNumber.get(), holeNumber.get());
    }


    private void loadTestData(String testDataID) {

        wirelessTestDataData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                WirelessTestDataModel wirelessTestDataModel = (WirelessTestDataModel) models.get(models.size() - 1);
                dp = wirelessTestDataModel.deep;
                strDeep.set(StringUtils.format(dp, 2));
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, testDataID);
    }


    private int md[] = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334,
            0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
    private float dp = 0;

    public void doRecord() {
        dp += 0.1;
        int year, month, day, hr, min, sec; // 表示sytM与5整除
        char a = 13;
        char b = 10;
        String str = "@";
        str = str + TimeUtils.getCurrentTimeYYYY_MM_DD() + a + b;
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
        WirelessTestDataModel wirelessTestDataModel = new WirelessTestDataModel();
        wirelessTestDataModel.testDataID = projectNumber.get() + "-" + holeNumber.get();
        wirelessTestDataModel.probeNumber = obsProbeNumber.get();
        wirelessTestDataModel.deep = dp;
        wirelessTestDataModel.rtc = RTC;
        wirelessTestDataData.addData(wirelessTestDataModel);
        if (shock.get()) {
            VibratorUtils.Vibrate(myView.get(), 200);
        }
        strDeep.set(StringUtils.format(dp, 2));
    }


}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.test;

import android.view.MenuItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.BaseMVVMActivity;
import www.jingkan.com.databinding.ActivityWirelessTestBinding;
import www.jingkan.com.framework.utils.MyFileUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataDaoForRoom;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataEntity;
import www.jingkan.com.wireless.dataSynchronization.DataSyncActivity;

/**
 * Created by lushengbo on 2017/10/25.
 * 无缆试验界面
 */

public class WirelessTestActivity extends BaseMVVMActivity<WirelessTestViewModel, ActivityWirelessTestBinding> {
    private String strFileName;
    private String strTestID;


    @Override
    public int initView() {
        return R.layout.activity_wireless_test;
    }

    @Override
    protected WirelessTestViewModel createdViewModel() {
        return new WirelessTestViewModel();
    }


    @Override
    protected void setView() {
        String[] strings = (String[]) mData;//1.mac,2.工程编号,3.孔号,4.试验类型,5.探头编号
        strTestID = strings[1] + "_" + strings[2];
        strFileName = strTestID + "W.txt";
        setToolBar(strings[3], R.menu.wireless_test);
    }

    private StringBuilder strContent;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                final String strReturn = "\r\n";
                strContent = new StringBuilder();
                WirelessTestDataDaoForRoom wirelessTestDataDaoForRoom = AppDatabase.getInstance(getApplicationContext()).wirelessTestDataDaoForRoom();
                LiveData<List<WirelessTestDataEntity>> liveData = wirelessTestDataDaoForRoom.getWTDEByTestDataId(strTestID);
                List<WirelessTestDataEntity> wirelessTestDataEntities = liveData.getValue();
                if (wirelessTestDataEntities != null && !wirelessTestDataEntities.isEmpty()) {
                    strContent.append(wirelessTestDataEntities.get(0).probeNumber).append(strReturn);//探头编号
                    strContent.append(strTestID).append(strReturn);//试验ID
                    for (WirelessTestDataEntity wirelessTestDataModel : wirelessTestDataEntities) {
                        strContent.append(wirelessTestDataModel.deep).append(strReturn);
                        strContent.append(wirelessTestDataModel.rtc).append(strReturn);
                    }
                    saveDataToSD();
                } else {
                    showToast("当前无可保存的数据");
                }

//                WirelessTestDaoDao wirelessTestDataDao = DataFactory.getBaseData(WirelessTestDaoDao.class);
//                wirelessTestDataDao.getData(new DataLoadCallBack<WirelessTestDataModel>() {
//
//                    @Override
//                    public void onDataLoaded(List<WirelessTestDataModel> models) {
//                        strContent.append(models.get(0).probeNumber).append(strReturn);//探头编号
//                        strContent.append(strTestID).append(strReturn);//试验ID
//                        for (WirelessTestDataModel wirelessTestDataModel : models) {
//                            strContent.append(wirelessTestDataModel.deep).append(strReturn);
//                            strContent.append(wirelessTestDataModel.rtc).append(strReturn);
//                        }
//                        saveDataToSD();
//                    }
//
//                    @Override
//                    public void onDataNotAvailable() {
//                        showToast("当前无可保存的数据");
//                    }
//                }, strTestID);

                return false;
            case R.id.data_syn:
                goTo(DataSyncActivity.class, null, true);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDataToSD() {
        if (StringUtils.isNotEmpty(strContent.toString())) {
            MyFileUtils.getInstance().saveToSD(this,
                    strFileName,
                    strContent.toString(),
                    new MyFileUtils.SaveFileCallBack() {
                        @Override
                        public void onSuccess() {
                            showToast("保存成功");
                        }

                        @Override
                        public void onFail(String e) {
                            showToast(e);
                        }
                    });
        }
    }
}

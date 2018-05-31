/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.test;

import android.view.MenuItem;

import com.activeandroid.Model;

import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.BaseMVVMActivity;
import www.jingkan.com.databinding.ActivityWirelessTestBinding;
import www.jingkan.com.framework.utils.MyFileUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataData;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataModel;
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
                WirelessTestDataData wirelessTestDataData = DataFactory.getBaseData(WirelessTestDataData.class);
                wirelessTestDataData.getData(new DataLoadCallBack() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public <T extends Model> void onDataLoaded(List<T> models) {
                        List<WirelessTestDataModel> wirelessTestDataModels = (List<WirelessTestDataModel>) models;
                        strContent.append(wirelessTestDataModels.get(0).probeNumber).append(strReturn);//探头编号
                        strContent.append(strTestID).append(strReturn);//试验ID
                        for (WirelessTestDataModel wirelessTestDataModel : wirelessTestDataModels) {
                            strContent.append(wirelessTestDataModel.deep).append(strReturn);
                            strContent.append(wirelessTestDataModel.rtc).append(strReturn);
                        }
                        saveDataToSD();
                    }

                    @Override
                    public void onDataNotAvailable() {
                        showToast("当前无可保存的数据");
                    }
                }, strTestID);

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

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import android.view.MenuItem;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityWirelessTestBinding;
import com.jinkan.www.cpttest.db.dao.WirelessTestDao;
import com.jinkan.www.cpttest.db.dao.WirelessTestDataDao;
import com.jinkan.www.cpttest.db.entity.WirelessTestDataEntity;
import com.jinkan.www.cpttest.db.entity.WirelessTestEntity;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.util.MyFileUtil;
import com.jinkan.www.cpttest.util.StringUtil;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.WirelessTestViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by lushengbo on 2017/10/25.
 * 无缆试验界面
 */

public class WirelessTestActivity extends BaseMVVMDaggerActivity<WirelessTestViewModel, ActivityWirelessTestBinding> {
    private String strFileName;
    private String strTestID;
    @Inject
    WirelessTestDataDao wirelessTestDataDao;
    @Inject
    WirelessTestDao wirelessTestDao;

    @Override
    public int initView() {
        return R.layout.activity_wireless_test;
    }

    @Override
    public WirelessTestViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(WirelessTestViewModel.class);
    }


    private StringBuilder strContent;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                final String strReturn = "\r\n";
                strContent = new StringBuilder();
                wirelessTestDataDao.getWTDEByTestDataId(strTestID).observe(this, wirelessTestDataEntities -> {
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
                });
                return false;
            case R.id.data_syn:
                goTo(DataSyncActivity.class, null, true);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDataToSD() {
        if (StringUtil.isNotEmpty(strContent.toString())) {
            MyFileUtil.getInstance().saveToSD(this,
                    strFileName,
                    strContent.toString(),
                    new MyFileUtil.SaveFileCallBack() {
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

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, wirelessTestDataDao, wirelessTestDao};
    }

    @Override
    protected void setMVVMView() {
        String[] strings = (String[]) mData;//1.mac,2.工程编号,3.孔号,4.试验类型,5.探头编号
        strTestID = strings[1] + "_" + strings[2];
        strFileName = strTestID + "W.txt";
        setToolBar(strings[3], R.menu.wireless_test);
        mViewModel.liveDataWirelessTestEntity.observe(this, wirelessTestEntities -> {
            if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
                WirelessTestEntity wirelessTestEntity = wirelessTestEntities.get(0);
                mViewModel.projectNumber.set(wirelessTestEntity.projectNumber);
                mViewModel.holeNumber.set(wirelessTestEntity.holeNumber);
            } else {
                showToast("找不到该孔信息");
            }
        });
        mViewModel.liveDataWirelessTestDataEntity.observe(this, wirelessTestDataEntities -> {
            if (wirelessTestDataEntities != null && !wirelessTestDataEntities.isEmpty()) {
                WirelessTestDataEntity wirelessTestDataModel = wirelessTestDataEntities.get(wirelessTestDataEntities.size() - 1);
                mViewModel.dp = wirelessTestDataModel.deep;
                mViewModel.strDeep.set(StringUtil.format(mViewModel.dp, 2));
            }
        });
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

    }
}

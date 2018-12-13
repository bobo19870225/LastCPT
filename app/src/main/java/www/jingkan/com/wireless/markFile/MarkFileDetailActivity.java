/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.markFile;

import android.view.MenuItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMListActivity;
import www.jingkan.com.databinding.ActivityMarkFileDetailBinding;
import www.jingkan.com.framework.utils.MyFileUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataDaoForRoom;
import www.jingkan.com.localData.wirelessTestData.WirelessTestDataEntity;
import www.jingkan.com.wireless.dataSynchronization.DataSyncActivity;

/**
 * Created by lushengbo on 2018/1/16.
 * 标记数据详情页面
 */

public class MarkFileDetailActivity extends MVVMListActivity<MarkFileDetailViewModel, ActivityMarkFileDetailBinding> {
    @Override
    protected MarkFileDetailViewModel createdViewModel() {
        return new MarkFileDetailViewModel();
    }

    private String strTestID;

    @Override
    protected void setViewWithOutListView() {
        setToolBar("标记数据详情", R.menu.wireless_test);
        strTestID = (String) mData;
        strFileName = strTestID + "W.txt";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return mViewDataBinding.srl;
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
                    strContent.append(wirelessTestDataEntities.get(0).probeNumber).append(strReturn);
                    strContent.append(strTestID).append(strReturn);
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
//                        strContent.append(models.get(0).probeNumber).append(strReturn);
//                        strContent.append(strTestID).append(strReturn);
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
                goTo(DataSyncActivity.class, strFileName, true);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private String strFileName;

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

    @Override
    public int initView() {
        return R.layout.activity_mark_file_detail;
    }

    @Override
    public void setListView(List list) {

    }
}

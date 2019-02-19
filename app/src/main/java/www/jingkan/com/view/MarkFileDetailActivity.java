/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import android.view.MenuItem;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityMarkFileDetailBinding;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.dao.WirelessTestDataDao;
import www.jingkan.com.db.entity.WirelessTestDataEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.MyFileUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.view.adapter.MarkFileDetailAdapter;
import www.jingkan.com.view.base.ListMVVMActivity;
import www.jingkan.com.view_model.MarkFileDetailViewModel;

/**
 * Created by lushengbo on 2018/1/16.
 * 标记数据详情页面
 */

public class MarkFileDetailActivity extends ListMVVMActivity<MarkFileDetailViewModel, ActivityMarkFileDetailBinding, MarkFileDetailAdapter> {
    @Inject
    WirelessTestDataDao wirelessTestDataDao;
    @Inject
    WirelessTestDao wirelessTestDao;


    private String strTestID;

    @Override
    protected RecyclerView setRecyclerView() {
        return null;
    }

    @Override
    protected MarkFileDetailAdapter setAdapter() {
        return null;
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("标记数据详情", R.menu.wireless_test);
        strTestID = (String) mData;
        strFileName = strTestID + "W.txt";
        mViewModel.ldWirelessTestEntities.observe(this, wirelessTestEntities -> {
            if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
                WirelessTestEntity wirelessTestEntity = wirelessTestEntities.get(0);
                mViewModel.strProjectNumber.set(wirelessTestEntity.projectNumber);
                mViewModel.strHoleNumber.set(wirelessTestEntity.holeNumber);
                mViewModel.strTestDate.set(wirelessTestEntity.testDate);
            }
        });

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
                wirelessTestDataDao.getWTDEByTestDataId(strTestID).observe(this, wirelessTestDataEntities -> {
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
                });
                return false;
            case R.id.data_syn:
                goTo(DataSyncActivity.class, strFileName, true);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private String strFileName;

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
    public int initView() {
        return R.layout.activity_mark_file_detail;
    }


    @Override
    public void action(CallbackMessage callbackMessage) {

    }

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{
                mData,
                wirelessTestDataDao,
                wirelessTestDao
        };
    }

    @Override
    public MarkFileDetailViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(MarkFileDetailViewModel.class);
    }
}

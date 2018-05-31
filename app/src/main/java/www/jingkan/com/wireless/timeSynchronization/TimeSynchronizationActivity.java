/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.timeSynchronization;

import android.view.MenuItem;

import www.jingkan.com.base.baseMVVM.MVVMDialogActivity;
import www.jingkan.com.databinding.ActivityTimeSynchronizationBinding;
import www.jingkan.com.wireless.test.WirelessTestActivity;

/**
 * Created by lushengbo on 2017/10/17.
 * 时间同步页面
 */

public class TimeSynchronizationActivity extends MVVMDialogActivity<TimeSynchronizationViewModel, ActivityTimeSynchronizationBinding> {

    private String strMac;


    @Override
    protected void setView() {
        String[] strings = (String[]) mData;
        strMac = strings[0];
        setToolBar("时间同步", www.jingkan.com.R.menu.time_synchronization);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case www.jingkan.com.R.id.link:
                mViewModel.linkDevice(strMac);
                return false;
            case www.jingkan.com.R.id.skip://跳过
                if (mViewDataBinding.probeNumber.getText().toString().contains("JK")) {
                    String[] strings = (String[]) mData;
                    goTo(WirelessTestActivity.class, new String[]{
                            strings[0],//蓝牙地址
                            strings[1],//工程编号
                            strings[2],//孔号
                            strings[3],//试验类型
                            mViewModel.obsProbeNumber.get()//探头编号
                    });
                } else {
                    showToast("请填写探头编号");
                }

                return false;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int initView() {
        return www.jingkan.com.R.layout.activity_time_synchronization;
    }

    @Override
    protected TimeSynchronizationViewModel createdViewModel() {
        return new TimeSynchronizationViewModel();
    }

    @Override
    protected void toRefresh() {
        mViewModel.linkDevice(strMac);
    }


}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common;

import android.view.MenuItem;
import android.view.View;

import www.jingkan.com.R;
import www.jingkan.com.chart.DoubleBridgeStrategy;
import www.jingkan.com.common.baseTest.BaseTestActivity;

/**
 * Created by lushengbo on 2018/1/5.
 * 双桥试验类
 */

public class DoubleBridgeTestActivity extends BaseTestActivity {
    @Override
    protected void setView() {
        mViewDataBinding.rlFs.setVisibility(View.VISIBLE);
        mViewDataBinding.rlFsLimit.setVisibility(View.VISIBLE);
        mViewDataBinding.rlFa.setVisibility(View.GONE);
        drawChartHelper.setStrategy(new DoubleBridgeStrategy(this, mViewDataBinding.lineChart));
        super.setView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save://保存数据到sd卡
                showSaveDataDialog();
                return false;
            case R.id.email://发送邮件到指定邮箱
                showEmailDataDialog();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void toRefresh() {
        super.toRefresh();
        setToolBar("双桥试验", R.menu.test);
    }
}

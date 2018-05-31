/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common;

import android.view.MenuItem;
import android.view.View;

import www.jingkan.com.R;
import www.jingkan.com.chart.SingleBridgeStrategy;
import www.jingkan.com.common.baseTest.BaseTestActivity;


/**
 * 试验
 */

public class SingleBridgeTestActivity extends BaseTestActivity {
    @Override
    protected void setView() {
        mViewDataBinding.rlFs.setVisibility(View.GONE);
        mViewDataBinding.fsLimit.setVisibility(View.GONE);
        mViewDataBinding.rlFa.setVisibility(View.GONE);
        drawChartHelper.setStrategy(new SingleBridgeStrategy(this, mViewDataBinding.lineChart));
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
        setToolBar("单桥试验", R.menu.test);
    }

}

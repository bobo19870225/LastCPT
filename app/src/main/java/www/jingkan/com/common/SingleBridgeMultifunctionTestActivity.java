/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common;

import android.view.MenuItem;
import android.view.View;

import www.jingkan.com.R;
import www.jingkan.com.chart.SingleBridgeMuStrategy;
import www.jingkan.com.common.baseTest.BaseTestActivity;

import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_CORRECT_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_CORRECT_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.SINGLE_BRIDGE_MULTI_TEST;

/**
 * Created by lushengbo on 2018/1/5.
 * 单桥多功能试验类
 */

public class SingleBridgeMultifunctionTestActivity extends BaseTestActivity {
    @Override
    protected void setView() {
        mViewDataBinding.rlFs.setVisibility(View.GONE);
        mViewDataBinding.rlFsLimit.setVisibility(View.GONE);
        mViewDataBinding.rlFa.setVisibility(View.VISIBLE);
        drawChartHelper.setStrategy(new SingleBridgeMuStrategy(this, mViewDataBinding.lineChart));
        super.setView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save://保存数据到sd卡
                saveItems = new String[]{SAVE_TYPE_LY_TXT,
                        SAVE_TYPE_LY_DAT,
                        SAVE_TYPE_HN_111,
                        SAVE_TYPE_LZ_TXT,
                        SAVE_TYPE_CORRECT_TXT};
                showSaveDataDialog();
                return false;
            case R.id.email://发送邮件到指定邮箱
                emailItems = new String[]{EMAIL_TYPE_LY_TXT,
                        EMAIL_TYPE_LY_DAT,
                        EMAIL_TYPE_HN_111,
                        EMAIL_TYPE_LZ_TXT,
                        EMAIL_TYPE_CORRECT_TXT};
                showEmailDataDialog();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void toRefresh() {
        super.toRefresh();
        setToolBar(SINGLE_BRIDGE_MULTI_TEST, R.menu.test);
    }


}

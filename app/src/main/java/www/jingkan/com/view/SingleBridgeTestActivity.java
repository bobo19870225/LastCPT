package www.jingkan.com.view;

import android.view.MenuItem;
import android.view.View;

import www.jingkan.com.R;
import www.jingkan.com.view.base.BaseTestActivity;
import www.jingkan.com.view.chart.SingleBridgeStrategy;

/**
 * Created by Sampson on 2018/12/14.
 * CPTTest
 */
public class SingleBridgeTestActivity extends BaseTestActivity {

    @Override
    protected void setMVVMView() {
        setToolBar("单桥试验", R.menu.test);
        mViewDataBinding.rlFs.setVisibility(View.GONE);
        mViewDataBinding.fsLimit.setVisibility(View.GONE);
        mViewDataBinding.ttFaEffectiveValue.setVisibility(View.GONE);
        mViewDataBinding.faEffectiveValue.setVisibility(View.GONE);
        drawChartHelper.setStrategy(new SingleBridgeStrategy(this, mViewDataBinding.lineChart));
        super.setMVVMView();
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

}

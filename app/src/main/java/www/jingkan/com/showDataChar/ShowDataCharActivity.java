package www.jingkan.com.showDataChar;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.BaseMVVMActivity;
import www.jingkan.com.chart.CrossStrategy;
import www.jingkan.com.chart.DoubleBridgeMultifunctionStrategy;
import www.jingkan.com.chart.DoubleBridgeStrategy;
import www.jingkan.com.chart.DrawChartHelper;
import www.jingkan.com.chart.InterfaceDrawChartStrategy;
import www.jingkan.com.chart.SingleBridgeMuStrategy;
import www.jingkan.com.chart.SingleBridgeStrategy;
import www.jingkan.com.databinding.ActivityShowDataCharBinding;
import www.jingkan.com.localData.testData.TestDataModel;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataModel;

import static www.jingkan.com.parameter.SystemConstant.DOUBLE_BRIDGE_MULTI_TEST;
import static www.jingkan.com.parameter.SystemConstant.DOUBLE_BRIDGE_MULTI_WIRELESS_TEST;
import static www.jingkan.com.parameter.SystemConstant.DOUBLE_BRIDGE_TEST;
import static www.jingkan.com.parameter.SystemConstant.SINGLE_BRIDGE_MULTI_TEST;
import static www.jingkan.com.parameter.SystemConstant.SINGLE_BRIDGE_MULTI_WIRELESS_TEST;
import static www.jingkan.com.parameter.SystemConstant.SINGLE_BRIDGE_TEST;
import static www.jingkan.com.parameter.SystemConstant.VANE_TEST;

/**
 * Created by Sampson on 2018/3/12.
 * LastCPT
 */

public class ShowDataCharActivity extends BaseMVVMActivity<ShowDataCharViewModel, ActivityShowDataCharBinding> {


    @Override
    protected void setView() {
        setToolBar("显示曲线", R.menu.refresh);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                mViewModel.refreshTestData();
                return false;
            case R.id.ok:
                InterfaceDrawChartStrategy strategy = drawChartHelper.getStrategy();
                mViewModel.modifyTestDataBase(strategy.getSeriesQcData(),
                        strategy.getSeriesFsData(),
                        strategy.getSeriesFaData());
                return false;
        }
        return super.onOptionsItemSelected(item);

    }

    protected DrawChartHelper drawChartHelper;

    @Override
    public int initView() {
        drawChartHelper = new DrawChartHelper();
        return R.layout.activity_show_data_char;
    }

    @Override
    protected ShowDataCharViewModel createdViewModel() {
        return new ShowDataCharViewModel();
    }

    /**
     * 显示图表
     *
     * @param testDataModels 试验数据
     */
    @SuppressWarnings("unchecked")
    public void showDataInTheChar(List testDataModels) {
        List<float[]> listPoints = new ArrayList<>();
        if (testDataModels.get(0) instanceof TestDataModel) {
            for (TestDataModel testDataModel : (List<TestDataModel>) testDataModels) {
                listPoints.add(new float[]{testDataModel.qc,
                        testDataModel.fs,
                        testDataModel.fa,
                        testDataModel.deep});
            }
        } else if (testDataModels.get(0) instanceof WirelessResultDataModel) {
            for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) testDataModels) {
                listPoints.add(new float[]{wirelessResultDataModel.qc,
                        wirelessResultDataModel.fs,
                        wirelessResultDataModel.fa,
                        wirelessResultDataModel.deep});
            }
        }

        drawChartHelper.addPointsToChart(listPoints);
    }

    /**
     * 设置绘图策略
     *
     * @param testType 试验类型
     */
    public void setCharStrategy(String testType) {
        switch (testType) {
            case SINGLE_BRIDGE_TEST:
                drawChartHelper.setStrategy(new SingleBridgeStrategy(this, mViewDataBinding.lineChart));
                break;
            case SINGLE_BRIDGE_MULTI_TEST:
            case SINGLE_BRIDGE_MULTI_WIRELESS_TEST:
                drawChartHelper.setStrategy(new SingleBridgeMuStrategy(this, mViewDataBinding.lineChart));
                break;
            case DOUBLE_BRIDGE_TEST:
                drawChartHelper.setStrategy(new DoubleBridgeStrategy(this, mViewDataBinding.lineChart));
                break;
            case DOUBLE_BRIDGE_MULTI_TEST:
            case DOUBLE_BRIDGE_MULTI_WIRELESS_TEST:
                drawChartHelper.setStrategy(new DoubleBridgeMultifunctionStrategy(this, mViewDataBinding.lineChart));
                break;
            case VANE_TEST:
                drawChartHelper.setStrategy(new CrossStrategy(this, mViewDataBinding.lineChart));
                break;
        }
    }
}

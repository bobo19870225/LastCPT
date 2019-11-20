package www.jingkan.com.view;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityShowDataCharBinding;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.dao.TestDataDao;
import www.jingkan.com.db.dao.WirelessResultDataDao;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.entity.TestDataEntity;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.db.entity.WirelessResultDataEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view.chart.CrossStrategy;
import www.jingkan.com.view.chart.DoubleBridgeMultifunctionStrategy;
import www.jingkan.com.view.chart.DoubleBridgeStrategy;
import www.jingkan.com.view.chart.DrawChartHelper;
import www.jingkan.com.view.chart.InterfaceDrawChartStrategy;
import www.jingkan.com.view.chart.SingleBridgeMuStrategy;
import www.jingkan.com.view.chart.SingleBridgeStrategy;
import www.jingkan.com.view_model.ShowDataCharViewModel;

import static www.jingkan.com.util.SystemConstant.DOUBLE_BRIDGE_MULTI_TEST;
import static www.jingkan.com.util.SystemConstant.DOUBLE_BRIDGE_MULTI_WIRELESS_TEST;
import static www.jingkan.com.util.SystemConstant.DOUBLE_BRIDGE_TEST;
import static www.jingkan.com.util.SystemConstant.SINGLE_BRIDGE_MULTI_TEST;
import static www.jingkan.com.util.SystemConstant.SINGLE_BRIDGE_MULTI_WIRELESS_TEST;
import static www.jingkan.com.util.SystemConstant.SINGLE_BRIDGE_TEST;
import static www.jingkan.com.util.SystemConstant.VANE_TEST;

/**
 * Created by Sampson on 2018/3/12.
 * LastCPT
 */

public class ShowDataCharActivity extends BaseMVVMDaggerActivity<ShowDataCharViewModel, ActivityShowDataCharBinding> {
    @Inject
    DrawChartHelper drawChartHelper;
    @Inject
    TestDao testDao;
    @Inject
    TestDataDao testDataData;
    @Inject
    WirelessTestDao wirelessTestDao;
    @Inject
    WirelessResultDataDao wirelessResultDataDao;

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{
                mData,
                drawChartHelper,
                testDao,
                testDataData,
                wirelessTestDao,
                wirelessResultDataDao
        };
    }

    @Override
    protected void setMVVMView() {
//        setToolBar("显示曲线", R.menu.refresh);
        setToolBar("显示曲线");
        mViewModel.ldTestEntities.observe(this, testEntities -> {
            if (testEntities != null && !testEntities.isEmpty()) {
                TestEntity testEntity = testEntities.get(0);
                mViewModel.projectNumber.set(testEntity.projectNumber);
                mViewModel.holeNumber.set(testEntity.holeNumber);
                mViewModel.testDate.set(testEntity.testDate);
                mViewModel.strOperators.set(testEntity.tester);
                setCharStrategy(testEntity.testType);
            }
        });
        mViewModel.ldTestDataEntities.observe(this, testDataEntities -> {
            if (testDataEntities != null && !testDataEntities.isEmpty()) {
                showDataInTheChar(testDataEntities);
            }
        });
        mViewModel.ldWirelessTestEntities.observe(this, wirelessTestEntities -> {
            if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
                WirelessTestEntity wirelessTestEntity = wirelessTestEntities.get(0);
                mViewModel.projectNumber.set(wirelessTestEntity.projectNumber);
                mViewModel.holeNumber.set(wirelessTestEntity.holeNumber);
                mViewModel.testDate.set(wirelessTestEntity.testDate);
                mViewModel.strOperators.set(wirelessTestEntity.tester);
                setCharStrategy(wirelessTestEntity.testType);
            }
        });
        mViewModel.ldWirelessResultDataEntities.observe(this, wirelessResultDataEntities -> {
            if (wirelessResultDataEntities != null && !wirelessResultDataEntities.isEmpty()) {
                showDataInTheChar(wirelessResultDataEntities);
            }
        });
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


    @Override
    public int initView() {
        return R.layout.activity_show_data_char;
    }

    @Override
    public ShowDataCharViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(ShowDataCharViewModel.class);
    }

    /**
     * 显示图表
     *
     * @param testDataModels 试验数据
     */
    @SuppressWarnings("unchecked")
    public void showDataInTheChar(List testDataModels) {
        List<float[]> listPoints = new ArrayList<>();
        if (testDataModels.get(0) instanceof TestDataEntity) {
            for (TestDataEntity testDataModel : (List<TestDataEntity>) testDataModels) {
                listPoints.add(new float[]{testDataModel.qc,
                        testDataModel.fs,
                        testDataModel.fa,
                        testDataModel.deep});
            }
        } else if (testDataModels.get(0) instanceof WirelessResultDataEntity) {
            for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) testDataModels) {
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

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

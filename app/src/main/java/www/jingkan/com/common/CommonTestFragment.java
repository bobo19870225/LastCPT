/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.activeandroid.Model;

import java.util.List;
import java.util.Map;

import www.jingkan.com.R;
import www.jingkan.com.activity.NewTestActivity;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseFragment;
import www.jingkan.com.common.commonProbe.CommonProbeActivity;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.common.historyData.HistoryDataActivity;
import www.jingkan.com.linkBluetooth.LinkBluetoothActivity;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.test.TestData;
import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.parameter.SystemConstant;
import www.jingkan.com.common.crossTest.CrossTestActivity;


public class CommonTestFragment extends BaseFragment {
    @BindView(id = R.id.history_data, click = true)
    private RelativeLayout history_data;
    @BindView(id = R.id.new_test, click = true)
    private RelativeLayout new_test;
    @BindView(id = R.id.test_again, click = true)
    private RelativeLayout test_again;
    @BindView(id = R.id.common_probe_list, click = true)
    private RelativeLayout common_probe_list;


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_common_test, container, false);
    }

    @Override
    protected void setView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_test:
                goTo(NewTestActivity.class, null);
                break;
            case R.id.test_again:
                TestData testData = DataFactory.getBaseData(TestData.class);
                testData.getData(new DataLoadCallBack() {
                    @Override
                    public <T extends Model> void onDataLoaded(List<T> models) {
                        PreferencesUtils preferencesUtils = new PreferencesUtils(getContext());
                        Map<String, String> linkerPreferences = preferencesUtils.getLinkerPreferences();
                        String add = linkerPreferences.get("add");
                        TestModel testModel = (TestModel) models.get(0);
                        if (StringUtils.isEmpty(add)) {
                            goTo(LinkBluetoothActivity.class, new String[]{testModel.projectNumber, testModel.holeNumber, testModel.testType});
                        } else {//mac地址，工程编号，孔号，试验类型。
                            switch (testModel.testType) {
                                case SystemConstant.SINGLE_BRIDGE_TEST:
                                    goTo(SingleBridgeTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
                                    break;
                                case SystemConstant.SINGLE_BRIDGE_MULTI_TEST:
                                    goTo(SingleBridgeMultifunctionTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
                                    break;
                                case SystemConstant.DOUBLE_BRIDGE_TEST:
                                    goTo(DoubleBridgeTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
                                    break;
                                case SystemConstant.DOUBLE_BRIDGE_MULTI_TEST:
                                    goTo(DoubleBridgeMultifunctionTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
                                    break;
                                case SystemConstant.VANE_TEST:
                                    goTo(CrossTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
                                    break;
                            }

                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        showToast("暂无可进行二次测量的试验");
                    }
                });
                break;
            case R.id.history_data:
                goTo(HistoryDataActivity.class, null);
                break;
            case R.id.common_probe_list:
                goTo(CommonProbeActivity.class, null);
                break;
        }

    }

}




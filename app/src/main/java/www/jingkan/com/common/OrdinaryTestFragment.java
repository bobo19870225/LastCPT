/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import www.jingkan.com.R;
import www.jingkan.com.activity.NewTestActivity;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseFragment;
import www.jingkan.com.common.commonProbe.CommonProbeActivity;
import www.jingkan.com.common.crossTest.CrossTestActivity;
import www.jingkan.com.common.historyData.HistoryDataActivity;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.linkBluetooth.LinkBluetoothActivity;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.test.TestDaoForRoom;
import www.jingkan.com.localData.test.TestEntity;
import www.jingkan.com.parameter.SystemConstant;


public class OrdinaryTestFragment extends BaseFragment {
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
                showChooseDialog();

                break;
            case R.id.test_again:
                TestDaoForRoom testDaoForRoom = AppDatabase.getInstance(getContext()).testDaoForRoom();
                LiveData<List<TestEntity>> liveData = testDaoForRoom.getAllTestes();
                List<TestEntity> testEntities = liveData.getValue();
                if (testEntities != null && !testEntities.isEmpty()) {
                    PreferencesUtils preferencesUtils = new PreferencesUtils(getContext());
                    Map<String, String> linkerPreferences = preferencesUtils.getLinkerPreferences();
                    String add = linkerPreferences.get("add");
                    TestEntity testModel = testEntities.get(0);
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
                } else {
                    showToast("暂无可进行二次测量的试验");
                }

//                TestDao testData = DataFactory.getBaseData(TestDao.class);
//                testData.getData(new DataLoadCallBack<TestEntity>() {
//
//                    @Override
//                    public void onDataLoaded(List<TestEntity> models) {
//                        PreferencesUtils preferencesUtils = new PreferencesUtils(getContext());
//                        Map<String, String> linkerPreferences = preferencesUtils.getLinkerPreferences();
//                        String add = linkerPreferences.get("add");
//                        TestEntity testModel = models.get(0);
//                        if (StringUtils.isEmpty(add)) {
//                            goTo(LinkBluetoothActivity.class, new String[]{testModel.projectNumber, testModel.holeNumber, testModel.testType});
//                        } else {//mac地址，工程编号，孔号，试验类型。
//                            switch (testModel.testType) {
//                                case SystemConstant.SINGLE_BRIDGE_TEST:
//                                    goTo(SingleBridgeTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
//                                    break;
//                                case SystemConstant.SINGLE_BRIDGE_MULTI_TEST:
//                                    goTo(SingleBridgeMultifunctionTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
//                                    break;
//                                case SystemConstant.DOUBLE_BRIDGE_TEST:
//                                    goTo(DoubleBridgeTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
//                                    break;
//                                case SystemConstant.DOUBLE_BRIDGE_MULTI_TEST:
//                                    goTo(DoubleBridgeMultifunctionTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
//                                    break;
//                                case SystemConstant.VANE_TEST:
//                                    goTo(CrossTestActivity.class, new String[]{add, testModel.projectNumber, testModel.holeNumber});
//                                    break;
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onDataNotAvailable() {
//                        showToast("暂无可进行二次测量的试验");
//                    }
//                });
                break;
            case R.id.history_data:
                goTo(HistoryDataActivity.class, null);
                break;
            case R.id.common_probe_list:
                goTo(CommonProbeActivity.class, null);
                break;
        }

    }

    private int probeType;

    private void showChooseDialog() {

        CharSequence[] saveItems = new CharSequence[]{"数字探头", "模拟探头"};

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("请选择要使用的探头类型")
                .setSingleChoiceItems(saveItems, 0, (dialog, which) -> probeType = which)
                .setPositiveButton("确定", (dialog, which) -> goTo(NewTestActivity.class, saveItems[probeType]))
                .setNegativeButton("取消", (dialog, which) -> {
                    probeType = 0;
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }

}




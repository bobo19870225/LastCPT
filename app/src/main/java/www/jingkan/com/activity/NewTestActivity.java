/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.activity;

import android.Manifest;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import www.jingkan.com.R;
import www.jingkan.com.adapter.OneTextListAdapter;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.common.DoubleBridgeMultifunctionTestActivity;
import www.jingkan.com.common.DoubleBridgeTestActivity;
import www.jingkan.com.common.SingleBridgeMultifunctionTestActivity;
import www.jingkan.com.common.SingleBridgeTestActivity;
import www.jingkan.com.common.crossTest.CrossTestActivity;
import www.jingkan.com.framework.acp.Acp;
import www.jingkan.com.framework.acp.AcpListener;
import www.jingkan.com.framework.acp.AcpOptions;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.framework.utils.TimeUtils;
import www.jingkan.com.linkBluetooth.LinkBluetoothActivity;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.test.TestDaoForRoom;
import www.jingkan.com.localData.test.TestEntity;
import www.jingkan.com.localData.wirelessTest.WirelessTestDaoForRoom;
import www.jingkan.com.localData.wirelessTest.WirelessTestEntity;
import www.jingkan.com.parameter.SystemConstant;
import www.jingkan.com.wireless.timeSynchronization.TimeSynchronizationActivity;

import static www.jingkan.com.parameter.SystemConstant.DOUBLE_BRIDGE_MULTI_TEST;
import static www.jingkan.com.parameter.SystemConstant.DOUBLE_BRIDGE_TEST;
import static www.jingkan.com.parameter.SystemConstant.GET_LOCATION;
import static www.jingkan.com.parameter.SystemConstant.LOCATION;
import static www.jingkan.com.parameter.SystemConstant.SINGLE_BRIDGE_MULTI_TEST;
import static www.jingkan.com.parameter.SystemConstant.SINGLE_BRIDGE_TEST;
import static www.jingkan.com.parameter.SystemConstant.VANE_TEST;

/**
 * 新增试验页面
 * 传入一个参数，判断是否为无缆试验。
 */

public class NewTestActivity extends BaseActivity {
    @BindView(id = R.id.project_number)
    private EditText project_number;
    @BindView(id = R.id.hole_number)
    private EditText hole_number;
    @BindView(id = R.id.hole_high)
    private EditText hole_high;
    @BindView(id = R.id.water_level)
    private EditText water_level;
    @BindView(id = R.id.location)
    private EditText location;
    @BindView(id = R.id.tester)
    private EditText tester;
    @BindView(id = R.id.test_type)
    private TextView test_type;
    @BindView(id = R.id.chose_type, click = true)
    private RelativeLayout chose_type;
    @BindView(id = R.id.new_test, click = true)
    private Button new_test;
    @BindView(id = R.id.img_location, click = true)
    private Button img_location;
    private PopupWindow popupWindow;
    private boolean isWireless = false;
    private boolean isAnalog = false;
    private TestDaoForRoom testDaoForRoom = AppDatabase.getInstance(getApplicationContext()).testDaoForRoom();
    private WirelessTestDaoForRoom wirelessTestDaoForRoom = AppDatabase.getInstance(getApplicationContext()).wirelessTestDaoForRoom();
//    private TestDao testData = DataFactory.getBaseData(TestDao.class);
//    private WirelessTestDao wirelessTestDao = DataFactory.getBaseData(WirelessTestDao.class);

    @Override
    protected void setView() {
        setToolBar("新增试验");
        if (mData != null) {
            if (mData.equals("无缆试验"))
                isWireless = true;
            if (mData.equals("模拟探头"))
                isAnalog = true;

        }
    }

    @Override
    public int initView() {
        return R.layout.activity_new_test;
    }


    private void PopupWindowShow() {
        View contentView = getLayoutInflater().inflate(R.layout.theo, null);
        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        // 点击消失属性
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        if (isWireless) {
            String[] s = {SystemConstant.SINGLE_BRIDGE_MULTI_WIRELESS_TEST, SystemConstant.DOUBLE_BRIDGE_MULTI_WIRELESS_TEST};
            List<String> ls = new ArrayList<>();
            ls.add(s[0]);
            ls.add(s[1]);
            OneTextListAdapter adapter = new OneTextListAdapter(NewTestActivity.this, R.layout.listitem, ls);
            ListView listView = contentView.findViewById(R.id.lv_item);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {

                TextView tv_item = view.findViewById(R.id.TextView);
                test_type.setText(tv_item.getText());
                popupWindow.dismiss();
            });
        } else {
            String[] s = {SINGLE_BRIDGE_TEST, SINGLE_BRIDGE_MULTI_TEST, DOUBLE_BRIDGE_TEST, DOUBLE_BRIDGE_MULTI_TEST, VANE_TEST};
            List<String> ls = new ArrayList<>();
            ls.add(s[0]);
            ls.add(s[1]);
            ls.add(s[2]);
            ls.add(s[3]);
            ls.add(s[4]);
            OneTextListAdapter adapter = new OneTextListAdapter(NewTestActivity.this, R.layout.listitem, ls);
            ListView listView = contentView.findViewById(R.id.lv_item);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {

                TextView tv_item = view.findViewById(R.id.TextView);
                test_type.setText(tv_item.getText());
                popupWindow.dismiss();
            });
        }
        contentView.findViewById(R.id.cancel).setOnClickListener(view -> popupWindow.dismiss());
        // 显示在屏幕上
        popupWindow.showAtLocation(test_type, Gravity.CENTER, 0, 0);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chose_type:
                PopupWindowShow();
                break;
            case R.id.new_test:
                newOneTest();
                break;
            case R.id.img_location:
                Acp.getInstance(this).request(new AcpOptions.Builder()
                                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                                .build(),
                        new AcpListener() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent();
                                intent.setClass(getApplicationContext(), BasicMapActivity.class);
                                startActivityForResult(intent, GET_LOCATION);
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                showToast(permissions.toString() + "权限拒绝");
                            }
                        });

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_LOCATION && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                double[] doubleArray = extras.getDoubleArray(LOCATION);
                if (doubleArray != null) {
                    String strLocation = String.valueOf(doubleArray[0]) + String.valueOf(doubleArray[1]);
                    location.setText(strLocation);
                }
            }
        }
    }


    private void newOneTest() {
        final String strProjectNumber = String.valueOf(project_number.getText());
        if (StringUtils.isEmpty(strProjectNumber)) {
            showToast("工程编号不能为空");
            return;
        }
        final String strHoleNumber = String.valueOf(hole_number.getText());
        if (StringUtils.isEmpty(strHoleNumber)) {
            showToast("孔号不能为空");
            return;
        }
        if (StringUtils.isEmpty(tester.getText())) {
            showToast("测试员工不能为空");
            return;
        }
        final String strTestType = String.valueOf(test_type.getText());
        if (StringUtils.isEmpty(strTestType)) {
            showToast("实验类型不能为空");
            return;
        }
        PreferencesUtils preferencesUtils = new PreferencesUtils(getApplicationContext());
        Map<String, String> linkerPreferences = preferencesUtils.getLinkerPreferences();
        final String add = linkerPreferences.get("add");
        if (isWireless) {
            LiveData<List<WirelessTestEntity>> liveDataWirelessTest = wirelessTestDaoForRoom.getWirelessTestEntityByPrjNumberAndHoleNumber(strProjectNumber, strHoleNumber);
            List<WirelessTestEntity> wirelessTestEntities = liveDataWirelessTest.getValue();
            if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
                showToast("该试验已经存在，请更换工程编号或孔号");
            } else {
                WirelessTestEntity wirelessTestEntity = new WirelessTestEntity();
                wirelessTestEntity.testID = strProjectNumber + "_" + strHoleNumber;
                wirelessTestEntity.testDate = TimeUtils.getCurrentTime();
                wirelessTestEntity.projectNumber = strProjectNumber;
                wirelessTestEntity.holeNumber = strHoleNumber;
                String strHoleHigh = hole_high.getText().toString();
                if (StringUtils.isFloat(strHoleHigh)) {
                    wirelessTestEntity.holeHigh = Float.parseFloat(strHoleHigh);
                }
                String strWaterLevel = water_level.getText().toString();
                if (StringUtils.isFloat(strWaterLevel)) {
                    wirelessTestEntity.waterLevel = Float.parseFloat(strWaterLevel);
                }
                wirelessTestEntity.location = location.getText().toString();
                wirelessTestEntity.tester = tester.getText().toString();
                wirelessTestEntity.testType = String.valueOf(test_type.getText());
                wirelessTestEntity.testDataID = strProjectNumber + "_" + strHoleNumber;
                wirelessTestDaoForRoom.insertWirelessTestEntity(wirelessTestEntity);

                if (StringUtils.isEmpty(add)) {
                    goTo(LinkBluetoothActivity.class, new String[]{strProjectNumber, strHoleNumber, strTestType});
                } else {
                    //如果是无缆试验，先进行时间同步。
                    goTo(TimeSynchronizationActivity.class, new String[]{add, strProjectNumber, strHoleNumber, strTestType});
                }
            }

//            wirelessTestDao.getData(new DataLoadCallBack<WirelessTestModel>() {
//                @Override
//                public void onDataLoaded(List models) {
//                    showToast("该试验已经存在，请更换工程编号或孔号");
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    WirelessTestModel wirelessTestModel = new WirelessTestModel();
//                    wirelessTestModel.testID = strProjectNumber + "_" + strHoleNumber;
//                    wirelessTestModel.testDate = TimeUtils.getCurrentTime();
//                    wirelessTestModel.projectNumber = strProjectNumber;
//                    wirelessTestModel.holeNumber = strHoleNumber;
//                    String strHoleHigh = hole_high.getText().toString();
//                    if (StringUtils.isFloat(strHoleHigh)) {
//                        wirelessTestModel.holeHigh = Float.parseFloat(strHoleHigh);
//                    }
//                    String strWaterLevel = water_level.getText().toString();
//                    if (StringUtils.isFloat(strWaterLevel)) {
//                        wirelessTestModel.waterLevel = Float.parseFloat(strWaterLevel);
//                    }
//                    wirelessTestModel.location = location.getText().toString();
//                    wirelessTestModel.tester = tester.getText().toString();
//                    wirelessTestModel.testType = String.valueOf(test_type.getText());
//                    wirelessTestModel.testDataID = strProjectNumber + "_" + strHoleNumber;
//                    wirelessTestDao.addData(wirelessTestModel);
//
//                    if (StringUtils.isEmpty(add)) {
//                        goTo(LinkBluetoothActivity.class, new String[]{strProjectNumber, strHoleNumber, strTestType});
//                    } else {
//                        //如果是无缆试验，先进行时间同步。
//                        goTo(TimeSynchronizationActivity.class, new String[]{add, strProjectNumber, strHoleNumber, strTestType});
//                    }
//                }
//            }, strProjectNumber, strHoleNumber);

        } else {
            LiveData<List<TestEntity>> liveDataTest = testDaoForRoom.getTestEntityByPrjNumberAndHoleNumber(strProjectNumber, strHoleNumber);
            List<TestEntity> testEntities = liveDataTest.getValue();
            if (testEntities != null && !testEntities.isEmpty()) {
                showToast("该试验已经存在，请更换工程编号或孔号");
            } else {//入库操作
                TestEntity testEntity = new TestEntity();
                testEntity.testID = strProjectNumber + "_" + strHoleNumber;
                testEntity.testDate = TimeUtils.getCurrentTime();
                testEntity.projectNumber = strProjectNumber;
                testEntity.holeNumber = strHoleNumber;
                String strHoleHigh = hole_high.getText().toString();
                if (StringUtils.isFloat(strHoleHigh)) {
                    testEntity.holeHigh = Float.parseFloat(strHoleHigh);
                }
                String strWaterLevel = water_level.getText().toString();
                if (StringUtils.isFloat(strWaterLevel)) {
                    testEntity.waterLevel = Float.parseFloat(strWaterLevel);
                }
                testEntity.location = location.getText().toString();
                testEntity.tester = tester.getText().toString();
                testEntity.testType = String.valueOf(test_type.getText());
                testEntity.testProbeType = isAnalog ? "模拟探头" : "数字探头";
                testEntity.testDataID = strProjectNumber + "_" + strHoleNumber;
                testDaoForRoom.insertTestEntity(testEntity);
                if (StringUtils.isEmpty(add)) {
                    goTo(LinkBluetoothActivity.class, new String[]{strProjectNumber, strHoleNumber, strTestType, isAnalog ? "模拟探头" : "数字探头"});
                } else {
                    String[] dataToSend = {add, testEntity.projectNumber, testEntity.holeNumber, isAnalog ? "模拟探头" : "数字探头"};
                    switch (testEntity.testType) {
                        case SINGLE_BRIDGE_TEST:
                            //mac地址，工程编号，孔号。
                            goTo(SingleBridgeTestActivity.class, dataToSend);
                            break;
                        case SINGLE_BRIDGE_MULTI_TEST:
                            goTo(SingleBridgeMultifunctionTestActivity.class, dataToSend);
                            break;
                        case DOUBLE_BRIDGE_TEST:
                            goTo(DoubleBridgeTestActivity.class, dataToSend);
                            break;
                        case DOUBLE_BRIDGE_MULTI_TEST:
                            goTo(DoubleBridgeMultifunctionTestActivity.class, dataToSend);
                            break;
                        case SystemConstant.VANE_TEST:
                            goTo(CrossTestActivity.class, dataToSend);
                            break;

                    }
                }
            }

//            testData.getData(new DataLoadCallBack<TestModel>() {
//                @Override
//                public void onDataLoaded(List models) {
//                    showToast("该试验已经存在，请更换工程编号或孔号");
//                }
//
//                @Override
//                public void onDataNotAvailable() {//入库操作
//                    TestModel testModel = new TestModel();
//                    testModel.testID = strProjectNumber + "_" + strHoleNumber;
//                    testModel.testDate = TimeUtils.getCurrentTime();
//                    testModel.projectNumber = strProjectNumber;
//                    testModel.holeNumber = strHoleNumber;
//                    String strHoleHigh = hole_high.getText().toString();
//                    if (StringUtils.isFloat(strHoleHigh)) {
//                        testModel.holeHigh = Float.parseFloat(strHoleHigh);
//                    }
//                    String strWaterLevel = water_level.getText().toString();
//                    if (StringUtils.isFloat(strWaterLevel)) {
//                        testModel.waterLevel = Float.parseFloat(strWaterLevel);
//                    }
//                    testModel.location = location.getText().toString();
//                    testModel.tester = tester.getText().toString();
//                    testModel.testType = String.valueOf(test_type.getText());
//                    testModel.testProbeType = isAnalog ? "模拟探头" : "数字探头";
//                    testModel.testDataID = strProjectNumber + "_" + strHoleNumber;
//                    testData.addData(testModel);
//                    if (StringUtils.isEmpty(add)) {
//                        goTo(LinkBluetoothActivity.class, new String[]{strProjectNumber, strHoleNumber, strTestType, isAnalog ? "模拟探头" : "数字探头"});
//                    } else {
//                        String[] dataToSend = {add, testModel.projectNumber, testModel.holeNumber, isAnalog ? "模拟探头" : "数字探头"};
//                        switch (testModel.testType) {
//                            case SINGLE_BRIDGE_TEST:
//                                //mac地址，工程编号，孔号。
//                                goTo(SingleBridgeTestActivity.class, dataToSend);
//                                break;
//                            case SINGLE_BRIDGE_MULTI_TEST:
//                                goTo(SingleBridgeMultifunctionTestActivity.class, dataToSend);
//                                break;
//                            case DOUBLE_BRIDGE_TEST:
//                                goTo(DoubleBridgeTestActivity.class, dataToSend);
//                                break;
//                            case DOUBLE_BRIDGE_MULTI_TEST:
//                                goTo(DoubleBridgeMultifunctionTestActivity.class, dataToSend);
//                                break;
//                            case SystemConstant.VANE_TEST:
//                                goTo(CrossTestActivity.class, dataToSend);
//                                break;
//
//                        }
//                    }
//
//                }
//            }, strProjectNumber, strHoleNumber);
        }

    }


}

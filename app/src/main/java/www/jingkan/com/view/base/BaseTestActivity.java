/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityBaseTestBinding;
import www.jingkan.com.db.dao.ProbeDao;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.dao.TestDataDao;
import www.jingkan.com.db.dao.TestDataDaoHelper;
import www.jingkan.com.db.entity.ProbeEntity;
import www.jingkan.com.db.entity.TestDataEntity;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.DataUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.util.VibratorUtil;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view.chart.DrawChartHelper;
import www.jingkan.com.view_model.BaseTestViewModel;
import www.jingkan.com.view_model.ISkip;

import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_ZHD_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LZ_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_ZHD_TXT;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.MESSAGE_READ;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.MESSAGE_STATE_CHANGE;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECTED;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECTING;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECT_FAILED;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECT_LOST;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_LISTEN;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_NONE;


/**
 * 试验基类
 */


@SuppressLint("Registered")
public class BaseTestActivity extends DialogMVVMDaggerActivity<BaseTestViewModel, ActivityBaseTestBinding> implements ISkip {
    @Inject
    protected
    DrawChartHelper drawChartHelper;
    @Inject
    TestDao testDao;
    @Inject
    TestDataDao testDataDao;
    @Inject
    TestDataDaoHelper testDataDaoHelper;
    @Inject
    ProbeDao probeDao;
    @Inject
    DataUtil dataUtil;
    @Inject
    VibratorUtil vibratorUtil;
    @Inject
    BluetoothUtil bluetoothUtil;
    @Inject
    BluetoothCommService bluetoothCommService;

    protected String strProjectNumber;
    protected String strHoleNumber;
    private String mac;
    private TestEntity testEntity;
    private String fileType;
    protected String[] emailItems = {
            EMAIL_TYPE_ZHD_TXT,
            EMAIL_TYPE_LY_TXT,
            EMAIL_TYPE_LY_DAT,
            EMAIL_TYPE_HN_111,
            EMAIL_TYPE_LZ_TXT
    };
    protected String[] saveItems = {
            SAVE_TYPE_ZHD_TXT,
            SAVE_TYPE_LY_TXT,
            SAVE_TYPE_LY_DAT,
            SAVE_TYPE_HN_111,
            SAVE_TYPE_LZ_TXT
    };

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{
                mData,
                testDataDao,
                testDataDaoHelper,
                probeDao,
                vibratorUtil,
                bluetoothUtil,
                bluetoothCommService,
                this
        };
    }

    @Override
    protected void setMVVMView() {
        String[] strings = (String[]) mData;//1.mac,2.工程编号,3.孔号,4.试验类型
        mac = strings[0];
        strProjectNumber = strings[1];
        strHoleNumber = strings[2];
        mViewModel.linkDevice(mac);
        mViewModel.getTestParameters(testDao, strProjectNumber, strHoleNumber)
                .observe(this, testEntities -> {
                    if (testEntities != null && !testEntities.isEmpty()) {
                        testEntity = testEntities.get(0);
                        mViewModel.setTestEntity(testEntity);
                        mViewModel.obsProjectNumber.set(testEntity.projectNumber);
                        mViewModel.obsHoleNumber.set(testEntity.holeNumber);
                    }

                });
        mViewModel.ldTestDataEntity.observe(this, testDataEntities -> {
            mViewModel.ldTestDataEntity.removeObservers(this);
            if (testDataEntities != null && !testDataEntities.isEmpty()) {
                showTestData(testDataEntities);
                mViewModel.obsTestDeep.set(testDataEntities.get(testDataEntities.size() - 1).deep);
            }
        });

        mViewModel.loadProbe.observe(this, probeEntities -> {
            if (probeEntities != null && !probeEntities.isEmpty()) {
                ProbeEntity probeModel = probeEntities.get(0);
                mViewModel.obsProbeNumber.set(probeModel.number);
                mViewModel.obsQcCoefficient.set(String.valueOf(probeModel.qc_coefficient));
                mViewModel.obsQcLimit.set(String.valueOf(probeModel.qc_limit));
                mViewModel.obsFsCoefficient.set(String.valueOf(probeModel.fs_coefficient));
                mViewModel.obsFsLimit.set(String.valueOf(probeModel.fs_limit));
            } else {
                showToast("该探头未添加到探头列表中，暂时不能使用，请在探头列表里添加该探头");
            }

        });
        bluetoothCommService.getBluetoothMessageMutableLiveData().observe(this, bluetoothMessage -> {
            switch (bluetoothMessage.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (bluetoothMessage.arg1) {
                        case STATE_NONE:
                            break;
                        case STATE_LISTEN:// 监听连接
                            break;
                        case STATE_CONNECTING: // now initiating an outgoing connection
                            showToast("正在连接");
                            break;
                        case STATE_CONNECTED:   // 已连接上远程设备
                            closeWaitDialog();
                            showToast("连接成功");
                            break;
                        case STATE_CONNECT_FAILED: // 连接失败
                            closeWaitDialog();
                            showToast("连接失败");
                            break;
                        case STATE_CONNECT_LOST: // 失去连接
                            showToast("失去连接");
                            break;
                    }
                    break;
                case MESSAGE_READ:
                    byte[] b = (byte[]) bluetoothMessage.obj;
                    String mDate = new String(b);
                    if (mDate.length() > 40) {
                        if (mDate.contains("\r")) {
                            mDate = mDate.substring(0, mDate.indexOf("\r"));
                        }
                        mDate = mDate.replace(" ", "");
                        if (mDate.contains("Sn:")) {
                            String sn = mDate.substring(mDate.indexOf("Sn:") + 3, mDate.indexOf("Sn:") + 11);
                            mViewModel.identificationProbe(sn);
                            Float qcInitialValue = mViewModel.obsQcInitialValue.get();
                            if (qcInitialValue != null) {
                                mViewModel.obsQcEffectiveValue.set(mViewModel.getQcEffectiveValue(mDate, qcInitialValue));
                            }
                            Float fsInitialValue = mViewModel.obsFsInitialValue.get();
                            if (fsInitialValue != null)
                                mViewModel.obsFsEffectiveValue.set(mViewModel.getFsEffectiveValue(mDate, fsInitialValue));
                            mViewModel.obsFaEffectiveValue.set(mViewModel.getFaEffectiveValue(mDate));
                        }
                    }
                    break;
            }
        });
        mViewModel.recordValue.observe(this, floats -> drawChartHelper.addOnePointToChart(floats));


    }


    @Override
    public int initView() {
        return R.layout.activity_base_test;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                mViewModel.linkDevice(mac);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void showEmailDataDialog() {
        fileType = EMAIL_TYPE_ZHD_TXT;
        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择发送的数据类型")
                .setSingleChoiceItems(emailItems, 0, (dialog, which) -> fileType = emailItems[which])
                .setPositiveButton("确定", (dialog, which) -> {
                    mViewModel.saveTestDataToSD()
                            .observe(this, testDataEntities -> {
                                if (testDataEntities != null && !testDataEntities.isEmpty()) {
//                                    mModels = testDataEntities;
                                    dataUtil.saveDataToSd(testDataEntities, fileType, testEntity, this);
                                } else {
                                    showToast("读取数据失败！");
                                }
                            });
                    mViewModel.emailTestData(fileType, dataUtil);
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    fileType = emailItems[0];
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }


    protected void showSaveDataDialog() {
        fileType = SAVE_TYPE_ZHD_TXT;
        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择要保存的数据类型")
                .setSingleChoiceItems(saveItems, 0, (dialog, which) -> fileType = saveItems[which])
                .setPositiveButton("确定", (dialog, which) ->

                        mViewModel.saveTestDataToSD()
                                .observe(this, testDataEntities -> {
                                    if (testDataEntities != null && !testDataEntities.isEmpty()) {
//                                    mModels = testDataEntities;
                                        dataUtil.saveDataToSd(testDataEntities, fileType, testEntity, this);
                                    } else {
                                        showToast("读取数据失败！");
                                    }
                                }))
                .setNegativeButton("取消", (dialog, which) -> {
                    fileType = saveItems[0];
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }



    @Override
    protected void toRefresh() {

    }


    public void showTestData(List<TestDataEntity> testDataModels) {
        mViewDataBinding.deep.setText(StringUtil.format(testDataModels.get(testDataModels.size() - 1).deep, 1));
        List<float[]> listPoints = new ArrayList<>();
        for (TestDataEntity testDataModel : testDataModels) {
            listPoints.add(new float[]{testDataModel.qc,
                    testDataModel.fs,
                    testDataModel.fa,
                    testDataModel.deep});
        }
        drawChartHelper.addPointsToChart(listPoints);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK) {
            mViewModel.doRecord();
        }
        return true;
    }

    @Override
    public BaseTestViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(BaseTestViewModel.class);
    }


    public void showModifyDialog(String strDistance) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_modify_distance, findViewById(R.id.dialog));
        final Dialog alertDialog = new AlertDialog.Builder(BaseTestActivity.this)
                .setView(view)
                .create();
        alertDialog.show();
        final EditText distance = view.findViewById(R.id.distance);
        distance.setText(strDistance);

        Button ok = view.findViewById(R.id.ok);
        ok.setOnClickListener(view1 -> {
            String _distance = distance.getText().toString();
            if (!_distance.equals(strDistance)) {
                if (StringUtil.isFloat(_distance)) {
                    mViewModel.setDistance(_distance);
                } else {
                    showToast("测量间距不合法");
                }
            }
            alertDialog.dismiss();
        });
        Button cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(view12 -> alertDialog.dismiss());
    }

    @Override
    public void skipForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void skip(Intent intent) {

    }

    @Override
    public void sendToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

        switch (callbackMessage.what) {
            case 0:
                showModifyDialog(mViewModel.obsStringDeepDistance.get());
                break;
            case 1:
                showWaitDialog("正在连接蓝牙", false, true);
                break;
            case 2:
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 0);
                break;
        }

    }
}

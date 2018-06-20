/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.crossTest;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.activeandroid.Model;

import java.util.List;

import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.bluetooth.BluetoothCommService;
import www.jingkan.com.framework.utils.BluetoothUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.commonProbe.ProbeData;
import www.jingkan.com.localData.commonProbe.ProbeModel;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.test.TestData;
import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataData;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataModel;

/**
 * Created by lushengbo on 2018/1/5.
 * 十字板试验处理者类
 */

public class CrossTestPresenter extends BasePresenter implements CrossTestContract.Presenter {
    private CrossTestViewModel mCrossTestViewModel;
    private String mac;
    private String projectNumber;
    private String holeNumber;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (myView != null) {
                switch (msg.what) {
                    case BluetoothCommService.MESSAGE_READ://读数据
                        byte[] b = (byte[]) msg.obj;
                        String mDate = new String(b);
                        if (mDate.length() > 40) {
                            if (mDate.contains("\r")) {
                                mDate = mDate.substring(0, mDate.indexOf("\r"));
                            }
                            mDate = mDate.replace(" ", "");
                            if (mDate.contains("Sn:")) {
                                String sn = mDate.substring(mDate.indexOf("Sn:") + 3, mDate.indexOf("Sn:") + 11);
//                                IdentificationProbe(sn);
//                                mCrossTestViewModel.setStrCuEffective(getCuEffectiveValue(mDate, mCrossTestViewModel.getStrCuInitial()));
                            }
                        }
                        break;
                    case BluetoothCommService.MESSAGE_TOAST://提示信息
                        Bundle bundle = msg.getData();
                        String s = bundle.getString(BluetoothCommService.TOAST);
//                        myView.get().showToast(s);
                        break;
                    case BluetoothCommService.MESSAGE_STATE_CHANGE:
                        if (msg.arg1 == BluetoothCommService.STATE_CONNECTED) {
//                            myView.get().showToast("连接成功");
                            mCrossTestViewModel.setLinked(true);
                        } else if (msg.arg1 == BluetoothCommService.STATE_CONNECTING) {
//                            myView.get().showToast("正在连接");
                            mCrossTestViewModel.setLinked(false);
                        } else {
                            mCrossTestViewModel.setLinked(false);
                        }
                        break;
                }
            }
        }
    };


    public CrossTestPresenter(CrossTestViewModel crossTestViewModel) {
        mCrossTestViewModel = crossTestViewModel;
    }

    private boolean isIdentification;


//    private void IdentificationProbe(String sn) {
//        if (!isIdentification) {
//            isIdentification = true;
//            ProbeData probeData = DataFactory.getBaseData(ProbeData.class);
//            probeData.getData(new DataLoadCallBack() {
//                @Override
//                public <T extends Model> void onDataLoaded(List<T> model) {
//                    ProbeModel probeModel = (ProbeModel) model.get(0);
//                    mCrossTestViewModel.setStrCuCoefficient(String.valueOf(probeModel.qc_coefficient));
//                    mCrossTestViewModel.setStrCuLimit(String.valueOf(probeModel.qc_limit));
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    myView.get().showToast("该探头未添加到探头列表中，暂时不能使用，请在探头列表里添加该探头");
//                }
//            }, sn);
//        }
//
//    }

    private BluetoothCommService bluetoothCommService = new BluetoothCommService(mHandler);

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {
        bluetoothCommService.stop();
    }

    @Override
    public void init(Context context, Object data) {
        String[] strings = (String[]) data;
        mac = strings[0];
        projectNumber = strings[1];
        holeNumber = strings[2];
        getTestParameters();
        loadTestData();
        linkDevice();
    }


    @SuppressWarnings("unchecked")
    @Override
    public void loadTestData() {
        CrossTestDataData crossTestDataData = DataFactory.getBaseData(CrossTestDataData.class);
        crossTestDataData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
//                myView.get().showTestData((List<CrossTestDataModel>) models);
//                mCrossTestViewModel.setDeg(String.valueOf(models.size()));
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, projectNumber + "_" + holeNumber);
    }


    @Override
    public void getTestParameters() {
        TestData testData = DataFactory.getBaseData(TestData.class);
        testData.getData(new DataLoadCallBack() {
            @Override
            public <T extends Model> void onDataLoaded(List<T> models) {
                TestModel testModel = (TestModel) models.get(0);
//                mCrossTestViewModel.setStrProjectNumber(testModel.projectNumber);
//                mCrossTestViewModel.setStrHoleNumber(testModel.holeNumber);
            }

            @Override
            public void onDataNotAvailable() {
//                myView.get().showToast("找不到该孔信息");
            }
        }, projectNumber, holeNumber);
    }

    @Override
    public void linkDevice() {
        BluetoothAdapter bluetoothAdapter = BluetoothUtils.getInstance().
                getBluetoothAdapter();
        if (bluetoothAdapter.isEnabled()) {// 蓝牙已打开
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac);
            bluetoothCommService.connect(bluetoothDevice);
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            myView.get().startActivityForResult(intent, 0);
        }
    }

    @Override
    public void saveTestDataToSD(String projectNumber, String holeNumber, int fileType, String testType) {

    }

    @Override
    public void emailTestData(String fileName) {

    }

    private String getCuEffectiveValue(String mDate, String cuInitialValue) {
        if (mDate.contains("Cu:") && mDate.contains("kPa")) {
            String cu = mDate.substring(mDate.indexOf("Cu:") + 3, mDate.indexOf("kPa"));
            if (StringUtils.isFloat(cu)) {
                return String.valueOf(Float.parseFloat(cu) - Float.parseFloat(cuInitialValue));
            } else {
                return "0";
            }

        } else {
            return "0";
        }

    }

}

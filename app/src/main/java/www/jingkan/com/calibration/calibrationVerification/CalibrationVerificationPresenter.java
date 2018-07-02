/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.calibrationVerification;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.bluetooth.BluetoothCommService;
import www.jingkan.com.framework.utils.BluetoothUtils;
import www.jingkan.com.framework.utils.MyFileUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.framework.utils.TimeUtils;
import www.jingkan.com.framework.utils.VibratorUtils;
import www.jingkan.com.framework.utils.headset.HeadSetHelper;
import www.jingkan.com.localData.calibrationProbe.CalibrationProbeDao;
import www.jingkan.com.localData.calibrationProbe.CalibrationProbeModel;
import www.jingkan.com.localData.calibrationVerification.CalibrationVerificationDao;
import www.jingkan.com.localData.calibrationVerification.CalibrationVerificationModel;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2017/5/26.
 * 标定验证中介
 * 注意：数据在标定结束时入库！
 */

public class CalibrationVerificationPresenter extends BasePresenter<CalibrationVerificationActivity> implements CalibrationVerificationContract.Presenter {
    private float effectiveValue = 0;
    private float initialValue = 0;
    private boolean isFs;
    private List<String[]> list;
    private final CalibrationVerificationDao calibrationVerificationDao = DataFactory.getBaseData(CalibrationVerificationDao.class);
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
                                mDate = mDate.replaceAll(" ", "");
                            }
                            analyticData(mDate);
                        }
                        break;
                    case BluetoothCommService.MESSAGE_TOAST://提示信息
                        Bundle bundle = msg.getData();
                        String s = bundle.getString(BluetoothCommService.TOAST);
                        myView.get().showToast(s);
                        break;
                    case BluetoothCommService.MESSAGE_DEVICE_NAME:
                        bundle = msg.getData();
                        String string = bundle.getString(BluetoothCommService.DEVICE_NAME);
                        myView.get().showToast(string);
                        break;

                }
            }
        }
    };

    private void analyticData(String mDate) {
        if (isFs) {//侧壁读数
            if (mDate.contains("Fs:")) {
                String fs = null;
                if (mDate.contains("kPa")) {
                    fs = mDate.substring(mDate.indexOf("Fs:") + 3, mDate.indexOf("kPa"));
                } else if (mDate.contains("KPa")) {
                    fs = mDate.substring(mDate.indexOf("Fs:") + 3, mDate.indexOf("KPa"));
                }
                if (StringUtils.isFloat(fs)) {
                    effectiveValue = Float.parseFloat(fs) - initialValue;
                    myView.get().showEffectiveValue(StringUtils.format(effectiveValue, 2));
                }
            }

        } else {//锥尖读数
            if (mDate.contains("Ps:") && mDate.contains("MPa")) {
                String qc = mDate.substring(mDate.indexOf("Ps:") + 3, mDate.indexOf("MPa"));
                if (StringUtils.isFloat(qc)) {
                    effectiveValue = Float.parseFloat(qc) - initialValue;
                    myView.get().showEffectiveValue(StringUtils.format(effectiveValue, 2));
                }
            } else if (mDate.contains("Qc:") && mDate.contains("MPa")) {
                String qc = mDate.substring(mDate.indexOf("Qc:") + 3, mDate.indexOf("MPa"));
                effectiveValue = Float.parseFloat(qc) - initialValue;
                myView.get().showEffectiveValue(StringUtils.format(effectiveValue, 2));
            }
        }
    }

    private BluetoothCommService bluetoothCommService = new BluetoothCommService(mHandler);
    private boolean isShock;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        bluetoothCommService.stop();
        bluetoothCommService = null;
    }

    @Override
    public void init(Context context, Object data) {
        String[] strings = (String[]) data;
        isFs = strings[2].contains("侧壁");
        initProbeParameters(strings[1], isFs);
        list = new ArrayList<>();
        HeadSetHelper.getInstance().setOnHeadSetListener(new HeadSetHelper.OnHeadSetListener() {
            @Override
            public void onClick() {
                doRecord();
            }
        });
        HeadSetHelper.getInstance().open(context);
    }


    private String probeNo;
    private int differential;
    private String workArea;


    private void deleteData() {

        if (isFs) {
            calibrationVerificationDao.getData(new DataLoadCallBack() {
                @Override
                public void onDataLoaded(List models) {

                }

                @Override
                public void onDataNotAvailable() {
                    myView.get().showMyDialog("清除数据",
                            "要重新标定需清除原有侧壁数据，确定清除请点击【确定】",
                            true,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    calibrationVerificationDao.deleteData(probeNo, "侧壁");
                                }
                            });
                }
            }, probeNo, "侧壁");
        } else {
            calibrationVerificationDao.getData(new DataLoadCallBack() {
                @Override
                public void onDataLoaded(List model) {
                    myView.get().showMyDialog("清除数据",
                            "要重新标定需清除原有锥头数据，确定清除请点击【确定】",
                            true,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    calibrationVerificationDao.deleteData(probeNo, "锥头");
                                }
                            });
                }

                @Override
                public void onDataNotAvailable() {

                }
            }, probeNo, "锥头");

        }

    }

    @Override
    public void initProbeParameters(final String sn, final boolean isFS) {
        this.isFs = isFS;
        CalibrationProbeDao calibrationProbeDao = DataFactory.getBaseData(CalibrationProbeDao.class);
        calibrationProbeDao.getData(new DataLoadCallBack() {
            @Override
            public void onDataLoaded(List model) {
                CalibrationProbeModel calibrationProbeModel = (CalibrationProbeModel) model.get(0);
                probeNo = calibrationProbeModel.number;
                workArea = calibrationProbeModel.work_area;
                if (isFS) {
                    differential = Integer.parseInt(calibrationProbeModel.differential) * 10;
                } else {
                    differential = Integer.parseInt(calibrationProbeModel.differential);
                }
                myView.get().showProbeParameters(probeNo,
                        sn,
                        String.valueOf(differential), workArea
                );
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, sn);

        deleteData();
    }

    @Override
    public void getInitialValue() {
        initialValue = effectiveValue;
        myView.get().showInitialValue(String.valueOf(initialValue));
    }

    private int index = 0;


    @Override
    public void doRecord() {
        int x = index * differential;
        if (index < 7) {
            myView.get().showLoadingLine(x, effectiveValue, "加荷1");
            list.add(new String[]{String.valueOf(x), "加荷", String.valueOf(effectiveValue)});
        } else if (index < 14) {
            x = (13 - index) * differential;
            myView.get().showLoadingLine(x, effectiveValue, "卸荷1");
            list.add(new String[]{String.valueOf(x), "卸荷", String.valueOf(effectiveValue)});
        } else if (index < 21) {
            x = (index - 14) * differential;
            myView.get().showLoadingLine(x, effectiveValue, "加荷2");
            list.add(new String[]{String.valueOf(x), "加荷", String.valueOf(effectiveValue)});
        } else if (index < 28) {
            x = (27 - index) * differential;
            myView.get().showLoadingLine(x, effectiveValue, "卸荷2");
            list.add(new String[]{String.valueOf(x), "卸荷", String.valueOf(effectiveValue)});
            if (index == 27) {
                for (String[] strings : list) {
                    putDateBase(strings);
                    myView.get().showToast("标定结束");
                }
            }
        } else {
            myView.get().showToast("标定结束");
        }
        index++;
        if (isShock) {
            VibratorUtils.Vibrate(myView.get(), 200);
        }
    }

    @Override
    public void saveData(final String fileName) {
        final String content = calibrationResult();
        if (content.equals("没有数据")) {
            myView.get().showToast("数据未采集完成不能保存");
        } else {
            MyFileUtils.getInstance().saveToSD(myView.get(), fileName, content, new MyFileUtils.SaveFileCallBack() {
                @Override
                public void onSuccess() {
                    myView.get().showToast("保存成功");
                }

                @Override
                public void onFail(String e) {
                    myView.get().showToast(e);
                }
            });
        }
    }

    @Override
    public void linkDevice(String mac) {
        BluetoothAdapter bluetoothDevice = BluetoothUtils.getInstance().
                getBluetoothAdapter();
        if (bluetoothDevice.isEnabled()) {// 蓝牙已打开
            BluetoothDevice remoteDevice = BluetoothUtils.getInstance().
                    getBluetoothAdapter().getRemoteDevice(mac);
            bluetoothCommService.connect(remoteDevice);
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            myView.get().startActivityForResult(intent, 0);
        }
    }

    /**
     * 标定数据入库
     *
     * @param data 数据数组
     *             1.当前荷载
     *             2.荷载类型
     *             3.实际读数
     */
    private void putDateBase(String[] data) {
        CalibrationVerificationModel calibrationVerificationModel = new CalibrationVerificationModel();
        calibrationVerificationModel.probeNo = probeNo;
        if (isFs) {
            calibrationVerificationModel.type = "侧壁";
        } else {
            calibrationVerificationModel.type = "锥头";
        }
        calibrationVerificationModel.standardValue = data[0];
        calibrationVerificationModel.forceType = data[1];
        calibrationVerificationModel.loadValue = Float.parseFloat(data[2]);
        calibrationVerificationModel.save();//入库
    }


    private boolean haveData;

    private String calibrationResult() {
        StringBuilder result;
        String strReturn = "\r\n";
        String strTab = "\t";
        if (isFs) {
            loadData("侧壁");
            if (!haveData) {
                return "没有数据";
            }
            result = new StringBuilder("侧壁标定" + strReturn);
        } else {
            loadData("锥头");
            if (!haveData) {
                return "没有数据";
            }
            result = new StringBuilder("锥头标定" + strReturn);
        }
        float[] parameters = calculationParameter();
        result.append("标定日期：").append(TimeUtils.getCurrentTime()).append(strReturn);
        result.append("工作面积：").append(workArea).append(strReturn);
        result.append("荷载级差：").append(String.valueOf(differential)).append(strReturn);
        result.append("电缆长度：").append("2").append(strReturn);
        result.append("电缆规格：").append("0.15").append(strReturn).append(strReturn);//换行
        result.append("线性误差：").append(StringUtils.format(parameters[0], 2)).append(strReturn);
        result.append("重复误差：").append(StringUtils.format(parameters[1], 2)).append(strReturn);
        result.append("滞后误差：").append(StringUtils.format(parameters[2], 2)).append(strReturn);
        result.append("归零误差：").append(StringUtils.format(parameters[3], 2)).append(strReturn);
        result.append("起始感量：").append(StringUtils.format(parameters[4], 2)).append(strReturn);
        result.append("标定系数：").append(parameters[5]).append(strReturn);
        String evaluate;
        if (parameters[0] >= 1 || parameters[1] >= 1 || parameters[2] >= 1 || parameters[3] >= 1) {
            evaluate = "不合格";
        } else {
            evaluate = "合格";
        }
        result.append("质量评定：").append(evaluate).append(strReturn).append(strReturn);
        result.append("序号").append(strTab).append("加荷1").append(strTab).append("加荷2").append(strTab).append("加荷3").append(strTab).append("卸荷1").append(strTab).append("卸荷2").append(strTab).append("卸荷3").append(strReturn);
        for (int i = 0; i < load.length; i++) {
            result.append(String.valueOf(i)).append(strTab).append(StringUtils.format(load[i], 2)).append(strTab).append(StringUtils.format(load1[i], 2)).append(strTab).append(strTab).append(StringUtils.format(unLoad1[i], 2)).append(strTab).append(StringUtils.format(unLoad[i], 2)).append(strReturn);
        }
        return result.toString();
    }

    /**
     * 计算参数
     *
     * @return 参数数组分别为：非线性误差，重复误差，滞后误差，归零误差，起始感量，标定系数。
     */
    private float[] calculationParameter() {
        float[] parameter = new float[6];
        int length = loadAverage.length;
        int[] perLoad = new int[length];
        float[] loadUnloadAverage = new float[length];
        float squareSumLoadUnloadAverage = 0;
        float optimumValue;
        float maxOptimumValue;
        float[] nonlinear = new float[length];
        float maxNonlinear = 0;
        float[] hysteresis = new float[length];
        float maxHysteresis = 0;
        float sumPerLoad = 0;
        for (int i = 0; i < length; i++) {
            loadUnloadAverage[i] = (loadAverage[i] + unLoadAverage[i]) / 2;
            squareSumLoadUnloadAverage += loadUnloadAverage[i] * loadUnloadAverage[i];
            perLoad[i] = differential * i;
            sumPerLoad += perLoad[i] * loadUnloadAverage[i];
        }

        for (int i = 0; i < length; i++) {
            optimumValue = loadUnloadAverage[length - 1] / (length - 1) * i;
            nonlinear[i] = Math.abs(loadAverage[i] - optimumValue) > Math.abs(unLoadAverage[i] - optimumValue) ?
                    Math.abs(loadAverage[i] - optimumValue) : Math.abs(unLoadAverage[i] - optimumValue);
            hysteresis[i] = Math.abs(loadAverage[i] - unLoadAverage[i]);
            maxNonlinear = nonlinear[i] > maxNonlinear ? nonlinear[i] : maxNonlinear;
            maxHysteresis = hysteresis[i] > maxHysteresis ? hysteresis[i] : maxHysteresis;
        }


        maxOptimumValue = loadUnloadAverage[length - 1];
        parameter[0] = maxNonlinear / maxOptimumValue * 100;//非线性误差
        float maxRepetition = maxLoadDifferenceValue > maxUnLoadDifferenceValue ?
                maxLoadDifferenceValue : maxUnLoadDifferenceValue;
        parameter[1] = maxRepetition / maxOptimumValue * 100;//重复误差
        parameter[2] = maxHysteresis / maxOptimumValue * 100;//滞后误差
        parameter[3] = Math.abs(loadAverage[0] - unLoadAverage[0]) / maxOptimumValue * 100;//归零误差
        parameter[4] = 0.01f;
        parameter[5] = sumPerLoad / squareSumLoadUnloadAverage;

        return parameter;
    }

    private float[] loadAverage;
    private float[] unLoadAverage;
    private float maxLoadDifferenceValue = 0;
    private float maxUnLoadDifferenceValue = 0;
    private float[] load;
    private float[] load1;
    private float[] unLoad;
    private float[] unLoad1;

    @SuppressWarnings("unchecked")
    private void loadData(String type) {
        calibrationVerificationDao.getData(new DataLoadCallBack() {
            @Override
            public void onDataLoaded(List model) {
                List<CalibrationVerificationModel> calibrationVerificationModels = (List<CalibrationVerificationModel>) model;
                int size = calibrationVerificationModels.size();
                load = new float[size / 2];
                load1 = new float[size / 2];
                loadAverage = new float[size / 2];
                for (int i = 0; i < size / 2; i++) {
                    loadAverage[i] =
                            (calibrationVerificationModels.get(0).loadValue
                                    + calibrationVerificationModels.get(size / 2).loadValue)
                                    / 2;
                    load[i] = calibrationVerificationModels.get(0).loadValue;
                    calibrationVerificationModels.remove(0);
                }
                for (int i = 0; i < calibrationVerificationModels.size(); i++) {
                    load1[i] = calibrationVerificationModels.get(i).loadValue;
                }
                for (int i = 0; i < size / 2; i++) {
                    float loadDifferenceValue = Math.abs(load[i] - load1[i]);
                    maxLoadDifferenceValue =
                            loadDifferenceValue > maxLoadDifferenceValue ?
                                    loadDifferenceValue : maxLoadDifferenceValue;

                }
                haveData = true;
            }

            @Override
            public void onDataNotAvailable() {
                haveData = false;
            }
        }, probeNo, type, "加荷");
        calibrationVerificationDao.getData(new DataLoadCallBack() {
            @Override
            public void onDataLoaded(List model) {
                List<CalibrationVerificationModel> calibrationVerificationModels = (List<CalibrationVerificationModel>) model;
                int size = calibrationVerificationModels.size();
                unLoad = new float[size / 2];
                unLoad1 = new float[size / 2];
                unLoadAverage = new float[size / 2];
                Collections.reverse(calibrationVerificationModels);//倒序
                for (int i = 0; i < size / 2; i++) {
                    unLoadAverage[i] =
                            (calibrationVerificationModels.get(0).loadValue
                                    + calibrationVerificationModels.get(size / 2).loadValue)
                                    / 2;
                    unLoad[i] = calibrationVerificationModels.get(0).loadValue;
                    calibrationVerificationModels.remove(0);
                }

                for (int i = 0; i < calibrationVerificationModels.size(); i++) {
                    unLoad1[i] = calibrationVerificationModels.get(i).loadValue;
                }
                for (int i = 0; i < size / 2; i++) {
                    float unLoadDifference = Math.abs(unLoad[i] - unLoad1[i]);
                    maxUnLoadDifferenceValue = unLoadDifference > maxUnLoadDifferenceValue ?
                            unLoadDifference : maxUnLoadDifferenceValue;
                }
                haveData = true;
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, probeNo, type, "卸荷");

    }


    @Override
    public void enableShock(boolean isEnable) {
        isShock = isEnable;
    }
}

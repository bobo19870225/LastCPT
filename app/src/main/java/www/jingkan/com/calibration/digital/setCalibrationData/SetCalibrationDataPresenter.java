/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.digital.setCalibrationData;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.bluetooth.BluetoothCommService;
import www.jingkan.com.framework.utils.BluetoothUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.framework.utils.VibratorUtils;
import www.jingkan.com.localData.calibrationProbe.CalibrationProbeDao;
import www.jingkan.com.localData.calibrationProbe.CalibrationProbeModel;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.memoryData.MemoryDaoDao;
import www.jingkan.com.localData.memoryData.MemoryDataModel;
import www.jingkan.com.parameter.SystemConstant;


/**
 * Created by bobo on 2017/4/2.
 * 开始标定中介
 */

public class SetCalibrationDataPresenter extends BasePresenter<SetCalibrationDataActivity> implements SetCalibrationDataContract.Presenter {
    private String[] effectiveValues;
    private int effectiveValue = 0;
    private MemoryDaoDao memoryData = DataFactory.getBaseData(MemoryDaoDao.class);
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (myView != null) {
                switch (msg.what) {
                    case BluetoothCommService.MESSAGE_WRITE://写数据

                        break;
                    case BluetoothCommService.MESSAGE_READ://读数据
                        byte[] b = (byte[]) msg.obj;
                        String mDate = new String(b);
                        if (mDate.length() > 40) {
                            if (mDate.contains("\r")) {
                                mDate = mDate.substring(0, mDate.indexOf("\r"));
                            }
                            if (mDate.contains("Sn")) {
                                myView.get().showEffectiveValue("已标定");
                                break;
                            }

                            String[] split = mDate.split(" ");
                            for (int i = 0, j = 0; i < split.length; i++) {
                                String aSplit = split[i];
                                if (StringUtils.isInteger(aSplit)) {
                                    effectiveValues[j] = aSplit;
                                    if (j == 4) {
                                        break;
                                    }
                                    j++;
                                }
                            }
                            if (isFa) {
                                if (StringUtils.isInteger(effectiveValues[2])
                                        && StringUtils.isInteger(effectiveValues[3])
                                        && StringUtils.isInteger(effectiveValues[4])) {
                                    myView.get().showFaChannelValue(
                                            Integer.parseInt(effectiveValues[2]),
                                            Integer.parseInt(effectiveValues[4]),
                                            Integer.parseInt(effectiveValues[3]));
                                }
                            } else {
                                if (isFs) {//侧壁读数
                                    if (StringUtils.isInteger(effectiveValues[1])) {
                                        effectiveValue = Integer.parseInt(effectiveValues[1]) - initialValue;
                                    }
                                } else {//锥尖读数
                                    if (StringUtils.isInteger(effectiveValues[0])) {
                                        effectiveValue = Integer.parseInt(effectiveValues[0]) - initialValue;
                                    }
                                }
                            }
                            myView.get().showEffectiveValue(String.valueOf(effectiveValue));
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
    private BluetoothCommService bluetoothCommService;
    private int[][] YBL;
    private String snToW;
    private boolean isFs;
    private boolean isFa;
    private boolean isShock;
    private int mType;

    /**
     * 清除资源
     */
    @Override
    public void clear() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        bluetoothCommService.stop();
        bluetoothCommService = null;
    }

    @Override
    public void init(Context context, Object data) {
        effectiveValues = new String[5];
        YBL = new int[6][7];
        bluetoothCommService = new BluetoothCommService(mHandler);
        String[] strings = (String[]) data;
        boolean isDoubleBridge = strings[2].contains("双桥");
        boolean isMultifunctional = strings[2].contains("多功能");
        initProbeParameters(strings[1], isDoubleBridge, isMultifunctional);//参数为探头序列号
    }


    /**
     * 活动回调
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        结果数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private byte command[] = new byte[281];
    private int ds = 0;
    //第一维0表示锥尖，1表示侧壁；第二维0表示标准读数，1表示加荷读数，2表示卸荷读数；第三维表示各级差读数。
    private int[][][] Acc = new int[2][3][8];

    @SuppressWarnings("unchecked")
    @Override
    public void setDataToProbe() {
        if (isFs) {//侧壁标定
            memoryData.getData(new DataLoadCallBack<MemoryDataModel>() {


                @Override
                public void onDataLoaded(List<MemoryDataModel> models) {
                    getQcData(models);
                    memoryData.getData(new DataLoadCallBack<MemoryDataModel>() {
                        @Override
                        public void onDataLoaded(List<MemoryDataModel> models) {
                            for (int i = 0; i < models.size(); i++) {
                                if (i < 7) {//侧壁加荷
                                    Acc[1][1][i] = models.get(i).ADValue;
                                } else {//侧壁卸荷
                                    Acc[1][2][i - 7] = models.get(i).ADValue;
                                }

                            }
                            sendData();
                        }

                        @Override
                        public void onDataNotAvailable() {
                            myView.get().showToast("没有侧壁数据,不能标定");
                        }
                    }, sn, "fs");
                }

                @Override
                public void onDataNotAvailable() {
                    myView.get().showToast("没有锥头数据,不能标定");
                }
            }, sn, "qc");

        } else {//锥头标定
            memoryData.getData(new DataLoadCallBack<MemoryDataModel>() {

                @Override
                public void onDataLoaded(List<MemoryDataModel> models) {
                    getQcData(models);
                    //侧壁数据
                    for (int i = 0; i < 7; i++) {
                        Acc[1][1][i] = i * 1200;
                        Acc[1][2][i] = Acc[1][1][i];
                    }
                    sendData();
                }

                @Override
                public void onDataNotAvailable() {
                    myView.get().showToast("没有锥头数据,不能标定");
                }
            }, sn, "qc");

        }

    }

    private void getQcData(List<MemoryDataModel> memoryDataModels) {
        for (int i = 0; i < memoryDataModels.size(); i++) {
            if (i < 7) {//锥头加荷
                Acc[0][1][i] = memoryDataModels.get(i).ADValue;
            } else {//锥头卸荷
                Acc[0][2][i - 7] = memoryDataModels.get(i).ADValue;
            }
        }
    }

    private void sendData() {
        Acc[0][1][7] = Acc[0][0][7] / Acc[0][0][6] * Acc[0][1][6];
        Acc[0][2][7] = Acc[0][0][7] / Acc[0][0][6] * Acc[0][2][6];
        Acc[1][1][7] = Acc[1][0][7] / Acc[1][0][6] * Acc[1][1][6];
        Acc[1][2][7] = Acc[1][0][7] / Acc[1][0][6] * Acc[1][2][6];
        command[0] = 83;
        command[1] = 69;
        command[2] = 84;
        command[3] = 85;
        command[4] = 80;
        if (sn.length() != 0) {
            sn = sn + "        ";
            sn = sn.substring(0, 8);
            switch (strModel) {
                case SystemConstant.SINGLE_BRIDGE_3:
                    if (area.equals("10")) {
                        snToW = sn + "C";
                    } else {
                        snToW = sn + "I";
                    }
                    break;
                case SystemConstant.SINGLE_BRIDGE_4:
                    if (area.equals("10")) {
                        snToW = sn + "D";
                    } else {
                        snToW = sn + "J";
                    }
                    break;
                case SystemConstant.SINGLE_BRIDGE_6:
                    if (area.equals("10")) {
                        snToW = sn + "F";
                    } else {
                        snToW = sn + "L";
                    }
                    break;
                case SystemConstant.DOUBLE_BRIDGE_3:
                    if (area.equals("10")) {
                        snToW = sn + "O";
                    } else {
                        snToW = sn + "U";
                    }
                    break;
                case SystemConstant.DOUBLE_BRIDGE_4:
                    if (area.equals("10")) {
                        snToW = sn + "P";
                    } else {
                        snToW = sn + "V";
                    }
                    break;
                case SystemConstant.DOUBLE_BRIDGE_6:
                    if (area.equals("10")) {
                        snToW = sn + "R";
                    } else {
                        snToW = sn + "X";
                    }
                    break;
                case SystemConstant.VANE:
                    if (area.equals("10")) {
                        snToW = sn + "Y";
                    } else {
                        snToW = sn + "Z";
                    }
                    break;

                default:
                    break;
            }
            String[] split = number.split("-");
            if (split[2] != null) {
                snToW = snToW + split[2];
            }

            char[] bm = snToW.toCharArray();
            for (int i = 1; i < 13; i++) {
                command[i + 4] = (byte) bm[i - 1];
            }
            snToW = null;

            if (obliquityX < 0) {
                obliquityX = 65536 + obliquityX;
            }
            if (obliquityY < 0) {
                obliquityY = 65536 + obliquityY;
            }
            if (obliquityZ < 0) {
                obliquityZ = 65536 + obliquityZ;
            }
            if (Acc[0][1][6] >= 0) {
                Acc[0][1][0] = 1;
            } else {
                Acc[0][1][0] = -1;
            }
            if (Acc[1][1][6] >= 0) {
                Acc[1][1][0] = 1;
            } else {
                Acc[1][1][0] = -1;
            }
            command[17] = (byte) (obliquityX / 256);
            command[18] = (byte) (obliquityX % 256);
            command[21] = (byte) (obliquityY / 256);
            command[22] = (byte) (obliquityY % 256);
            command[19] = (byte) (obliquityZ / 256);
            command[20] = (byte) (obliquityZ % 256);
            for (int i = 0; i < 8; i++) {
                command[i * 4 + 151] = (byte) (Acc[0][0][i] / 16777216);
                command[i * 4 + 152] = (byte) ((Acc[0][0][i] % 16777216) / 65536);
                command[i * 4 + 153] = (byte) ((Acc[0][0][i] % 65536) / 256);
                command[i * 4 + 154] = (byte) (Acc[0][0][i] % 256);

                command[i * 4 + 183] = (byte) (Acc[0][0][i] / 16777216);
                command[i * 4 + 184] = (byte) ((Acc[0][0][i] % 16777216) / 65536);
                command[i * 4 + 185] = (byte) ((Acc[0][0][i] % 65536) / 256);
                command[i * 4 + 186] = (byte) (Acc[0][0][i] % 256);

                command[i * 4 + 215] = (byte) (Acc[1][0][i] / 16777216);
                command[i * 4 + 216] = (byte) ((Acc[1][0][i] % 16777216) / 65536);
                command[i * 4 + 217] = (byte) ((Acc[1][0][i] % 65536) / 256);
                command[i * 4 + 218] = (byte) (Acc[1][0][i] % 256);

                command[i * 4 + 247] = (byte) (Acc[1][0][i] / 16777216);
                command[i * 4 + 248] = (byte) ((Acc[1][0][i] % 16777216) / 65536);
                command[i * 4 + 249] = (byte) ((Acc[1][0][i] % 65536) / 256);
                command[i * 4 + 250] = (byte) (Acc[1][0][i] % 256);

                command[i * 4 + 23] = (byte) ((Math.abs(Acc[0][1][i]) / 0.0023283) / 16777216);
                command[i * 4 + 24] = (byte) (((Math.abs(Acc[0][1][i]) / 0.0023283) % 16777216) / 65536);
                command[i * 4 + 25] = (byte) (((Math.abs(Acc[0][1][i]) / 0.0023283) % 65536) / 256);
                command[i * 4 + 26] = (byte) ((Math.abs(Acc[0][1][i]) / 0.0023283) % 256);

                command[i * 4 + 55] = (byte) ((Math.abs(Acc[0][2][i]) / 0.0023283) / 16777216);
                command[i * 4 + 56] = (byte) (((Math.abs(Acc[0][2][i]) / 0.0023283) % 16777216) / 65536);
                command[i * 4 + 57] = (byte) (((Math.abs(Acc[0][2][i]) / 0.0023283) % 65536) / 256);
                command[i * 4 + 58] = (byte) ((Math.abs(Acc[0][2][i]) / 0.0023283) % 256);

                command[i * 4 + 87] = (byte) ((Math.abs(Acc[1][1][i]) / 0.0023283) / 16777216);
                command[i * 4 + 88] = (byte) (((Math.abs(Acc[1][1][i]) / 0.0023283) % 16777216) / 65536);
                command[i * 4 + 89] = (byte) (((Math.abs(Acc[1][1][i]) / 0.0023283) % 65536) / 256);
                command[i * 4 + 90] = (byte) ((Math.abs(Acc[1][1][i]) / 0.0023283) % 256);

                command[i * 4 + 119] = (byte) ((Math.abs(Acc[1][2][i]) / 0.0023283) / 16777216);
                command[i * 4 + 120] = (byte) (((Math.abs(Acc[1][2][i]) / 0.0023283) % 16777216) / 65536);
                command[i * 4 + 121] = (byte) (((Math.abs(Acc[1][2][i]) / 0.0023283) % 65536) / 256);
                command[i * 4 + 122] = (byte) ((Math.abs(Acc[1][2][i]) / 0.0023283) % 256);
            }
            if (Acc[0][1][6] >= 0) {
                command[279] = 0x1;
            } else {
                command[279] = 0x10;
            }
            if (Acc[1][1][6] >= 0) {
                command[280] = 0x67;
            } else {
                command[280] = 0x76;
            }
            ds = 0;
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 你要做的事。。。
                    sendMessage(command);
                    ds++;
                    if (ds == 9) {
                        timer.cancel();// 取消操作
                        myView.get().showToast("设置成功");
                    }
                }
            }, 0, 1000);// 0秒后执行，每1秒执行一次

        }
    }

    @Override
    public void resetDataToProbe(int which) {
        //先删除以前数据库里的数据
        switch (which) {
            case 0://全部数据
                memoryData.deleteData(sn);
                switchingChannel(0);//切换到锥头通道
                break;
            case 1://锥头
                memoryData.deleteData(sn, "qc");
                switchingChannel(0);//切换到锥头通道
                break;
            case 2://侧壁
                memoryData.deleteData(sn, "fs");
                switchingChannel(1);//切换到侧壁通道
                break;
            case 3:
                break;
        }
        command[0] = 83;
        command[1] = 69;
        command[2] = 84;
        command[3] = 85;
        command[4] = 80;
        for (int i = 5; i < 279; i++) {
            command[i] = 0;
        }
        command[279] = 0x1;
        command[280] = 0x67;
        ds = 0;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 你要做的事。。。
                sendMessage(command);
                ds++;
                if (ds == 9) {
                    timer.cancel();// 取消操作
                    myView.get().showToast("重置成功");
                }
            }
        }, 0, 1000);// 0秒后执行，每1秒执行一次
    }

    private String number;
    private String sn;
    private String area;
    private String strModel;
    private String differential;

    @Override
    public void initProbeParameters(final String sn, boolean isFs, final boolean isFa) {
        this.sn = sn;
        CalibrationProbeDao calibrationProbeDao = DataFactory.getBaseData(CalibrationProbeDao.class);
        calibrationProbeDao.getData(new DataLoadCallBack<CalibrationProbeModel>() {


            @Override
            public void onDataLoaded(List<CalibrationProbeModel> models) {
                CalibrationProbeModel calibrationProbeModel = models.get(0);
                number = calibrationProbeModel.number;
                area = calibrationProbeModel.work_area;
                differential = calibrationProbeModel.differential;
                myView.get().showProbeParameters(number, sn,
                        differential, calibrationProbeModel.work_area);
                String[] split = number.split("-");
                String type = null;
                String strModel1 = null;
                if (split.length == 3) {
                    type = split[0].substring(2, 3);
                    strModel1 = split[1];
                }
                if (type != null) {
                    switch (type) {
                        case "D":
                            switch (strModel1) {
                                case "3":
                                    mType = 3;
                                    initDifferential();
                                    strModel = SystemConstant.SINGLE_BRIDGE_3;
                                    initAcc(3);
                                    break;
                                case "4":
                                    mType = 4;
                                    initDifferential();
                                    strModel = SystemConstant.SINGLE_BRIDGE_4;
                                    initAcc(4);
                                    break;
                                case "6":
                                    mType = 6;
                                    initDifferential();
                                    strModel = SystemConstant.SINGLE_BRIDGE_6;
                                    initAcc(6);
                                    break;
                            }
                            break;
                        case "S":
                            switch (strModel1) {
                                case "3":
                                    mType = 3;
                                    initDifferential();
                                    strModel = SystemConstant.DOUBLE_BRIDGE_3;
                                    initAcc(3);
                                    break;
                                case "4":
                                    mType = 4;
                                    initDifferential();
                                    strModel = SystemConstant.DOUBLE_BRIDGE_4;
                                    initAcc(4);
                                    break;
                                case "6":
                                    mType = 6;
                                    initDifferential();
                                    strModel = SystemConstant.DOUBLE_BRIDGE_6;
                                    initAcc(6);
                                    break;
                            }
                            break;
                        case "V":
                            strModel = SystemConstant.VANE;
                            for (int i = 0; i < 7; i++) {
                                Acc[0][0][i] = i * 20000;
                                Acc[1][0][i] = i * 20000;
                            }
                            Acc[0][0][7] = 140000;
                            Acc[1][0][7] = 140000;
                            break;
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                myView.get().showToast("序列号错误");
            }
        }, sn);


        if (isFs) {//双桥
            memoryData.getData(new DataLoadCallBack() {
                @Override
                public void onDataLoaded(List model) {//有锥头数据判断有无侧壁数据
                    memoryData.getData(new DataLoadCallBack() {
                        @Override
                        public void onDataLoaded(List model) {
                            if (isFa) {
                                switchingChannel(2);//切换到测斜通道
                            }
                        }

                        @Override
                        public void onDataNotAvailable() {//没有侧壁数据时准备采集侧壁数据
                            switchingChannel(1);//切换到侧壁
                        }
                    }, sn, "fs");

                }

                @Override
                public void onDataNotAvailable() {//无锥头数据时准备采集锥头数据
                    //switchingChannel(0);//切换到锥头通道
                    myView.get().showToast("没有历史数据");
                }
            }, sn, "qc");

        } else {//单桥或十字板
            memoryData.getData(new DataLoadCallBack() {
                @Override
                public void onDataLoaded(List model) {//有锥头数据时直接采集侧壁的数据
                    if (isFa) {
                        switchingChannel(2);//切换到测斜通道
                    } else {
                        switchingChannel(0);//切换到锥头通道
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    myView.get().showToast("没有历史数据");
                }
            }, sn, "qc");
        }


    }

    private void storeData(String type) {
        MemoryDataModel memoryDataModel;
        if (type.equals("qc")) {
            for (int i = 0; i < 7; i++) {
                Acc[0][1][i] = YBL[4][i];// 锥头加荷平均
                memoryDataModel = new MemoryDataModel();
                memoryDataModel.probeID = sn;
                memoryDataModel.type = type;
                memoryDataModel.forceType = "加荷";
                memoryDataModel.ADValue = YBL[4][i];
                memoryData.addData(memoryDataModel);
            }
            for (int i = 0; i < 7; i++) {
                Acc[0][2][i] = YBL[5][i];// 锥头卸荷平均
                memoryDataModel = new MemoryDataModel();
                memoryDataModel.probeID = sn;
                memoryDataModel.type = type;
                memoryDataModel.forceType = "卸荷";
                memoryDataModel.ADValue = YBL[5][i];
                memoryData.addData(memoryDataModel);
            }
        } else {
            for (int i = 0; i < 7; i++) {
                Acc[1][1][i] = YBL[4][i];// 侧壁加荷平均
                memoryDataModel = new MemoryDataModel();
                memoryDataModel.probeID = sn;
                memoryDataModel.type = type;
                memoryDataModel.forceType = "加荷";
                memoryDataModel.ADValue = YBL[4][i];
                memoryData.addData(memoryDataModel);
            }
            for (int i = 0; i < 7; i++) {
                Acc[1][2][i] = YBL[5][i];// 侧壁卸荷平均
                memoryDataModel = new MemoryDataModel();
                memoryDataModel.probeID = sn;
                memoryDataModel.type = type;
                memoryDataModel.forceType = "卸荷";
                memoryDataModel.ADValue = YBL[5][i];
                memoryData.addData(memoryDataModel);
            }
        }

    }


    private void initDifferential() {
        if (StringUtils.isInteger(area)) {
            if (isFs) {
                myView.get().showDifferential(10 * mType * Integer.parseInt(area) / 5);
            } else {
                myView.get().showDifferential(mType * Integer.parseInt(area) / 5);
            }
        } else {
            myView.get().showToast("探头编号有误");
        }
    }

    private void initAcc(int ratio) {
        for (int i = 0; i < 7; i++) {
            Acc[0][0][i] = i * ratio * 2000;
            Acc[1][0][i] = i * ratio * 20000;
        }
        Acc[0][0][7] = 144000;
        Acc[1][0][7] = 1440000;
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */

    private void sendMessage(byte[] message) {
        // 没有连接设备，不能发送
        if (myView.get() != null) {
            if (bluetoothCommService.getState() != BluetoothCommService.STATE_CONNECTED) {
                myView.get().showToast("未连接");
                return;
            }
            bluetoothCommService.write(message);
        }
//        mOutStringBuffer.setLength(0);
    }

    private int initialValue = 0;


    private int index = 0;

    @Override
    public void doRecord() {
        if (isShock) {
            VibratorUtils.Vibrate(myView.get(), 200);
        }
        if (index < 7) {//加荷1
            if (index == 0) {//读初值
                if (effectiveValues.length > 0) {
                    if (isFs) {//侧壁读数
                        initialValue = Integer.parseInt(effectiveValues[1]);
                    } else {//锥尖读数
                        initialValue = Integer.parseInt(effectiveValues[0]);
                    }
                    myView.get().showInitialValue(String.valueOf(initialValue));
                }
                YBL[0][0] = initialValue;
            } else {
                YBL[0][index] = effectiveValue;
            }
        } else if (index < 14) {//卸荷1
            YBL[1][13 - index] = effectiveValue;

        } else if (index < 21) {//加荷2
            YBL[2][index - 14] = effectiveValue;

        } else if (index < 28) {//卸荷2
            YBL[3][27 - index] = effectiveValue;
            if (index == 27) {
                getAverageValue();
                if (isFs) {
                    storeData("fs");
                } else {
                    storeData("qc");
                }

            }
        }
        myView.get().showRecordValue(String.valueOf(effectiveValue));
        index++;

    }

    private int obliquityX = 0;
    private int obliquityY = 0;
    private int obliquityZ = 0;

    @Override
    public void setObliquityInitialValue(int x, int y, int z) {
        obliquityX = x;
        obliquityY = y;
        obliquityZ = z;

    }

    private void getAverageValue() {

        for (int i = 0; i < YBL[0].length; i++) {
            YBL[4][i] = (YBL[0][i] + YBL[2][i]) / 2;
            YBL[5][i] = (YBL[1][i] + YBL[3][i]) / 2;
        }
        //去皮
        for (int i = 0; i < YBL[0].length; i++) {
            YBL[4][i] = YBL[4][i] - YBL[5][0] * i / 13;
            YBL[5][i] = YBL[5][i] - YBL[4][0] * (13 - i) / 13;
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

    @SuppressWarnings("unchecked")
    @Override
    public void switchingChannel(int which) {
        switch (which) {
            case 0://锥头
                isFs = false;
                memoryData.getData(new DataLoadCallBack<MemoryDataModel>() {
                    @Override
                    public void onDataLoaded(List<MemoryDataModel> models) {//有锥头数据时直接采集侧壁的数据
                        myView.get().resetView("锥头", models);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        myView.get().resetView("锥头", null);
                    }
                }, sn, "qc");

                initialValue = 0;
                myView.get().showInitialValue(String.valueOf(initialValue));
                initDifferential();
                index = 0;
                break;
            case 1://侧壁通道
                isFs = true;
                memoryData.getData(new DataLoadCallBack<MemoryDataModel>() {
                    @Override
                    public void onDataLoaded(List<MemoryDataModel> models) {//有锥头数据时直接采集侧壁的数据
                        myView.get().resetView("侧壁", models);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        myView.get().resetView("侧壁", null);
                    }
                }, sn, "fs");

                initialValue = 0;
                myView.get().showInitialValue(String.valueOf(initialValue));
                initDifferential();
                index = 0;
                break;
            case 2://测斜通道
                isFa = true;
                myView.get().showFaChannel();
                break;
        }

    }


    @Override
    public void enableShock(boolean isEnable) {
        isShock = isEnable;
    }
}

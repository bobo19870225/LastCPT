package www.jingkan.com.view_model;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import www.jingkan.com.db.dao.CalibrationProbeDao;
import www.jingkan.com.db.dao.MemoryDataDao;
import www.jingkan.com.db.entity.CalibrationProbeEntity;
import www.jingkan.com.db.entity.MemoryDataEntity;
import www.jingkan.com.util.SingleLiveEvent;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.util.SystemConstant;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 * {@link www.jingkan.com.view.SetCalibrationDataActivity}
 */
public class SetCalibrationDataVM extends BaseViewModel {
    private BluetoothUtil bluetoothUtil;
    private BluetoothCommService bluetoothCommService;
    private MemoryDataDao memoryDataDao;
    private CalibrationProbeDao calibrationProbeDao;
    public final SingleLiveEvent<List<MemoryDataEntity>> resetQcView = new SingleLiveEvent<>();
    public final SingleLiveEvent<List<MemoryDataEntity>> resetFsView = new SingleLiveEvent<>();

    public final MutableLiveData<String> ldSN = new MutableLiveData<>();
    public final MutableLiveData<String> ldNumber = new MutableLiveData<>();
    public final MutableLiveData<String> ldArea = new MutableLiveData<>();
    public final MutableLiveData<String> ldDifferential = new MutableLiveData<>();
    public final MutableLiveData<String> ldInitial = new MutableLiveData<>();
    public final MutableLiveData<String> ldValid = new MutableLiveData<>();
    public final MutableLiveData<String> ldChannel = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ6 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ7 = new MutableLiveData<>();

    public final MutableLiveData<String> ldJH1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH6 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH7 = new MutableLiveData<>();

    public final MutableLiveData<String> ldXH1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH6 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH7 = new MutableLiveData<>();

    public final MutableLiveData<String> ldJh1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh6 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh7 = new MutableLiveData<>();

    public final MutableLiveData<String> ldXh1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh6 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh7 = new MutableLiveData<>();

    private boolean isFs;
    private int mType;
    private int index = 0;
    private byte[] command = new byte[281];
    //第一维0表示锥尖，1表示侧壁；第二维0表示标准读数，1表示加荷读数，2表示卸荷读数；第三维表示各级差读数。
    private int[][][] Acc = new int[2][3][8];
    private boolean isFa;
    private int ds = 0;
    private String snToW;
    private int obliquityX = 0;
    private int obliquityY = 0;
    private int obliquityZ = 0;
    private String strModel;

    public SetCalibrationDataVM(@NonNull Application application, BluetoothUtil bluetoothUtil, BluetoothCommService bluetoothCommService, MemoryDataDao memoryDataDao, CalibrationProbeDao calibrationProbeDao) {
        super(application);
        this.bluetoothUtil = bluetoothUtil;
        this.bluetoothCommService = bluetoothCommService;
        this.memoryDataDao = memoryDataDao;
        this.calibrationProbeDao = calibrationProbeDao;
    }


    public void setDataToProbe() {
        String snValue = ldSN.getValue();
        if (isFs) {//侧壁标定
            if (snValue != null)
                memoryDataDao.getMemoryDataByProbeIdAndType(snValue, "fs").observe(lifecycleOwner, memoryDataEntities -> {

                });


        } else {//锥头标定
            if (snValue != null)
                memoryDataDao.getMemoryDataByProbeIdAndType(snValue, "qc").observe(lifecycleOwner, memoryDataEntities -> {
//                        getQcData(models);
//                        //侧壁数据
//                        for (
//                                int i = 0;
//                                i < 7; i++) {
//                            Acc[1][1][i] = i * 1200;
//                            Acc[1][2][i] = Acc[1][1][i];
//                        }
//
                    sendData();
                });

        }

    }
    @Override
    public void inject(Object... objects) {
//        effectiveValues = new String[5];
//        YBL = new int[6][7];
        String[] strings = (String[]) objects[0];
        ldSN.setValue(strings[1]);
        boolean isDoubleBridge = strings[2].contains("双桥");
        boolean isMultifunctional = strings[2].contains("多功能");
        initProbeParameters(strings[1], isDoubleBridge, isMultifunctional);//参数为探头序列号
    }

    private void initProbeParameters(final String sn, boolean isFs, final boolean isFa) {
        calibrationProbeDao.getCalbrationProbeEntityByProbeId(sn).observe(lifecycleOwner, calibrationProbeEntities -> {
            if (calibrationProbeEntities != null && calibrationProbeEntities.size() > 0) {
                CalibrationProbeEntity calibrationProbeEntity = calibrationProbeEntities.get(0);
                ldNumber.setValue(calibrationProbeEntity.number);
                ldArea.setValue(calibrationProbeEntity.work_area);
                ldDifferential.setValue(calibrationProbeEntity.differential);
                String[] split = calibrationProbeEntity.number.split("-");
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
            } else {
                toast("序列号错误");
            }
        });
        if (isFs) {//双桥
            memoryDataDao.getMemoryDataByProbeIdAndType(sn, "qc").observe(lifecycleOwner, memoryDataEntities -> {
                //有锥头数据判断有无侧壁数据
                if (memoryDataEntities != null && memoryDataEntities.size() > 0) {
                    memoryDataDao.getMemoryDataByProbeIdAndType(sn, "fs").observe(lifecycleOwner, memoryDataEntities1 -> {
                        if (memoryDataEntities1 != null && memoryDataEntities1.size() > 0) {
                            if (isFa) {
                                switchingChannel(2);//切换到测斜通道
                            }
                        } else {//没有侧壁数据时准备采集侧壁数据
                            switchingChannel(1);//切换到侧壁
                        }
                    });
                } else {
                    toast("没有历史数据");
                }
            });
        } else {//单桥或十字板
            memoryDataDao.getMemoryDataByProbeIdAndType(sn, "qc").observe(lifecycleOwner, new Observer<List<MemoryDataEntity>>() {
                @Override
                public void onChanged(List<MemoryDataEntity> memoryDataEntities) {
                    if (memoryDataEntities != null && memoryDataEntities.size() > 0) {
                        if (isFa) {
                            switchingChannel(2);//切换到测斜通道
                        } else {
                            switchingChannel(0);//切换到锥头通道
                        }
                    } else {
                        toast("没有历史数据");
                    }
                }
            });

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
    public void linkDevice(String mac) {
        BluetoothAdapter bluetoothDevice = bluetoothUtil.
                getBluetoothAdapter();
        if (bluetoothDevice.isEnabled()) {// 蓝牙已打开
            BluetoothDevice remoteDevice = bluetoothUtil.
                    getBluetoothAdapter().getRemoteDevice(mac);
            bluetoothCommService.connect(remoteDevice);
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            action.setValue("ACTION_REQUEST_ENABLE");
        }

    }

    public void resetDataToProbe(int which) {
        //先删除以前数据库里的数据
        String sn = ldSN.getValue();
        switch (which) {
            case 0://全部数据
                ExecutorService DB_IO = Executors.newFixedThreadPool(2);
                DB_IO.execute(() -> {
                    memoryDataDao.deleteMemoryDataEntityByProbeId(sn);
                    DB_IO.shutdown();//关闭线程
                });
                switchingChannel(0);//切换到锥头通道
                break;
            case 1://锥头
                DB_IO = Executors.newFixedThreadPool(2);
                DB_IO.execute(() -> {
                    memoryDataDao.deleteMemoryDataEntityByProbeIdAndType(sn, "qc");
                    DB_IO.shutdown();//关闭线程
                });
                switchingChannel(0);//切换到锥头通道
                break;
            case 2://侧壁
                DB_IO = Executors.newFixedThreadPool(2);
                DB_IO.execute(() -> {
                    memoryDataDao.deleteMemoryDataEntityByProbeIdAndType(sn, "fs");
                    DB_IO.shutdown();//关闭线程
                });
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
                    toast("重置成功");
                }
            }
        }, 0, 1000);// 0秒后执行，每1秒执行一次
    }

    private void sendData() {
        String sn = ldSN.getValue();
        String area = ldArea.getValue();
        Acc[0][1][7] = Acc[0][0][7] / Acc[0][0][6] * Acc[0][1][6];
        Acc[0][2][7] = Acc[0][0][7] / Acc[0][0][6] * Acc[0][2][6];
        Acc[1][1][7] = Acc[1][0][7] / Acc[1][0][6] * Acc[1][1][6];
        Acc[1][2][7] = Acc[1][0][7] / Acc[1][0][6] * Acc[1][2][6];
        command[0] = 83;
        command[1] = 69;
        command[2] = 84;
        command[3] = 85;
        command[4] = 80;
        if (sn != null)
            if (sn.length() != 0) {
                sn = sn + "        ";
                sn = sn.substring(0, 8);
                if (area != null)
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
                String number = ldNumber.getValue();
                if (number != null) {
                    String[] split = number.split("-");
                    if (split[2] != null) {
                        snToW = snToW + split[2];
                    }
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
                            toast("设置成功");
                        }
                    }
                }, 0, 1000);// 0秒后执行，每1秒执行一次

            }
    }

    public void switchingChannel(int which) {
        String sn = ldSN.getValue();
        switch (which) {
            case 0://锥头
                isFs = false;
                memoryDataDao.getMemoryDataByProbeIdAndType(sn, "qc").observe(lifecycleOwner, memoryDataEntities -> {
                    if (memoryDataEntities != null && memoryDataEntities.size() > 0) {
                        resetQcView.setValue(memoryDataEntities);
                    } else {
                        resetQcView.setValue(null);
                    }
                });

                ldInitial.setValue("0");
                initDifferential();
                index = 0;
                break;
            case 1://侧壁通道
                isFs = true;
                memoryDataDao.getMemoryDataByProbeIdAndType(sn, "fs").observe(lifecycleOwner, memoryDataEntities -> {
                    if (memoryDataEntities != null && memoryDataEntities.size() > 0) {
                        resetFsView.setValue(memoryDataEntities);
                    } else {
                        resetFsView.setValue(null);
                    }
                });

                ldInitial.setValue("0");
                initDifferential();
                index = 0;
                break;
            case 2://测斜通道
                isFa = true;
                action.setValue("showFaChannel");
                break;
        }

    }

    private void sendMessage(byte[] message) {
        // 没有连接设备，不能发送

        if (bluetoothCommService.getState() != BluetoothCommService.STATE_CONNECTED) {
            toast("未连接");
            return;
        }
        bluetoothCommService.write(message);

//        mOutStringBuffer.setLength(0);
    }

    private void initDifferential() {
        String area = ldArea.getValue();
        if (area != null) {
            if (StringUtil.isInteger(area)) {
                if (isFs) {
                    ldDifferential.setValue(String.valueOf(10 * mType * Integer.parseInt(area) / 5));
                } else {
                    ldDifferential.setValue(String.valueOf(10 * mType * Integer.parseInt(area) / 5));
                }
            } else {
                toast("探头编号有误");
            }
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

package www.jingkan.com.view_model;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import www.jingkan.com.db.dao.CalibrationProbeDao;
import www.jingkan.com.db.dao.MemoryDataDao;
import www.jingkan.com.db.entity.CalibrationProbeEntity;
import www.jingkan.com.db.entity.MemoryDataEntity;
import www.jingkan.com.util.ByteArrayConveter;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.util.SystemConstant;
import www.jingkan.com.util.VibratorUtil;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view_model.base.BaseViewModel;

import static www.jingkan.com.util.bluetooth.BluetoothCommService.MESSAGE_READ;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.MESSAGE_STATE_CHANGE;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECTED;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECTING;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECT_FAILED;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_CONNECT_LOST;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_LISTEN;
import static www.jingkan.com.util.bluetooth.BluetoothCommService.STATE_NONE;

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
    private VibratorUtil vibratorUtil;
    private String[] effectiveValues = new String[5];
    private int[][] YBL;
    private boolean haveQCData;
    private List<MutableLiveData<String>> points = new ArrayList<>();
    private List<MutableLiveData<String>> Y = new ArrayList<>();

    public final MutableLiveData<Boolean> ldIsShock = new MutableLiveData<>();

    public final MutableLiveData<String> ldSN = new MutableLiveData<>();
    public final MutableLiveData<String> ldNumber = new MutableLiveData<>();
    public final MutableLiveData<String> ldArea = new MutableLiveData<>();
    public final MutableLiveData<String> ldDifferential = new MutableLiveData<>();
    //    public final MutableLiveData<String> ldInitial = new MutableLiveData<>();
    public final MutableLiveData<String> ldValid = new MutableLiveData<>();
    public final MutableLiveData<String> ldChannel = new MutableLiveData<>();
    public final MutableLiveData<String> ldFaX = new MutableLiveData<>();
    public final MutableLiveData<String> ldFaY = new MutableLiveData<>();
    public final MutableLiveData<String> ldFaZ = new MutableLiveData<>();

    public final MutableLiveData<String> ldFaEffectiveX = new MutableLiveData<>();
    public final MutableLiveData<String> ldFaEffectiveY = new MutableLiveData<>();
    public final MutableLiveData<String> ldFaEffectiveZ = new MutableLiveData<>();

    public final MutableLiveData<String> ldFaInitialX = new MutableLiveData<>();
    public final MutableLiveData<String> ldFaInitialY = new MutableLiveData<>();
    public final MutableLiveData<String> ldFaInitialZ = new MutableLiveData<>();

    public final MutableLiveData<String> ldBZHZ1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldBZHZ6 = new MutableLiveData<>();
//    public final MutableLiveData<String> ldBZHZ7 = new MutableLiveData<>();

    public final MutableLiveData<String> ldJH1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJH6 = new MutableLiveData<>();
//    public final MutableLiveData<String> ldJH7 = new MutableLiveData<>();

    public final MutableLiveData<String> ldXH1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXH6 = new MutableLiveData<>();
//    public final MutableLiveData<String> ldXH7 = new MutableLiveData<>();

    public final MutableLiveData<String> ldJh1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldJh6 = new MutableLiveData<>();
//    public final MutableLiveData<String> ldJh7 = new MutableLiveData<>();

    public final MutableLiveData<String> ldXh1 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh2 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh3 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh4 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh5 = new MutableLiveData<>();
    public final MutableLiveData<String> ldXh6 = new MutableLiveData<>();
//    public final MutableLiveData<String> ldXh7 = new MutableLiveData<>();

    private boolean isFsChannel;
    private boolean isDoubleBridge;
    private boolean isMultifunctional;
    private int mType;
    private int index = 0;
    private byte[] command = new byte[87];
    //第一维0表示锥尖，1表示侧壁；第二维0表示标准荷载，1表示加荷读数，2表示卸荷读数；第三维表示各级差读数。
    private int[][][] Acc;
    private boolean isFaChannel;
    private int ds = 0;
    private String snToW;
    private int obliquityX = 0;
    private int obliquityY = 0;
    private int obliquityZ = 0;
    private String strModel;

    public SetCalibrationDataVM(@NonNull Application application,
                                BluetoothUtil bluetoothUtil,
                                BluetoothCommService bluetoothCommService,
                                MemoryDataDao memoryDataDao,
                                CalibrationProbeDao calibrationProbeDao,
                                VibratorUtil vibratorUtil) {
        super(application);
        this.bluetoothUtil = bluetoothUtil;
        this.bluetoothCommService = bluetoothCommService;
        this.memoryDataDao = memoryDataDao;
        this.calibrationProbeDao = calibrationProbeDao;
        this.vibratorUtil = vibratorUtil;
    }


    public void setDataToProbe() {
        String snValue = ldSN.getValue();
        memoryDataDao.getMemoryDataByProbeIdAndType(snValue, "qc").observe(lifecycleOwner, memoryDataEntities -> {
            if (memoryDataEntities != null && memoryDataEntities.size() > 0) {
                getQcData(memoryDataEntities);
                if (isDoubleBridge) {//双桥标定
                    memoryDataDao.getMemoryDataByProbeIdAndType(snValue, "fs").observe(lifecycleOwner, memoryDataEntities1 -> {
                        if (memoryDataEntities1 != null && memoryDataEntities1.size() > 0) {
                            for (int i = 0; i < memoryDataEntities1.size(); i++) {
                                if (i < memoryDataEntities1.size() / 4) {//侧壁加荷
                                    Acc[1][1][i] = memoryDataEntities1.get(i).ADValue;
                                } else {//侧壁卸荷
                                    Acc[1][2][i - memoryDataEntities1.size() / 4] = memoryDataEntities1.get(i).ADValue;
                                }
                            }
                            sendData();
                        } else {
                            toast("没有侧壁数据");
                        }
                    });
                } else {
                    sendData();
                }
            } else {
                toast("没有锥头数据");
            }
        });
    }

    private void getQcData(List<MemoryDataEntity> memoryDataEntities) {
        for (int i = 0; i < memoryDataEntities.size(); i++) {
            if (i < memoryDataEntities.size() / 2) {//锥头加荷
                Acc[0][1][i] = memoryDataEntities.get(i).ADValue;
            } else {//锥头卸荷
                Acc[0][2][i - memoryDataEntities.size() / 2] = memoryDataEntities.get(i).ADValue;
            }
        }
    }

    @Override
    public void inject(Object... objects) {
        String[] strings = (String[]) objects[0];
        ldSN.setValue(strings[1]);
        isDoubleBridge = strings[2].contains("双桥");
        isMultifunctional = strings[2].contains("多功能");
        initProbeParameters(strings[1]);//参数为探头序列号
        ldValid.setValue("0");
        initPoints();
        bluetoothCommService.getBluetoothMessageMutableLiveData().observe(lifecycleOwner, bluetoothMessage -> {
            switch (bluetoothMessage.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (bluetoothMessage.arg1) {
                        case STATE_NONE:
                            break;
                        case STATE_LISTEN:// 监听连接
                            break;
                        case STATE_CONNECTING: // now initiating an outgoing connection
                            toast("正在连接");
                            break;
                        case STATE_CONNECTED:   // 已连接上远程设备
                            action.setValue("closeWaitDialog");
//                            closeWaitDialog();
                            toast("连接成功");
                            break;
                        case STATE_CONNECT_FAILED: // 连接失败
                            action.setValue("closeWaitDialog");
//                            closeWaitDialog();
                            toast("连接失败");
                            break;
                        case STATE_CONNECT_LOST: // 失去连接
                            toast("失去连接");
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
                        if (mDate.contains("Sn")) {
                            ldValid.setValue("已标定");
                            break;
                        }

                        String[] split = mDate.split(" ");
                        for (int i = 0, j = 0; i < split.length; i++) {
                            String aSplit = split[i];
                            if (StringUtil.isInteger(aSplit)) {
                                effectiveValues[j] = aSplit;
                                if (j == 4) {
                                    break;
                                }
                                j++;
                            }
                        }
                        if (isFsChannel) {//侧壁读数
                            if (StringUtil.isInteger(effectiveValues[1])) {
                                ldValid.setValue(effectiveValues[1]);
                            }
                        } else {//锥尖读数
                            if (StringUtil.isInteger(effectiveValues[0])) {
                                ldValid.setValue(effectiveValues[0]);
                            }
                        }
                        if (isFaChannel) {
                            if (StringUtil.isInteger(effectiveValues[2])
                                    && StringUtil.isInteger(effectiveValues[3])
                                    && StringUtil.isInteger(effectiveValues[4])) {
                                ldFaX.setValue(effectiveValues[2]);
                                ldFaY.setValue(effectiveValues[4]);
                                ldFaZ.setValue(effectiveValues[3]);
                            }
                        }

                    }
                    break;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void initPoints() {
        points.add(ldJH1);
        points.add(ldJH2);
        points.add(ldJH3);
        points.add(ldJH4);
        points.add(ldJH5);
        points.add(ldJH6);
//        points.add(ldJH7);

        points.add(ldXH6);
        points.add(ldXH5);
        points.add(ldXH4);
        points.add(ldXH3);
        points.add(ldXH2);
        points.add(ldXH1);
//        points.add(ldXH7);

        points.add(ldJh1);
        points.add(ldJh2);
        points.add(ldJh3);
        points.add(ldJh4);
        points.add(ldJh5);
        points.add(ldJh6);
//        points.add(ldJh7);

        points.add(ldXh6);
        points.add(ldXh5);
        points.add(ldXh4);
        points.add(ldXh3);
        points.add(ldXh2);
        points.add(ldXh1);
//        points.add(ldXh7);

        YBL = new int[6][points.size()];
        //第一维0表示锥尖，1表示侧壁；第二维0表示标准荷载，1表示加荷读数，2表示卸荷读数；第三维表示各级差读数。
        Acc = new int[2][3][points.size()];
    }

    private void initProbeParameters(final String sn) {
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
//                                    initAcc(3);
                                    break;
                                case "4":
                                    mType = 4;
                                    initDifferential();
                                    strModel = SystemConstant.SINGLE_BRIDGE_4;
//                                    initAcc(4);
                                    break;
                                case "6":
                                    mType = 6;
                                    initDifferential();
                                    strModel = SystemConstant.SINGLE_BRIDGE_6;
//                                    initAcc(6);
                                    break;
                            }
                            break;
                        case "S":
                            switch (strModel1) {
                                case "3":
                                    mType = 3;
                                    initDifferential();
                                    strModel = SystemConstant.DOUBLE_BRIDGE_3;
//                                    initAcc(3);
                                    break;
                                case "4":
                                    mType = 4;
                                    initDifferential();
                                    strModel = SystemConstant.DOUBLE_BRIDGE_4;
//                                    initAcc(4);
                                    break;
                                case "6":
                                    mType = 6;
                                    initDifferential();
                                    strModel = SystemConstant.DOUBLE_BRIDGE_6;
//                                    initAcc(6);
                                    break;
                            }
                            break;
                        case "V":
                            strModel = SystemConstant.VANE;
                            break;
                    }
                }
            } else {
                toast("序列号错误");
            }
        });
        if (isDoubleBridge) {//双桥
            memoryDataDao.getMemoryDataByProbeIdAndType(sn, "qc").observe(lifecycleOwner, memoryDataEntities -> {
                //有锥头数据判断有无侧壁数据
                if (memoryDataEntities != null && memoryDataEntities.size() > 0) {
                    switchingChannel(0, false);
                    setPoint(memoryDataEntities);
                    haveQCData = true;
                } else {
                    switchingChannel(0, true);
                    haveQCData = false;
                    toast("没有历史数据");
                }
                memoryDataDao.getMemoryDataByProbeIdAndType(sn, "fs").observe(lifecycleOwner, memoryDataEntities1 -> {

                    if (memoryDataEntities1 != null && memoryDataEntities1.size() > 0) {
                        setPoint(memoryDataEntities1);
                        if (isMultifunctional) {
                            switchingChannel(2, false);//切换到测斜通道
                        }
                    } else if (haveQCData) {//没有侧壁数据时准备采集侧壁数据
                        switchingChannel(1, true);//切换到侧壁
                    }
                });
            });
        } else {//单桥或十字板
            memoryDataDao.getMemoryDataByProbeIdAndType(sn, "qc").observe(lifecycleOwner, memoryDataEntities -> {
                if (memoryDataEntities != null && memoryDataEntities.size() > 0) {
                    setPoint(memoryDataEntities);
                    if (isMultifunctional) {
                        switchingChannel(2, false);//切换到测斜通道
                    } else {
                        switchingChannel(0, false);//切换到锥头通道
                    }
                } else {
                    toast("没有历史数据");
                }
            });

        }


    }

    private void setPoint(List<MemoryDataEntity> memoryDataEntities) {
        for (int i = 0; i < memoryDataEntities.size(); i++) {
            MemoryDataEntity memoryDataEntity = memoryDataEntities.get(i);
            if (i < points.size() / 4) {
                points.get(i).setValue(String.valueOf(memoryDataEntity.ADValue));
            } else if (i < points.size() / 2) {
                points.get(points.size() / 4 * 3 - 1 - i).setValue(String.valueOf(memoryDataEntity.ADValue));
            } else if (i < points.size() / 4 * 3) {
                points.get(i).setValue(String.valueOf(memoryDataEntity.ADValue));
            } else {
                points.get(points.size() / 4 * 7 - 1 - i).setValue(String.valueOf(memoryDataEntity.ADValue));
            }

        }
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
        //全部数据
        if (which == 0) {
            ExecutorService DB_IO = Executors.newFixedThreadPool(2);
            DB_IO.execute(() -> {
                memoryDataDao.deleteMemoryDataEntityByProbeId(sn);
                DB_IO.shutdown();//关闭线程
            });
        }
        command[0] = 'S';
        command[1] = 'E';
        command[2] = 'T';
        command[3] = 'U';
        command[4] = 'P';
        for (int i = 5; i < command.length; i++) {
            command[i] = 0;
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
                    toast("重置成功");
                }
            }
        }, 0, 1000);// 0秒后执行，每1秒执行一次
    }

    private void sendData() {
        String sn = ldSN.getValue();
        String area = ldArea.getValue();
        command[0] = 'S';
        command[1] = 'E';
        command[2] = 'T';
        command[3] = 'U';
        command[4] = 'P';
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
                command[17] = (byte) (obliquityX / 256);
                command[18] = (byte) (obliquityX % 256);
                command[21] = (byte) (obliquityY / 256);
                command[22] = (byte) (obliquityY % 256);
                command[19] = (byte) (obliquityZ / 256);
                command[20] = (byte) (obliquityZ % 256);

                WeightedObservedPoints obs = new WeightedObservedPoints();
                for (int i = 0; i < points.size() / 4; i++) {
                    obs.add(Acc[0][1][i], Acc[0][0][i]);
                }
                float[] QCJH = getCoefficient(obs);
                int destPosNow = convert(QCJH, 23);

                obs.clear();
                for (int i = 0; i < points.size() / 4; i++) {
                    obs.add(Acc[0][2][i], Acc[0][0][i]);
                }
                float[] QCXH = getCoefficient(obs);
                destPosNow = convert(QCXH, destPosNow + 4);

                obs.clear();
                for (int i = 0; i < points.size() / 4; i++) {
                    obs.add(Acc[1][1][i], Acc[1][0][i]);
                }
                float[] FSJH = getCoefficient(obs);
                destPosNow = convert(FSJH, destPosNow + 4);

                obs.clear();
                for (int i = 0; i < points.size() / 4; i++) {
                    obs.add(Acc[1][2][i], Acc[1][0][i]);
                }
                float[] FSXH = getCoefficient(obs);
                convert(FSXH, destPosNow + 4);
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

    private int convert(float[] floats, int destPos) {
        int destPosNow = -1;
        for (int i = 0; i < floats.length; i++) {
            byte[] byteArray = ByteArrayConveter.getByteArray(floats[i]);
            //每个浮点数四个字节
            destPosNow = destPos + i * 4;
            System.arraycopy(byteArray, 0, command, destPosNow, byteArray.length);
        }
        return destPosNow;
    }

    private void switchingChannel(int which, boolean isReset) {
        if (isReset) {
            for (MutableLiveData<String> text :
                    points) {
                text.setValue("null");
            }
        }

        switch (which) {
            case 0://锥头
                action.setValue("disShowFaChannel");
                isFsChannel = false;
                isFaChannel = false;
                ldChannel.setValue("锥头");
                initDifferential();
                index = 0;
                break;
            case 1://侧壁通道
                action.setValue("disShowFaChannel");
                isFsChannel = true;
                isFaChannel = false;
                ldChannel.setValue("侧壁");
                initDifferential();
                index = 0;
                break;
            case 2://测斜通道
                action.setValue("showFaChannel");
                isFaChannel = true;
                isFsChannel = false;
//                ldChannel.setValue("测斜");
                break;
        }

    }

    public void doRecord() {
        if (bluetoothCommService.getState() != BluetoothCommService.STATE_CONNECTED) {
            toast("未连接设备");
            return;
        }
        Boolean shockValue = ldIsShock.getValue();
        if (shockValue != null && shockValue) {
            vibratorUtil.Vibrate(200);
        }
        String validValue = ldValid.getValue();
        if (validValue != null) {
            if (index < points.size() / 4) {//加荷1
                YBL[0][index] = Integer.parseInt(validValue);
            } else if (index < points.size() / 2) {//卸荷1
                YBL[1][points.size() / 2 - 1 - index] = Integer.parseInt(validValue);
            } else if (index < points.size() / 4 * 3) {//加荷2
                YBL[2][index - points.size() / 2] = Integer.parseInt(validValue);
            } else if (index < points.size()) {//卸荷2
                YBL[3][points.size() - 1 - index] = Integer.parseInt(validValue);
                if (index == points.size() - 1) {
                    getAverageValue();
                    if (isFsChannel) {
                        storeData("fs");
                        if (isMultifunctional) {
                            action.setValue("showSwitchDialogFa");
                        }
                    } else {
                        storeData("qc");
                        if (isDoubleBridge) {
                            action.setValue("showSwitchDialogFs");
                        } else if (isMultifunctional) {
                            action.setValue("showSwitchDialogFa");
                        }
                    }

                }
            }
        }
        if (index < points.size()) {
            points.get(index).setValue(validValue);
            index++;
        }

    }

    private void storeData(String type) {
        MemoryDataEntity memoryDataEntity;
        String snValue = ldSN.getValue();
        String numberValue = ldNumber.getValue();
        if (type.equals("qc")) {
            for (int i = 0; i < YBL.length; i++) {
                Acc[0][1][i] = YBL[4][i];// 锥头加荷平均
                memoryDataEntity = new MemoryDataEntity();

                if (snValue != null) {
                    memoryDataEntity.probeID = snValue;
                }
                if (numberValue != null) {
                    memoryDataEntity.probeNo = numberValue;
                }
                memoryDataEntity.id = i;
                memoryDataEntity.type = type;
                memoryDataEntity.forceType = "加荷";
                memoryDataEntity.ADValue = YBL[4][i];
                ExecutorService DB_IO = Executors.newFixedThreadPool(2);
                MemoryDataEntity finalMemoryDataEntity = memoryDataEntity;
                DB_IO.execute(() -> {
                    memoryDataDao.insertMemoryDataEntity(finalMemoryDataEntity);
                    DB_IO.shutdown();//关闭线程
                });

            }
            for (int i = 0; i < YBL.length; i++) {
                Acc[0][2][i] = YBL[5][i];// 锥头卸荷平均
                memoryDataEntity = new MemoryDataEntity();
                if (snValue != null) {
                    memoryDataEntity.probeID = snValue;
                }
                if (numberValue != null) {
                    memoryDataEntity.probeNo = numberValue;
                }
                memoryDataEntity.id = i + YBL.length;
                memoryDataEntity.type = type;
                memoryDataEntity.forceType = "卸荷";
                memoryDataEntity.ADValue = YBL[5][i];
                ExecutorService DB_IO = Executors.newFixedThreadPool(2);
                MemoryDataEntity finalMemoryDataEntity = memoryDataEntity;
                DB_IO.execute(() -> {
                    memoryDataDao.insertMemoryDataEntity(finalMemoryDataEntity);
                    DB_IO.shutdown();//关闭线程
                });
            }
        } else {
            for (int i = 0; i < YBL.length; i++) {
                Acc[1][1][i] = YBL[4][i];// 侧壁加荷平均
                memoryDataEntity = new MemoryDataEntity();
                if (snValue != null) {
                    memoryDataEntity.probeID = snValue;
                }
                if (numberValue != null) {
                    memoryDataEntity.probeNo = numberValue;
                }
                memoryDataEntity.id = i + YBL.length * 2;
                memoryDataEntity.type = type;
                memoryDataEntity.forceType = "加荷";
                memoryDataEntity.ADValue = YBL[4][i];
                ExecutorService DB_IO = Executors.newFixedThreadPool(2);
                MemoryDataEntity finalMemoryDataEntity = memoryDataEntity;
                DB_IO.execute(() -> {
                    memoryDataDao.insertMemoryDataEntity(finalMemoryDataEntity);
                    DB_IO.shutdown();//关闭线程
                });
            }
            for (int i = 0; i < YBL.length; i++) {
                Acc[1][2][i] = YBL[5][i];// 侧壁卸荷平均
                memoryDataEntity = new MemoryDataEntity();
                if (snValue != null) {
                    memoryDataEntity.probeID = snValue;
                }
                if (numberValue != null) {
                    memoryDataEntity.probeNo = numberValue;
                }
                memoryDataEntity.id = i + YBL.length * 3;
                memoryDataEntity.type = type;
                memoryDataEntity.forceType = "卸荷";
                memoryDataEntity.ADValue = YBL[5][i];
                ExecutorService DB_IO = Executors.newFixedThreadPool(2);
                MemoryDataEntity finalMemoryDataEntity = memoryDataEntity;
                DB_IO.execute(() -> {
                    memoryDataDao.insertMemoryDataEntity(finalMemoryDataEntity);
                    DB_IO.shutdown();//关闭线程
                });
            }
        }

    }

    private void getAverageValue() {
        for (int i = 0; i < YBL[0].length; i++) {
            YBL[4][i] = (YBL[0][i] + YBL[2][i]) / 2;
            YBL[5][i] = (YBL[1][i] + YBL[3][i]) / 2;
        }
        //去零
//        for (int i = 0; i < YBL[0].length; i++) {
//            YBL[4][i] = YBL[4][i] - YBL[4][0];
//            YBL[5][i] = YBL[5][i] - YBL[5][0];
//        }
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
                if (isFsChannel) {
                    ldDifferential.setValue(String.valueOf(10 * mType * 2));
                } else {
                    ldDifferential.setValue(String.valueOf(mType * 2));
                }
                Y.clear();
                Y.add(ldBZHZ1);
                Y.add(ldBZHZ2);
                Y.add(ldBZHZ3);
                Y.add(ldBZHZ4);
                Y.add(ldBZHZ5);
                Y.add(ldBZHZ6);
//                Y.add(ldBZHZ7);
                String differentialValue = ldDifferential.getValue();

                if (differentialValue != null) {
                    int parseInt = Integer.parseInt(differentialValue);
                    int kg;
                    float kn;
                    if (isFsChannel) {
                        kn = parseInt * Integer.parseInt(area) * 20 / 10000f;
                        kg = (int) (kn * 1000 / 9.8);
                    } else {
                        kn = parseInt * Integer.parseInt(area) / 10f;
                        kg = (int) (kn * 1000 / 9.8);
                    }
                    for (int i = 0; i < Y.size(); i++) {
                        Y.get(i).setValue(String.valueOf(kg * i));
                        Acc[1][0][i] = parseInt * i;
                    }
                }

            } else {
                toast("探头编号有误");
            }
        }

    }


    @Override
    public void clear() {

    }

    private float[] getCoefficient(WeightedObservedPoints obs) {

        // Instantiate a third-degree polynomial fitter.
        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(3);
        // Retrieve fitted parameters (coefficients of the polynomial function).

        double[] fit = fitter.fit(obs.toList());
        float[] floats = new float[fit.length];
        for (int i = 0; i < fit.length; i++) {
            floats[i] = (float) fit[i];
        }
        return floats;
    }

}

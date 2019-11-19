package www.jingkan.com.view_model;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import www.jingkan.com.db.dao.CalibrationProbeDao;
import www.jingkan.com.db.dao.CalibrationVerificationDao;
import www.jingkan.com.db.entity.CalibrationProbeEntity;
import www.jingkan.com.db.entity.CalibrationVerificationEntity;
import www.jingkan.com.util.MyFileUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.util.TimeUtil;
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
 * {@link www.jingkan.com.view.CalibrationVerificationActivity}
 */
public class CalibrationVerificationVM extends BaseViewModel {
    private BluetoothUtil bluetoothUtil;
    private BluetoothCommService bluetoothCommService;
    private CalibrationVerificationDao calibrationVerificationDao;
    private CalibrationProbeDao calibrationProbeDao;
    private VibratorUtil vibratorUtil;
    private String mac;

    CalibrationVerificationVM(@NonNull Application application, BluetoothUtil bluetoothUtil, BluetoothCommService bluetoothCommService, CalibrationProbeDao calibrationProbeDao, CalibrationVerificationDao calibrationVerificationDao, VibratorUtil vibratorUtil) {
        super(application);
        this.bluetoothUtil = bluetoothUtil;
        this.bluetoothCommService = bluetoothCommService;
        this.calibrationVerificationDao = calibrationVerificationDao;
        this.calibrationProbeDao = calibrationProbeDao;
        this.vibratorUtil = vibratorUtil;
    }


    public final MutableLiveData<Boolean> ldIsShock = new MutableLiveData<>();
    public final MutableLiveData<String> ldSN = new MutableLiveData<>();
    public final MutableLiveData<String> ldNumber = new MutableLiveData<>();
    public final MutableLiveData<String> ldArea = new MutableLiveData<>();
    public final MutableLiveData<String> ldDifferential = new MutableLiveData<>();
    public final MutableLiveData<String> ldInitial = new MutableLiveData<>();
    public final MutableLiveData<String> ldValid = new MutableLiveData<>();
    public final MutableLiveData<Float[]> ldData = new MutableLiveData<>();
    @Override
    public void inject(Object... objects) {
        String[] strings = (String[]) objects[0];
        isFs = strings[2].contains("侧壁");
        mac = strings[0];
        initProbeParameters(strings[1], isFs);
        list = new ArrayList<>();
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
                            mDate = mDate.replaceAll(" ", "");
                        }
                        analyticData(mDate);
                    }
                    break;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    private float effectiveValue = 0;
    private float initialValue = 0;
    private boolean isFs;
    private List<String[]> list;


    private void analyticData(String mDate) {
        if (isFs) {//侧壁读数
            if (mDate.contains("Fs:")) {
                String fs = null;
                if (mDate.contains("kPa")) {
                    fs = mDate.substring(mDate.indexOf("Fs:") + 3, mDate.indexOf("kPa"));
                } else if (mDate.contains("KPa")) {
                    fs = mDate.substring(mDate.indexOf("Fs:") + 3, mDate.indexOf("KPa"));
                }
                if (StringUtil.isFloat(fs)) {
                    if (fs != null) {
                        effectiveValue = Float.parseFloat(fs) - initialValue;
                        ldValid.setValue(String.valueOf(effectiveValue));
                    }
                }
            }

        } else {//锥尖读数
            if (mDate.contains("Ps:") && mDate.contains("MPa")) {
                String qc = mDate.substring(mDate.indexOf("Ps:") + 3, mDate.indexOf("MPa"));
                if (StringUtil.isFloat(qc)) {
                    effectiveValue = Float.parseFloat(qc) - initialValue;
                    ldValid.setValue(String.valueOf(effectiveValue));
                }
            } else if (mDate.contains("Qc:") && mDate.contains("MPa")) {
                String qc = mDate.substring(mDate.indexOf("Qc:") + 3, mDate.indexOf("MPa"));
                effectiveValue = Float.parseFloat(qc) - initialValue;
                ldValid.setValue(String.valueOf(effectiveValue));
            }
        }
    }


    @Override
    public void clear() {
        bluetoothCommService.stop();
        bluetoothCommService = null;
    }


    //    private String probeNo;
//    private int differential;
//    private String workArea;

    public void deleteDBData(String type) {
        if (type.equals("侧壁")) {
            calibrationVerificationDao.deleteCVEntityByProbeNoAndType(ldNumber.getValue(), "侧壁");
        } else {
            calibrationVerificationDao.deleteCVEntityByProbeNoAndType(ldNumber.getValue(), "锥头");
        }

    }

    private void deleteData() {

        if (isFs) {
            calibrationVerificationDao.getCVEntityByProbeNoAndType(ldNumber.getValue(), "侧壁").observe(lifecycleOwner, calibrationVerificationEntities -> {
                if (calibrationVerificationEntities != null && calibrationVerificationEntities.size() != 0) {
                    action.setValue("清除侧壁数据");
                }
            });
        } else {
            calibrationVerificationDao.getCVEntityByProbeNoAndType(ldNumber.getValue(), "锥头").observe(lifecycleOwner, calibrationVerificationEntities -> {
                if (calibrationVerificationEntities != null && calibrationVerificationEntities.size() != 0) {
                    action.setValue("清除锥头数据");
                }
            });
        }

    }


    private void initProbeParameters(final String sn, final boolean isFS) {
        this.isFs = isFS;
        calibrationProbeDao.getCalbrationProbeEntityByProbeId(sn).observe(lifecycleOwner, calibrationProbeEntities -> {
            if (calibrationProbeEntities != null && calibrationProbeEntities.size() != 0) {
                CalibrationProbeEntity calibrationProbeModel = calibrationProbeEntities.get(0);
                ldNumber.setValue(calibrationProbeModel.number);
                ldArea.setValue(calibrationProbeModel.work_area);
                if (isFS) {
                    ldDifferential.setValue(String.valueOf(Integer.parseInt(calibrationProbeModel.differential) * 10));
//                    differential = Integer.parseInt(calibrationProbeModel.differential) * 10;
                } else {
                    ldDifferential.setValue(String.valueOf(Integer.parseInt(calibrationProbeModel.differential)));
                }
            }
        });

        deleteData();
    }


    public void getInitialValue() {
        initialValue = effectiveValue;
        ldInitial.setValue(String.valueOf(initialValue));
    }

    private int index = 0;


    public void doRecord() {
        boolean test = true;
        if (test) {
            if (index != 0)
                return;
            String ldDifferentialValue = ldDifferential.getValue();
            if (ldDifferentialValue != null) {
                int differential = Integer.parseInt(ldDifferentialValue);
                Random ra = new Random();
                int x;
                for (; index < 28; index++) {
                    double v = 0.3 * ra.nextDouble();
                    if (index < 7) {
                        x = index * differential;
                        list.add(new String[]{String.valueOf(x), "加荷", StringUtil.format(x + v, 2)});
                    } else if (index < 14) {
                        x = (13 - index) * differential;
                        list.add(new String[]{String.valueOf(x), "卸荷", StringUtil.format(x + v, 2)});
                    } else if (index < 21) {
                        x = (index - 14) * differential;
                        list.add(new String[]{String.valueOf(x), "加荷", StringUtil.format(x + v, 2)});
                    } else {
                        x = (27 - index) * differential;
                        list.add(new String[]{String.valueOf(x), "卸荷", StringUtil.format(x + v, 2)});
                        if (index == 27) {
                            for (int i = 0; i < list.size(); i++) {
                                putDateBase(i, list.get(i));
                            }
                            toast("标定结束");
                        }
                    }
                }
                Boolean shockValue = ldIsShock.getValue();
                if (shockValue != null && shockValue) {
                    vibratorUtil.Vibrate(200);
                }
            }

        } else {
            String ldDifferentialValue = ldDifferential.getValue();
            if (ldDifferentialValue != null) {
                int differential = Integer.parseInt(ldDifferentialValue);
                int x = index * differential;
                if (index < 7) {
                    ldData.setValue(new Float[]{(float) x, effectiveValue, (float) 0});
//                myView.get().showLoadingLine(x, effectiveValue, "加荷1");
                    list.add(new String[]{String.valueOf(x), "加荷", String.valueOf(effectiveValue)});
                } else if (index < 14) {
                    x = (13 - index) * differential;
                    ldData.setValue(new Float[]{(float) x, effectiveValue, (float) 1});
//                myView.get().showLoadingLine(x, effectiveValue, "卸荷1");
                    list.add(new String[]{String.valueOf(x), "卸荷", String.valueOf(effectiveValue)});
                } else if (index < 21) {
                    x = (index - 14) * differential;
                    ldData.setValue(new Float[]{(float) x, effectiveValue, (float) 2});
//                myView.get().showLoadingLine(x, effectiveValue, "加荷2");
                    list.add(new String[]{String.valueOf(x), "加荷", String.valueOf(effectiveValue)});
                } else if (index < 28) {
                    x = (27 - index) * differential;
                    ldData.setValue(new Float[]{(float) x, effectiveValue, (float) 3});
//                myView.get().showLoadingLine(x, effectiveValue, "卸荷2");
                    list.add(new String[]{String.valueOf(x), "卸荷", String.valueOf(effectiveValue)});
                    if (index == 27) {
                        for (int i = 0; i < list.size(); i++) {
                            putDateBase(i, list.get(i));
                        }
                        toast("标定结束");
                    }
                } else {
                    toast("标定结束");
                }
                index++;
                Boolean shockValue = ldIsShock.getValue();
                if (shockValue != null && shockValue) {
                    vibratorUtil.Vibrate(200);
                }
            }
        }
    }


    public void linkDevice() {
        BluetoothAdapter bluetoothDevice = bluetoothUtil.getBluetoothAdapter();
        if (bluetoothDevice.isEnabled()) {// 蓝牙已打开
            BluetoothDevice remoteDevice = bluetoothUtil.getBluetoothAdapter().getRemoteDevice(mac);
            bluetoothCommService.connect(remoteDevice);
        } else {
            // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
            action.setValue("startActivityForResult");
        }
    }

    /**
     * 标定数据入库
     *
     * @param i    id
     * @param data 数据数组
     *             1.当前荷载
     *             2.荷载类型
     */
    private void putDateBase(int i, String[] data) {
        CalibrationVerificationEntity calibrationVerificationEntity = new CalibrationVerificationEntity();
        String ldNumberValue = ldNumber.getValue();

        if (ldNumberValue != null) {
            calibrationVerificationEntity.probeNo = ldNumberValue;
        }
        if (isFs) {
            calibrationVerificationEntity.type = "侧壁";
            calibrationVerificationEntity.id = i + 27;
        } else {
            calibrationVerificationEntity.type = "锥头";
            calibrationVerificationEntity.id = i;
        }
        calibrationVerificationEntity.standardValue = data[0];
        calibrationVerificationEntity.forceType = data[1];
        calibrationVerificationEntity.loadValue = Float.parseFloat(data[2]);
        ExecutorService DB_IO = Executors.newFixedThreadPool(2);
        DB_IO.execute(() -> {
            calibrationVerificationDao.insertCVEntity(calibrationVerificationEntity);
            DB_IO.shutdown();//关闭线程
        });
    }

    private String calibrationResult() {
        StringBuilder result;
        String strReturn = "\r\n";
        String strTab = "\t";
        if (isFs) {
            result = new StringBuilder("侧壁标定" + strReturn);
        } else {
            result = new StringBuilder("锥头标定" + strReturn);
        }
        float[] parameters = calculationParameter();
        result.append("标定日期：").append(TimeUtil.getCurrentTime()).append(strReturn);
        result.append("探头编号：").append(ldNumber.getValue()).append(strReturn);
        result.append("工作面积：").append(ldArea.getValue()).append(strReturn);
        result.append("荷载级差：").append(ldDifferential.getValue()).append(strReturn);
        result.append("电缆长度：").append("2").append(strReturn);
        result.append("电缆规格：").append("0.15").append(strReturn).append(strReturn);//换行
        result.append("线性误差：").append(StringUtil.format(parameters[0], 2)).append(strReturn);
        result.append("重复误差：").append(StringUtil.format(parameters[1], 2)).append(strReturn);
        result.append("滞后误差：").append(StringUtil.format(parameters[2], 2)).append(strReturn);
        result.append("归零误差：").append(StringUtil.format(parameters[3], 2)).append(strReturn);
        result.append("起始感量：").append(StringUtil.format(parameters[4], 2)).append(strReturn);
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
            result.append(i).append(strTab).append(StringUtil.format(load[i], 2)).append(strTab).append(StringUtil.format(load1[i], 2)).append(strTab).append(strTab).append(StringUtil.format(unLoad1[i], 2)).append(strTab).append(StringUtil.format(unLoad[i], 2)).append(strReturn);
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
            int differential = Integer.parseInt(Objects.requireNonNull(ldDifferential.getValue()));
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


    public void saveData() {
        String type;
        if (isFs) {
            type = "侧壁";
        } else {
            type = "锥头";
        }

        calibrationVerificationDao.getCVEntityByProbeNoAndTypeAndForceType(ldNumber.getValue(), type, "加荷").observe(lifecycleOwner, calibrationVerificationEntities -> {
            if (calibrationVerificationEntities != null && calibrationVerificationEntities.size() != 0) {
                int size = calibrationVerificationEntities.size();
                load = new float[size / 2];
                load1 = new float[size / 2];
                loadAverage = new float[size / 2];
                for (int i = 0; i < size / 2; i++) {
                    loadAverage[i] =
                            (calibrationVerificationEntities.get(0).loadValue
                                    + calibrationVerificationEntities.get(size / 2).loadValue)
                                    / 2;
                    load[i] = calibrationVerificationEntities.get(0).loadValue;
                    calibrationVerificationEntities.remove(0);
                }
                for (int i = 0; i < calibrationVerificationEntities.size(); i++) {
                    load1[i] = calibrationVerificationEntities.get(i).loadValue;
                }
                for (int i = 0; i < size / 2; i++) {
                    float loadDifferenceValue = Math.abs(load[i] - load1[i]);
                    maxLoadDifferenceValue =
                            loadDifferenceValue > maxLoadDifferenceValue ?
                                    loadDifferenceValue : maxLoadDifferenceValue;
                }
//                haveData = true;
                calibrationVerificationDao.getCVEntityByProbeNoAndTypeAndForceType(ldNumber.getValue(), type, "卸荷").observe(lifecycleOwner, calibrationVerificationEntities1 -> {
                    if (calibrationVerificationEntities1 != null && calibrationVerificationEntities1.size() != 0) {
                        int size1 = calibrationVerificationEntities1.size();
                        unLoad = new float[size1 / 2];
                        unLoad1 = new float[size1 / 2];
                        unLoadAverage = new float[size1 / 2];
                        Collections.reverse(calibrationVerificationEntities1);//倒序
                        for (int i = 0; i < size1 / 2; i++) {
                            unLoadAverage[i] =
                                    (calibrationVerificationEntities1.get(0).loadValue
                                            + calibrationVerificationEntities1.get(size1 / 2).loadValue)
                                            / 2;
                            unLoad[i] = calibrationVerificationEntities1.get(0).loadValue;
                            calibrationVerificationEntities1.remove(0);
                        }

                        for (int i = 0; i < calibrationVerificationEntities1.size(); i++) {
                            unLoad1[i] = calibrationVerificationEntities1.get(i).loadValue;
                        }
                        for (int i = 0; i < size1 / 2; i++) {
                            float unLoadDifference = Math.abs(unLoad[i] - unLoad1[i]);
                            maxUnLoadDifferenceValue = unLoadDifference > maxUnLoadDifferenceValue ?
                                    unLoadDifference : maxUnLoadDifferenceValue;
                        }
//                        haveData = true;
                        String fileName;
                        if (isFs) {
                            fileName = ldNumber.getValue() + "侧壁标定.txt";
                        } else {
                            fileName = ldNumber.getValue() + "锥尖标定.txt";
                        }
                        String content = calibrationResult();
                        MyFileUtil.getInstance().saveToSD(getApplication(), fileName, content, new MyFileUtil.SaveFileCallBack() {
                            @Override
                            public void onSuccess() {
                                toast("保存成功");
                            }

                            @Override
                            public void onFail(String e) {
                                toast(e);
                            }
                        });
                    } else {
//                        haveData = false;
                        toast("标定未完成");
                    }
                });
            } else {
                toast("标定未完成");
//                haveData = false;
            }
        });

    }

}

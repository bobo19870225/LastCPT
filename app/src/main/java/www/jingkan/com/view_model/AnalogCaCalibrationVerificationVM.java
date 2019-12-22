package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.util.Objects;
import java.util.Random;

import www.jingkan.com.db.dao.CalibrationProbeDao;
import www.jingkan.com.db.entity.CalibrationProbeEntity;
import www.jingkan.com.util.MyFileUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.util.TimeUtil;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2019-12-18.
 * LastCPT 2
 * {@link www.jingkan.com.view.AnalogCaCalibrationVerificationActivity}
 */
public class AnalogCaCalibrationVerificationVM extends BaseViewModel {
    public final MutableLiveData<String> ld0 = new MutableLiveData<>();
    public final MutableLiveData<String> ld1 = new MutableLiveData<>();
    public final MutableLiveData<String> ld2 = new MutableLiveData<>();

    public final MutableLiveData<String> ldNumber = new MutableLiveData<>();
    public final MutableLiveData<String> ldArea = new MutableLiveData<>();
    public final MutableLiveData<String> ldDifferential = new MutableLiveData<>();

    private float[] loadAverage;
    private float[] unLoadAverage;
    private float maxLoadDifferenceValue = 0;
    private float maxUnLoadDifferenceValue = 0;
    private int[] load;
    private int[] load1;
    private int[] unLoad;
    private int[] unLoad1;
    private boolean isFs;

    private CalibrationProbeDao calibrationProbeDao;

    AnalogCaCalibrationVerificationVM(@NonNull Application application, CalibrationProbeDao calibrationProbeDao) {
        super(application);
        this.calibrationProbeDao = calibrationProbeDao;
    }

    @Override
    public void inject(Object... objects) {
        String[] strings = (String[]) objects[0];
        isFs = strings[2].contains("侧壁");
        initProbeParameters(strings[1], isFs);
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }


    public void save() {
        int size = 7;
        load = new int[size];
        load1 = new int[size];
        loadAverage = new float[size];
        unLoad = new int[size];
        unLoad1 = new int[size];
        unLoadAverage = new float[size];
        Random ra = new Random();
        WeightedObservedPoints obs = new WeightedObservedPoints();
        int[] data = new int[3];
        if (ld0.getValue() != null && ld1.getValue() != null && ld2.getValue() != null) {
            data[0] = Integer.parseInt(ld0.getValue());
            data[1] = Integer.parseInt(ld1.getValue());
            data[2] = Integer.parseInt(ld2.getValue());
        } else {
            return;
        }
        Integer differential = Integer.valueOf(Objects.requireNonNull(ldDifferential.getValue()));
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                obs.add(i * differential, ra.nextInt(10));
            } else {
                obs.add(i * differential, data[i - 1]);
            }

        }
        // Instantiate a third-degree polynomial fitter.
        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);
        // Retrieve fitted parameters (coefficients of the polynomial function).

        double[] fit = fitter.fit(obs.toList());
        float[] floats = new float[fit.length];
        for (int i = 0; i < fit.length; i++) {
            floats[i] = (float) fit[i];
        }

        for (int i = 0; i < size; i++) {
            load[i] = (int) (floats[0] + i * differential * floats[1]);
            load1[i] = (int) (floats[0] + i * differential * floats[1] + ra.nextInt(10));
            unLoad[i] = (int) (floats[0] + 50 + i * differential * floats[1] + ra.nextInt(20));
            unLoad1[i] = (int) (floats[0] + 50 + i * differential * floats[1] + ra.nextInt(15));
            loadAverage[i] = (load[i] + load1[i]) / 2.0f;
            unLoadAverage[i] = (unLoad[i] + unLoad1[i]) / 2.0f;
            float loadDifferenceValue = Math.abs(load[i] - load1[i]);
            maxLoadDifferenceValue =
                    loadDifferenceValue > maxLoadDifferenceValue ?
                            loadDifferenceValue : maxLoadDifferenceValue;
            float unLoadDifference = Math.abs(unLoad[i] - unLoad1[i]);
            maxUnLoadDifferenceValue = unLoadDifference > maxUnLoadDifferenceValue ?
                    unLoadDifference : maxUnLoadDifferenceValue;
        }


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
        parameter[5] = sumPerLoad * 10000 / (squareSumLoadUnloadAverage * Integer.valueOf(Objects.requireNonNull(ldArea.getValue())));

        return parameter;
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.crossTest;

import android.annotation.SuppressLint;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Handler;
import android.os.Message;

import www.jingkan.com.BR;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.framework.utils.TimeUtils;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataData;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataModel;

/**
 * Created by lushengbo on 2018/1/8.
 * 十字板试验VM
 */

public class CrossTestViewModel extends BaseObservable {

    private CrossTestContract.View view;
    private String strProjectNumber;
    private String strHoleNumber;
    private String strCuCoefficient;
    private String strCuLimit;
    private String strCuInitial = "0";
    private String strCuEffective = "0";
    private String deg = "0";
    private String strTestType = "原状土";
    private final String[] type = {"原状土", "重塑土"};
    private int typeIndex = 0;
    private int testNumber = 1;
    private boolean start;
    private boolean linked;

    //    public final ObservableField<String> deep = new ObservableField<>();
    @Bindable
    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
        notifyPropertyChanged(BR.testNumber);
    }

    private String deep = "0";

    @Bindable
    public String getDeep() {
        return deep;
    }

    public void setDeep(String deep) {
        this.deep = deep;
        notifyPropertyChanged(BR.deep);
    }

    @Bindable
    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
        notifyPropertyChanged(BR.linked);
    }

    public CrossTestViewModel(CrossTestContract.View view) {
        this.view = view;
    }


    @Bindable
    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
        notifyPropertyChanged(BR.start);
    }

    @Bindable
    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
        notifyPropertyChanged(BR.deg);
    }


    @Bindable
    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
        strTestType = type[typeIndex];
        stopTest();

    }

    private void stopTest() {
        timeUtils.stopTimedTask();//切换土样类型时停止
        setStart(false);
    }

    public String[] getType() {
        return type;
    }


    @Bindable
    public String getStrProjectNumber() {
        return strProjectNumber;
    }

    public void setStrProjectNumber(String strProjectNumber) {
        this.strProjectNumber = strProjectNumber;
        notifyPropertyChanged(BR.strProjectNumber);
    }

    @Bindable
    public String getStrHoleNumber() {
        return strHoleNumber;
    }

    public void setStrHoleNumber(String strHoldNumber) {
        this.strHoleNumber = strHoldNumber;
        notifyPropertyChanged(BR.strHoleNumber);
    }

    @Bindable
    public String getStrCuCoefficient() {
        return strCuCoefficient;
    }

    public void setStrCuCoefficient(String strCuCoefficient) {
        this.strCuCoefficient = strCuCoefficient;
        notifyPropertyChanged(BR.strCuCoefficient);
    }

    @Bindable
    public String getStrCuLimit() {
        return strCuLimit;
    }

    public void setStrCuLimit(String strCuLimit) {
        this.strCuLimit = strCuLimit;
        notifyPropertyChanged(BR.strCuLimit);
    }

    @Bindable
    public String getStrCuInitial() {
        return strCuInitial;
    }

    public void setStrCuInitial(String strCuInitial) {
        this.strCuInitial = strCuInitial;
        notifyPropertyChanged(BR.strCuInitial);
    }

    @Bindable
    public String getStrCuEffective() {
        return strCuEffective;
    }

    public void setStrCuEffective(String strCuEffective) {
        this.strCuEffective = strCuEffective;
        notifyPropertyChanged(BR.strCuEffective);
    }

    public void doInitialValue() {
        setStrCuInitial(getStrCuEffective());
    }

    private void doRecord() {
        float parseFloat = Float.parseFloat(deg);
        parseFloat += 1;
        CrossTestDataModel crossTestDataModel = new CrossTestDataModel();
        crossTestDataModel.testDataID = strProjectNumber + "-" + strHoleNumber;
        crossTestDataModel.deep = parseFloat;
        crossTestDataModel.cu = Float.parseFloat(strCuEffective);
        crossTestDataModel.number = testNumber;
        crossTestDataModel.type = strTestType;
        CrossTestDataData crossTestDataData = DataFactory.getBaseData(CrossTestDataData.class);
        crossTestDataData.addData(crossTestDataModel);
        deg = StringUtils.format(parseFloat, 1);
        setDeg(deg);
        view.showRecordValue(strCuEffective, parseFloat);
    }

    private @SuppressLint("HandlerLeak")
    TimeUtils timeUtils = new TimeUtils(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            doRecord();
        }
    });

    public void doStart() {
        setStart(!start);

        if (start) {
            timeUtils.timedTask(0, 10000);
        } else {
            timeUtils.stopTimedTask();
        }

    }

}

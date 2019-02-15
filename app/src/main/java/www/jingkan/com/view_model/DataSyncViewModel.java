/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view_model;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import www.jingkan.com.db.dao.WirelessResultDataDao;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.entity.WirelessResultDataEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.util.DataUtil;
import www.jingkan.com.util.MyFileUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.view.OpenFileActivity;
import www.jingkan.com.view_model.base.BaseViewModel;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;

import static www.jingkan.com.util.DataUtil.SET_EMAIL;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_ORIGINAL_TXT;
import static www.jingkan.com.view.DataSyncActivity.REQUEST_OPEN_MARK_FILE;

/**
 * Created by lushengbo on 2018/1/17.
 * 数据同步
 */

public class DataSyncViewModel extends BaseViewModel {
    public final ObservableField<String> projectNumber = new ObservableField<>();
    public final ObservableField<String> markFileName = new ObservableField<>("未打开");
    public final ObservableField<String> holeNumber = new ObservableField<>();
    public final ObservableField<String> probeNumber = new ObservableField<>();
    public final ObservableField<String> strDeep = new ObservableField<>();
    public final ObservableField<String> synchronizationRate = new ObservableField<>("同步率：0%");
    public final ObservableField<Integer> index = new ObservableField<>(0);

    private long minR;
    private long maxR;
    private long[] cptR;
    private int[] rwaData;
    private List<WirelessResultDataEntity> wirelessResultDataModels = new ArrayList<>();
    private ArrayList<OriginalTestData> originalTestDataList;
    private boolean find;
    private String strTestID;
    private String mSaveType;
    private WirelessTestEntity wirelessTestEntity;
    private WirelessTestDao wirelessTestDao;
    private WirelessResultDataDao wirelessResultDataDao;
    private DataUtil dataUtil;
    private ISkip iSkip;

    public DataSyncViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    public void inject(Object... objects) {
        Object data = objects[0];
        wirelessTestDao = (WirelessTestDao) objects[1];
        wirelessResultDataDao = (WirelessResultDataDao) objects[2];
        dataUtil = (DataUtil) objects[3];
        iSkip = (ISkip) objects[4];
        if (data != null) {
            String strFileName = (String) data;
            File mFile = new File(MyFileUtil.getSDPath() + "/" + strFileName);
            ByteArrayOutputStream byteArrayOutputStream = OpenFileActivity.readFile(mFile);
            if (byteArrayOutputStream != null) {
                readWFile(new String[]{strFileName, byteArrayOutputStream.toString()}, "正在同步...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doDataSync();//卡UI，前面加个延时
                    }
                }, 1000);

            } else {
                toast("未打开文件");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_OPEN_MARK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        String[] str = extras.getStringArray(OpenFileActivity.EXTRA_FILE_DATES);
                        if (str != null) {
                            readWFile(str, "成功打开定位文件");
                        }
                    }
                } else {
                    toast("定位文件打开失败");
                }
                break;
            case SET_EMAIL:
                sendEmail();
                break;
        }
    }

    /**
     * @param str 1.文件名 2.W文件的内容（1.探头编号 2.试验编号）
     * @param msg 提示信息
     */
    private void readWFile(String[] str, String msg) {
        markFileName.set(str[0]);
        projectNumber.set(str[0].substring(0, str[0].indexOf("_")));
        holeNumber.set(str[0].substring(str[0].indexOf("_") + 1,
                str[0].indexOf("W")));
        String[] strData = str[1].split("\r\n");
        probeNumber.set(strData[0]);
        strTestID = strData[1];
        getTestMsg();
        int j = 1;
        minR = 1099511627775L;
        maxR = 0;
        cptR = new long[((strData.length - 2) / 2) + 1];
        cptR[0] = 0;
        long lastR = 0;
        for (int i = 3; i < strData.length; i = 2 * j + 1) {
            cptR[j] = Long.parseLong(strData[i]);
            if (cptR[j] == lastR) {
                cptR[j] = cptR[j] + 128;
            }
            if (minR > cptR[j]) {
                minR = cptR[j];
            }
            if (maxR < cptR[j]) {
                maxR = cptR[j];
            }
            j++;
        }
        toast(msg);
    }


    /**
     * 查无缆探头试验表
     */
    private void getTestMsg() {
        LiveData<List<WirelessTestEntity>> liveData = wirelessTestDao.getWirelessTestEntityByTestId(strTestID);
        List<WirelessTestEntity> wirelessTestEntities = liveData.getValue();
        if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
            wirelessTestEntity = wirelessTestEntities.get(0);
        }


//        WirelessTestDao wirelessTestDao = DataFactory.getBaseData(WirelessTestDao.class);
//        wirelessTestDao.getData(new DataLoadCallBack<WirelessTestEntity>() {
//
//            @Override
//            public void onDataLoaded(List<WirelessTestEntity> models) {
//                wirelessTestEntity = models.get(0);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        }, strTestID);
    }

    @Override
    public void clear() {

    }

    /**
     * FileUtils.listFiles 是一个卡UI的操作
     */
    public void doDataSync() {

        wirelessResultDataDao.deleteWirelessResultDataEntityByTestDataId(strTestID);
//        WirelessResultDaoDao wirelessResultDataDao = DataFactory.getBaseData(WirelessResultDaoDao.class);
//        wirelessResultDataDao.deleteData(strTestID);
        if (cptR == null) {
            toast("请先打开定位文件*W.txt");
        } else {
            Collection<File> collection = FileUtils.listFiles(Environment.getExternalStorageDirectory(),
                    new String[]{"REC"}, true);//耗时操作
            File[] files = collection.toArray(new File[]{});
            String[] fileName = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileName[i] = files[i].getName();
                String str = fileName[i];
                str = str.substring(0, str.indexOf(".REC"));
                int by = Integer.valueOf(str, 16);
                if ((long) by * 256 > cptR[1]) {
                    final File file = files[i];
                    ByteArrayOutputStream byteArrayOutputStream = OpenFileActivity.readFile(file);
                    byte[] byt = new byte[0];
                    if (byteArrayOutputStream != null) {
                        byt = byteArrayOutputStream.toByteArray();
                    }
                    int[] unsignedByte = new int[byt.length + 1];
                    unsignedByte[0] = by;
                    for (int j = 0; j < byt.length; j++) {
                        unsignedByte[j + 1] = byt[j] >= 0 ? byt[j]
                                : byt[j] + 256;
                    }
                    rwaData = unsignedByte;
                    long rtc = 0;
                    int j = 1;
                    for (; j < 5; j++) {
                        rtc = rtc * 256 + rwaData[j];
                    }
                    rtc = rtc * 256 + rwaData[j] * 128;
                    if (rtc < cptR[1]) {
                        dataSync();
                        find = true;
                    }
                }
            }
            if (!find) {
                toast("未找到对应的.REC文件");
            } else {
                toast("同步成功");
            }

        }
    }

    public void moveTimeUp() {
        if (!find) {
            toast("未找到对应的.REC文件");
        } else {
            int i = index.get() + 1;
            index.set(i);
            //doDataSync();
        }
    }

    public void moveTimeDown() {
        if (!find) {
            toast("未找到对应的.REC文件");
        } else {
            int i = index.get() - 1;
            index.set(i);
            //doDataSync();
        }
    }


    /**
     * 数据同步
     */
    private void dataSync() {
        float qc;
        float fs;
        float fa;
        int jls = 0;
        int x = 0;
        int k;
        if (rwaData == null) {
            toast("未打开定位文件*W.txt或未找到对应的原始记录文件*.REC");
        } else {
            long RTC;
            int y = index.get() * 128;
            int totalDataNumber = cptR.length - 1;
            float[][] cpt = new float[totalDataNumber][6];
            wirelessResultDataModels.clear();
            originalTestDataList = new ArrayList<>();
            do {
                RTC = 0;
                for (int j = 1; j < 5; j++) {
                    jls++;
                    RTC = RTC * 256 + rwaData[jls];
                }
                jls++;
                RTC = RTC * 256 + rwaData[jls] * 128;
                jls++;
                qc = rwaData[jls];
                jls++;
                qc = qc * 256 + rwaData[jls];
                if (qc >= 32768) {
                    qc = qc - 65536;
                }
                qc = qc * 0.01f;
                jls++;
                fs = rwaData[jls];
                jls++;
                fs = fs * 256 + rwaData[jls];
                if (fs >= 32768) {
                    fs = fs - 65536;
                }
                fs = fs * 0.1f;
                jls++;
                fa = rwaData[jls];
                jls++;
                fa = fa * 256 + rwaData[jls];
                if (fa >= 32768) {
                    fa = fa - 65536;
                }
                fa = fa * 0.1f;
                OriginalTestData originalTestData = new OriginalTestData(RTC, qc, fs, fa);
                originalTestDataList.add(originalTestData);
            } while (jls < rwaData.length - 12);
            jls = 0;
            do {
                RTC = 0;
                for (int j = 1; j < 5; j++) {
                    jls++;
                    RTC = RTC * 256 + rwaData[jls];
                }
                jls++;
                RTC = RTC * 256 + rwaData[jls] * 128;
                jls++;
                qc = rwaData[jls];
                jls++;
                qc = qc * 256 + rwaData[jls];
                if (qc >= 32768) {
                    qc = qc - 65536;
                }
                qc = qc * 0.01f;
                jls++;
                fs = rwaData[jls];
                jls++;
                fs = fs * 256 + rwaData[jls];
                if (fs >= 32768) {
                    fs = fs - 65536;
                }
                fs = fs * 0.1f;
                jls++;
                fa = rwaData[jls];
                jls++;
                fa = fa * 256 + rwaData[jls];
                if (fa >= 32768) {
                    fa = fa - 65536;
                }
                fa = fa * 0.1f;
                if (RTC > maxR) {
                    break;
                }
                if (RTC > minR) {
                    for (int i = 1; i < totalDataNumber; ) {
                        if (cptR[i] + y == RTC) {
                            x = x + 1;
                            cpt[i][0] = x / 10.0f;
                            cpt[i][1] = qc;
                            cpt[i][2] = fs;
                            cpt[i][3] = fa;
                            if (i != x) {
                                k = i - x;
                                for (int j = 1; j < k + 1; j++) {
                                    cpt[i - j][5] = 1;
                                }
                                x = i;
                                cpt[i][0] = x / 10.0f;
                            }
                            break;
                        }
                        i++;
                    }
                }
            } while (jls < rwaData.length - 12);
            for (int i = 1; i < totalDataNumber; i++) {
                if (cpt[i][5] == 1.0) {
                    cpt[i][0] = i / 10.0f;
                    if (i + 1 < totalDataNumber) {
                        cpt[i][1] = (cpt[i + 1][1] + cpt[i - 1][1]) / 2;
                        cpt[i][2] = (cpt[i + 1][2] + cpt[i - 1][2]) / 2;
                        cpt[i][3] = (cpt[i + 1][3] + cpt[i - 1][3]) / 2;
                    } else {
                        cpt[i][1] = cpt[i - 1][1];
                        cpt[i][2] = cpt[i - 1][2];
                        cpt[i][3] = cpt[i - 1][3];
                    }
                }
                WirelessResultDataEntity wirelessResultDataModel = new WirelessResultDataEntity();
                wirelessResultDataModel.testDataID = projectNumber.get() + "_" + holeNumber.get();
                wirelessResultDataModel.probeNumber = probeNumber.get();
                wirelessResultDataModel.deep = cpt[i][0];
                wirelessResultDataModel.qc = cpt[i][1];
                wirelessResultDataModel.fs = cpt[i][2];
                wirelessResultDataModel.fa = cpt[i][3];
                wirelessResultDataDao.insertWirelessResultDataEntity(wirelessResultDataModel);
//                wirelessResultDataModel.save();
                wirelessResultDataModels.add(wirelessResultDataModel);
            }
            synchronizationRate.set("同步率"
                    + StringUtil.format(((double) (x + 1) / (double) totalDataNumber * 100), 1)
                    + "%");
            callbackMessage.setValue(0, cpt);

            getView().action(callbackMessage);
            strDeep.set(StringUtil.format(0.1 * cpt.length, 1));
        }
    }

    public void saveDataAndSendEmail(String saveType, boolean isSave) {
        mSaveType = saveType;
        if (rwaData == null) {
            toast("未打开定位文件*W.txt或未找到对应的原始记录文件*.REC");
        } else if (isSave) {//保存数据
            if (saveType.equals(SAVE_TYPE_ORIGINAL_TXT)) {
                dataUtil.saveDataToSd(
                        originalTestDataList,
                        saveType,
                        wirelessTestEntity,
                        iSkip);
            } else {
                dataUtil.saveDataToSd(
                        wirelessResultDataModels,
                        saveType,
                        wirelessTestEntity,
                        iSkip);
            }

        } else {//发送邮件
            sendEmail();

        }

    }

    private void sendEmail() {
        if (mSaveType.equals(SAVE_TYPE_ORIGINAL_TXT)) {
            dataUtil.emailData(
                    originalTestDataList,
                    mSaveType,
                    wirelessTestEntity,
                    iSkip);
        } else {
            dataUtil.emailData(
                    wirelessResultDataModels,
                    mSaveType,
                    wirelessTestEntity,
                    iSkip);
        }
    }


}

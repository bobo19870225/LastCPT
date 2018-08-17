package www.jingkan.com.saveUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.jingkan.com.framework.email.MailSenderRunnable;
import www.jingkan.com.framework.utils.MyFileUtils;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataModel;
import www.jingkan.com.localData.testData.TestDataModel;
import www.jingkan.com.localData.wirelessResultData.WirelessResultDataModel;
import www.jingkan.com.localData.wirelessTest.WirelessTestModel;
import www.jingkan.com.mInterface.ISkip;
import www.jingkan.com.me.SetEmailActivity;
import www.jingkan.com.wireless.dataSynchronization.OriginalTestData;

import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_CORRECT_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_ORIGINAL_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_ZHD_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_CORRECT_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_ORIGINAL_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_ZHD_TXT;

/**
 * Created by Sampson on 2018/3/9.
 * LastCPT
 */

public class DataUtils {
    private static final float PI = 3.1415F;
    public static final int SET_EMAIL = 1;
    private static volatile DataUtils INSTANCE;
    //private ISkip mISkip;

    public static DataUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (DataUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataUtils();
                }
            }
        }
        return INSTANCE;
    }

    private DataUtils() {
        //mISkip = iSkip;
    }

    @SuppressWarnings("unchecked")
    public void saveDataToSd(
            final Context context, List models, String saveType, Object testModel, final ISkip iSkip) {
        StringBuilder content = new StringBuilder();
        String strReturn = "\r\n";
        String strTable = "\t";
        TestModel mTestModel = null;
        WirelessTestModel wirelessTestModel = null;
        String projectNumber = null;
        String holeNumber = null;
        String testDate = null;
        String holeHigh = null;
        String waterLevel = null;
        String location = null;
        String tester = null;
        String testType = null;
        if (testModel instanceof TestModel) {
            mTestModel = (TestModel) testModel;
            testType = mTestModel.testType;
            projectNumber = mTestModel.projectNumber;
            holeNumber = mTestModel.holeNumber;
            testDate = mTestModel.testDate;
            holeHigh = StringUtils.format(mTestModel.holeHigh, 1);
            waterLevel = StringUtils.format(mTestModel.waterLevel, 1);
            location = mTestModel.location;
            tester = mTestModel.tester;
        } else if (testModel instanceof WirelessTestModel) {
            wirelessTestModel = (WirelessTestModel) testModel;
            testType = wirelessTestModel.testType;
            projectNumber = wirelessTestModel.projectNumber;
            holeNumber = wirelessTestModel.holeNumber;
            testDate = wirelessTestModel.testDate;
            holeHigh = StringUtils.format(wirelessTestModel.holeHigh, 1);
            waterLevel = StringUtils.format(wirelessTestModel.waterLevel, 1);
            location = wirelessTestModel.location;
            tester = wirelessTestModel.tester;
        }
        if (testType != null) {
            switch (saveType) {
                case SAVE_TYPE_ZHD_TXT://浙海大.txt
                    content.append("试验日期：").append(testDate).append(strReturn);
                    content.append("工程编号：").append(projectNumber).append(strReturn);
                    content.append("孔号：").append(holeNumber).append(strReturn);
                    content.append("孔口高程：").append(holeHigh).append(strReturn);
                    content.append("地下水位：").append(waterLevel).append(strReturn);
                    if (location != null)
                        content.append("孔位：").append(location).append(strReturn);
                    content.append("测试员工：").append(tester).append(strReturn);
                    content.append("试验类型：").append(testType).append(strReturn);
                    switch (testType) {
                        case "单桥试验":
                            content.append("deep").append(strTable).append("ps").append(strReturn);
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(StringUtils.format(mTestDataModels.deep, 1)).append(strTable)
                                            .append(StringUtils.format(mTestDataModels.qc, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(StringUtils.format(wirelessResultDataModel.deep, 1)).append(strTable)
                                            .append(StringUtils.format(wirelessResultDataModel.qc, 3)).append(strReturn);
                                }
                            }
                            break;
                        case "单桥测斜试验":
                            content.append("deep/m").append(strTable).append("ps/MPa")
                                    .append(strTable).append("fa/.").append(strReturn);
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(StringUtils.format(mTestDataModels.deep, 1)).append(strTable)
                                            .append(StringUtils.format(mTestDataModels.qc, 3)).append(strTable)
                                            .append(StringUtils.format(mTestDataModels.fa, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(StringUtils.format(wirelessResultDataModel.deep, 1)).append(strTable)
                                            .append(StringUtils.format(wirelessResultDataModel.qc, 3)).append(strTable)
                                            .append(StringUtils.format(wirelessResultDataModel.fa, 3)).append(strReturn);
                                }
                            }

                            break;
                        case "双桥试验":
                            content.append("deep/m").append(strTable).append("qc/MPa")
                                    .append(strTable).append("fs/kPa").append(strReturn);
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(StringUtils.format(mTestDataModels.deep, 1)).append(strTable)
                                            .append(StringUtils.format(mTestDataModels.qc, 3)).append(strTable)
                                            .append(StringUtils.format(mTestDataModels.fs, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(StringUtils.format(wirelessResultDataModel.deep, 1)).append(strTable)
                                            .append(StringUtils.format(wirelessResultDataModel.qc, 3)).append(strTable)
                                            .append(StringUtils.format(wirelessResultDataModel.fs, 3)).append(strReturn);
                                }
                            }
                            break;
                        case "双桥测斜试验":
                            content.append("deep/m").append(strTable).append("qc/MPa")
                                    .append(strTable).append("fs/kPa").append(strTable)
                                    .append("fa/.").append(strReturn);
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(StringUtils.format(mTestDataModels.deep, 1)).append(strTable)
                                            .append(StringUtils.format(mTestDataModels.qc, 3)).append(strTable)
                                            .append(StringUtils.format(mTestDataModels.fs, 3)).append(strTable)
                                            .append(StringUtils.format(mTestDataModels.fa, 1)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(StringUtils.format(wirelessResultDataModel.deep, 1)).append(strTable)
                                            .append(StringUtils.format(wirelessResultDataModel.qc, 3)).append(strTable)
                                            .append(StringUtils.format(wirelessResultDataModel.fs, 3)).append(strTable)
                                            .append(StringUtils.format(wirelessResultDataModel.fa, 1)).append(strReturn);
                                }
                            }
                            break;
                    }

                    MyFileUtils.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber,
                            content.toString(),
                            "txt",
                            new MyFileUtils.SaveFileCallBack() {
                                @Override
                                public void onSuccess() {
                                    iSkip.sendToastMsg("保存成功！");
                                }

                                @Override
                                public void onFail(String e) {
                                    iSkip.sendToastMsg(e);
                                }
                            });
                    break;
                case SAVE_TYPE_LY_TXT://溧阳科尔.txt
                    switch (testType) {
                        case "单桥试验":
                        case "单桥测斜试验":
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(StringUtils.format(mTestDataModels.qc, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(StringUtils.format(wirelessResultDataModel.qc, 3)).append(strReturn);
                                }
                            }

                            break;
                        case "双桥试验":
                        case "双桥测斜试验":
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(StringUtils.format(mTestDataModels.qc, 3)).append(strReturn)
                                            .append(StringUtils.format(mTestDataModels.fs, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(StringUtils.format(wirelessResultDataModel.qc, 3)).append(strReturn)
                                            .append(StringUtils.format(wirelessResultDataModel.fs, 3)).append(strReturn);
                                }
                            }
                            break;
                    }

                    MyFileUtils.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber + "LY",
                            content.toString(),
                            "txt",
                            new MyFileUtils.SaveFileCallBack() {
                                @Override
                                public void onSuccess() {
                                    iSkip.sendToastMsg("保存成功！");
                                }

                                @Override
                                public void onFail(String e) {
                                    iSkip.sendToastMsg(e);
                                }
                            });
                    break;
                case SAVE_TYPE_LY_DAT://溧阳科尔.DAT
                    if (testModel instanceof TestModel) {
                        getDATContent(models, content, strReturn, mTestModel, testType);
                    } else {
                        getDATContent(models, content, strReturn, wirelessTestModel, testType);
                    }
                    MyFileUtils.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber,
                            content.toString(),
                            "DAT",
                            new MyFileUtils.SaveFileCallBack() {
                                @Override
                                public void onSuccess() {
                                    iSkip.sendToastMsg("保存成功！");
                                }

                                @Override
                                public void onFail(String e) {
                                    iSkip.sendToastMsg(e);
                                }
                            });
                    break;
                case SAVE_TYPE_HN_111://南京华宁.111
                    switch (testType) {
                        case "单桥试验":
                        case "单桥测斜试验":
                            content.append(" 1").append(strReturn);
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(" ").append(StringUtils.format(mTestDataModels.qc, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(" ").append(StringUtils.format(wirelessResultDataModel.qc, 3)).append(strReturn);
                                }
                            }

                            break;
                        case "双桥试验":
                        case "双桥测斜试验":
                            content.append(" 1 ，1").append(strReturn);
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(" ").append(StringUtils.format(mTestDataModels.qc, 3))
                                            .append(" ， ")
                                            .append(StringUtils.format(mTestDataModels.fs, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(" ").append(StringUtils.format(wirelessResultDataModel.qc, 3))
                                            .append(" ， ")
                                            .append(StringUtils.format(wirelessResultDataModel.fs, 3))
                                            .append(strReturn);
                                }
                            }
                            content.append(" " + "END" + " ， " + "END");
                            break;
                    }
                    MyFileUtils.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber,
                            content.toString(),
                            "111",
                            new MyFileUtils.SaveFileCallBack() {
                                @Override
                                public void onSuccess() {
                                    iSkip.sendToastMsg("保存成功！");
                                }

                                @Override
                                public void onFail(String e) {
                                    iSkip.sendToastMsg("保存失败了！");
                                }
                            });
                    break;
                case SAVE_TYPE_LZ_TXT://北京理正.txt
                    switch (testType) {
                        case "单桥试验":
                        case "单桥测斜试验":
                            content.append(holeNumber).append("，").append("1").append(strReturn);
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(String.valueOf(mTestDataModels.deep))
                                            .append("，")
                                            .append(StringUtils.format(mTestDataModels.qc, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(String.valueOf(wirelessResultDataModel.deep))
                                            .append("，")
                                            .append(StringUtils.format(wirelessResultDataModel.qc, 3))
                                            .append(strReturn);
                                }
                            }

                            break;
                        case "双桥试验":
                        case "双桥测斜试验":
                            content.append(holeNumber).append("，").append("2").append(strReturn);
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    content.append(String.valueOf(mTestDataModels.deep))
                                            .append("，")
                                            .append(StringUtils.format(mTestDataModels.qc, 3))
                                            .append("，")
                                            .append(StringUtils.format(mTestDataModels.fs, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    content.append(String.valueOf(wirelessResultDataModel.deep))
                                            .append("，")
                                            .append(StringUtils.format(wirelessResultDataModel.qc, 3))
                                            .append("，")
                                            .append(StringUtils.format(wirelessResultDataModel.fs, 3))
                                            .append(strReturn);
                                }
                            }
                            break;
                    }
                    //为了区分文件名有所不同
                    MyFileUtils.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber + "LZ",
                            content.toString(),
                            "txt",
                            new MyFileUtils.SaveFileCallBack() {
                                @Override
                                public void onSuccess() {
                                    iSkip.sendToastMsg("保存成功！");
                                }

                                @Override
                                public void onFail(String e) {
                                    iSkip.sendToastMsg("保存失败了！");
                                }
                            });
                    break;
                case SAVE_TYPE_CORRECT_TXT://修正深度*X.txt
                    switch (testType) {
                        case "单桥测斜试验":
                            float mDeep = 0;
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    mDeep += 0.1 * Math.cos(2 * PI / 360 * mTestDataModels.fa);
                                    content.append(StringUtils.format(mDeep, 3))
                                            .append(",")
                                            .append(StringUtils.format(mTestDataModels.qc, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    mDeep += 0.1 * Math.cos(2 * PI / 360 * wirelessResultDataModel.fa);
                                    content.append(StringUtils.format(mDeep, 3))
                                            .append(",")
                                            .append(StringUtils.format(wirelessResultDataModel.qc, 3))
                                            .append(strReturn);
                                }
                            }

                            break;
                        case "双桥测斜试验":
                            mDeep = 0;
                            if (models.get(0) instanceof TestDataModel) {
                                for (TestDataModel mTestDataModels : (List<TestDataModel>) models) {
                                    mDeep += 0.1 * Math.cos(2 * PI / 360 * mTestDataModels.fa);
                                    content.append(StringUtils.format(mDeep, 3))
                                            .append(",")
                                            .append(StringUtils.format(mTestDataModels.qc, 3))
                                            .append(",")
                                            .append(StringUtils.format(mTestDataModels.fs, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataModel) {
                                for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                                    mDeep += 0.1 * Math.cos(2 * PI / 360 * wirelessResultDataModel.fa);
                                    content.append(StringUtils.format(mDeep, 3))
                                            .append(",")
                                            .append(StringUtils.format(wirelessResultDataModel.qc, 3))
                                            .append(",")
                                            .append(StringUtils.format(wirelessResultDataModel.fs, 3))
                                            .append(strReturn);
                                }
                            }
                            break;
                    }
                    MyFileUtils.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber + "X",
                            content.toString(),
                            "txt",
                            new MyFileUtils.SaveFileCallBack() {
                                @Override
                                public void onSuccess() {
                                    iSkip.sendToastMsg("保存成功！");
                                }

                                @Override
                                public void onFail(String e) {
                                    iSkip.sendToastMsg(e);
                                }
                            });
                    break;
                case SAVE_TYPE_ORIGINAL_TXT://无缆原始数据
                    if (models.get(0) instanceof OriginalTestData) {
                        for (OriginalTestData originalTestData : (List<OriginalTestData>) models) {
                            content.append(originalTestData.getSj())
                                    .append(",")
                                    .append(originalTestData.getQc())
                                    .append(",")
                                    .append(originalTestData.getFs())
                                    .append(",")
                                    .append(originalTestData.getFa())
                                    .append(strReturn);
                        }
                        MyFileUtils.getInstance().saveToSD(context,
                                projectNumber + "_" + holeNumber + "ORI",
                                content.toString(),
                                "txt",
                                new MyFileUtils.SaveFileCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        iSkip.sendToastMsg("文件已保存完毕，位于sd卡内");
                                    }

                                    @Override
                                    public void onFail(String e) {
                                        iSkip.sendToastMsg(e);
                                    }
                                });
                    }


                    break;

            }
        }
    }

    private Float maxCu = 0f;
    @SuppressWarnings("unchecked")
    public void saveDataToSd(final Context context, List<CrossTestDataModel> models, TestModel testModel, final ISkip iSkip) {
        StringBuilder content = new StringBuilder();
        String strReturn = "\r\n";
        String strTable = "\t";
        String projectNumber = testModel.projectNumber;
        String holeNumber = testModel.holeNumber;
        String holeHigh = StringUtils.format(testModel.holeHigh, 1);
        String waterLevel = StringUtils.format(testModel.waterLevel, 1);
        String location = testModel.location;
        content.append("试验日期：").append(testModel.testDate).append(strReturn);
        content.append("工程编号：").append(projectNumber).append(strReturn);
        content.append("孔号：").append(holeNumber).append(strReturn);
        content.append("孔口高程：").append(holeHigh).append(strReturn);
        content.append("地下水位：").append(waterLevel).append(strReturn);
        if (location != null)
            content.append("孔位：").append(location).append(strReturn);
        content.append("操作员工：").append(testModel.tester).append(strReturn);
        content.append("试验类型：").append(testModel.testType).append(strReturn);
        List<Float> listMaxCu = new ArrayList<>();
        List<Float> listDeep = new ArrayList<>();
        String type = "";
        float deep = -1;

        for (CrossTestDataModel crossTestDataModel : models) {
            if (crossTestDataModel.deep != deep) {
                if (deep != -1) {
                    listMaxCu.add(maxCu);
                }
                deep = crossTestDataModel.deep;
                maxCu = 0f;
                listDeep.add(deep);
                content.append("试验深度：").append(StringUtils.format(deep, 2)).append(strReturn);
                type = crossTestDataModel.type;
                content.append("土样类型：").append(type).append(strReturn);
                content.append("角度/。").append(strTable).append("Cu/kPa").append(strReturn);
            }
            if (!crossTestDataModel.type.equals(type)) {
                type = crossTestDataModel.type;
                content.append("土样类型：").append(type).append(strReturn);
                content.append("角度/。").append(strTable).append("Cu/kPa").append(strReturn);
            }
            maxCu = crossTestDataModel.cu > maxCu ? crossTestDataModel.cu : maxCu;
            content.append(StringUtils.format(crossTestDataModel.deg, 1)).append(strTable).append(StringUtils.format(crossTestDataModel.cu, 3)).append(strReturn);
        }
        listMaxCu.add(maxCu);//添加最后一组数据
        content.append("各深度的极限抗剪切值").append(strReturn);
        content.append("d/m").append(strTable).append("maxCu/kPa").append(strReturn);
        for (int i = 0; i < listDeep.size(); i++) {
            content.append(StringUtils.format(listDeep.get(i), 1)).append(strTable).append(StringUtils.format(listMaxCu.get(i), 2)).append(strReturn);
        }
        MyFileUtils.getInstance().saveToSD(context,
                projectNumber + "_" + holeNumber,
                content.toString(),
                "txt",
                new MyFileUtils.SaveFileCallBack() {
                    @Override
                    public void onSuccess() {
                        iSkip.sendToastMsg("保存成功！");
                    }

                    @Override
                    public void onFail(String e) {
                        iSkip.sendToastMsg(e);
                    }
                });

    }

    @SuppressWarnings("unchecked")
    private void getDATContent(List models, StringBuilder content, String strReturn, TestModel
            mTestModel, String testType) {
        switch (testType) {
            case "单桥试验":
            case "单桥测斜试验":
                content.append(mTestModel.testDate).append(strReturn);//测试日期
                content.append(mTestModel.holeNumber).append(strReturn);//孔号
                content.append(mTestModel.projectNumber).append(strReturn);//工程编号
                content.append(mTestModel.holeHigh).append(strReturn);//孔口高程
                content.append(mTestModel.waterLevel).append(strReturn);//地下水位
                content.append("JKD10-4-001").append(strReturn);//探头编号
                content.append("10").append(strReturn);//锥头面积
                content.append("1").append(strReturn);//锥头系数
                content.append("48").append(strReturn);//锥头限值
                content.append("0").append(strReturn);//侧壁系数
                content.append("0").append(strReturn);//侧壁面积
                content.append("0").append(strReturn);
                content.append("").append(strReturn);
                if (models.get(0) instanceof TestDataModel) {
                    for (TestDataModel testDataModel : (List<TestDataModel>) models) {
                        content.append(StringUtils.format(testDataModel.qc, 3)).append(strReturn);
                    }
                } else if (models.get(0) instanceof WirelessResultDataModel) {
                    for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                        content.append(StringUtils.format(wirelessResultDataModel.qc, 3)).append(strReturn);
                    }
                }
                break;
            case "双桥试验":
            case "双桥测斜试验":
                content.append(mTestModel.testDate).append(strReturn);//测试日期
                content.append(mTestModel.holeNumber).append(strReturn);//孔号
                content.append(mTestModel.projectNumber).append(strReturn);//工程编号
                content.append(mTestModel.holeHigh).append(strReturn);//孔口高程
                content.append(mTestModel.waterLevel).append(strReturn);//地下水位
                content.append("JKD10-4-001").append(strReturn);//探头编号
                content.append("10").append(strReturn);//锥头面积
                content.append("1").append(strReturn);//锥头系数
                content.append("48").append(strReturn);//锥头限值
                content.append("0").append(strReturn);//侧壁系数
                content.append("200").append(strReturn);//侧壁面积
                content.append("0").append(strReturn);
                content.append("").append(strReturn);
                if (models.get(0) instanceof TestDataModel) {
                    for (TestDataModel testDataModel : (List<TestDataModel>) models) {
                        content.append(StringUtils.format(testDataModel.qc, 3));
                        content.append(StringUtils.format(testDataModel.fs, 3));
                    }
                } else if (models.get(0) instanceof WirelessResultDataModel) {
                    for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                        content.append(StringUtils.format(wirelessResultDataModel.qc, 3));
                        content.append(StringUtils.format(wirelessResultDataModel.fs, 3));
                    }
                }
                break;
        }
    }


    @SuppressWarnings("unchecked")
    private void getDATContent(List models, StringBuilder content, String
            strReturn, WirelessTestModel wirelessTestModel, String testType) {
        switch (testType) {
            case "单桥试验":
            case "单桥测斜试验":
                content.append(wirelessTestModel.testDate).append(strReturn);//测试日期
                content.append(wirelessTestModel.holeNumber).append(strReturn);//孔号
                content.append(wirelessTestModel.projectNumber).append(strReturn);//工程编号
                content.append(wirelessTestModel.holeHigh).append(strReturn);//孔口高程
                content.append(wirelessTestModel.waterLevel).append(strReturn);//地下水位
                content.append("JKD").append(strReturn);//探头编号
                content.append("0").append(strReturn);
                content.append("0").append(strReturn);
                content.append("").append(strReturn);
                if (models.get(0) instanceof TestDataModel) {
                    for (TestDataModel testDataModel : (List<TestDataModel>) models) {
                        content.append(StringUtils.format(testDataModel.qc, 3));
                    }
                } else if (models.get(0) instanceof WirelessResultDataModel) {
                    for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                        content.append(StringUtils.format(wirelessResultDataModel.qc, 3));
                    }
                }
                break;
            case "双桥试验":
            case "双桥测斜试验":
                content.append(wirelessTestModel.testDate).append(strReturn);//测试日期
                content.append(wirelessTestModel.holeNumber).append(strReturn);//孔号
                content.append(wirelessTestModel.projectNumber).append(strReturn);//工程编号
                content.append(wirelessTestModel.holeHigh).append(strReturn);//孔口高程
                content.append(wirelessTestModel.waterLevel).append(strReturn);//地下水位
                content.append("JKD").append(strReturn);//探头编号
                content.append("0").append(strReturn);
                content.append("").append(strReturn);
                if (models.get(0) instanceof TestDataModel) {
                    for (TestDataModel testDataModel : (List<TestDataModel>) models) {
                        content.append(StringUtils.format(testDataModel.qc, 3));
                        content.append(StringUtils.format(testDataModel.fs, 3));
                    }
                } else if (models.get(0) instanceof WirelessResultDataModel) {
                    for (WirelessResultDataModel wirelessResultDataModel : (List<WirelessResultDataModel>) models) {
                        content.append(StringUtils.format(wirelessResultDataModel.qc, 3));
                        content.append(StringUtils.format(wirelessResultDataModel.fs, 3));
                    }
                }
                break;
        }
    }

    public void emailData(Context context, List models, String saveType, Object testModel, ISkip iSkip) {
        String fileName = null;
        String name = null;
        if (testModel instanceof TestModel) {
            name = ((TestModel) testModel).projectNumber + "_" + ((TestModel) testModel).holeNumber;
        } else if (testModel instanceof WirelessTestModel) {
            name = ((WirelessTestModel) testModel).projectNumber + "_" + ((WirelessTestModel) testModel).holeNumber;
        }
        switch (saveType) {
            case EMAIL_TYPE_ZHD_TXT:
                fileName = name + ".txt";
                break;
            case EMAIL_TYPE_LY_TXT:
                fileName = name + "LY.txt";
                break;
            case EMAIL_TYPE_LY_DAT:
                fileName = name + ".DAT";
                break;
            case EMAIL_TYPE_HN_111:
                fileName = name + ".111";
                break;
            case EMAIL_TYPE_LZ_TXT:
                fileName = name + "LZ.txt";
                break;
            case EMAIL_TYPE_CORRECT_TXT:
                fileName = name + "X.txt";
                break;
            case EMAIL_TYPE_ORIGINAL_TXT:
                fileName = name + "ORI.txt";
                break;
        }
        PreferencesUtils preferencesUtils = new PreferencesUtils(context);
        Map<String, String> emailPreferences = preferencesUtils.getEmailPreferences();
        String sEmail = emailPreferences.get("sEmail");
        String sEmailPassword = emailPreferences.get("sEmailPassword");
        String rEmail = emailPreferences.get("rEmail");
        if (StringUtils.isNotEmpty(sEmail)
                && StringUtils.isNotEmpty(sEmailPassword)
                && StringUtils.isNotEmpty(rEmail)) {
            if (MyFileUtils.fileIsExists(fileName)) {
                sendFile(context, fileName, sEmail, sEmailPassword, rEmail, iSkip);
            } else {
                saveDataToSd(context, models, saveType, testModel, iSkip);
                sendFile(context, fileName, sEmail, sEmailPassword, rEmail, iSkip);
            }

        } else {
            iSkip.skipForResult(new Intent().setClass(context, SetEmailActivity.class), SET_EMAIL);
        }
    }

    public void emailData(Context context, List<CrossTestDataModel> models, TestModel testModel, ISkip iSkip) {
        String fileName = testModel.projectNumber + "_" + testModel.holeNumber + ".txt";
        PreferencesUtils preferencesUtils = new PreferencesUtils(context);
        Map<String, String> emailPreferences = preferencesUtils.getEmailPreferences();
        String sEmail = emailPreferences.get("sEmail");
        String sEmailPassword = emailPreferences.get("sEmailPassword");
        String rEmail = emailPreferences.get("rEmail");
        if (StringUtils.isNotEmpty(sEmail)
                && StringUtils.isNotEmpty(sEmailPassword)
                && StringUtils.isNotEmpty(rEmail)) {
            if (MyFileUtils.fileIsExists(fileName)) {
                sendFile(context, fileName, sEmail, sEmailPassword, rEmail, iSkip);
            } else {
                saveDataToSd(context, models, testModel, iSkip);
                sendFile(context, fileName, sEmail, sEmailPassword, rEmail, iSkip);
            }

        } else {
            iSkip.skipForResult(new Intent().setClass(context, SetEmailActivity.class), SET_EMAIL);
        }
    }

    private void sendFile(
            Context context,
            String fileName,
            String sEmail,
            String sEmailPassword,
            String rEmail, ISkip iSkip) {
        MailSenderRunnable senderRunnable = new MailSenderRunnable(context,
                sEmail, sEmailPassword);
        senderRunnable.setMail(fileName + "的试验数据",
                fileName + "的试验数据",
                rEmail,
                Environment.getExternalStorageDirectory().getPath()
                        + "/" + fileName);
        new Thread(senderRunnable).start();
        iSkip.sendToastMsg("正在发送");
    }
}

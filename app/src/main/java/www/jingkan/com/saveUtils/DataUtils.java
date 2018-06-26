package www.jingkan.com.saveUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

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
            final Context context,
            List models,
            int saveType,
            Object testModel,
            final ISkip iSkip) {
        StringBuilder content = new StringBuilder();
        String strReturn = "\r\n";
        TestModel mTestModel = null;
        WirelessTestModel wirelessTestModel = null;
        String testType = null;
        String projectNumber = null;
        String holeNumber = null;
        if (testModel instanceof TestModel) {
            mTestModel = (TestModel) testModel;
            testType = mTestModel.testType;
            projectNumber = mTestModel.projectNumber;
            holeNumber = mTestModel.holeNumber;
        } else if (testModel instanceof WirelessTestModel) {
            wirelessTestModel = (WirelessTestModel) testModel;
            testType = wirelessTestModel.testType;
            projectNumber = wirelessTestModel.projectNumber;
            holeNumber = wirelessTestModel.holeNumber;
        }
        if (testType != null) {
            switch (saveType) {
                case 0://溧阳科尔.text
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
                case 1://溧阳科尔.DAT
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
                case 2://南京华宁
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
                case 3://北京理正
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
                case 4://修正深度*X.txt
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
                case 5://无缆原始数据
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

    @SuppressWarnings("unchecked")
    public void saveDataToSd(
            final Context context,
            List<CrossTestDataModel> models,
            TestModel testModel,
            final ISkip iSkip) {
        StringBuilder content = new StringBuilder();
        String strReturn = "\r\n";


        String projectNumber = testModel.projectNumber;
        String holeNumber = testModel.holeNumber;
        content.append("试验日期：").append(testModel.testDate).append(strReturn);
        content.append("工程编号：").append(projectNumber).append(strReturn);
        content.append("孔号：").append(holeNumber).append(strReturn);
        content.append("试验类型：").append(testModel.testType).append(strReturn);
        content.append("操作员工：").append(testModel.tester).append(strReturn);

        String type = "";
        float deep = -1;

        for (CrossTestDataModel crossTestDataModel : models) {
            if (crossTestDataModel.deep != deep) {
                deep = crossTestDataModel.deep;
                content.append("试验深度：").append(StringUtils.format(deep, 2)).append(strReturn);
                type = crossTestDataModel.type;
                content.append("土样类型：").append(type).append(strReturn);
            }
            if (!crossTestDataModel.type.equals(type)) {
                type = crossTestDataModel.type;
                content.append("土样类型：").append(type).append(strReturn);
            }
            content.append(StringUtils.format(crossTestDataModel.cu, 3)).append(strReturn);
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

    public void emailData(
            Context context,
            List models,
            int saveType,
            Object testModel,
            ISkip iSkip) {
        String fileName = null;
        if (testModel instanceof TestModel) {
            String name = ((TestModel) testModel).projectNumber + "_" + ((TestModel) testModel).holeNumber;
            switch (saveType) {
                case 0:
                    fileName = name + ".txt";
                    break;
                case 1:
                    fileName = name + ".DAT";
                    break;
                case 2:
                    fileName = name + ".111";
                    break;
                case 3:
                    fileName = name + "LZ.txt";
                    break;
                case 4:
                    fileName = name + "X.txt";
                    break;
                case 5:
                    fileName = name + "ORI.txt";
                    break;
            }
        } else if (testModel instanceof WirelessTestModel) {
            String name = ((WirelessTestModel) testModel).projectNumber + "_" + ((WirelessTestModel) testModel).holeNumber;
            switch (saveType) {
                case 0:
                    fileName = name + ".txt";
                    break;
                case 1:
                    fileName = name + ".DAT";
                    break;
                case 2:
                    fileName = name + ".111";
                    break;
                case 3:
                    fileName = name + "LZ.txt";
                    break;
                case 4:
                    fileName = name + "X.txt";
                    break;
                case 5:
                    fileName = name + "ORI.txt";
                    break;
            }
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

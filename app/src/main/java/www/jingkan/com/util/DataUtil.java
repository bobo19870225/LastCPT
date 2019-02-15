package www.jingkan.com.util;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import www.jingkan.com.db.entity.CrossTestDataEntity;
import www.jingkan.com.db.entity.TestDataEntity;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.db.entity.WirelessResultDataEntity;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.util.email.MailSenderRunnable;
import www.jingkan.com.view.SetEmailActivity;
import www.jingkan.com.view_model.ISkip;
import www.jingkan.com.view_model.OriginalTestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_CORRECT_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_ORIGINAL_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_ZHD_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_CORRECT_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LZ_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_ORIGINAL_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_ZHD_TXT;

/**
 * Created by Sampson on 2018/3/9.
 * LastCPT
 */

public class DataUtil {
    private Context context;
    private static final float PI = 3.1415F;
    public static final int SET_EMAIL = 1;
    //    private static volatile DataUtil INSTANCE;

    private TestEntity mTestEntity;

    private WirelessTestEntity wirelessTestEntity;
    //private ISkip mISkip;
    @Inject
    PreferencesUtil preferencesUtil;

    //    public static DataUtil getInstance() {
//        if (INSTANCE == null) {
//            synchronized (DataUtil.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new DataUtil();
//                }
//            }
//        }
//        return INSTANCE;
//    }
    @Inject
    public DataUtil(Context context) {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    public void saveDataToSd(List models, String saveType, Object testModel, final ISkip iSkip) {
        StringBuilder content = new StringBuilder();
        String strReturn = "\r\n";
        String strTable = "\t";
        String projectNumber = null;
        String holeNumber = null;
        String testDate = null;
        String holeHigh = null;
        String waterLevel = null;
        String location = null;
        String tester = null;
        String testType = null;
        if (testModel instanceof TestEntity) {
            mTestEntity = (TestEntity) testModel;
            testType = mTestEntity.testType;
            projectNumber = mTestEntity.projectNumber;
            holeNumber = mTestEntity.holeNumber;
            testDate = mTestEntity.testDate;
            holeHigh = StringUtil.format(mTestEntity.holeHigh, 1);
            waterLevel = StringUtil.format(mTestEntity.waterLevel, 1);
            location = mTestEntity.location;
            tester = mTestEntity.tester;
        } else if (testModel instanceof WirelessTestEntity) {
            wirelessTestEntity = (WirelessTestEntity) testModel;
            testType = wirelessTestEntity.testType;
            projectNumber = wirelessTestEntity.projectNumber;
            holeNumber = wirelessTestEntity.holeNumber;
            testDate = wirelessTestEntity.testDate;
            holeHigh = StringUtil.format(wirelessTestEntity.holeHigh, 1);
            waterLevel = StringUtil.format(wirelessTestEntity.waterLevel, 1);
            location = wirelessTestEntity.location;
            tester = wirelessTestEntity.tester;
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
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(StringUtil.format(mTestDataEntitys.deep, 1)).append(strTable)
                                            .append(StringUtil.format(mTestDataEntitys.qc, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(StringUtil.format(wirelessResultDataModel.deep, 1)).append(strTable)
                                            .append(StringUtil.format(wirelessResultDataModel.qc, 3)).append(strReturn);
                                }
                            }
                            break;
                        case "单桥测斜试验":
                            content.append("deep/m").append(strTable).append("ps/MPa")
                                    .append(strTable).append("fa/.").append(strReturn);
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(StringUtil.format(mTestDataEntitys.deep, 1)).append(strTable)
                                            .append(StringUtil.format(mTestDataEntitys.qc, 3)).append(strTable)
                                            .append(StringUtil.format(mTestDataEntitys.fa, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(StringUtil.format(wirelessResultDataModel.deep, 1)).append(strTable)
                                            .append(StringUtil.format(wirelessResultDataModel.qc, 3)).append(strTable)
                                            .append(StringUtil.format(wirelessResultDataModel.fa, 3)).append(strReturn);
                                }
                            }

                            break;
                        case "双桥试验":
                            content.append("deep/m").append(strTable).append("qc/MPa")
                                    .append(strTable).append("fs/kPa").append(strReturn);
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(StringUtil.format(mTestDataEntitys.deep, 1)).append(strTable)
                                            .append(StringUtil.format(mTestDataEntitys.qc, 3)).append(strTable)
                                            .append(StringUtil.format(mTestDataEntitys.fs, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(StringUtil.format(wirelessResultDataModel.deep, 1)).append(strTable)
                                            .append(StringUtil.format(wirelessResultDataModel.qc, 3)).append(strTable)
                                            .append(StringUtil.format(wirelessResultDataModel.fs, 3)).append(strReturn);
                                }
                            }
                            break;
                        case "双桥测斜试验":
                            content.append("deep/m").append(strTable).append("qc/MPa")
                                    .append(strTable).append("fs/kPa").append(strTable)
                                    .append("fa/.").append(strReturn);
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(StringUtil.format(mTestDataEntitys.deep, 1)).append(strTable)
                                            .append(StringUtil.format(mTestDataEntitys.qc, 3)).append(strTable)
                                            .append(StringUtil.format(mTestDataEntitys.fs, 3)).append(strTable)
                                            .append(StringUtil.format(mTestDataEntitys.fa, 1)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(StringUtil.format(wirelessResultDataModel.deep, 1)).append(strTable)
                                            .append(StringUtil.format(wirelessResultDataModel.qc, 3)).append(strTable)
                                            .append(StringUtil.format(wirelessResultDataModel.fs, 3)).append(strTable)
                                            .append(StringUtil.format(wirelessResultDataModel.fa, 1)).append(strReturn);
                                }
                            }
                            break;
                    }

                    MyFileUtil.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber,
                            content.toString(),
                            "txt",
                            new MyFileUtil.SaveFileCallBack() {
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
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(StringUtil.format(mTestDataEntitys.qc, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(StringUtil.format(wirelessResultDataModel.qc, 3)).append(strReturn);
                                }
                            }

                            break;
                        case "双桥试验":
                        case "双桥测斜试验":
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(StringUtil.format(mTestDataEntitys.qc, 3)).append(strReturn)
                                            .append(StringUtil.format(mTestDataEntitys.fs, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(StringUtil.format(wirelessResultDataModel.qc, 3)).append(strReturn)
                                            .append(StringUtil.format(wirelessResultDataModel.fs, 3)).append(strReturn);
                                }
                            }
                            break;
                    }

                    MyFileUtil.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber + "LY",
                            content.toString(),
                            "txt",
                            new MyFileUtil.SaveFileCallBack() {
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
                    if (testModel instanceof TestEntity) {
                        getDATContent(models, content, strReturn, mTestEntity, testType);
                    } else {
                        getDATContent(models, content, strReturn, wirelessTestEntity, testType);
                    }
                    MyFileUtil.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber,
                            content.toString(),
                            "DAT",
                            new MyFileUtil.SaveFileCallBack() {
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
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(" ").append(StringUtil.format(mTestDataEntitys.qc, 3)).append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(" ").append(StringUtil.format(wirelessResultDataModel.qc, 3)).append(strReturn);
                                }
                            }

                            break;
                        case "双桥试验":
                        case "双桥测斜试验":
                            content.append(" 1 ，1").append(strReturn);
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(" ").append(StringUtil.format(mTestDataEntitys.qc, 3))
                                            .append(" ， ")
                                            .append(StringUtil.format(mTestDataEntitys.fs, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(" ").append(StringUtil.format(wirelessResultDataModel.qc, 3))
                                            .append(" ， ")
                                            .append(StringUtil.format(wirelessResultDataModel.fs, 3))
                                            .append(strReturn);
                                }
                            }
                            content.append(" " + "END" + " ， " + "END");
                            break;
                    }
                    MyFileUtil.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber,
                            content.toString(),
                            "111",
                            new MyFileUtil.SaveFileCallBack() {
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
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(String.valueOf(mTestDataEntitys.deep))
                                            .append("，")
                                            .append(StringUtil.format(mTestDataEntitys.qc, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(String.valueOf(wirelessResultDataModel.deep))
                                            .append("，")
                                            .append(StringUtil.format(wirelessResultDataModel.qc, 3))
                                            .append(strReturn);
                                }
                            }

                            break;
                        case "双桥试验":
                        case "双桥测斜试验":
                            content.append(holeNumber).append("，").append("2").append(strReturn);
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    content.append(String.valueOf(mTestDataEntitys.deep))
                                            .append("，")
                                            .append(StringUtil.format(mTestDataEntitys.qc, 3))
                                            .append("，")
                                            .append(StringUtil.format(mTestDataEntitys.fs, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    content.append(String.valueOf(wirelessResultDataModel.deep))
                                            .append("，")
                                            .append(StringUtil.format(wirelessResultDataModel.qc, 3))
                                            .append("，")
                                            .append(StringUtil.format(wirelessResultDataModel.fs, 3))
                                            .append(strReturn);
                                }
                            }
                            break;
                    }
                    //为了区分文件名有所不同
                    MyFileUtil.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber + "LZ",
                            content.toString(),
                            "txt",
                            new MyFileUtil.SaveFileCallBack() {
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
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    mDeep += 0.1 * Math.cos(2 * PI / 360 * mTestDataEntitys.fa);
                                    content.append(StringUtil.format(mDeep, 3))
                                            .append(",")
                                            .append(StringUtil.format(mTestDataEntitys.qc, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    mDeep += 0.1 * Math.cos(2 * PI / 360 * wirelessResultDataModel.fa);
                                    content.append(StringUtil.format(mDeep, 3))
                                            .append(",")
                                            .append(StringUtil.format(wirelessResultDataModel.qc, 3))
                                            .append(strReturn);
                                }
                            }

                            break;
                        case "双桥测斜试验":
                            mDeep = 0;
                            if (models.get(0) instanceof TestDataEntity) {
                                for (TestDataEntity mTestDataEntitys : (List<TestDataEntity>) models) {
                                    mDeep += 0.1 * Math.cos(2 * PI / 360 * mTestDataEntitys.fa);
                                    content.append(StringUtil.format(mDeep, 3))
                                            .append(",")
                                            .append(StringUtil.format(mTestDataEntitys.qc, 3))
                                            .append(",")
                                            .append(StringUtil.format(mTestDataEntitys.fs, 3))
                                            .append(strReturn);
                                }
                            } else if (models.get(0) instanceof WirelessResultDataEntity) {
                                for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                                    mDeep += 0.1 * Math.cos(2 * PI / 360 * wirelessResultDataModel.fa);
                                    content.append(StringUtil.format(mDeep, 3))
                                            .append(",")
                                            .append(StringUtil.format(wirelessResultDataModel.qc, 3))
                                            .append(",")
                                            .append(StringUtil.format(wirelessResultDataModel.fs, 3))
                                            .append(strReturn);
                                }
                            }
                            break;
                    }
                    MyFileUtil.getInstance().saveToSD(context,
                            projectNumber + "_" + holeNumber + "X",
                            content.toString(),
                            "txt",
                            new MyFileUtil.SaveFileCallBack() {
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
                        MyFileUtil.getInstance().saveToSD(context,
                                projectNumber + "_" + holeNumber + "ORI",
                                content.toString(),
                                "txt",
                                new MyFileUtil.SaveFileCallBack() {
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
    public void saveDataToSd(List<CrossTestDataEntity> models, TestEntity testModel, final ISkip iSkip) {
        StringBuilder content = new StringBuilder();
        String strReturn = "\r\n";
        String strTable = "\t";
        String projectNumber = testModel.projectNumber;
        String holeNumber = testModel.holeNumber;
        String holeHigh = StringUtil.format(testModel.holeHigh, 1);
        String waterLevel = StringUtil.format(testModel.waterLevel, 1);
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

        for (CrossTestDataEntity crossTestDataEntity : models) {
            if (crossTestDataEntity.deep != deep) {
                if (deep != -1) {
                    listMaxCu.add(maxCu);
                }
                deep = crossTestDataEntity.deep;
                maxCu = 0f;
                listDeep.add(deep);
                content.append("试验深度：").append(StringUtil.format(deep, 2)).append(strReturn);
                type = crossTestDataEntity.type;
                content.append("土样类型：").append(type).append(strReturn);
                content.append("角度/。").append(strTable).append("Cu/kPa").append(strReturn);
            }
            if (!crossTestDataEntity.type.equals(type)) {
                type = crossTestDataEntity.type;
                content.append("土样类型：").append(type).append(strReturn);
                content.append("角度/。").append(strTable).append("Cu/kPa").append(strReturn);
            }
            maxCu = crossTestDataEntity.cu > maxCu ? crossTestDataEntity.cu : maxCu;
            content.append(StringUtil.format(crossTestDataEntity.deg, 1)).append(strTable).append(StringUtil.format(crossTestDataEntity.cu, 3)).append(strReturn);
        }
        listMaxCu.add(maxCu);//添加最后一组数据
        content.append("各深度的极限抗剪切值").append(strReturn);
        content.append("d/m").append(strTable).append("maxCu/kPa").append(strReturn);
        for (int i = 0; i < listDeep.size(); i++) {
            content.append(StringUtil.format(listDeep.get(i), 1)).append(strTable).append(StringUtil.format(listMaxCu.get(i), 2)).append(strReturn);
        }
        MyFileUtil.getInstance().saveToSD(context,
                projectNumber + "_" + holeNumber,
                content.toString(),
                "txt",
                new MyFileUtil.SaveFileCallBack() {
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
    private void getDATContent(List models, StringBuilder content, String strReturn, TestEntity
            mTestEntity, String testType) {
        switch (testType) {
            case "单桥试验":
            case "单桥测斜试验":
                content.append(mTestEntity.testDate).append(strReturn);//测试日期
                content.append(mTestEntity.holeNumber).append(strReturn);//孔号
                content.append(mTestEntity.projectNumber).append(strReturn);//工程编号
                content.append(mTestEntity.holeHigh).append(strReturn);//孔口高程
                content.append(mTestEntity.waterLevel).append(strReturn);//地下水位
                content.append("JKD10-4-001").append(strReturn);//探头编号
                content.append("10").append(strReturn);//锥头面积
                content.append("1").append(strReturn);//锥头系数
                content.append("48").append(strReturn);//锥头限值
                content.append("0").append(strReturn);//侧壁系数
                content.append("0").append(strReturn);//侧壁面积
                content.append("0").append(strReturn);
                content.append("").append(strReturn);
                if (models.get(0) instanceof TestDataEntity) {
                    for (TestDataEntity testDataModel : (List<TestDataEntity>) models) {
                        content.append(StringUtil.format(testDataModel.qc, 3)).append(strReturn);
                    }
                } else if (models.get(0) instanceof WirelessResultDataEntity) {
                    for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                        content.append(StringUtil.format(wirelessResultDataModel.qc, 3)).append(strReturn);
                    }
                }
                break;
            case "双桥试验":
            case "双桥测斜试验":
                content.append(mTestEntity.testDate).append(strReturn);//测试日期
                content.append(mTestEntity.holeNumber).append(strReturn);//孔号
                content.append(mTestEntity.projectNumber).append(strReturn);//工程编号
                content.append(mTestEntity.holeHigh).append(strReturn);//孔口高程
                content.append(mTestEntity.waterLevel).append(strReturn);//地下水位
                content.append("JKD10-4-001").append(strReturn);//探头编号
                content.append("10").append(strReturn);//锥头面积
                content.append("1").append(strReturn);//锥头系数
                content.append("48").append(strReturn);//锥头限值
                content.append("0").append(strReturn);//侧壁系数
                content.append("200").append(strReturn);//侧壁面积
                content.append("0").append(strReturn);
                content.append("").append(strReturn);
                if (models.get(0) instanceof TestDataEntity) {
                    for (TestDataEntity testDataModel : (List<TestDataEntity>) models) {
                        content.append(StringUtil.format(testDataModel.qc, 3));
                        content.append(StringUtil.format(testDataModel.fs, 3));
                    }
                } else if (models.get(0) instanceof WirelessResultDataEntity) {
                    for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                        content.append(StringUtil.format(wirelessResultDataModel.qc, 3));
                        content.append(StringUtil.format(wirelessResultDataModel.fs, 3));
                    }
                }
                break;
        }
    }


    @SuppressWarnings("unchecked")
    private void getDATContent(List models, StringBuilder content, String
            strReturn, WirelessTestEntity wirelessTestEntity, String testType) {
        switch (testType) {
            case "单桥试验":
            case "单桥测斜试验":
                content.append(wirelessTestEntity.testDate).append(strReturn);//测试日期
                content.append(wirelessTestEntity.holeNumber).append(strReturn);//孔号
                content.append(wirelessTestEntity.projectNumber).append(strReturn);//工程编号
                content.append(wirelessTestEntity.holeHigh).append(strReturn);//孔口高程
                content.append(wirelessTestEntity.waterLevel).append(strReturn);//地下水位
                content.append("JKD").append(strReturn);//探头编号
                content.append("0").append(strReturn);
                content.append("0").append(strReturn);
                content.append("").append(strReturn);
                if (models.get(0) instanceof TestDataEntity) {
                    for (TestDataEntity testDataModel : (List<TestDataEntity>) models) {
                        content.append(StringUtil.format(testDataModel.qc, 3));
                    }
                } else if (models.get(0) instanceof WirelessResultDataEntity) {
                    for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                        content.append(StringUtil.format(wirelessResultDataModel.qc, 3));
                    }
                }
                break;
            case "双桥试验":
            case "双桥测斜试验":
                content.append(wirelessTestEntity.testDate).append(strReturn);//测试日期
                content.append(wirelessTestEntity.holeNumber).append(strReturn);//孔号
                content.append(wirelessTestEntity.projectNumber).append(strReturn);//工程编号
                content.append(wirelessTestEntity.holeHigh).append(strReturn);//孔口高程
                content.append(wirelessTestEntity.waterLevel).append(strReturn);//地下水位
                content.append("JKD").append(strReturn);//探头编号
                content.append("0").append(strReturn);
                content.append("").append(strReturn);
                if (models.get(0) instanceof TestDataEntity) {
                    for (TestDataEntity testDataModel : (List<TestDataEntity>) models) {
                        content.append(StringUtil.format(testDataModel.qc, 3));
                        content.append(StringUtil.format(testDataModel.fs, 3));
                    }
                } else if (models.get(0) instanceof WirelessResultDataEntity) {
                    for (WirelessResultDataEntity wirelessResultDataModel : (List<WirelessResultDataEntity>) models) {
                        content.append(StringUtil.format(wirelessResultDataModel.qc, 3));
                        content.append(StringUtil.format(wirelessResultDataModel.fs, 3));
                    }
                }
                break;
        }
    }

    public void emailData(List models, String saveType, Object testModel, ISkip iSkip) {
        String fileName = null;
        String name = null;
        if (testModel instanceof TestEntity) {
            name = ((TestEntity) testModel).projectNumber + "_" + ((TestEntity) testModel).holeNumber;
        } else if (testModel instanceof WirelessTestEntity) {
            name = ((WirelessTestEntity) testModel).projectNumber + "_" + ((WirelessTestEntity) testModel).holeNumber;
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

        Map<String, String> emailPreferences = preferencesUtil.getEmailPreferences();
        String sEmail = emailPreferences.get("sEmail");
        String sEmailPassword = emailPreferences.get("sEmailPassword");
        String rEmail = emailPreferences.get("rEmail");
        if (StringUtil.isNotEmpty(sEmail)
                && StringUtil.isNotEmpty(sEmailPassword)
                && StringUtil.isNotEmpty(rEmail)) {
            if (MyFileUtil.fileIsExists(fileName)) {
                sendFile(fileName, sEmail, sEmailPassword, rEmail, iSkip);
            } else {
                saveDataToSd(models, saveType, testModel, iSkip);
                sendFile(fileName, sEmail, sEmailPassword, rEmail, iSkip);
            }

        } else {
            iSkip.skipForResult(new Intent().setClass(context, SetEmailActivity.class), SET_EMAIL);
        }
    }

    public void emailData(List<CrossTestDataEntity> models, TestEntity testModel, ISkip iSkip) {
        String fileName = testModel.projectNumber + "_" + testModel.holeNumber + ".txt";
        Map<String, String> emailPreferences = preferencesUtil.getEmailPreferences();
        String sEmail = emailPreferences.get("sEmail");
        String sEmailPassword = emailPreferences.get("sEmailPassword");
        String rEmail = emailPreferences.get("rEmail");
        if (StringUtil.isNotEmpty(sEmail)
                && StringUtil.isNotEmpty(sEmailPassword)
                && StringUtil.isNotEmpty(rEmail)) {
            if (MyFileUtil.fileIsExists(fileName)) {
                sendFile(fileName, sEmail, sEmailPassword, rEmail, iSkip);
            } else {
                saveDataToSd(models, testModel, iSkip);
                sendFile(fileName, sEmail, sEmailPassword, rEmail, iSkip);
            }

        } else {
            iSkip.skipForResult(new Intent().setClass(context, SetEmailActivity.class), SET_EMAIL);
        }
    }

    private void sendFile(
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

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.baseTest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMDialogActivity;
import www.jingkan.com.chart.DrawChartHelper;
import www.jingkan.com.databinding.ActivityBaseTestBinding;
import www.jingkan.com.localData.testData.TestDataModel;

import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LZ_TXT;


/**
 * 试验基类
 */


public class BaseTestActivity extends MVVMDialogActivity<BaseTestViewModel, ActivityBaseTestBinding> {

    protected DrawChartHelper drawChartHelper;
    protected String strProjectNumber;
    protected String strHoleNumber;


    @Override
    protected void setView() {

    }

    @Override
    public int initView() {
        drawChartHelper = new DrawChartHelper();
        return R.layout.activity_base_test;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                mViewModel.linkDevice(mac);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    int emailType = 0;
    protected String[] emailItems = {EMAIL_TYPE_LY_TXT, EMAIL_TYPE_LY_DAT, EMAIL_TYPE_HN_111, EMAIL_TYPE_LZ_TXT};

    protected void showEmailDataDialog() {
        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择发送的数据类型")
                .setSingleChoiceItems(emailItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        emailType = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.saveTestDataToSD(emailType);
                        mViewModel.emailTestData(emailType);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        emailType = 0;
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    private int saveType = 0;
    protected String[] saveItems = {SAVE_TYPE_LY_TXT, SAVE_TYPE_LY_DAT, SAVE_TYPE_HN_111, SAVE_TYPE_LZ_TXT};

    protected void showSaveDataDialog() {

        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择要保存的数据类型")
                .setSingleChoiceItems(saveItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveType = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.saveTestDataToSD(saveType);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveType = 0;
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    private String mac;

    @Override
    protected void toRefresh() {
        String[] strings = (String[]) mData;//1.mac,2.工程编号,3.孔号,4.试验类型
        mac = strings[0];
        strProjectNumber = strings[1];
        strHoleNumber = strings[2];
        mViewModel.linkDevice(mac);
    }

    @Override
    protected BaseTestViewModel createdViewModel() {
        return new BaseTestViewModel();
    }


    public void showTestData(List<TestDataModel> testDataModels) {
//        deep.setText(StringUtils.format(testDataModels.get(testDataModels.size() - 1).deep, 1));
        List<float[]> listPoints = new ArrayList<>();
        for (TestDataModel testDataModel : testDataModels) {
            listPoints.add(new float[]{testDataModel.qc,
                    testDataModel.fs,
                    testDataModel.fa,
                    testDataModel.deep});
        }
        drawChartHelper.addPointsToChart(listPoints);
    }

    public void showRecordValue(float qc, float fs, float fa, float deep) {
        drawChartHelper.addOnePointToChart(new float[]{qc, fs, fa, deep});
    }


}

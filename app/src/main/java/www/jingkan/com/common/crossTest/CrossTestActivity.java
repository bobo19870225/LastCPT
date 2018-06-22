/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.crossTest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMDialogActivity;
import www.jingkan.com.chart.CrossStrategy;
import www.jingkan.com.chart.DrawChartHelper;
import www.jingkan.com.databinding.ActivityCrossTestBinding;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataModel;
import www.jingkan.com.parameter.SystemConstant;

/**
 * Created by lushengbo on 2018/1/5.
 * 十字板试验
 */

public class CrossTestActivity extends MVVMDialogActivity<CrossTestViewModel, ActivityCrossTestBinding> {


    private DrawChartHelper drawChartHelper;


    @Override
    protected void setView() {

        drawChartHelper = new DrawChartHelper();
        drawChartHelper.setStrategy(new CrossStrategy(this, mViewDataBinding.lineChart));

        setToolBar(SystemConstant.VANE_TEST, R.menu.test);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                mViewModel.linkDevice();
                return false;
            case R.id.save://保存数据到sd卡
                showSaveDataDialog(SystemConstant.VANE_TEST);
                return false;
            case R.id.email://发送邮件到指定邮箱
                showEmailDataDialog(SystemConstant.VANE_TEST);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private int saveType = 0;

    private void showSaveDataDialog(final String testType) {
        final String[] items = {"溧阳科尔(.txt)", "溧阳科尔(.DAT)", "南京华宁(.111)", "北京理正(.txt)"};
        Dialog alertDialog = new AlertDialog.Builder(CrossTestActivity.this)
                .setTitle("请选择要保存的数据类型")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveType = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.saveTestDataToSD(mViewModel.strProjectNumber.get(),
                                mViewModel.strHoleNumber.get(), saveType, testType);
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

    int emailType = 0;

    protected void showEmailDataDialog(final String testType) {
        final String[] items = {"溧阳科尔(.txt)", "溧阳科尔(.DAT)", "南京华宁(.111)", "北京理正(.txt)"};
        Dialog alertDialog = new AlertDialog.Builder(CrossTestActivity.this)
                .setTitle("请选择发送的数据类型")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        emailType = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strProjectNumber = mViewModel.strProjectNumber.get();
                        String strHoleNumber = mViewModel.strHoleNumber.get();
                        mViewModel.saveTestDataToSD(strProjectNumber,
                                strHoleNumber, emailType, testType);
                        switch (emailType) {
                            case 0:
                                mViewModel.emailTestData(strProjectNumber
                                        + "_" + strHoleNumber + ".txt");
                                break;
                            case 1:
                                mViewModel.emailTestData(strProjectNumber
                                        + "_" + strHoleNumber + ".DAT");
                                break;
                            case 2:
                                mViewModel.emailTestData(strProjectNumber
                                        + "_" + strHoleNumber + ".111");
                                break;
                            case 3:
                                mViewModel.emailTestData(strProjectNumber
                                        + "_" + strHoleNumber + "LZ.txt");
                                break;
                        }

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

    @Override
    public int initView() {
        return R.layout.activity_cross_test;
    }

    @Override
    protected CrossTestViewModel createdViewModel() {
        return new CrossTestViewModel();
    }


    @Override
    protected void toRefresh() {

    }

    @Override
    public void onClick(View view) {

    }


    public void showTestData(List<CrossTestDataModel> crossTestDataModels) {
        List<float[]> listPoints = new ArrayList<>();
        for (CrossTestDataModel crossTestDataModel : crossTestDataModels) {
            listPoints.add(new float[]{crossTestDataModel.cu, 0, 0, crossTestDataModel.deg});
        }
        drawChartHelper.addPointsToChart(listPoints);
    }

    public void showRecordValue(String strCu, float deep) {
        drawChartHelper.addOnePointToChart(new float[]{Float.parseFloat(strCu), 0, 0, deep});
    }


    public void resetChart() {
        drawChartHelper.cleanChart();
    }

    public void showModifyDialog() {
        Dialog alertDialog = new AlertDialog.Builder(CrossTestActivity.this)
                .setView(R.layout.dialog_modify)
                .create();
        alertDialog.show();
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.testData;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMListActivity;
import www.jingkan.com.databinding.ActivityWirelessResultDataBinding;
import www.jingkan.com.showDataChar.ShowDataCharActivity;

import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_CORRECT_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_CORRECT_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LZ_TXT;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验详情
 */

public class WirelessResultDataDetailActivity extends MVVMListActivity<WirelessResultDataDetailVM, ActivityWirelessResultDataBinding> {
    @Override
    protected WirelessResultDataDetailVM createdViewModel() {
        return new WirelessResultDataDetailVM();
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("试验数据详情", R.menu.test_data_details);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_char:
                goTo(ShowDataCharActivity.class, new String[]{"无缆试验", (String) mData});
                return false;
            case R.id.save://保存数据到sd卡
                showSaveDataDialog();
                return false;
            case R.id.email://发送邮件到指定邮箱
                showEmailDataDialog();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    int emailType = 0;
    private String[] emailItems = {EMAIL_TYPE_LY_TXT, EMAIL_TYPE_LY_DAT, EMAIL_TYPE_HN_111, EMAIL_TYPE_LZ_TXT, EMAIL_TYPE_CORRECT_TXT};

    private void showEmailDataDialog() {
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
    private String[] saveItems = {
            SAVE_TYPE_LY_TXT,
            SAVE_TYPE_LY_DAT,
            SAVE_TYPE_HN_111,
            SAVE_TYPE_LZ_TXT,
            SAVE_TYPE_CORRECT_TXT};

    private void showSaveDataDialog() {

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

    @SuppressWarnings("unchecked")
    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return mViewDataBinding.srl;
    }

    @Override
    public int initView() {
        return R.layout.activity_wireless_result_data;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @Override
    public void setListView(List list) {
        mViewDataBinding.list.setEmptyView(mViewDataBinding.hint);

    }
}

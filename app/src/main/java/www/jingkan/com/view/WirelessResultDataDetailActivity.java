/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.MenuItem;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityWirelessResultDataDetailsBinding;
import www.jingkan.com.db.dao.WirelessResultDataDao;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.entity.WirelessTestEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.DataUtil;
import www.jingkan.com.view.adapter.WirelessResultDataDetailsAdapter;
import www.jingkan.com.view.base.ListMVVMActivity;
import www.jingkan.com.view_model.ISkip;
import www.jingkan.com.view_model.WirelessResultDataDetailVM;

import static www.jingkan.com.util.DataUtil.SET_EMAIL;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_CORRECT_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_CORRECT_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LZ_TXT;

/**
 * Created by lushengbo on 2018/1/25.
 * 无缆试验详情
 */

public class WirelessResultDataDetailActivity extends ListMVVMActivity<WirelessResultDataDetailVM, ActivityWirelessResultDataDetailsBinding, WirelessResultDataDetailsAdapter> implements ISkip {
    @Inject
    WirelessTestDao wirelessTestDao;
    @Inject
    WirelessResultDataDao wirelessResultDataDao;
    @Inject
    DataUtil dataUtil;
    private WirelessTestEntity wirelessTestEntity;


    @SuppressWarnings("unchecked")
    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return mViewDataBinding.srl;
    }


    @Override
    protected RecyclerView setRecyclerView() {
        return mViewDataBinding.listView;
    }

    @Override
    protected WirelessResultDataDetailsAdapter setAdapter() {
        return new WirelessResultDataDetailsAdapter(R.layout.item_wireless_result_data_details, null);
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("试验数据详情", R.menu.test_data_details);
        mViewModel.lvWirelessTestEntities.observe(this, wirelessTestEntities -> {
            if (wirelessTestEntities != null && !wirelessTestEntities.isEmpty()) {
                wirelessTestEntity = wirelessTestEntities.get(0);
                mViewModel.strProjectNumber.set(wirelessTestEntity.projectNumber);
                mViewModel.strHoleNumber.set(wirelessTestEntity.holeNumber);
                mViewModel.strTestDate.set(wirelessTestEntity.testDate);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_char:
//                goTo(ShowDataCharActivity.class, new String[]{"无缆试验", (String) mData});
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

    private String emailType = EMAIL_TYPE_LY_TXT;
    private String[] emailItems = {EMAIL_TYPE_LY_TXT, EMAIL_TYPE_LY_DAT, EMAIL_TYPE_HN_111, EMAIL_TYPE_LZ_TXT, EMAIL_TYPE_CORRECT_TXT};

    private void showEmailDataDialog() {
        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择发送的数据类型")
                .setSingleChoiceItems(emailItems, 0, (dialog, which) -> emailType = emailItems[which])
                .setPositiveButton("确定", (dialog, which) -> mViewModel.emailTestData(getList(), emailType, wirelessTestEntity, this))
                .setNegativeButton("取消", (dialog, which) -> {
                    emailType = emailItems[0];
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }

    private String saveType = SAVE_TYPE_LY_TXT;
    private String[] saveItems = {SAVE_TYPE_LY_TXT, SAVE_TYPE_LY_DAT, SAVE_TYPE_HN_111,
            SAVE_TYPE_LZ_TXT, SAVE_TYPE_CORRECT_TXT};

    private void showSaveDataDialog() {

        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择要保存的数据类型")
                .setSingleChoiceItems(saveItems, 0, (dialog, which) -> saveType = saveItems[which])
                .setPositiveButton("确定", (dialog, which) -> mViewModel.saveTestDataToSD(getList(), saveType, wirelessTestEntity, this))
                .setNegativeButton("取消", (dialog, which) -> {
                    saveType = saveItems[0];
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, wirelessTestDao, wirelessResultDataDao, dataUtil};
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SET_EMAIL && resultCode == Activity.RESULT_OK) {
            mViewModel.emailTestData(getList(), emailType, wirelessTestEntity, this);
        }
    }


    @Override
    public void action(CallbackMessage callbackMessage) {

    }

    @Override
    public int initView() {
        return R.layout.activity_wireless_result_data_details;
    }

    @Override
    public WirelessResultDataDetailVM createdViewModel() {
        return ViewModelProviders.of(this).get(WirelessResultDataDetailVM.class);
    }

    @Override
    public void skipForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void skip(Intent intent) {

    }

    @Override
    public void sendToastMsg(String msg) {
        showToast(msg);
    }
}

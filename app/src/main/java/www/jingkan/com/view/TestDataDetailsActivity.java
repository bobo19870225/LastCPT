package www.jingkan.com.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityTestDataDetailsBinding;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.dao.TestDataDao;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.DataUtil;
import www.jingkan.com.view.adapter.TestDataDetailsAdapter;
import www.jingkan.com.view.base.ListMVVMActivity;
import www.jingkan.com.view_model.ISkip;
import www.jingkan.com.view_model.TestDataDetailsVM;

import static www.jingkan.com.util.DataUtil.SET_EMAIL;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.util.SystemConstant.EMAIL_TYPE_ZHD_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_CORRECT_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_LZ_TXT;
import static www.jingkan.com.util.SystemConstant.SAVE_TYPE_ZHD_TXT;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class TestDataDetailsActivity extends ListMVVMActivity<TestDataDetailsVM, ActivityTestDataDetailsBinding, TestDataDetailsAdapter>
        implements ISkip {
    @Inject
    TestDataDao testDataDao;
    @Inject
    DataUtil dataUtil;
    @Inject
    TestDao testDao;

    private String saveType;
    private String[] saveItems = {SAVE_TYPE_ZHD_TXT, SAVE_TYPE_LY_TXT, SAVE_TYPE_LY_DAT, SAVE_TYPE_HN_111, SAVE_TYPE_LZ_TXT};
    private String emailType = EMAIL_TYPE_ZHD_TXT;
    private String[] emailItems = {EMAIL_TYPE_ZHD_TXT, EMAIL_TYPE_LY_TXT, EMAIL_TYPE_LY_DAT, EMAIL_TYPE_HN_111, EMAIL_TYPE_LZ_TXT};
    private TestEntity testModel;
    private String testDataID;
    @SuppressWarnings("unchecked")
    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return null;
    }


    @Override
    protected RecyclerView setRecyclerView() {
        return mViewDataBinding.listView;
    }

    @Override
    protected TestDataDetailsAdapter setAdapter() {
        return new TestDataDetailsAdapter(R.layout.item_test_data_details, null);
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("数据处理", R.menu.test_data_details);
        testDataID = (String) mData;
        mViewModel.ldTestEntities.observe(this, testEntities -> {
            if (testEntities != null && !testEntities.isEmpty()) {
                testModel = testEntities.get(0);
                showTest();
            } else {
                showToast("找不到该试验");
            }
        });

    }

    public void showTest() {
        mViewModel.ldProjectNumber.setValue(testModel.projectNumber);
        mViewModel.ldHoleNumber.setValue(testModel.holeNumber);
        mViewModel.ldTestDate.setValue(testModel.testDate);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_char:
                goTo(ShowDataCharActivity.class, testDataID);
                return false;
            case R.id.save://保存数据到sd卡
                if (testModel != null) {
                    showSaveDataDialog(testModel);
                } else {
                    showToast("没有数据");
                }
                return false;
            case R.id.email://发送邮件到指定邮箱
                if (testModel != null) {
                    showEmailDataDialog(testModel);
                } else {
                    showToast("没有数据");
                }
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, testDataDao, dataUtil, testDao};
    }

    @Override
    public int initView() {
        return R.layout.activity_test_data_details;
    }


    @Override
    public TestDataDetailsVM createdViewModel() {
        return ViewModelProviders.of(this).get(TestDataDetailsVM.class);
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }

    private void showSaveDataDialog(final TestEntity testModel) {
        saveType = SAVE_TYPE_ZHD_TXT;
        if (testModel.testType.contains("测斜")) {
            saveItems = new String[]{
                    SAVE_TYPE_ZHD_TXT,
                    SAVE_TYPE_LY_TXT,
                    SAVE_TYPE_LY_DAT,
                    SAVE_TYPE_HN_111,
                    SAVE_TYPE_LZ_TXT,
                    SAVE_TYPE_CORRECT_TXT};
        }
        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择要保存的数据类型")
                .setSingleChoiceItems(saveItems, 0, (dialog, which) -> saveType = saveItems[which])
                .setPositiveButton("确定", (dialog, which) -> mViewModel.saveTestDataToSD(getList(), saveType, testModel, this))
                .setNegativeButton("取消", (dialog, which) -> {
                    saveType = saveItems[0];
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }

    private void showEmailDataDialog(final TestEntity testModel) {
        saveType = EMAIL_TYPE_ZHD_TXT;
        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择发送的数据类型")
                .setSingleChoiceItems(emailItems, 0, (dialog, which) -> emailType = emailItems[which])
                .setPositiveButton("确定", (dialog, which) -> {
                    mViewModel.saveTestDataToSD(getList(), emailType, testModel, this);
                    mViewModel.emailTestData(getList(), emailType, testModel, this);
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    emailType = emailItems[0];
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SET_EMAIL && resultCode == Activity.RESULT_OK) {
            mViewModel.emailTestData(getList(), emailType, testModel, this);
        }

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

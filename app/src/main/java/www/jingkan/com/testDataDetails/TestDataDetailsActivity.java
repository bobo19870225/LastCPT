/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.testDataDetails;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.ListTestDataAdapter;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.baseMVP.BaseMvpActivity;
import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.localData.testData.TestDataModel;
import www.jingkan.com.showDataChar.ShowDataCharActivity;

import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_ZHD_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_CORRECT_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_ZHD_TXT;

public class TestDataDetailsActivity extends BaseMvpActivity<TestDataDetailsPresenter>
        implements TestDataDetailsContract.View {
    @BindView(id = R.id.lv_test_details)
    private ListView lv_test_details;
    @BindView(id = R.id.empty)
    private TextView empty;
    @BindView(id = R.id.project_number)
    private TextView project_number;
    @BindView(id = R.id.hole_number)
    private TextView hole_number;
    @BindView(id = R.id.test_date)
    private TextView test_date;

    private List<TestDataModel> mTestDataModels;
    private TestDataModel testDataModel;

    private ListTestDataAdapter listTestDataAdapter;
    private String testDataID;

    @Override
    protected void setView() {
        setToolBar("历史数据", R.menu.test_data_details);
        testDataID = (String) mData;
        setListView();
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

    private String saveType = SAVE_TYPE_ZHD_TXT;
    private String[] saveItems = {SAVE_TYPE_ZHD_TXT, SAVE_TYPE_LY_TXT, SAVE_TYPE_LY_DAT, SAVE_TYPE_HN_111, SAVE_TYPE_LZ_TXT};

    private void showSaveDataDialog(final TestModel testModel) {
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
                .setPositiveButton("确定", (dialog, which) -> mPresenter.saveTestDataToSD(testModel.projectNumber, testModel.holeNumber, saveType, testModel.testType))
                .setNegativeButton("取消", (dialog, which) -> {
                    saveType = saveItems[0];
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }

    private String emailType = EMAIL_TYPE_ZHD_TXT;
    private String[] emailItems = {EMAIL_TYPE_ZHD_TXT, EMAIL_TYPE_LY_TXT, EMAIL_TYPE_LY_DAT, EMAIL_TYPE_HN_111, EMAIL_TYPE_LZ_TXT};

    private void showEmailDataDialog(final TestModel testModel) {
        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择发送的数据类型")
                .setSingleChoiceItems(emailItems, 0, (dialog, which) -> emailType = emailItems[which])
                .setPositiveButton("确定", (dialog, which) -> {
                    mPresenter.saveTestDataToSD(testModel.projectNumber, testModel.holeNumber, emailType, testModel.testType);

                    mPresenter.emailTestData(testModel.projectNumber,
                            testModel.holeNumber, emailType, testModel.testType);
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    emailType = emailItems[0];
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }

    private void setListView() {
        mTestDataModels = new ArrayList<>();
        listTestDataAdapter = new ListTestDataAdapter(TestDataDetailsActivity.this, R.layout.item_test_data_details, mTestDataModels);
        lv_test_details.setEmptyView(empty);
        lv_test_details.setAdapter(listTestDataAdapter);
        //查看详情
        lv_test_details.setOnItemClickListener((parent, view, position, id) -> {

        });
        registerForContextMenu(lv_test_details);
        lv_test_details.setOnItemLongClickListener((parent, view, position, id) -> {
            testDataModel = mTestDataModels.get(position);
            return false;
        });
    }

    @Override
    protected void toRefresh() {
        String[] split = testDataID.split("_");
        mPresenter.getTest(split[0], split[1]);
        mPresenter.getTestData(testDataID);
    }

    @Override
    public int initView() {
        return R.layout.activity_test_data_details;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "删除");
        menu.add(0, 0, 1, "取消");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                if (testDataModel != null) {
//                    mPresenter.deleteOneHistoryData(testDataModel);
                }
                break;
            case 1:

                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onClick(View view) {

    }


    @Override
    public BasePresenter createdPresenter() {
        return new TestDataDetailsPresenter();

    }

    private TestModel testModel;

    @Override
    public void showTest(TestModel testModel) {
        this.testModel = testModel;
        project_number.setText(testModel.projectNumber);
        hole_number.setText(testModel.holeNumber);
        test_date.setText(testModel.testDate);
    }

    @Override
    public void showTestData(List<TestDataModel> testDataModels) {
        mTestDataModels.clear();
        mTestDataModels.addAll(testDataModels);
//        mTestDataModels = testModels;
        listTestDataAdapter.notifyDataSetChanged();
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.historyData;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import www.jingkan.com.R;
import www.jingkan.com.adapter.ListHistoryAdapter;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.baseMVVM.MVVMListActivity;
import www.jingkan.com.databinding.ActivityHistoryDataBinding;
import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.testDataDetails.TestDataDetailsActivity;
import www.jingkan.com.testDataDetails.crossTestDataDetails.CrossTestDataDetailsActivity;

import static www.jingkan.com.parameter.SystemConstant.VANE_TEST;

public class HistoryDataActivity extends MVVMListActivity<HistoryDataViewModel, ActivityHistoryDataBinding> {
    @BindView(id = R.id.lv_history)
    private ListView lv_history;
    @BindView(id = R.id.empty)
    private TextView empty;

    private List<TestModel> mTestModels;
    private TestModel testModel;

    private ListHistoryAdapter listHistoryAdapter;


    @Override
    protected void setViewWithOutListView() {
        setToolBar("历史数据");
    }

    @Override
    public void setListView(List list) {

    }

    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return null;
    }

    private void setListView() {
        mTestModels = new ArrayList<>();
        listHistoryAdapter = new ListHistoryAdapter(HistoryDataActivity.this, R.layout.item_history_test, mTestModels);
        lv_history.setEmptyView(empty);
        lv_history.setAdapter(listHistoryAdapter);
        //查看详情
        lv_history.setOnItemClickListener((parent, view, position, id) -> {
            TestModel testModel = mTestModels.get(position);
            if (testModel.testType.equals(VANE_TEST)) {
                goTo(CrossTestDataDetailsActivity.class, testModel.testDataID);
            } else {
                goTo(TestDataDetailsActivity.class, testModel.testDataID);
            }
        });
        registerForContextMenu(lv_history);
        lv_history.setOnItemLongClickListener((parent, view, position, id) -> {
            testModel = mTestModels.get(position);
            return false;
        });
    }

    @Override
    protected void toRefresh() {
        mViewModel.getHistoryData();
    }

    @Override
    public int initView() {
        return R.layout.activity_history_data;
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
                if (testModel != null) {
                    mViewModel.deleteOneHistoryData(testModel);
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
    protected HistoryDataViewModel createdViewModel() {
        return new HistoryDataViewModel();
    }

    @Override
    public void onClick(View view) {

    }


//    @Override
//    public void showHistoryData(List<TestModel> testModels) {
//        mTestModels.clear();
//        if (testModels != null) {
//            mTestModels.addAll(testModels);
//        }
//        listHistoryAdapter.notifyDataSetChanged();
//    }
}

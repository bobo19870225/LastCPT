package www.jingkan.com.testDataDetails.crossTestDataDetails;

import android.view.MenuItem;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.BaseMVVMActivity;
import www.jingkan.com.databinding.ActivityCrossTestDataDetailsBinding;
import www.jingkan.com.framework.view.NestedExpandableListView;
import www.jingkan.com.showDataChar.ShowDataCharActivity;

/**
 * Created by Sampson on 2018/7/21.
 * LastCPT
 */
public class CrossTestDataDetailsActivity extends BaseMVVMActivity<CrossTestDataDetailsVM, ActivityCrossTestDataDetailsBinding> {
    @Override
    protected CrossTestDataDetailsVM createdViewModel() {
        return new CrossTestDataDetailsVM();
    }

    @Override
    protected void setView() {
        setToolBar("十字版剪切试验数据", R.menu.test_data_details);
    }

    @Override
    public int initView() {
        return R.layout.activity_cross_test_data_details;
    }

    public void setListView(CrossTestDataListAdapter crossTestDataListAdapter) {
        NestedExpandableListView nestedExpandableListView = mViewDataBinding.ex;
        nestedExpandableListView.setAdapter(crossTestDataListAdapter);
        int count = nestedExpandableListView.getCount();
        for (int i = 0; i < count; i++) {
            nestedExpandableListView.expandGroup(i);
        }
        nestedExpandableListView.setGroupIndicator(null);
        nestedExpandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_char:
//                goTo(ShowDataCharActivity.class, testDataID);
                return false;
            case R.id.save://保存数据到sd卡
                mViewModel.saveTestDataToSD();
                return false;
            case R.id.email://发送邮件到指定邮箱
                mViewModel.emailTestData();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
}

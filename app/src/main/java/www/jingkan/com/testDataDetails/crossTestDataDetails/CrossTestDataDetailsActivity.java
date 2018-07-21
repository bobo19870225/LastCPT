package www.jingkan.com.testDataDetails.crossTestDataDetails;

import android.widget.ExpandableListView;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.BaseMVVMActivity;
import www.jingkan.com.databinding.ActivityCrossTestDataDetailsBinding;

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
        setToolBar("十字版剪切试验数据");
    }

    @Override
    public int initView() {
        return R.layout.activity_cross_test_data_details;
    }

    public void setListView(CrossTestDataListAdapter crossTestDataListAdapter) {
        ExpandableListView expandableListView = mViewDataBinding.ex;
        expandableListView.setAdapter(crossTestDataListAdapter);
    }
}

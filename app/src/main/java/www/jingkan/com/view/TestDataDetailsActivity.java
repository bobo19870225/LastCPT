package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityTestDataDetailsBinding;
import www.jingkan.com.db.dao.TestDataDao;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.adapter.TestDataDetailsAdapter;
import www.jingkan.com.view.base.ListMVVMActivity;
import www.jingkan.com.view_model.TestDataDetailsVM;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class TestDataDetailsActivity extends ListMVVMActivity<TestDataDetailsVM, ActivityTestDataDetailsBinding, TestDataDetailsAdapter> {
    @Inject
    TestDataDao testDataDao;

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
        setToolBar("历史数据", R.menu.test_data_details);
    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, testDataDao};
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
}

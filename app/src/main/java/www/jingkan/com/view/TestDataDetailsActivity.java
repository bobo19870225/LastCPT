package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityTestDataDetailsBinding;
import com.jinkan.www.cpttest.db.dao.TestDataDao;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.adapter.TestDataDetailsAdapter;
import com.jinkan.www.cpttest.view.base.ListMVVMActivity;
import com.jinkan.www.cpttest.view_model.TestDataDetailsVM;

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
    public void callback(CallbackMessage callbackMessage) {

    }
}

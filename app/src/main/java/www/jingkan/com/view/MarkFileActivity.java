package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityMarkFileBinding;
import com.jinkan.www.cpttest.db.dao.WirelessTestDao;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.adapter.MarkFileAdapter;
import com.jinkan.www.cpttest.view.base.ListMVVMActivity;
import com.jinkan.www.cpttest.view_model.MarkFileViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MarkFileActivity extends ListMVVMActivity<MarkFileViewModel, ActivityMarkFileBinding, MarkFileAdapter> {
    @Inject
    WirelessTestDao wirelessTestDao;

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
    protected MarkFileAdapter setAdapter() {
        return new MarkFileAdapter(R.layout.item_mark_file, null);
    }

    @Override
    protected void setViewWithOutListView() {

    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, wirelessTestDao};
    }

    @Override
    public int initView() {
        return R.layout.activity_mark_file;
    }

    @Override
    public MarkFileViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(MarkFileViewModel.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

    }
}

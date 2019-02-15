package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityMarkFileBinding;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.adapter.MarkFileAdapter;
import www.jingkan.com.view.base.ListMVVMActivity;
import www.jingkan.com.view_model.MarkFileViewModel;

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
    public void action(CallbackMessage callbackMessage) {

    }
}

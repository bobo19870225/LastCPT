package www.jingkan.com.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityMarkFileBinding;
import www.jingkan.com.db.dao.WirelessTestDao;
import www.jingkan.com.db.dao.dao_factory.WirelessTestDaoHelper;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.adapter.ItemMarkFileClickCallback;
import www.jingkan.com.view.adapter.ItemMarkupFile;
import www.jingkan.com.view.adapter.MarkFileAdapter;
import www.jingkan.com.view.base.ListMVVMActivity;
import www.jingkan.com.view_model.MarkFileViewModel;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MarkFileActivity extends ListMVVMActivity<MarkFileViewModel, ActivityMarkFileBinding, MarkFileAdapter> {
    @Inject
    WirelessTestDao wirelessTestDao;
    @Inject
    WirelessTestDaoHelper wirelessTestDaoHelper;
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
        return new MarkFileAdapter(R.layout.item_mark_file, new ItemMarkFileClickCallback() {
            @Override
            public void onClick(ItemMarkupFile markupFile) {
                goTo(MarkFileDetailActivity.class, markupFile.getId());
            }

            @Override
            public void onDeleteClick(ItemMarkupFile markupFile) {
                mViewModel.deleteDBData(markupFile);
            }
        });
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("标记数据");
        mViewModel.action.observe(this, s -> toRefresh());
    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, wirelessTestDao, wirelessTestDaoHelper};
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

package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityOrdinaryProbeBinding;
import www.jingkan.com.db.dao.ProbeDao;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.adapter.ItemOrdinaryProbeCallback;
import www.jingkan.com.view.adapter.OrdinaryProbeAdapter;
import www.jingkan.com.view.base.ListMVVMActivity;
import www.jingkan.com.view_model.OrdinaryProbeVM;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Sampson on 2018/12/24.
 * CPTTest
 */
public class OrdinaryProbeActivity extends ListMVVMActivity<OrdinaryProbeVM, ActivityOrdinaryProbeBinding, OrdinaryProbeAdapter> {

    @Inject
    ProbeDao probeDao;

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
    protected OrdinaryProbeAdapter setAdapter() {

        return new OrdinaryProbeAdapter(R.layout.item_ordinary_probe, (ItemOrdinaryProbeCallback) itemOrdinaryProbe -> {
            goTo(AddProbeInfoActivity.class, new String[]{"普通探头", (String) itemOrdinaryProbe.getId()});
        });
    }

    @Override
    protected void setViewWithOutListView() {

    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, probeDao};
    }

    @Override
    public int initView() {
        return R.layout.activity_ordinary_probe;
    }


    @Override
    public OrdinaryProbeVM createdViewModel() {
        return ViewModelProviders.of(this).get(OrdinaryProbeVM.class);
    }

    @Override
    public void action(CallbackMessage callbackMessage) {
        switch (callbackMessage.what) {
            case 0:
                goTo(AddProbeActivity.class, "普通探头");
                break;
        }
    }
}

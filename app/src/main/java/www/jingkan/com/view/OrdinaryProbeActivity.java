package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityOrdinaryProbeBinding;
import com.jinkan.www.cpttest.db.dao.ProbeDao;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.adapter.ItemOrdinaryProbeCallback;
import com.jinkan.www.cpttest.view.adapter.OrdinaryProbeAdapter;
import com.jinkan.www.cpttest.view.base.ListMVVMActivity;
import com.jinkan.www.cpttest.view_model.OrdinaryProbeVM;

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
    public void callback(CallbackMessage callbackMessage) {
        switch (callbackMessage.what) {
            case 0:
                goTo(AddProbeActivity.class, "普通探头");
                break;
        }
    }
}

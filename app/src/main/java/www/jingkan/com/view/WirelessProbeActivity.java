package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityWirelessProbeBinding;
import com.jinkan.www.cpttest.db.dao.WirelessProbeDao;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.adapter.WirelessProbeAdapter;
import com.jinkan.www.cpttest.view.base.ListMVVMActivity;
import com.jinkan.www.cpttest.view_model.WirelessProbeVM;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Sampson on 2019/1/2.
 * CPTTest
 */
public class WirelessProbeActivity extends ListMVVMActivity<WirelessProbeVM, ActivityWirelessProbeBinding, WirelessProbeAdapter> {
    @Inject
    WirelessProbeDao wirelessProbeDao;

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
    protected WirelessProbeAdapter setAdapter() {
        return new WirelessProbeAdapter(R.layout.item_wireless_probe, null);
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("无缆探头列表");
    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, wirelessProbeDao};
    }

    @Override
    public int initView() {
        return R.layout.activity_wireless_probe;
    }

    @Override
    public WirelessProbeVM createdViewModel() {
        return ViewModelProviders.of(this).get(WirelessProbeVM.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {
        switch (callbackMessage.what) {
            case 0:
                goTo(AddProbeActivity.class, "无缆探头");
                break;
        }
    }
}

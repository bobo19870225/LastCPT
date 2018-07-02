/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.wirelessProbe;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMListActivity;
import www.jingkan.com.databinding.ActivityWirelessProbeBinding;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeDao;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeModel;

/**
 * Created by lushengbo on 2018/1/24.
 * 无缆探头列表
 */

public class WirelessProbeActivity extends MVVMListActivity<WirelessProbeViewModel, ActivityWirelessProbeBinding> {
    private int index = 0;
    private List<WirelessProbeModel> wirelessProbeModels;

    @SuppressWarnings("unchecked")
    @Override
    public void setListView(final List list) {
        wirelessProbeModels = list;
        ListView listView = mViewDataBinding.list;
        listView.setEmptyView(mViewDataBinding.hint);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WirelessProbeModel wirelessProbeModel = (WirelessProbeModel) list.get(i);
                goTo(AddWirelessProbeInfoActivity.class, wirelessProbeModel.probeID);
            }
        });
        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("删除操作");
        // add context menu item
        menu.add(0, 0, Menu.NONE, "删除");
        menu.add(0, 1, Menu.NONE, "取消");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0://删除
                WirelessProbeModel wirelessProbeModel = wirelessProbeModels.get(index);
                if (wirelessProbeModel != null) {
                    WirelessProbeDao wirelessProbeDao = DataFactory.getBaseData(WirelessProbeDao.class);
                    wirelessProbeDao.deleteData(wirelessProbeModel.probeID);
                }
                mViewModel.wirelessProbeItemViewModels.clear();
                mViewModel.getWirelessProbeList();
                return true;
            case 1://取消

                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    protected WirelessProbeViewModel createdViewModel() {
        return new WirelessProbeViewModel();
    }


    @Override
    protected void setViewWithOutListView() {
        setToolBar("无缆探头列表");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SwipeRefreshLayout setSwipeRefreshLayout() {
        return mViewDataBinding.srl;
    }

    @Override
    public int initView() {
        return R.layout.activity_wireless_probe;
    }
}

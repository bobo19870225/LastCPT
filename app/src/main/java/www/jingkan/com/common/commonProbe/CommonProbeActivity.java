/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.commonProbe;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.CommonProbeListAdapter;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.baseMVP.BaseMvpActivity;
import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.commonProbe.ProbeDao;
import www.jingkan.com.localData.commonProbe.ProbeModel;

public class CommonProbeActivity extends BaseMvpActivity<CommonProbePresenter> implements CommonProbeContract.View {
    @BindView(id = R.id.probes)
    private ListView probes;
    @BindView(id = R.id.add, click = true)
    private FloatingActionButton add;
    @BindView(id = R.id.hint)
    private TextView hint;

    private CommonProbeListAdapter commonProbeListAdapter;
    private ArrayList<ProbeModel> probeModels;
    private ProbeModel selectedProbeModel;

    @Override
    protected void setView() {
        setToolBar("探头管理");
        registerForContextMenu(probes);
        probeModels = new ArrayList<>();
        commonProbeListAdapter = new CommonProbeListAdapter(CommonProbeActivity.this,
                R.layout.item_common_probe_list, probeModels);
        probes.setAdapter(commonProbeListAdapter);
        probes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goTo(AddProbeInfoActivity.class, probeModels.get(position));
            }
        });
        probes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProbeModel = probeModels.get(position);
                return false;
            }
        });
    }

    @Override
    public int initView() {
        return R.layout.activity_common_probe;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // set context menu title
        menu.setHeaderTitle("删除操作");
        // add context menu item
        menu.add(0, 0, Menu.NONE, "删除");
        menu.add(0, 1, Menu.NONE, "取消");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0://删除
                if (selectedProbeModel != null) {
                    ProbeDao probeDao = DataFactory.getBaseData(ProbeDao.class);
                    probeDao.deleteData(selectedProbeModel.probeID);
                }
                mPresenter.getProbeList();
                return true;
            case 1://取消

                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    protected void toRefresh() {
        mPresenter.getProbeList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                goTo(AddProbeActivity.class, null);
                break;
        }

    }

    @Override
    public BasePresenter createdPresenter() {
        return new CommonProbePresenter();
    }


    @Override
    public void showProbeList(List<ProbeModel> probeModels) {
        this.probeModels.clear();//避免出现重复数据
        this.probeModels.addAll(probeModels);
        hint.setVisibility(View.GONE);
        commonProbeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoProbeList() {
        this.probeModels.clear();//清除数据
        hint.setVisibility(View.VISIBLE);
        commonProbeListAdapter.notifyDataSetChanged();
    }
}

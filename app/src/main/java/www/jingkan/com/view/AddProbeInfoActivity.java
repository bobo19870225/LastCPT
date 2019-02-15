/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityAddProbeInfoBinding;
import com.jinkan.www.cpttest.db.dao.ProbeDao;
import com.jinkan.www.cpttest.db.dao.ProbeDaoHelper;
import com.jinkan.www.cpttest.db.dao.WirelessProbeDao;
import com.jinkan.www.cpttest.db.dao.WirelessProbeDaoHelper;
import com.jinkan.www.cpttest.db.entity.ProbeEntity;
import com.jinkan.www.cpttest.db.entity.WirelessProbeEntity;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.view.adapter.OneTextListAdapter;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.AddProbeInfoVM;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class AddProbeInfoActivity extends BaseMVVMDaggerActivity<AddProbeInfoVM, ActivityAddProbeInfoBinding> {
    private Boolean isWireless;
    @Inject
    SingleBridgeFragment singleBridgeFragment;
    @Inject
    DoubleBridgeFragment doubleBridgeFragment;
    @Inject
    CrossFragment crossFragment;
    @Inject
    ProbeDao probeDao;
    @Inject
    ProbeDaoHelper probeDaoHelper;
    @Inject
    WirelessProbeDao wirelessProbeDao;
    @Inject
    WirelessProbeDaoHelper wirelessProbeDaoHelper;

    private String[] strings;

    @Override
    protected Object[] injectToViewModel() {
        strings = (String[]) mData;
        isWireless = strings[0].equals("无缆探头");
        return new Object[]{mData, probeDaoHelper, wirelessProbeDaoHelper, probeDao};
    }

    @Override
    protected void setMVVMView() {
        mViewModel.titleProbeType.setValue("选择探头类型");
        if (strings.length == 2) {
            setToolBar("编辑探头参数");
            mViewModel.getProbeEntity(strings[1]).observe(this, new Observer<List<ProbeEntity>>() {
                @Override
                public void onChanged(List<ProbeEntity> probeEntities) {
                    ProbeEntity probeEntity = probeEntities.get(0);
                    mViewModel.titleProbeType.setValue("探头类型：");
                    String type = probeEntity.type;
                    mViewModel.probeType.setValue(type);
                    mViewModel.sn.setValue(probeEntity.sn);
                    mViewModel.number.setValue(probeEntity.number);
                    mViewModel.qcArea.setValue(probeEntity.qc_area);
                    mViewModel.qcCoefficient.setValue(String.valueOf(probeEntity.qc_coefficient));
                    mViewModel.qcLimit.setValue(String.valueOf(probeEntity.qc_limit));
                    switch (type) {
                        case "双桥":
                        case "双桥测斜":
                            mViewModel.fsArea.setValue(probeEntity.fs_area);
                            mViewModel.fsCoefficient.setValue(String.valueOf(probeEntity.fs_coefficient));
                            mViewModel.fsLimit.setValue(String.valueOf(probeEntity.fs_limit));
                            break;
                    }
                    setMyFragment(type);
                }
            });

        } else {
            setToolBar("填写探头参数");
        }

    }

    @Override
    public int initView() {
        return R.layout.activity_add_probe_info;
    }

    private void showChoseTypeWindow() {
        View v = getLayoutInflater().inflate(R.layout.theo, null, false);
        final PopupWindow popupWindow = new PopupWindow(v);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        ListView lv_list = v.findViewById(R.id.lv_item);
        List<String> list = new ArrayList<>();
        if (isWireless) {
            list.add("单桥测斜");
            list.add("双桥测斜");
        } else {
            list.add("单桥");
            list.add("单桥测斜");
            list.add("双桥");
            list.add("双桥测斜");
            list.add("十字板");
        }

        OneTextListAdapter adapter = new OneTextListAdapter(AddProbeInfoActivity.this, R.layout.listitem, list);
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener((parent, view, position, id) -> {
            TextView t = view.findViewById(R.id.TextView);
            mViewModel.titleProbeType.setValue("探头类型：");
            String type = t.getText().toString();
            mViewModel.probeType.setValue(type);
            setMyFragment(type);
            popupWindow.dismiss();

        });
        popupWindow.showAtLocation(mViewDataBinding.add, Gravity.CENTER, 0, 0);

    }

    private void setMyFragment(String type) {
        switch (type) {
            case "单桥":
            case "单桥测斜":
                setFragment(R.id.change, new SingleBridgeFragment());
                break;
            case "双桥":
            case "双桥测斜":
                setFragment(R.id.change, new DoubleBridgeFragment());
                break;
            default:
                setFragment(R.id.change, new CrossFragment());
                break;
        }
    }


    @Override
    public AddProbeInfoVM createdViewModel() {
        return ViewModelProviders.of(this).get(AddProbeInfoVM.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {
        switch (callbackMessage.what) {
            case 0:
                showChoseTypeWindow();
                break;
            case 1:
                String strSn = mViewModel.sn.getValue();
                if (strSn != null && strSn.length() == 8) {
                    if (isWireless) {
                        LiveData<List<WirelessProbeEntity>> liveData = wirelessProbeDao.getWirelessProbeEntityByProbeId(strSn);

                        liveData.observe(this, new Observer<List<WirelessProbeEntity>>() {
                            @Override
                            public void onChanged(List<WirelessProbeEntity> wirelessProbeEntities) {
                                liveData.removeObserver(this);
                                if (wirelessProbeEntities != null && !wirelessProbeEntities.isEmpty()) {
                                    mViewModel.saveDataToLocal(true, true);
                                } else {
                                    mViewModel.saveDataToLocal(false, true);
                                }
                            }
                        });
                    } else {
                        LiveData<List<ProbeEntity>> liveData = probeDao.getProbeByProbeId(strSn);
                        liveData.observe(this, new Observer<List<ProbeEntity>>() {
                            @Override
                            public void onChanged(List<ProbeEntity> probeEntities) {
                                liveData.removeObserver(this);
                                if (probeEntities != null && !probeEntities.isEmpty()) {
                                    mViewModel.saveDataToLocal(true, false);
                                } else {
                                    mViewModel.saveDataToLocal(false, false);
                                }
                            }
                        });

                    }

                    goTo(OrdinaryProbeActivity.class, null, true);
                } else {
                    showToast("序列号错误，请查询！");
                }

                break;
        }
    }
}

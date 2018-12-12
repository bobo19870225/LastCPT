/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.commonProbe;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import www.jingkan.com.R;
import www.jingkan.com.adapter.OneTextListAdapter;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.fragment.CrossFragment;
import www.jingkan.com.fragment.DoubleBridgeFragment;
import www.jingkan.com.fragment.SingleBridgeFragment;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.commonProbe.ProbeDaoForRoom;
import www.jingkan.com.localData.commonProbe.ProbeEntity;

public class AddProbeInfoActivity extends BaseActivity implements SingleBridgeFragment.SingleBridgeData,
        DoubleBridgeFragment.DoubleBridgeData,
        CrossFragment.Cross {
    @BindView(id = R.id.chose_type, click = true)
    private RelativeLayout chose_type;
    @BindView(id = R.id.et_sn)
    private EditText et_sn;
    @BindView(id = R.id.et_id)
    private EditText et_id;
    @BindView(id = R.id.add, click = true)
    private Button add;
    @BindView(id = R.id.probe_type)
    private TextView probe_type;
    @BindView(id = R.id.tt_probe_type)
    private TextView tt_probe_type;

    private String[] singleBridgeData;
    private String[] doubleBridgeData;
    private String[] crossData;

    private String strProbeType, sn, strNumber;
    private ProbeDaoForRoom probeDaoForRoom = AppDatabase.getInstance(getApplicationContext()).probeDaoForRoom();

//    private ProbeDao probeDao = DataFactory.getBaseData(ProbeDao.class);

    @Override
    protected void setView() {
        singleBridgeData = new String[3];
        doubleBridgeData = new String[6];
        crossData = new String[3];

        if (mData instanceof ProbeEntity) {
            setToolBar("编辑探头参数");
            ProbeEntity mProbeModel = (ProbeEntity) mData;
            tt_probe_type.setText("探头类型：");
            String type = mProbeModel.type;
            probe_type.setText(type);
            et_sn.setText(mProbeModel.sn);
            et_id.setText(mProbeModel.number);
            setMyFragment(type, new String[]{mProbeModel.qc_area,
                    String.valueOf(mProbeModel.qc_coefficient),
                    String.valueOf(mProbeModel.qc_limit),
                    mProbeModel.fs_area,
                    String.valueOf(mProbeModel.fs_coefficient),
                    String.valueOf(mProbeModel.fs_limit)});
        } else {
            setToolBar("填写探头参数");
        }

    }

    @Override
    public int initView() {
        return R.layout.activity_add_probe_info;
    }

    private void PopupWindow() {
        View v = getLayoutInflater().inflate(R.layout.theo, null);
        final PopupWindow popupWindow = new PopupWindow(v);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        ListView lv_list = v.findViewById(R.id.lv_item);
        List<String> list = new ArrayList<>();
        list.add("单桥");
        list.add("单桥测斜");
        list.add("双桥");
        list.add("双桥测斜");
        list.add("十字板");
        OneTextListAdapter adapter = new OneTextListAdapter(AddProbeInfoActivity.this, R.layout.listitem, list);
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener((parent, view, position, id) -> {
            TextView t = view.findViewById(R.id.TextView);
            tt_probe_type.setText("探头类型：");
            String type = t.getText().toString();
            probe_type.setText(type);
            setMyFragment(type);
            popupWindow.dismiss();

        });
        popupWindow.showAtLocation(add, Gravity.CENTER, 0, 0);

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

    private void setMyFragment(String type, String[] strings) {
        switch (type) {
            case "单桥":
            case "单桥测斜":
                setFragment(R.id.change, new SingleBridgeFragment(), strings);
                break;
            case "双桥":
            case "双桥测斜":
                setFragment(R.id.change, new DoubleBridgeFragment(), strings);
                break;
            default:
                setFragment(R.id.change, new CrossFragment(), strings);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                strProbeType = probe_type.getText().toString();
                sn = et_sn.getText().toString();
                strNumber = et_id.getText().toString();
                if (sn.length() > 0) {
                    LiveData<List<ProbeEntity>> liveData = probeDaoForRoom.getProbeByProbeId(sn);
                    List<ProbeEntity> probeEntities = liveData.getValue();
                    if (probeEntities != null && !probeEntities.isEmpty()) {
                        saveDataToLocal(true);
                        goTo(CommonProbeActivity.class, null, true);
                    } else {
                        saveDataToLocal(false);
                        goTo(CommonProbeActivity.class, null, true);
                    }
//                    probeDao.getData(new DataLoadCallBack() {
//                        @Override
//                        public void onDataLoaded(List model) {//修改
//                            saveDataToLocal(true);
//                            goTo(CommonProbeActivity.class, null, true);
//
//                        }
//
//                        @Override
//                        public void onDataNotAvailable() {
//                            saveDataToLocal(false);
//                            goTo(CommonProbeActivity.class, null, true);
//
//                        }
//                    }, sn);

                } else {
                    showToast("请将参数填写完整");
                }
                break;
            case R.id.chose_type:
                PopupWindow();
                break;
        }
    }

    private void saveDataToLocal(boolean isUpdate) {
        ProbeEntity probeModel = new ProbeEntity();
        probeModel.probeID = sn;
        probeModel.sn = sn;
        probeModel.number = strNumber;
        probeModel.type = strProbeType;
        switch (strProbeType) {
            case "单桥":
            case "单桥测斜":
                if (StringUtils.isInteger(singleBridgeData[0])) {
                    probeModel.qc_area = singleBridgeData[0];
                } else {
                    showToast("锥底面积不合法");
                    return;
                }
                if (StringUtils.isFloat(singleBridgeData[1])) {
                    probeModel.qc_coefficient = Float.parseFloat(singleBridgeData[1]);
                } else {
                    showToast("锥头标定系数不合法");
                    return;
                }
                if (StringUtils.isInteger(singleBridgeData[2])) {
                    probeModel.qc_limit = Integer.parseInt(singleBridgeData[2]);
                } else {
                    showToast("锥头限值不合法");
                    return;
                }
                break;
            case "双桥":
            case "双桥测斜":
//----------------------fs---------------------------------------------------
                if (StringUtils.isInteger(doubleBridgeData[0])) {
                    probeModel.qc_area = doubleBridgeData[0];
                } else {
                    showToast("锥底面积不合法");
                    return;
                }
                if (StringUtils.isFloat(doubleBridgeData[1])) {
                    probeModel.qc_coefficient = Float.parseFloat(doubleBridgeData[1]);
                } else {
                    showToast("锥头标定系数不合法");
                    return;
                }
                if (StringUtils.isInteger(doubleBridgeData[2])) {
                    probeModel.qc_limit = Integer.parseInt(doubleBridgeData[2]);
                } else {
                    showToast("锥头限值不合法");
                    return;
                }
                if (StringUtils.isInteger(doubleBridgeData[3])) {
                    probeModel.fs_area = doubleBridgeData[3];
                } else {
                    showToast("侧壁面积不合法");
                    return;
                }
                if (StringUtils.isFloat(doubleBridgeData[4])) {
                    probeModel.fs_coefficient = Float.parseFloat(doubleBridgeData[4]);
                } else {
                    showToast("侧壁标定系数不合法");
                    return;
                }
                if (StringUtils.isInteger(doubleBridgeData[5])) {
                    probeModel.fs_limit = Integer.parseInt(doubleBridgeData[5]);
                } else {
                    showToast("侧壁限值不合法");
                    return;
                }
                break;
            case "十字板":
                if (StringUtils.isInteger(crossData[0])) {
                    probeModel.qc_area = crossData[0];
                } else {
                    showToast("板头面积不合法");
                    return;
                }
                if (StringUtils.isFloat(crossData[1])) {
                    probeModel.qc_coefficient = Float.parseFloat(crossData[1]);
                } else {
                    showToast("板头标定系数不合法");
                    return;
                }
                if (StringUtils.isInteger(crossData[2])) {
                    probeModel.qc_limit = Integer.parseInt(crossData[2]);
                } else {
                    showToast("板头限值不合法");
                    return;
                }
                break;
        }
        if (isUpdate) {
            probeDaoForRoom.upDateProbe(probeModel);
        } else {
            probeDaoForRoom.insertProbeEntity(probeModel);
        }

    }

    @Override
    public void getDoubleBridgeData(String[] ss) {
        doubleBridgeData = ss;
    }

    @Override
    public void getSingleBridgeData(String[] data) {
        singleBridgeData = data;
    }

    @Override
    public void getCrossData(String[] ss) {
        crossData = ss;
    }
}

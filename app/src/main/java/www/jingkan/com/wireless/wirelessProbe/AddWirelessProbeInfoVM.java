/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.wirelessProbe;

import android.content.Intent;
import android.databinding.ObservableField;

import java.util.List;

import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeDao;
import www.jingkan.com.localData.wirelessProbe.WirelessProbeModel;

/**
 * Created by lushengbo on 2018/1/24.
 * 手动输入无缆探头
 */

public class AddWirelessProbeInfoVM extends BaseViewModel<AddWirelessProbeInfoActivity> {
    public final ObservableField<Boolean> doubleBridge = new ObservableField<>(false);
    public final ObservableField<String> strChoseProbeType = new ObservableField<>("请选择探头类型");
    public final ObservableField<String> strProbeType = new ObservableField<>();
    public final ObservableField<String> strSn = new ObservableField<>();
    public final ObservableField<String> strNumber = new ObservableField<>();
    public final ObservableField<String> strQcArea = new ObservableField<>();
    public final ObservableField<String> strQcCoefficient = new ObservableField<>();
    public final ObservableField<String> strQcLimit = new ObservableField<>();
    public final ObservableField<String> strFsArea = new ObservableField<>();
    public final ObservableField<String> strFsCoefficient = new ObservableField<>();
    public final ObservableField<String> strFsLimit = new ObservableField<>();

    @Override
    protected void init(Object data) {
        if (data != null) {
            WirelessProbeDao wirelessProbeDao = DataFactory.getBaseData(WirelessProbeDao.class);
            wirelessProbeDao.getData(new DataLoadCallBack<WirelessProbeModel>() {

                @Override
                public void onDataLoaded(List<WirelessProbeModel> models) {
                    WirelessProbeModel wirelessProbeModel = models.get(0);
                    if (wirelessProbeModel.type.contains("双桥")) {
                        doubleBridge.set(true);
                        strFsArea.set(wirelessProbeModel.fs_area);
                        strFsCoefficient.set(String.valueOf(wirelessProbeModel.fs_coefficient));
                        strFsLimit.set(String.valueOf(wirelessProbeModel.fs_limit));
                    } else {
                        doubleBridge.set(false);
                    }
                    strChoseProbeType.set("探头类型：");
                    strProbeType.set(wirelessProbeModel.type);
                    strSn.set(wirelessProbeModel.sn);
                    strNumber.set(wirelessProbeModel.number);
                    strQcArea.set(wirelessProbeModel.qc_area);
                    strQcCoefficient.set(String.valueOf(wirelessProbeModel.qc_coefficient));
                    strQcLimit.set(String.valueOf(wirelessProbeModel.qc_limit));
                }

                @Override
                public void onDataNotAvailable() {

                }
            }, (String) data);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }

    public boolean addProbe() {
        WirelessProbeModel wirelessProbeModel = new WirelessProbeModel();
        if (doubleBridge.get()) {
            if (StringUtils.isEmpty(strProbeType.get())) {
                getView().showToast("请选择探头类型");
                return false;
            }
            if (StringUtils.isEmpty(strSn.get())) {
                getView().showToast("请填写序列号");
                return false;
            }
            if (StringUtils.isEmpty(strNumber.get())) {
                getView().showToast("请填写探头编号");
                return false;
            }
            if (StringUtils.isEmpty(strQcArea.get())) {
                getView().showToast("请填写锥头面积");
                return false;
            }
            if (StringUtils.isEmpty(strQcCoefficient.get())) {
                getView().showToast("请填写锥头系数");
                return false;
            }
            if (StringUtils.isEmpty(strQcLimit.get())) {
                getView().showToast("请填写锥头限值");
                return false;
            }
            if (StringUtils.isEmpty(strFsArea.get())) {
                getView().showToast("请填写侧壁面积");
                return false;
            }
            if (StringUtils.isEmpty(strFsCoefficient.get())) {
                getView().showToast("请填写侧壁系数");
                return false;
            }
            if (StringUtils.isEmpty(strFsLimit.get())) {
                getView().showToast("请填写侧壁限值");
                return false;
            }
            wirelessProbeModel.fs_area = strFsArea.get();
            wirelessProbeModel.fs_coefficient = Float.parseFloat(strFsCoefficient.get());
            wirelessProbeModel.fs_limit = Integer.parseInt(strFsLimit.get());
        } else {
            if (StringUtils.isEmpty(strProbeType.get())) {
                getView().showToast("请选择探头类型");
                return false;
            }
            if (StringUtils.isEmpty(strSn.get())) {
                getView().showToast("请填写序列号");
                return false;
            }
            if (StringUtils.isEmpty(strNumber.get())) {
                getView().showToast("请填写探头编号");
                return false;
            }
            if (StringUtils.isEmpty(strQcArea.get())) {
                getView().showToast("请填写锥头面积");
                return false;
            }
            if (StringUtils.isEmpty(strQcCoefficient.get())) {
                getView().showToast("请填写锥头系数");
                return false;
            }
            if (StringUtils.isEmpty(strQcLimit.get())) {
                getView().showToast("请填写锥头限值");
                return false;
            }
        }
        wirelessProbeModel.type = strProbeType.get();
        wirelessProbeModel.probeID = strSn.get();
        wirelessProbeModel.sn = strSn.get();
        wirelessProbeModel.number = strNumber.get();
        wirelessProbeModel.qc_area = strQcArea.get();
        wirelessProbeModel.qc_coefficient = Float.parseFloat(strQcCoefficient.get());
        wirelessProbeModel.qc_limit = Integer.parseInt(strQcLimit.get());
        WirelessProbeDao wirelessProbeDao = DataFactory.getBaseData(WirelessProbeDao.class);
        wirelessProbeDao.addData(wirelessProbeModel);
        getView().goTo(WirelessProbeActivity.class, null, true);
        return true;
    }

    public void choseProbeType() {
        getView().showChoseProbeTypePPW();
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.commonProbe;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.localData.commonProbe.ProbeData;
import www.jingkan.com.localData.commonProbe.ProbeModel;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;

/**
 * Created by lushengbo on 2017/4/23.
 * 普通探头中介
 */

class CommonProbePresenter extends BasePresenter<CommonProbeActivity> implements CommonProbeContract.Presenter {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void init(Context context, Object data) {

    }


    @Override
    public void getProbeList() {
        ProbeData probeData = DataFactory.getBaseData(ProbeData.class);
        probeData.getData(new DataLoadCallBack<ProbeModel>() {

            @Override
            public void onDataLoaded(List<ProbeModel> models) {
                myView.get().showProbeList(models);
            }

            @Override
            public void onDataNotAvailable() {
                myView.get().showNoProbeList();
            }
        });
    }

    @Override
    public void deleteProbe(String sn) {

    }

    @Override
    public void addProbe(String mac) {

    }

    @Override
    public void modifyProbe(String sn) {

    }
}

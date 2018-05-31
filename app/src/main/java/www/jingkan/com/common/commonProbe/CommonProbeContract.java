/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.commonProbe;

import www.jingkan.com.localData.commonProbe.ProbeModel;

import java.util.List;

/**
 * Created by lushengbo on 2017/4/24.
 * 普通探头
 */

public interface CommonProbeContract {
    interface View {
        void showProbeList(List<ProbeModel> probeModels);

        void showNoProbeList();
    }

    interface Presenter {

        void getProbeList();

        void deleteProbe(String sn);

        void addProbe(String mac);

        void modifyProbe(String sn);


    }

}

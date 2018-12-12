/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.common.commonProbe;

import java.util.List;

import www.jingkan.com.localData.commonProbe.ProbeEntity;

/**
 * Created by lushengbo on 2017/4/24.
 * 普通探头
 */

public interface CommonProbeContract {
    interface View {
        void showProbeList(List<ProbeEntity> probeModels);

        void showNoProbeList();
    }

    interface Presenter {

        void getProbeList();

        void deleteProbe(String sn);

        void addProbe(String mac);

        void modifyProbe(String sn);


    }

}

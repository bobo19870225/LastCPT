/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.wirelessProbe;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.BaseMVVMActivity;
import www.jingkan.com.databinding.ActivityAddProbeBinding;

/**
 * Created by lushengbo on 2018/1/24.
 * 添加无缆探头
 */

public class AddWirelessProbeActivity extends BaseMVVMActivity<AddWirelessProbeViewModel, ActivityAddProbeBinding> {
    @Override
    protected AddWirelessProbeViewModel createdViewModel() {
        return new AddWirelessProbeViewModel();
    }


    @Override
    protected void setView() {
        setToolBar("添加探头");
    }

    @Override
    public int initView() {
        return R.layout.activity_add_probe;
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.probeTest;

import android.view.MenuItem;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMDialogActivity;
import www.jingkan.com.databinding.ActivityTestingBinding;

/**
 * Created by lushengbo on 2017/5/21.
 * 探头检测
 */

public class TestingActivity extends MVVMDialogActivity<TestingViewModel, ActivityTestingBinding> {


    @Override
    protected void setView() {
        setToolBar("探头检测",R.menu.link);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.link:
                mViewModel.link();
                return false;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public int initView() {
        return R.layout.activity_testing;
    }

    @Override
    protected TestingViewModel createdViewModel() {
        return new TestingViewModel();
    }

}

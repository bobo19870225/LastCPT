/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.main;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityCalibrationParameterBinding;
import com.jinkan.www.cpttest.db.dao.CalibrationProbeDao;
import com.jinkan.www.cpttest.db.entity.CalibrationProbeEntity;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.util.PreferencesUtil;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.CalibrationParameterVM;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by bobo on 2017/3/19.
 * 数字标定参数
 */

public class CalibrationParameterActivity extends BaseMVVMDaggerActivity<CalibrationParameterVM, ActivityCalibrationParameterBinding> {
    @Inject
    CalibrationProbeDao calibrationProbeDao;
    @Inject
    PreferencesUtil preferencesUtil;

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, calibrationProbeDao, preferencesUtil};
    }

    @Override
    protected void setMVVMView() {
        String[] strings = (String[]) mData;
        setTitle(strings[1]);
        mViewModel.ldCalibrationProbeEntity.observe(this, calibrationProbeEntities -> {
            if (calibrationProbeEntities != null && !calibrationProbeEntities.isEmpty()) {
                CalibrationProbeEntity calibrationProbeEntity = calibrationProbeEntities.get(calibrationProbeEntities.size() - 1);
                mViewModel.ldSn.setValue(calibrationProbeEntity.probeID);
                mViewModel.ldProbeNumber.setValue(calibrationProbeEntity.number);
                mViewModel.ldArea.setValue(calibrationProbeEntity.work_area);
                mViewModel.ldLoad.setValue(calibrationProbeEntity.differential);
            } else {
                showToast("还没有标定数据");
            }
        });

    }

    @Override
    public int initView() {
        return R.layout.activity_calibration_parameter;
    }

    @Override
    public CalibrationParameterVM createdViewModel() {
        return ViewModelProviders.of(this).get(CalibrationParameterVM.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

    }
}

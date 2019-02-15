/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityCalibrationParameterBinding;
import www.jingkan.com.db.dao.CalibrationProbeDao;
import www.jingkan.com.db.dao.CalibrationProbeDaoHelper;
import www.jingkan.com.db.entity.CalibrationProbeEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.PreferencesUtil;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.CalibrationParameterVM;

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
    CalibrationProbeDaoHelper calibrationProbeDaoHelper;
    @Inject
    PreferencesUtil preferencesUtil;


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, calibrationProbeDao, calibrationProbeDaoHelper, preferencesUtil};
    }

    @Override
    protected void setMVVMView() {
        String[] strings = (String[]) mData;
        setTitle(strings[1]);
        mViewModel.lvCalibrationProbeEntities.observe(this, calibrationProbeEntities -> {
            if (calibrationProbeEntities != null && !calibrationProbeEntities.isEmpty()) {
                CalibrationProbeEntity calibrationProbeEntity = calibrationProbeEntities.get(calibrationProbeEntities.size() - 1);
                mViewModel.lvSn.setValue(calibrationProbeEntity.probeID);
                mViewModel.lvProbeNumber.setValue(calibrationProbeEntity.number);
                mViewModel.lvArea.setValue(calibrationProbeEntity.work_area);
                mViewModel.lvDifferential.setValue(calibrationProbeEntity.differential);
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
    public void action(CallbackMessage callbackMessage) {

    }
}

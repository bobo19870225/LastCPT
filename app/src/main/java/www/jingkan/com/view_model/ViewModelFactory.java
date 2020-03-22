/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package www.jingkan.com.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import www.jingkan.com.db.dao.CalibrationProbeDao;
import www.jingkan.com.db.dao.CalibrationVerificationDao;
import www.jingkan.com.db.dao.CrossTestDataDao;
import www.jingkan.com.db.dao.MemoryDataDao;
import www.jingkan.com.db.dao.ProbeDao;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.util.DataUtil;
import www.jingkan.com.util.VibratorUtil;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;

/**
 * A creator is used to inject the product ID into the ViewModel
 * <p>
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
@Singleton
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application application;

    @Inject
    public ViewModelFactory(Application application) {
        this.application = application;
    }


    @Inject
    BluetoothUtil bluetoothUtil;
    @Inject
    BluetoothCommService bluetoothCommService;
    @Inject
    TestDao testDao;
    @Inject
    CrossTestDataDao crossTestDataDao;
    @Inject
    MemoryDataDao memoryDataDao;
    @Inject
    CalibrationProbeDao calibrationProbeDao;
    @Inject
    CalibrationVerificationDao calibrationVerificationDao;
    @Inject
    VibratorUtil vibratorUtil;
    @Inject
    ProbeDao probeDao;
    @Inject
    DataUtil dataUtil;
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(CrossTestViewModel.class)) {
            return (T) new CrossTestViewModel(application, bluetoothUtil, bluetoothCommService, testDao, crossTestDataDao, probeDao, dataUtil);
        } else if (modelClass.isAssignableFrom(SetCalibrationDataVM.class)) {
            return (T) new SetCalibrationDataVM(application, bluetoothUtil,
                    bluetoothCommService, memoryDataDao,
                    calibrationProbeDao, vibratorUtil);
        } else if (modelClass.isAssignableFrom(OldSetCalibrationDataVM.class)) {
            return (T) new OldSetCalibrationDataVM(application, bluetoothUtil,
                    bluetoothCommService, memoryDataDao,
                    calibrationProbeDao, vibratorUtil);
        } else if (modelClass.isAssignableFrom(CalibrationVerificationVM.class)) {
            return (T) new CalibrationVerificationVM(application, bluetoothUtil,
                    bluetoothCommService, calibrationProbeDao,
                    calibrationVerificationDao, vibratorUtil);
        } else if (modelClass.isAssignableFrom(AnalogCaCalibrationVerificationVM.class)) {
            return (T) new AnalogCaCalibrationVerificationVM(application, calibrationProbeDao);
        } else if (modelClass.isAssignableFrom(CrossTestDataDetailsMV.class)) {
            return (T) new CrossTestDataDetailsMV(application, testDao, crossTestDataDao, probeDao, dataUtil);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }


}

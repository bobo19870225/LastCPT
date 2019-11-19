package www.jingkan.com.view;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityCalibrationVerificationBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view.chart.CalibrationStrategy;
import www.jingkan.com.view.chart.DrawCalibrationChartHelper;
import www.jingkan.com.view_model.CalibrationVerificationVM;
import www.jingkan.com.view_model.ViewModelFactory;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class CalibrationVerificationActivity extends BaseMVVMDaggerActivity<CalibrationVerificationVM, ActivityCalibrationVerificationBinding> {
    @Inject
    DrawCalibrationChartHelper drawCalibrationChartHelper;
    @Inject
    ViewModelFactory viewModelFactory;
    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{
                mData
        };
    }

    @Override
    protected void setMVVMView() {
        String[] strings = (String[]) mData;
        setToolBar(strings[2], R.menu.calibration_verification);
        mViewModel.action.observe(this, s -> {
            switch (s) {
                case "startActivityForResult":
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 0);
                    break;
                case "清除侧壁数据":
                    showMyDialog("清除数据",
                            "要重新标定需清除原有侧壁数据，确定清除请点击【确定】",
                            true,
                            (dialog, which) -> mViewModel.deleteDBData("侧壁"));
                    break;
                case "清除锥头数据":
                    showMyDialog("清除数据",
                            "要重新标定需清除原有锥头数据，确定清除请点击【确定】",
                            true,
                            (dialog, which) -> mViewModel.deleteDBData("锥头"));
                    break;
            }
        });
        drawCalibrationChartHelper.setStrategy(new CalibrationStrategy(this, mViewDataBinding.chart1));
        mViewModel.ldData.observe(this, floats -> {
            if (floats[2] == 0) {
                drawCalibrationChartHelper.addOnePointToCalibrationChart(floats[0], floats[1], "加荷1");
            } else if (floats[2] == 1) {
                drawCalibrationChartHelper.addOnePointToCalibrationChart(floats[0], floats[1], "卸荷1");
            } else if (floats[2] == 2) {
                drawCalibrationChartHelper.addOnePointToCalibrationChart(floats[0], floats[1], "加荷2");
            } else if (floats[2] == 3) {
                drawCalibrationChartHelper.addOnePointToCalibrationChart(floats[0], floats[1], "卸荷2");

            }

        });
    }

    @Override
    public CalibrationVerificationVM createdViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(CalibrationVerificationVM.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_calibration_verification;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                mViewModel.saveData();
                return false;
            case R.id.link:
                mViewModel.linkDevice();
                return false;
            case R.id.help:
            case R.id.about:

                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK) {
            mViewModel.doRecord();
        }
        return true;
    }
}

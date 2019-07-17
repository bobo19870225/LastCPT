package www.jingkan.com.view;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivitySetCalibrationDataBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.SetCalibrationDataVM;
import www.jingkan.com.view_model.ViewModelFactory;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class SetCalibrationDataActivity extends BaseMVVMDaggerActivity<SetCalibrationDataVM, ActivitySetCalibrationDataBinding> {

    @Inject
    ViewModelFactory viewModelFactory;
    private int deleteWhich = 0;

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData};
    }

    @Override
    protected void setMVVMView() {
        mViewModel.action.observe(this, s -> {
            switch (s) {
                case "ACTION_REQUEST_ENABLE":
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 0);
                    break;
                case "outPut":
                    double[] doubles = testLeastSquareMethodFromApache();
                    toast("ok");
                    break;
                case "showFaChannel":
                    showFaChannel();
                    break;
            }

        });
        String[] strings = (String[]) mData;
        setToolBar(strings[2], R.menu.calibration);
    }

    private void showFaChannel() {
        mViewDataBinding.tbYbl.setVisibility(View.GONE);
        mViewDataBinding.rlFa.setVisibility(View.VISIBLE);
        mViewModel.ldChannel.setValue("斜度");
    }
//    private void showFaChannelValue(int x, int y, int z) {
//        tv_x.setText(String.valueOf(x));
//        tv_y.setText(String.valueOf(y));
//        tv_z.setText(String.valueOf(z));
//        bt_record.setEnabled(false);
//        bt_record.setBackgroundColor(getResources().getColor(R.color.rl_gray));
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set:
                showSetDialog();
                return false;
            case R.id.reset:
                showResetDialog();
                return false;

            case R.id.link:
                String[] strings = (String[]) mData;
                mViewModel.linkDevice(strings[0]);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSetDialog() {
        Dialog alertDialog = new AlertDialog.Builder(SetCalibrationDataActivity.this)
                .setTitle("设置探头内存数据")
                .setMessage("确定要设置探头内存数据吗？请拔掉蓝牙连接器电源，重插，重连开关打到B再点“确定”")
                .setPositiveButton("确定", (dialog, which) -> mViewModel.setDataToProbe())
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).create();
        alertDialog.show();
    }

    private void showResetDialog() {
        final String[] items = {"全部", "锥头", "侧壁", "无"};
        Dialog alertDialog = new AlertDialog.Builder(SetCalibrationDataActivity.this)
                .setTitle("请选择手机中要清除的数据")
                .setSingleChoiceItems(items, 0, (dialog, which) -> deleteWhich = which)
                .setPositiveButton("确定", (dialog, which) -> mViewModel.resetDataToProbe(deleteWhich))
                .setNegativeButton("取消", (dialog, which) -> {
                    deleteWhich = 0;
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }
    @Override
    public SetCalibrationDataVM createdViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(SetCalibrationDataVM.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_set_calibration_data;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }

    private double[] testLeastSquareMethodFromApache() {
        WeightedObservedPoints obs = new WeightedObservedPoints();
        obs.add(-3, -1);
        obs.add(-2, 0);
        obs.add(-1, 1);
        obs.add(0, 2.3);
        obs.add(1, 3);
        obs.add(2, 4);
        obs.add(3, 5.5);

        // Instantiate a third-degree polynomial fitter.
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(3);
        // Retrieve fitted parameters (coefficients of the polynomial function).
        return fitter.fit(obs.toList());
    }


}

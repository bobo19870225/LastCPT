package www.jingkan.com.view;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

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
                case "disShowFaChannel":
                    disShowFaChannel();
                    break;
                case "showFaChannel":
                    showFaChannel();
                    break;
                case "showSwitchDialogFs":
                    showSwitchDialog(0);
                    break;
                case "showSwitchDialogFa":
                    showSwitchDialog(1);
                    break;

            }

        });
        String[] strings = (String[]) mData;
        setToolBar(strings[2], R.menu.calibration);
    }

    /**
     * @param which 0：侧壁通道 1：测斜通道
     */
    private void showSwitchDialog(int which) {
        switch (which) {
            case 0://侧壁通道
                Dialog alertDialog = new AlertDialog.Builder(SetCalibrationDataActivity.this)
                        .setTitle("变换采集通道")
                        .setMessage("即将为您变换到侧壁数据通道")
                        .setPositiveButton("确定", (dialog, which1) -> {

                        })
                        .setCancelable(false).create();
                alertDialog.show();
                break;
            case 1://测斜通道
                alertDialog = new AlertDialog.Builder(SetCalibrationDataActivity.this)
                        .setTitle("变换采集通道")
                        .setMessage("即将为您变换到测斜数据通道")
                        .setPositiveButton("确定", (dialog, which12) -> {

                        }).setCancelable(false).create();
                alertDialog.show();
                break;
        }


    }
    private void showFaChannel() {
        mViewDataBinding.tbYbl.setVisibility(View.GONE);
        mViewDataBinding.ttValid.setVisibility(View.GONE);
        mViewDataBinding.valid.setVisibility(View.GONE);
        mViewDataBinding.llRecord.setVisibility(View.GONE);
        mViewDataBinding.rlFa.setVisibility(View.VISIBLE);
        mViewModel.ldChannel.setValue("斜度");
    }

    private void disShowFaChannel() {
        mViewDataBinding.tbYbl.setVisibility(View.VISIBLE);
        mViewDataBinding.ttValid.setVisibility(View.VISIBLE);
        mViewDataBinding.valid.setVisibility(View.VISIBLE);
        mViewDataBinding.llRecord.setVisibility(View.VISIBLE);
        mViewDataBinding.rlFa.setVisibility(View.GONE);
    }

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
        final String[] items = {"是", "否"};
        Dialog alertDialog = new AlertDialog.Builder(SetCalibrationDataActivity.this)
                .setTitle("是否同时清除手机中的数据")
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


}

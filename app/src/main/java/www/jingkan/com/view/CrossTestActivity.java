package www.jingkan.com.view;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityCrossTestBinding;
import www.jingkan.com.db.entity.CrossTestDataEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.SystemConstant;
import www.jingkan.com.view.base.DialogMVVMDaggerActivity;
import www.jingkan.com.view.chart.CrossStrategy;
import www.jingkan.com.view.chart.DrawChartHelper;
import www.jingkan.com.view_model.CrossTestViewModel;
import www.jingkan.com.view_model.ISkip;
import www.jingkan.com.view_model.ViewModelFactory;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class CrossTestActivity extends DialogMVVMDaggerActivity<CrossTestViewModel, ActivityCrossTestBinding> implements ISkip {
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    DrawChartHelper drawChartHelper;
    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{this};
    }

    @Override
    protected void setMVVMView() {
        setToolBar(SystemConstant.VANE_TEST, R.menu.test);
        String[] strings = (String[]) mData;
        mViewModel.mac = strings[0];
        mViewModel.strProjectNumber.setValue(strings[1]);
        mViewModel.strHoleNumber.setValue(strings[2]);
        mViewModel.getTestParameters();
        mViewModel.linkDevice();
        ActivityCrossTestBinding activityCrossTestBinding = mViewDataBinding;
        drawChartHelper.setStrategy(new CrossStrategy(getApplicationContext(), activityCrossTestBinding.lineChart));
        mViewModel.action.observe(this, s -> {
            switch (s) {
                case "LinkBT":
                    showWaitDialog("正在连接蓝牙", false, true);
                    break;
                case "OpenBT":
                    // 蓝牙没有打开，调用系统方法要求用户打开蓝牙
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 0);
                    break;
                case "modify":
                    showModifyDialog();
                    break;
                case "showRecordValue":
                    drawChartHelper.addOnePointToChart(new float[]{Float.parseFloat(Objects.requireNonNull(mViewModel.strCuEffective.getValue())), 0, 0, Float.parseFloat(Objects.requireNonNull(mViewModel.deg.getValue()))});
                    break;
                case "closeWaitDialog":
                    closeWaitDialog();
                    break;
                case "onBackPressed":
                    onBackPressed();
                    break;
            }
        });
        mViewModel.ldCrossTestDataEntities.observe(this, crossTestDataEntities -> {
            List<float[]> listPoints = new ArrayList<>();
            for (CrossTestDataEntity crossTestDataModel : crossTestDataEntities) {
                listPoints.add(new float[]{crossTestDataModel.cu, 0, 0, crossTestDataModel.deg});
            }
            drawChartHelper.addPointsToChart(listPoints);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解决内存泄漏
        mViewDataBinding.lineChart.removeAllViews();
    }

    public void showModifyDialog() {
        String deep = mViewModel.strDeep.getValue();
        String soilType = mViewModel.strSoilType.getValue();
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_modify, findViewById(R.id.dialog));
        final Dialog alertDialog = new AlertDialog.Builder(CrossTestActivity.this)
                .setView(view)
                .create();
        alertDialog.show();
        final EditText et_deep = view.findViewById(R.id.et_deep);
        et_deep.setText(deep);
        final Spinner sp_soil = view.findViewById(R.id.sp_soil);
        final String[] listSoilType = getResources().getStringArray(R.array.soil_type);
        for (int i = 0; i < listSoilType.length; i++) {
            if (listSoilType[i].equals(soilType)) {
                sp_soil.setSelection(i);
                break;
            }
        }

        Button ok = view.findViewById(R.id.ok);
        ok.setOnClickListener(view1 -> {
            String _deep = et_deep.getText().toString();
            String _soilType = listSoilType[sp_soil.getSelectedItemPosition()];
            if (!_deep.equals(deep) || !_soilType.equals(soilType)) {
                mViewModel.setModify(_deep, _soilType);
            }
            alertDialog.dismiss();
        });
        Button cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(view12 -> alertDialog.dismiss());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                mViewModel.linkDevice();
                return false;
            case R.id.save://保存数据到sd卡
                mViewModel.saveTestDataToSD();
                return false;
            case R.id.email://发送邮件到指定邮箱
                mViewModel.emailTestData();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public CrossTestViewModel createdViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(CrossTestViewModel.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_cross_test;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }

    @Override
    public void skipForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void skip(Intent intent) {

    }

    @Override
    public void sendToastMsg(String msg) {

    }
}

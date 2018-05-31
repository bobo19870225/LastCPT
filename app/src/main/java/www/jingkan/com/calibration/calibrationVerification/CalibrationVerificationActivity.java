/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.calibration.calibrationVerification;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.baseMVP.BaseMvpActivity;
import www.jingkan.com.base.baseMVP.BasePresenter;
import www.jingkan.com.framework.utils.AChartEngineUtils;

/**
 * Created by lushengbo on 2017/5/26.
 * 标定验证
 */

public class CalibrationVerificationActivity extends BaseMvpActivity<CalibrationVerificationPresenter>
        implements CalibrationVerificationContract.View {
    @BindView(id = R.id.chart1)
    private RelativeLayout mChart;
    @BindView(id = R.id.initial)
    private TextView initial;
    @BindView(id = R.id.initial_value, click = true)
    private Button initial_value;
    @BindView(id = R.id.bt_record, click = true)
    private Button bt_record;
    @BindView(id = R.id.valid)
    private TextView valid;
    @BindView(id = R.id.number)
    private TextView number;
    @BindView(id = R.id.differential)
    private TextView differential;
    @BindView(id = R.id.area)
    private TextView area;
    @BindView(id = R.id.sn)
    private TextView sn;
    @BindView(id = R.id.shock)
    private CheckBox shock;

    private boolean isFs;

    private AChartEngineUtils aChartEngineUtils;

    @Override
    protected void setView() {
        String[] strings = (String[]) mData;
        isFs = strings[2].contains("侧壁");
        boolean isAnalog = strings[2].contains("模拟");
        setToolBar(strings[2], R.menu.calibration_verification);
        aChartEngineUtils = new AChartEngineUtils(this, mChart);
        aChartEngineUtils.initChart(strings[2]);
        if (isAnalog) {
            if (isFs) {
                aChartEngineUtils.coordinateTransformation(720, 9000, 0);
            } else {
                aChartEngineUtils.coordinateTransformation(72, 9000, 0);
            }
        } else {
            if (isFs) {
                aChartEngineUtils.coordinateTransformation(480, 480, 0);
            }
        }
        shock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPresenter.enableShock(isChecked);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                String strNumber = String.valueOf(number.getText());
                if (isFs) {
                    mPresenter.saveData(strNumber + "侧壁标定.txt");
                } else {
                    mPresenter.saveData(strNumber + "锥头标定.txt");
                }
                return false;
            case R.id.link:
                toLink();
                return false;
            case R.id.help:

                return false;
            case R.id.about:

                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int initView() {
        return R.layout.activity_calibration_verification;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_record://记录
                mPresenter.doRecord();
                break;
            case R.id.initial_value:
                mPresenter.getInitialValue();
                break;
        }
    }

    @Override
    public BasePresenter createdPresenter() {
        return new CalibrationVerificationPresenter();
    }

    @Override
    public void showNotLinkError() {

    }

    @Override
    public void showInitialValue(String value) {
        initial.setText(value);
    }

    @Override
    public void showEffectiveValue(String value) {
        valid.setText(value);
    }


    @Override
    public void showProbeParameters(String strNumber, String strSn, String strDifferential, String workArea) {
        number.setText(strNumber);
        sn.setText(strSn);
        differential.setText(strDifferential);
        area.setText(workArea);

    }


    @Override
    public void showLoadingLine(int x, float loadingValue, String forceType) {
        aChartEngineUtils.addOneData(x, loadingValue, forceType);
    }

    @Override
    protected void toRefresh() {//每次进入时去连接蓝牙
        super.toRefresh();
        toLink();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            toLink();
        }
    }

    private void toLink() {
        String[] strings = (String[]) mData;
        mPresenter.linkDevice(strings[0]);
    }
}

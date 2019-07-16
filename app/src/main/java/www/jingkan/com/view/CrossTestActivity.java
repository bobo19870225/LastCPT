package www.jingkan.com.view;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.MenuItem;

import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityCrossTestBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.SystemConstant;
import www.jingkan.com.view.base.DialogMVVMDaggerActivity;
import www.jingkan.com.view_model.CrossTestViewModel;
import www.jingkan.com.view_model.ViewModelFactory;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class CrossTestActivity extends DialogMVVMDaggerActivity<CrossTestViewModel, ActivityCrossTestBinding> {
    @Inject
    ViewModelFactory viewModelFactory;
    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
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
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                mViewModel.linkDevice();
                return false;
            case R.id.save://保存数据到sd卡
//                mViewModel.saveTestDataToSD();
                return false;
            case R.id.email://发送邮件到指定邮箱
//                mViewModel.emailTestData();
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
}

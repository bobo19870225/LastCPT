package www.jingkan.com.view;

import android.view.MenuItem;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityTestingBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;
import www.jingkan.com.view.base.DialogMVVMDaggerActivity;
import www.jingkan.com.view_model.TestingViewModel;

/**
 * Created by Sampson on 2018/12/21.
 * CPTTest
 */
public class TestingActivity extends DialogMVVMDaggerActivity<TestingViewModel, ActivityTestingBinding> {
    @Inject
    BluetoothUtil bluetoothUtil;
    @Inject
    BluetoothCommService bluetoothCommService;


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, bluetoothUtil, bluetoothCommService};
    }

    @Override
    protected void setMVVMView() {
        setToolBar("探头检测", R.menu.link);
        mViewModel.singleLiveEvent.observe(this, aVoid -> showWaitDialog("正在连接蓝牙设备...", false, false));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                mViewModel.link();
                return false;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public TestingViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(TestingViewModel.class);
    }

    @Override
    public int initView() {
        return R.layout.activity_testing;
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

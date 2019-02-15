package www.jingkan.com.view;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityMyLinkerBinding;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.util.PreferencesUtil;
import com.jinkan.www.cpttest.util.StringUtil;
import com.jinkan.www.cpttest.view.base.BaseMVVMDaggerActivity;
import com.jinkan.www.cpttest.view_model.MyLinkerViewModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MyLinkerActivity extends BaseMVVMDaggerActivity<MyLinkerViewModel, ActivityMyLinkerBinding> {

    @Inject
    PreferencesUtil preferencesUtil;


    @Override
    protected void toRefresh() {
        Map<String, String> linkerPreferences = preferencesUtil.getLinkerPreferences();
        String add = linkerPreferences.get("add");
        if (StringUtil.isEmpty(add)) {
            mViewDataBinding.linker.setText("点击设置");
        } else {
            mViewDataBinding.linker.setText(add);
        }
        Map<String, String> analogLinkerPreferences = preferencesUtil.getAnalogLinkerPreferences();
        String add1 = analogLinkerPreferences.get("add");
        if (StringUtil.isEmpty(add1)) {
            mViewDataBinding.analogLinker.setText("点击设置");
        } else {
            mViewDataBinding.analogLinker.setText(add1);
        }
    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {
        setToolBar("我的连接器");

    }

    @Override
    public int initView() {
        return R.layout.activity_my_linker;
    }


    @Override
    public MyLinkerViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(MyLinkerViewModel.class);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {

        switch (callbackMessage.what) {
            case 0:
                HashMap<String, String> stringHashMap = new HashMap<>();
                stringHashMap.put("action", "选择数字连接器");
                goTo(LinkBluetoothActivity.class, stringHashMap);
                break;
            case 1:
                stringHashMap = new HashMap<>();
                stringHashMap.put("action", "选择模拟连接器");
                goTo(LinkBluetoothActivity.class, stringHashMap);
                break;
        }

    }
}

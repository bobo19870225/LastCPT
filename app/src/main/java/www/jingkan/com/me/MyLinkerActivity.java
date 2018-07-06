/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.me;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.linkBluetooth.LinkBluetoothActivity;

/**
 * Created by lushengbo on 2017/8/14.
 * 我的连接器页面
 */

public class MyLinkerActivity extends BaseActivity {
    @BindView(id = R.id.set_linker, click = true)
    private RelativeLayout set_linker;
    @BindView(id = R.id.set_analog_linker, click = true)
    private RelativeLayout set_analog_linker;
    @BindView(id = R.id.linker)
    private TextView linker;
    @BindView(id = R.id.analog_linker)
    private TextView analog_linker;
    private PreferencesUtils preferencesUtils;

    @Override
    protected void setView() {
        setToolBar("我的连接器");
        preferencesUtils = new PreferencesUtils(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_my_linker;
    }

    @Override
    protected void toRefresh() {
        Map<String, String> linkerPreferences = preferencesUtils.getLinkerPreferences();
        String add = linkerPreferences.get("add");
        if (StringUtils.isEmpty(add)) {
            linker.setText("点击设置");
        } else {
            linker.setText(add);
        }
        Map<String, String> analogLinkerPreferences = preferencesUtils.getAnalogLinkerPreferences();
        String add1 = analogLinkerPreferences.get("add");
        if (StringUtils.isEmpty(add1)) {
            analog_linker.setText("点击设置");
        } else {
            analog_linker.setText(add1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_linker:
                HashMap<String, String> stringHashMap = new HashMap<>();
                stringHashMap.put("action", "选择数字连接器");
                goTo(LinkBluetoothActivity.class, stringHashMap);
                break;
            case R.id.set_analog_linker:
                stringHashMap = new HashMap<>();
                stringHashMap.put("action", "选择模拟连接器");
                goTo(LinkBluetoothActivity.class, stringHashMap);
                break;
        }
    }
}

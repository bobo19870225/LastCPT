/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.me;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.framework.utils.PreferencesUtils;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.linkBluetooth.LinkBluetoothActivity;

import java.util.Map;

/**
 * Created by lushengbo on 2017/8/14.
 * 我的连接器页面
 */

public class MyLinkerActivity extends BaseActivity {
    @BindView(id = R.id.set_linker, click = true)
    private RelativeLayout set_linker;
    @BindView(id = R.id.linker)
    private TextView linker;
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_linker:
                goTo(LinkBluetoothActivity.class, "选择连接器");
                break;
        }
    }
}

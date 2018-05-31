/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.me.versionInfo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.external.tmselfupdatesdk.ITMSelfUpdateListener;
import com.tencent.external.tmselfupdatesdk.TMSelfUpdateConst;
import com.tencent.external.tmselfupdatesdk.TMSelfUpdateManager;
import com.tencent.external.tmselfupdatesdk.YYBDownloadListener;
import com.tencent.external.tmselfupdatesdk.model.TMSelfUpdateUpdateInfo;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.parameter.SystemConstant;

/**
 * Created by lushengbo on 2017/11/7.
 * 版本页面
 */

public class VersionInfoActivity extends BaseActivity {
    @BindView(id = R.id.check_version, click = true)
    private Button check_version;
    @BindView(id = R.id.version, click = true)
    private TextView version;

    private TMSelfUpdateManager tmSelfUpdateManager;

    @Override
    protected void setView() {
        setToolBar("版本信息");
        initUpdate();
        setVersion();
    }

    private void setVersion() {
        String verName;
        try {
            verName = getApplication().getPackageManager().
                    getPackageInfo(getApplication().getPackageName(), 0).versionName;
            version.setText("当前版本：" + verName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void initUpdate() {
        tmSelfUpdateManager = TMSelfUpdateManager.getInstance();
        try {
            ITMSelfUpdateListener itmSelfUpdateListener = new ITMSelfUpdateListener() {
                @Override
                public void onDownloadAppStateChanged(int i, int i1, String s) {
                    showToast("c");
                }

                @Override
                public void onDownloadAppProgressChanged(long l, long l1) {
                    showToast("b");
                }

                @Override
                public void onUpdateInfoReceived(TMSelfUpdateUpdateInfo tmSelfUpdateUpdateInfo) {
                    showToast("a");
                }
            };
            YYBDownloadListener yybDownloadListener = new YYBDownloadListener() {
                @Override
                public void onDownloadYYBStateChanged(String s, int i, int i1, String s1) {

                }

                @Override
                public void onDownloadYYBProgressChanged(String s, long l, long l1) {

                }

                @Override
                public void onCheckDownloadYYBState(String s, int i, long l, long l1) {

                }
            };
//            tmSelfUpdateManager.init(context, itmSelfUpdateListener, yybDownloadListener, null);
            Bundle bundle = new Bundle();
            bundle.putString(TMSelfUpdateConst.BUNDLE_KEY_SCENE, "FOO");
            tmSelfUpdateManager.init(getApplicationContext(), SystemConstant.TX_CHANNEL_NUMBER, itmSelfUpdateListener, yybDownloadListener, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int initView() {
        return R.layout.activity_version_info;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_version:
                tmSelfUpdateManager.startSelfUpdate(true);
                break;
        }
    }
}

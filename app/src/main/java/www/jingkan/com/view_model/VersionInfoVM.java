package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.tencent.external.tmselfupdatesdk.ITMSelfUpdateListener;
import com.tencent.external.tmselfupdatesdk.TMSelfUpdateConst;
import com.tencent.external.tmselfupdatesdk.TMSelfUpdateManager;
import com.tencent.external.tmselfupdatesdk.YYBDownloadListener;
import com.tencent.external.tmselfupdatesdk.model.TMSelfUpdateUpdateInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import www.jingkan.com.util.SystemConstant;
import www.jingkan.com.view_model.base.BaseViewModel;

import static www.jingkan.com.util.SystemConstant.TX_CHANNEL_NUMBER;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class VersionInfoVM extends BaseViewModel {
    public final MutableLiveData<String> ldVersion = new MutableLiveData<>();
    public final MutableLiveData<String> ldVersionDescription = new MutableLiveData<>();
    private TMSelfUpdateManager tmSelfUpdateManager;
    public VersionInfoVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        initUpdate();
        setVersion();
    }

    private void initUpdate() {
        tmSelfUpdateManager = TMSelfUpdateManager.getInstance();
        try {
            ITMSelfUpdateListener itmSelfUpdateListener = new ITMSelfUpdateListener() {
                @Override
                public void onDownloadAppStateChanged(int i, int i1, String s) {
                    toast("c");
                }

                @Override
                public void onDownloadAppProgressChanged(long l, long l1) {
                    toast("b");
                }

                @Override
                public void onUpdateInfoReceived(TMSelfUpdateUpdateInfo tmSelfUpdateUpdateInfo) {
                    toast("a");
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
            tmSelfUpdateManager.init(getApplication(), TX_CHANNEL_NUMBER, itmSelfUpdateListener, yybDownloadListener, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setVersion() {
        String verName;
        try {
            verName = getApplication().getPackageManager().
                    getPackageInfo(getApplication().getPackageName(), 0).versionName;
            ldVersion.setValue(verName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ldVersionDescription.setValue(SystemConstant.version_description);
    }

    public void CheckVersion() {
        tmSelfUpdateManager.startSelfUpdate(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

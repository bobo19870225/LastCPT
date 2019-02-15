/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jinkan.www.cpttest.R;
import com.jinkan.www.cpttest.databinding.ActivityDataSynBinding;
import com.jinkan.www.cpttest.db.dao.WirelessResultDataDao;
import com.jinkan.www.cpttest.db.dao.WirelessTestDao;
import com.jinkan.www.cpttest.util.CallbackMessage;
import com.jinkan.www.cpttest.util.DataUtil;
import com.jinkan.www.cpttest.util.acp.Acp;
import com.jinkan.www.cpttest.util.acp.AcpListener;
import com.jinkan.www.cpttest.util.acp.AcpOptions;
import com.jinkan.www.cpttest.view.adapter.OneTextListAdapter;
import com.jinkan.www.cpttest.view.base.DialogMVVMDaggerActivity;
import com.jinkan.www.cpttest.view.chart.DoubleBridgeMultifunctionStrategy;
import com.jinkan.www.cpttest.view.chart.DrawChartHelper;
import com.jinkan.www.cpttest.view_model.DataSyncViewModel;
import com.jinkan.www.cpttest.view_model.ISkip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

import static com.jinkan.www.cpttest.util.SystemConstant.EMAIL_TYPE_CORRECT_TXT;
import static com.jinkan.www.cpttest.util.SystemConstant.EMAIL_TYPE_HN_111;
import static com.jinkan.www.cpttest.util.SystemConstant.EMAIL_TYPE_LY_DAT;
import static com.jinkan.www.cpttest.util.SystemConstant.EMAIL_TYPE_LY_TXT;
import static com.jinkan.www.cpttest.util.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static com.jinkan.www.cpttest.util.SystemConstant.EMAIL_TYPE_ORIGINAL_TXT;
import static com.jinkan.www.cpttest.util.SystemConstant.SAVE_TYPE_CORRECT_TXT;
import static com.jinkan.www.cpttest.util.SystemConstant.SAVE_TYPE_HN_111;
import static com.jinkan.www.cpttest.util.SystemConstant.SAVE_TYPE_LY_DAT;
import static com.jinkan.www.cpttest.util.SystemConstant.SAVE_TYPE_LY_TXT;
import static com.jinkan.www.cpttest.util.SystemConstant.SAVE_TYPE_LZ_TXT;
import static com.jinkan.www.cpttest.util.SystemConstant.SAVE_TYPE_ORIGINAL_TXT;

/**
 * Created by lushengbo on 2018/1/17.
 * 数据同步页面
 */

public class DataSyncActivity extends DialogMVVMDaggerActivity<DataSyncViewModel, ActivityDataSynBinding>
        implements ISkip {

    @Inject
    DrawChartHelper drawChartHelper;
    @Inject
    WirelessTestDao wirelessTestDao;
    @Inject
    WirelessResultDataDao wirelessResultDataDao;
    @Inject
    DataUtil dataUtil;


    public static final int REQUEST_OPEN_MARK_FILE = 2;

    @Override
    public DataSyncViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(DataSyncViewModel.class);
    }


    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{
                mData,
                wirelessTestDao,
                wirelessResultDataDao,
                dataUtil,
                this
        };
    }

    @Override
    protected void setMVVMView() {
        drawChartHelper.setStrategy(new DoubleBridgeMultifunctionStrategy(this, mViewDataBinding.lineChart));
        setToolBar("数据同步", R.menu.data_syn);
    }


    private String[] saveTypes = {SAVE_TYPE_LY_TXT, SAVE_TYPE_LY_DAT, SAVE_TYPE_HN_111, SAVE_TYPE_LZ_TXT, SAVE_TYPE_CORRECT_TXT, SAVE_TYPE_ORIGINAL_TXT};
    private String[] emailTypes = {EMAIL_TYPE_LY_TXT, EMAIL_TYPE_LY_DAT, EMAIL_TYPE_HN_111, EMAIL_TYPE_LZ_TXT, EMAIL_TYPE_CORRECT_TXT, EMAIL_TYPE_ORIGINAL_TXT};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open://打开标记文件
                Acp.getInstance(getApplicationContext()).request(new AcpOptions.Builder().
                                setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE).build(),
                        new AcpListener() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(DataSyncActivity.this, OpenFileActivity.class);
                                startActivityForResult(intent, REQUEST_OPEN_MARK_FILE);
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                showToast(permissions.toString() + "权限拒绝");
                            }
                        });

                return false;
            case R.id.data_syn://数据同步
                mViewModel.doDataSync();
                return false;
            case R.id.save://保存数据
                showSaveOrEmailWindow(saveTypes, true);
                return false;
            case R.id.email:
                showSaveOrEmailWindow(emailTypes, false);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSaveOrEmailWindow(final String[] items, final boolean isSave) {
        View contentView = getLayoutInflater().inflate(R.layout.theo, null);
        final PopupWindow popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        // 点击消失属性
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        List<String> ls = new ArrayList<>(Arrays.asList(items));
        OneTextListAdapter adapter = new OneTextListAdapter(DataSyncActivity.this, R.layout.listitem, ls);
        ListView listView = contentView.findViewById(R.id.lv_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            mViewModel.saveDataAndSendEmail(emailTypes[position], isSave);
            popupWindow.dismiss();
        });
        contentView.findViewById(R.id.cancel).setOnClickListener(view -> popupWindow.dismiss());
        // 显示在屏幕上
        popupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);

    }


    @Override
    public int initView() {

        return R.layout.activity_data_syn;
    }

    private void drawChar(float[][] cpt) {
        List<float[]> dataList = new ArrayList<>();
        for (float[] aCpt : cpt) {
            dataList.add(new float[]{aCpt[1], aCpt[2], aCpt[3], aCpt[0]});
        }
        drawChartHelper.addPointsToChart(dataList);
    }

    @Override
    public void callback(CallbackMessage callbackMessage) {
        switch (callbackMessage.what) {
            case 0:
                drawChar((float[][]) callbackMessage.obj);
                break;
        }
    }

    /**
     * ISkip 接口
     *
     * @param intent      略
     * @param requestCode 略
     */
    @Override
    public void skipForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void skip(Intent intent) {

    }

    @Override
    public void sendToastMsg(String msg) {
        showToast(msg);
    }
}

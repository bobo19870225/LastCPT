/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.dataSynchronization;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.OneTextListAdapter;
import www.jingkan.com.base.baseMVVM.MVVMDialogActivity;
import www.jingkan.com.chart.DoubleBridgeMultifunctionStrategy;
import www.jingkan.com.chart.DrawChartHelper;
import www.jingkan.com.databinding.ActivityDataSynBinding;
import www.jingkan.com.wireless.openFile.OpenWFileActivity;

import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_CORRECT_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.EMAIL_TYPE_ORIGINAL_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_CORRECT_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_HN_111;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_DAT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LY_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_LZ_TXT;
import static www.jingkan.com.parameter.SystemConstant.SAVE_TYPE_ORIGINAL_TXT;

/**
 * Created by lushengbo on 2018/1/17.
 * 数据同步页面
 */

public class DataSyncActivity extends MVVMDialogActivity<DataSynViewModel, ActivityDataSynBinding> {
    public static final int REQUEST_OPEN_MARK_FILE = 2;

    @Override
    protected DataSynViewModel createdViewModel() {
        return new DataSynViewModel();
    }


    @Override
    protected void setView() {
        drawChartHelper.setStrategy(new DoubleBridgeMultifunctionStrategy(this, mViewDataBinding.lineChart));
        setToolBar("数据同步", R.menu.data_syn);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open://打开标记文件
                Intent intent = new Intent(this, OpenWFileActivity.class);
                startActivityForResult(intent, REQUEST_OPEN_MARK_FILE);
                return false;
            case R.id.data_syn://数据同步
                mViewModel.doDataSync();
                return false;
            case R.id.save://保存数据
                showSaveOrEmailWindow(new String[]{
                        SAVE_TYPE_LY_TXT,
                        SAVE_TYPE_LY_DAT,
                        SAVE_TYPE_HN_111,
                        SAVE_TYPE_LZ_TXT,
                        SAVE_TYPE_CORRECT_TXT,
                        SAVE_TYPE_ORIGINAL_TXT
                }, true);
                return false;
            case R.id.email:
                showSaveOrEmailWindow(new String[]{
                        EMAIL_TYPE_LY_TXT,
                        EMAIL_TYPE_LY_DAT,
                        EMAIL_TYPE_HN_111,
                        EMAIL_TYPE_LZ_TXT,
                        EMAIL_TYPE_CORRECT_TXT,
                        EMAIL_TYPE_ORIGINAL_TXT
                }, false);
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
        List<String> ls = new ArrayList<>();
        ls.addAll(Arrays.asList(items));
        OneTextListAdapter adapter = new OneTextListAdapter(DataSyncActivity.this, R.layout.listitem, ls);
        ListView listView = contentView.findViewById(R.id.lv_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.saveDataAndSendEmail(position, isSave);
                popupWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        // 显示在屏幕上
        popupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);

    }

    private DrawChartHelper drawChartHelper;

    @Override
    public int initView() {
        drawChartHelper = new DrawChartHelper();
        return R.layout.activity_data_syn;
    }

    public void drawChar(float[][] cpt) {
        List<float[]> dataList = new ArrayList<>();
        for (float[] aCpt : cpt) {
            dataList.add(new float[]{aCpt[1], aCpt[2], aCpt[3], aCpt[0]});
        }
        drawChartHelper.addPointsToChart(dataList);
    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.wirelessProbe;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.OneTextListAdapter;
import www.jingkan.com.base.baseMVVM.BaseMVVMActivity;
import www.jingkan.com.databinding.ActivityAddWirelessProbeInfoBinding;

/**
 * Created by lushengbo on 2018/1/24.
 * 手动添加无缆探头
 */

public class AddWirelessProbeInfoActivity extends BaseMVVMActivity<AddWirelessProbeInfoVM, ActivityAddWirelessProbeInfoBinding> {
    @Override
    protected AddWirelessProbeInfoVM createdViewModel() {
        return new AddWirelessProbeInfoVM();
    }

    @Override
    protected void setView() {
        if (mData != null) {
            setToolBar("修改探头信息");
        } else {
            setToolBar("输入探头信息");
        }

    }

    @Override
    public int initView() {
        return R.layout.activity_add_wireless_probe_info;
    }

    public void showChoseProbeTypePPW() {
        View v = getLayoutInflater().inflate(R.layout.theo, null);
        final PopupWindow popupWindow = new PopupWindow(v);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        ListView lv_list = v.findViewById(R.id.lv_item);
        List<String> list = new ArrayList<>();
        list.add("单桥无缆测斜");
        list.add("双桥无缆测斜");
        OneTextListAdapter adapter = new OneTextListAdapter(this, R.layout.listitem, list);
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener((parent, view, position, id) -> {
            TextView textView = view.findViewById(R.id.TextView);
            mViewModel.strProbeType.set(textView.getText().toString());
            mViewModel.strChoseProbeType.set("探头类型：");
            if (position == 0) {
                mViewModel.doubleBridge.set(false);
            } else if (position == 1) {
                mViewModel.doubleBridge.set(true);
            }
            popupWindow.dismiss();
        });
        popupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);

    }
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.base;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.jingkan.com.R;
import www.jingkan.com.view_model.IShowDialog;
import www.jingkan.com.view_model.base.BaseViewModel;

import androidx.databinding.ViewDataBinding;


/**
 * Created by lushengbo on 2018/1/19.
 * 显示各种对话框
 */

public abstract class DialogMVVMDaggerActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseMVVMDaggerActivity<VM, VDB> implements IShowDialog {
    private Dialog loadingDialog;

    @Override
    public void showWaitDialog(String msg, boolean isTransBg, boolean isCancelable) {
        LayoutInflater inflater = LayoutInflater.from(DialogMVVMDaggerActivity.this);
        View v = inflater.inflate(R.layout.dialog_loading, null);             // 得到加载view
        RelativeLayout layout = v.findViewById(R.id.dialog_view);// 加载布局

        // main.xml中的ImageView
        ImageView spaceshipImage = v.findViewById(R.id.img);
        TextView tipTextView = v.findViewById(R.id.tipTextView);   // 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(DialogMVVMDaggerActivity.this, R.anim.rotate_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        loadingDialog = new Dialog(DialogMVVMDaggerActivity.this, isTransBg ? R.style.TransDialogStyle : R.style.WhiteDialogStyle);    // 创建自定义样式dialog
        loadingDialog.setContentView(layout);
        loadingDialog.setCancelable(isCancelable);
        loadingDialog.setCanceledOnTouchOutside(false);

        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp;
        if (window != null) {
            lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setAttributes(lp);
            window.setWindowAnimations(R.style.PopWindowAnimStyle);
            window.setBackgroundDrawableResource(R.drawable.bg_dialog);
            loadingDialog.show();
        }

    }

    @Override
    public void closeWaitDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}

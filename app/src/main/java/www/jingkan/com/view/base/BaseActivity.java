package www.jingkan.com.view.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import www.jingkan.com.R;

import java.io.Serializable;
import java.util.Map;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Sampson on 2018/12/16.
 * CPTTest
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Object mData;
    protected FragmentManager mFragmentManager;
    protected View mRootView;
    protected Toolbar toolbar;
    protected
    @MenuRes
    int menuRes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int viewId = initView();
        init(viewId);
        toolbar = findViewById(R.id.tool_bar);
        initData();
        setView();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuRes != 0) {
            getMenuInflater().inflate(menuRes, menu);//加载menu文件到布局
        }
        return true;
    }

    public void showToast(String msg) {
        Snackbar.make(mRootView, msg, Snackbar.LENGTH_LONG).show();
    }

    protected abstract void setView();

    protected void setToolBar(String title) {
        if (null != toolbar) {
            toolbar.setTitle(title);
        }
    }

    protected void setToolBar(String title, @MenuRes int menuRes) {
        if (null != toolbar) {
            toolbar.setTitle(title);
            this.menuRes = menuRes;
        }
    }


    protected void init(int viewId) {
        mRootView = getLayoutInflater().inflate(viewId, null, false);
        setContentView(mRootView);

        mFragmentManager = getSupportFragmentManager();
    }


    public void setFragment(@IdRes int containerViewId, Fragment fragment) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(containerViewId, fragment);
        mFragmentTransaction.commit();
    }

    public void setFragment(@IdRes int containerViewId, Fragment fragment, @Nullable Object data) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        if (data instanceof String) {
            bundle.putString("DATA", String.valueOf(data));
        } else if (data instanceof Integer) {
            bundle.putInt("DATA", (Integer) data);
        } else if (data instanceof String[]) {
            bundle.putStringArray("DATA", (String[]) data);
        } else if (data instanceof Parcelable) {
            bundle.putParcelable("DATA", (Parcelable) data);
        }
        fragment.setArguments(bundle);
        mFragmentTransaction.replace(containerViewId, fragment);
        mFragmentTransaction.commit();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        if (bundle != null) {
            mData = bundle.get("DATA");
        } else {
            mData = null;
        }

    }


    /**
     * 跳转
     *
     * @param data   参数
     * @param mClass 类名
     */
    public void goTo(Class mClass, Object data) {
        Intent intent = new Intent();
        intent.setClass(this, mClass);
        if (data != null) {
            Bundle bundle = new Bundle();
            if (data instanceof String) {
                bundle.putString("DATA", String.valueOf(data));
            } else if (data instanceof Integer) {
                bundle.putInt("DATA", (Integer) data);
            } else if (data instanceof String[]) {
                bundle.putStringArray("DATA", (String[]) data);
            } else if (data instanceof Parcelable) {
                bundle.putParcelable("DATA", (Parcelable) data);
            } else if (data instanceof Map) {
                bundle.putSerializable("DATA", (Serializable) data);
            }
            intent.putExtra("BUNDLE", bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转
     *
     * @param data   参数
     * @param mClass 类名
     * @param isTop  是否关闭其它页面
     */
    public void goTo(Class mClass, Object data, boolean isTop) {
        Intent intent = new Intent();
        intent.setClass(this, mClass);
        if (isTop) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        if (data != null) {
            Bundle bundle = new Bundle();
            if (data instanceof String) {
                bundle.putString("DATA", String.valueOf(data));
            } else if (data instanceof Integer) {
                bundle.putInt("DATA", (Integer) data);
            } else if (data instanceof String[]) {
                bundle.putStringArray("DATA", (String[]) data);
            } else if (data instanceof Parcelable) {
                bundle.putParcelable("DATA", (Parcelable) data);
            } else if (data instanceof Map) {
                bundle.putSerializable("DATA", (Serializable) data);
            }
            intent.putExtra("BUNDLE", bundle);
        }
        startActivity(intent);
    }

    public abstract
    @LayoutRes
    int initView();

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        toRefresh();
    }

    /**
     * 需要每次进入时刷新的覆盖此方法
     */
    protected void toRefresh() {

    }

    public void showMyDialog(String title, String message, boolean isNegativeButton, DialogInterface.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (isNegativeButton) {
            builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        }
        Dialog alertDialog = builder
                .setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("确定", sureListener)
                .create();
        alertDialog.show();

    }
}

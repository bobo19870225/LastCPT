/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.activity;

import android.app.AlertDialog;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import www.jingkan.com.R;
import www.jingkan.com.adapter.MyStartAdapter;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.calibration.InstrumentCalibrationFragment;
import www.jingkan.com.common.OrdinaryTestFragment;
import www.jingkan.com.me.MeFragment;
import www.jingkan.com.wireless.WirelessTestFragment;

public class MainActivity extends BaseActivity {
    @BindView(id = www.jingkan.com.R.id.view_page)
    private ViewPager view_page;
    @BindView(id = www.jingkan.com.R.id.navigation)
    private BottomNavigationView navigation;
    private int selectedIndex = 0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case www.jingkan.com.R.id.navigation_home:
                    view_page.setCurrentItem(0);
                    selectedIndex = 0;
                    return true;
                case www.jingkan.com.R.id.navigation_dashboard:
                    view_page.setCurrentItem(1);
                    selectedIndex = 1;
                    return true;
                case www.jingkan.com.R.id.navigation_notifications:
                    view_page.setCurrentItem(2);
                    selectedIndex = 2;
                    return true;
                case www.jingkan.com.R.id.navigation_me:
                    view_page.setCurrentItem(3);
                    selectedIndex = 3;
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void setView() {
        JPushInterface.init(getApplicationContext());//初始化JPush
        initViewPage();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initViewPage() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new OrdinaryTestFragment());
        fragmentList.add(new WirelessTestFragment());
        fragmentList.add(new InstrumentCalibrationFragment());
        fragmentList.add(new MeFragment());
        MyStartAdapter myStartAdapter = new MyStartAdapter(mFragmentManager, fragmentList);
        view_page.setAdapter(myStartAdapter);
        view_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Menu menu = navigation.getMenu();
                menu.getItem(position).setChecked(true);
                selectedIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void toRefresh() {
        view_page.setCurrentItem(selectedIndex);
    }

    @Override
    public int initView() {
        return www.jingkan.com.R.layout.activity_main;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .create();
            alertDialog.show();
            Window window = alertDialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
                window.setWindowAnimations(R.style.exitStyle);
                window.setContentView(R.layout.dialog_exit);
                Button ok = window.findViewById(R.id.ok);
                View cancel = window.findViewById(R.id.cancel);
                ok.setOnClickListener(view -> {
                    alertDialog.dismiss();
                    new Handler().postDelayed(this::finish, 500);

                });
                cancel.setOnClickListener(view -> alertDialog.dismiss());
            }


        }
        return super.onKeyDown(keyCode, event);
    }
}

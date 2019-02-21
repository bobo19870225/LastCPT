/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.main;

import android.app.AlertDialog;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import cn.jpush.android.api.JPushInterface;
import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityMainBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.InstrumentCalibrationFragment;
import www.jingkan.com.view.MeFragment;
import www.jingkan.com.view.OrdinaryTestFragment;
import www.jingkan.com.view.WirelessTestFragment;
import www.jingkan.com.view.adapter.MyPagerAdapter;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.main.MainViewModel;

public class MainActivity extends BaseMVVMDaggerActivity<MainViewModel, ActivityMainBinding> {

    @Inject
    OrdinaryTestFragment ordinaryTestFragment;
    @Inject
    MeFragment meFragment;
    @Inject
    WirelessTestFragment wirelessTestFragment;
    @Inject
    InstrumentCalibrationFragment instrumentCalibrationFragment;
    private ViewPager view_page;
    private BottomNavigationView navigation;
    private int selectedIndex = 0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    view_page.setCurrentItem(0);
                    selectedIndex = 0;
                    return true;
                case R.id.navigation_dashboard:
                    view_page.setCurrentItem(1);
                    selectedIndex = 1;
                    return true;
                case R.id.navigation_notifications:
                    view_page.setCurrentItem(2);
                    selectedIndex = 2;
                    return true;
                case R.id.navigation_me:
                    view_page.setCurrentItem(3);
                    selectedIndex = 3;
                    return true;
            }
            return false;
        }

    };


    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {
        view_page = mViewDataBinding.viewPage;
        navigation = mViewDataBinding.navigation;
        JPushInterface.init(getApplicationContext());//初始化JPush
        initViewPage();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initViewPage() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ordinaryTestFragment);
        fragmentList.add(wirelessTestFragment);
        fragmentList.add(instrumentCalibrationFragment);
        fragmentList.add(meFragment);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(mFragmentManager, fragmentList);
        view_page.setAdapter(myPagerAdapter);
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
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
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

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

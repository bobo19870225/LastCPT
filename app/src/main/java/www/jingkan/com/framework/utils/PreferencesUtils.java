/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lushengbo on 2017/8/10.
 * 偏好设置
 */

public class PreferencesUtils {
    private Context context;

    public PreferencesUtils(Context context) {
        this.context = context;
    }

    /**
     * 保存参数
     *
     * @param sEmail         发送的邮箱
     * @param sEmailPassword 发送邮箱密码
     * @param rEmail         接收的邮箱
     */
    public void saveEmail(String sEmail, String sEmailPassword, String rEmail) {
        //获得SharedPreferences对象  
        SharedPreferences preferences = context.getSharedPreferences("email", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sEmail", sEmail);
        editor.putString("sEmailPassword", sEmailPassword);
        editor.putString("rEmail", rEmail);
        editor.apply();
    }

    /**
     * 保存参数
     *
     * @param add 蓝牙设备的地址
     */
    public void saveLinker(String add) {
        //获得SharedPreferences对象
        SharedPreferences preferences = context.getSharedPreferences("linker", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("add", add);
        editor.apply();
    }

    /**
     * 获取蓝牙连接器各项参数
     *
     * @return Linker参数
     */
    public Map<String, String> getLinkerPreferences() {
        Map<String, String> params = new HashMap<>();
        SharedPreferences preferences = context.getSharedPreferences("linker", Context.MODE_PRIVATE);
        params.put("add", preferences.getString("add", ""));
        return params;
    }

    /**
     * 保存参数
     *
     * @param add 蓝牙设备的地址
     */
    public void saveAnalogLinker(String add) {
        //获得SharedPreferences对象
        SharedPreferences preferences = context.getSharedPreferences("analogLinker", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("add", add);
        editor.apply();
    }

    /**
     * 获取蓝牙连接器各项参数
     *
     * @return Linker参数
     */
    public Map<String, String> getAnalogLinkerPreferences() {
        Map<String, String> params = new HashMap<>();
        SharedPreferences preferences = context.getSharedPreferences("analogLinker", Context.MODE_PRIVATE);
        params.put("add", preferences.getString("add", ""));
        return params;
    }

    /**
     * 获取各项参数
     *
     * @return email参数
     */
    public Map<String, String> getEmailPreferences() {
        Map<String, String> params = new HashMap<>();
        SharedPreferences preferences = context.getSharedPreferences("email", Context.MODE_PRIVATE);
        params.put("sEmail", preferences.getString("sEmail", ""));
        params.put("sEmailPassword", preferences.getString("sEmailPassword", ""));
        params.put("rEmail", preferences.getString("rEmail", ""));
        return params;
    }


}  

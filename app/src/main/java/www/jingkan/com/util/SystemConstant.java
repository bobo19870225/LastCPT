/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.util;

import java.util.UUID;

/**
 * Created by bobo on 2017/3/5.
 * 系统常量
 */

public class SystemConstant {
    public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//蓝牙串口设备
    public static final int GET_LOCATION = 110;//定位
    public static final String LOCATION = "location";


    public static final String SINGLE_BRIDGE_TEST = "单桥试验";
    public static final String SINGLE_BRIDGE_MULTI_TEST = "单桥测斜试验";
    public static final String DOUBLE_BRIDGE_TEST = "双桥试验";
    public static final String DOUBLE_BRIDGE_MULTI_TEST = "双桥测斜试验";
    public static final String VANE_TEST = "十字板剪切试验";
    public static final String SINGLE_BRIDGE_MULTI_WIRELESS_TEST = "无缆单桥测斜试验";
    public static final String DOUBLE_BRIDGE_MULTI_WIRELESS_TEST = "无缆双桥测斜试验";

    public static final String SINGLE_BRIDGE_3 = "单桥3型";
    public static final String SINGLE_BRIDGE_4 = "单桥4型";
    public static final String SINGLE_BRIDGE_6 = "单桥6型";
    public static final String DOUBLE_BRIDGE_3 = "双桥3型";
    public static final String DOUBLE_BRIDGE_4 = "双桥4型";
    public static final String DOUBLE_BRIDGE_6 = "双桥6型";
    public static final String VANE = "十字板";
    //    public static final String APP_ID = "1106510070";
//    public static final String APP_KEY = "mrCPpB1zl3qyhmWi";
//    public static final String JPUSH_APP_KEY = "e87122fdb968a4fd6512bc2d";
    public static final String TX_CHANNEL_NUMBER = "1003143";
    public static final String version_description = "十字板剪切试验数据输出增加角度列";
    public static final String SAVE_TYPE_ZHD_TXT = "浙海大(.txt)";
    public static final String SAVE_TYPE_LY_TXT = "溧阳科尔(.txt)";
    public static final String SAVE_TYPE_LY_DAT = "溧阳科尔(.DAT)";
    public static final String SAVE_TYPE_HN_111 = "南京华宁(.111)";
    public static final String SAVE_TYPE_LZ_TXT = "北京理正(.txt)";
    public static final String SAVE_TYPE_ORIGINAL_TXT = "原始数据*ORI.txt";
    public static final String SAVE_TYPE_CORRECT_TXT = "测斜数据*X.txt";

    public static final String EMAIL_TYPE_ZHD_TXT = "发送浙海大(.txt)";
    public static final String EMAIL_TYPE_LY_TXT = "发送溧阳科尔(.txt)";
    public static final String EMAIL_TYPE_LY_DAT = "发送溧阳科尔(.DAT)";
    public static final String EMAIL_TYPE_HN_111 = "发送南京华宁(.111)";
    public static final String EMAIL_TYPE_LZ_TXT = "发送北京理正(.txt)";
    public static final String EMAIL_TYPE_ORIGINAL_TXT = "发送原始数据*ORI.txt";
    public static final String EMAIL_TYPE_CORRECT_TXT = "发送测斜数据*X.txt";

}

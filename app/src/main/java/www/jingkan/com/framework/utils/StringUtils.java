/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by laidayuan on 16/1/12.
 * bobo
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static Pattern phone = Pattern
            .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * @param data  要格式化的数据
     * @param digit 小数位
     * @return 格式化后的字符串
     */
    public static String format(Object data, int digit) {
        switch (digit) {
            case 0:
                DecimalFormat df = new DecimalFormat("#");
                return String.valueOf(df.format(data));
            case 1:
                df = new DecimalFormat("#0.#");
                return String.valueOf(df.format(data));
            case 2:
                df = new DecimalFormat("#0.##");
                return String.valueOf(df.format(data));
            case 3:
                df = new DecimalFormat("#0.###");
                df.setRoundingMode(RoundingMode.FLOOR);
                return String.valueOf(df.format(data));
        }
        return null;
    }


    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence... strs) {
        for (CharSequence str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(String target) {
        return target != null && target.length() > 0 && !target.equalsIgnoreCase("null");

    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(CharSequence email) {
        return !isEmpty(email) && emailer.matcher(email).matches();
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    public static boolean isPhone(CharSequence phoneNum) {
        return !isEmpty(phoneNum) && phone.matcher(phoneNum).matches();
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        if (isEmpty(str) || str.length() != 11)
            return false;

        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * String转long
     *
     * @param str 要转换的字符串
     * @return 转换异常返回 0
     */
    public static long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * String转double
     *
     * @param str 字符串
     * @return 转换异常返回 0
     */
    public static double toDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0D;
        }

    }

    /**
     * 字符串转布尔
     *
     * @param b 字符串
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 判断一个字符串是不是整数
     */
    public static boolean isInteger(CharSequence str) {
        try {
            Integer.parseInt(str.toString());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 判断一个字符串是不是浮点数
     */
    public static boolean isFloat(CharSequence str) {
        try {
            Float.parseFloat(str.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断一个字符串是不是浮点数
     */
    public static boolean isDouble(CharSequence str) {
        try {
            Double.parseDouble(str.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 将 string 类型转化为 Long 类型。
     *
     * @param src 被转化的string。
     * @return Long 转化结果。
     */
    public static Character str2Character(String src) {
        int startPosition = 0;
        if (src == null) {
            return null;
        }
        try {
            return Character.valueOf(src.charAt(startPosition));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 将 string 类型转化为 Long 类型。
     *
     * @param src 被转化的string。
     * @return Long 转化结果。
     */
    public static Byte str2Byte(String src) {
        if (src == null) {
            return null;
        }
        try {
            return Byte.valueOf(src);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 将 string 类型转化为 Long 类型。
     *
     * @param src 被转化的string。
     * @param def 默认值String。
     * @return Long 转化结果。
     */
    public static Byte str2Byte(String src, Byte def) {
        if (src == null) {
            return def;
        }
        try {
            return Byte.valueOf(src);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * 将 string 类型转化为 Long 类型。
     *
     * @param src 被转化的string。
     * @return Long 转化结果。
     */
    public static Short str2Short(String src) {
        if (src == null) {
            return 0;
        }
        try {
            return Short.valueOf(src);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 将 string 类型转化为 Long 类型。
     *
     * @param src 被转化的string。
     * @param def 默认值String。
     * @return Long 转化结果。
     */
    public static Short str2Short(String src, Short def) {
        if (src == null) {
            return def;
        }
        try {
            return Short.valueOf(src);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * byte[]数组转换为16进制的字符串。
     *
     * @param data 要转换的字节数组。
     * @return 转换后的结果。
     */
    public static final String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 将 string 类型转化为 Float 类型。
     *
     * @param src 被转化的string。
     * @return float 转化结果。
     */
    public static Float str2Float(String src) {
        if (src == null) {
            return 0f;
        }

        try {
            return Float.valueOf(src);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    /**
     * 将 string 类型转化为 float 类型。
     *
     * @param src 被转化的string。
     * @param def 默认值。
     * @return float 转化结果。
     */
    public static Float str2Float(String src, float def) {
        if (src == null) {
            return def;
        }

        try {
            return Float.valueOf(src);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * 16进制表示的字符串转换为字节数组。
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] d = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return d;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate) {
        Date time = null;

        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate), TimeZone.getTimeZone("GMT+08"),
                    TimeZone.getDefault());
        }

        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天 ";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 判断用户的设备时区是否为东八区（中国） 2014年7月31日
     *
     * @return
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        defaultVaule = TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08");
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换时间 2014年7月31日
     */
    public static Date transformTime(Date date, TimeZone oldZone,
                                     TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }

    /**
     * 将毫秒转换成定样格式的日期 "yyyy.MM.dd hh:mm"
     */
    public static String milToSampleTime(Long milTime, String sample) {
        SimpleDateFormat sdf = new SimpleDateFormat(sample);
        return sdf.format(new Date(milTime));
    }

    /**
     * 格式化金额
     *
     * @param s   数字字符串
     * @param len 分割长度
     * @return
     */
    public static String formatMoney(String s, int len) {
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,###");

        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            formater = new DecimalFormat(buff.toString());
        }
        String result = formater.format(num);
        /*
         * if (result.indexOf(".") == -1) { result = result + ".00"; } else {
		 * result = result; }
		 */
        return result;
    }


    /**
     * 格式化金额,末尾不加“00”
     *
     * @param s 数字字符串
     * @return
     */
    public static String formatMoney(String s) {

        return formatMoney(s, 3, false);
    }

    /**
     * 直接格式化为金额，3位一断
     *
     * @param s
     * @param addZero
     * @return
     */
    public static String formatMoney(String s, boolean addZero) {

        return formatMoney(s, 3, addZero);
    }

    /**
     * 格式化金额
     *
     * @param s       数字字符串
     * @param len     分割长度
     * @param addZero 末位是否添加00
     * @return
     */
    public static String formatMoney(String s, int len, boolean addZero) {
        if (s == null || s.length() < 1) {
            return "";
        }

        if (s.contains(",")) {
            s = s.replaceAll(",", "").trim();
        }

        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,###");

        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            formater = new DecimalFormat(buff.toString());
        }
        String result = formater.format(num);
        if (addZero) {
            // 是否末位添加00
            if (result.indexOf(".") == -1) {
                result = result + ".00";
            } else if (result.length() > 2
                    && ".".equals(result.substring(result.length() - 2,
                    result.length() - 1))) {
                result = result + "0";
            }
        }
        return result;
    }

    /**
     * 将“,”分割的金额数字转为double
     *
     * @param formalMoney
     * @return
     */
    public static Double formalMoneyToNum(String formalMoney) {
        double money = 0;
        if (formalMoney == null || formalMoney.length() < 1) {
            return money;
        }
        if (formalMoney.startsWith(".")) {
            formalMoney = "0" + formalMoney;
        }
        String str = formalMoney.replaceAll(",", "").trim();
        str = str.replaceAll("，", "").trim();
        if (RegexMoney.regexMoney(str)) {
            money = Double.valueOf(str);
        }
        return money;
    }

    /**
     * 如果金额超过一万，则转化单位为万元 只有超过一万并且金额为一万的整数倍显示单位为万
     *
     * @param formalMoney
     * @return
     */
    public static String formatMoneyToWan(String formalMoney) {
        double money = formalMoneyToNum(formalMoney);
        int numMon = 0;
        String strMon = "";
        if (money >= 10000 && money % 10000 == 0) {
            numMon = (int) (money / 10000);
            strMon = formatMoney("" + numMon) + "万";
        } else {
            strMon = formatMoney(formalMoney, true);
        }
        return strMon;
    }

    /**
     * 限制输入框小数点后的位数
     */

    public static void limit(final EditText edit) {
        edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                String str = s.toString();
                if (str.contains(".")) {
                    int index = str.indexOf(".");
                    if (index + 3 < str.length()) {
                        str = str.substring(0, index + 3);
                        edit.setText(str);
                        edit.setSelection(str.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID() {
        return "" + UUID.randomUUID();
    }

    public static class RegexMoney {

        /**
         * @param money
         * @return
         */
        public static boolean regexMoney(String money) {
            Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)*$");
            Matcher matcher = pattern.matcher(money);
            boolean isTrue = matcher.matches();
            return isTrue;
        }

        public static boolean regexPhone(String phone) {
            Pattern pattern = Pattern
                    .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher matcher = pattern.matcher(phone);
            boolean isTrue = matcher.matches();
            return isTrue;
        }
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
}

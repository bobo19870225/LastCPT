/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.util;

import android.Manifest;
import android.content.Context;
import android.os.Environment;

import www.jingkan.com.util.acp.Acp;
import www.jingkan.com.util.acp.AcpListener;
import www.jingkan.com.util.acp.AcpOptions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class MyFileUtil {

    private static volatile MyFileUtil INSTANCE;

    public static MyFileUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (MyFileUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyFileUtil();
                }
            }
        }
        return INSTANCE;
    }

    private MyFileUtil() {
        //super();
    }

    public void saveToSD(Context context, final String fileNameAndFileType, final String content, final SaveFileCallBack saveFileCallBack) {
        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        BufferedWriter bufferedWriter = null;
                        String strPath = getSDPath();
                        try {//不存在就创建，存在就覆盖
                            FileOutputStream fileOutputStream = new FileOutputStream(strPath + "/" + fileNameAndFileType, false);
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");//要与编辑器的编码一致
                            bufferedWriter = new BufferedWriter(outputStreamWriter);
                            bufferedWriter.write(content);
                            saveFileCallBack.onSuccess();
                        } catch (IOException e) {
                            saveFileCallBack.onFail(e.toString());
                        } finally {
                            try {
                                if (bufferedWriter != null) {
                                    bufferedWriter.close();
                                }
                            } catch (IOException e) {
                                saveFileCallBack.onFail(e.toString());
                            }

                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });

    }

    public void saveToSD(Context context, final String fileName, final String content, final String fileType, final SaveFileCallBack saveFileCallBack) {
        String fileNameAndFileType = fileName + "." + fileType;
        saveToSD(context, fileNameAndFileType, content, saveFileCallBack);
    }

    /**
     * @param fileName 文件名
     * @return 是否存在
     */
    public static boolean fileIsExists(String fileName) {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 获取SD卡的路径
     *
     * @return SD卡的路径
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {

            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir != null ? sdDir.toString() : null;
    }

    /**
     * 保存结果回调接口
     */
    public interface SaveFileCallBack {
        void onSuccess();

        void onFail(String e);
    }
}

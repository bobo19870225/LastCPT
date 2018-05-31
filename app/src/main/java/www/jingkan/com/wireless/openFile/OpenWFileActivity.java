/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.openFile;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.MVVMListActivity;
import www.jingkan.com.databinding.ActivityOpenFileBinding;

/**
 * Created by lushengbo on 2018/1/17.
 * 打开文件
 */

public class OpenWFileActivity extends MVVMListActivity<OpenFileViewModel, ActivityOpenFileBinding> {
    public static String EXTRA_FILE_DATES = "file_dates";

    @Override
    protected OpenFileViewModel createdViewModel() {
        return new OpenFileViewModel();
    }

    @Override
    protected void setViewWithOutListView() {
        setToolBar("打开标记文件");
    }

    @Override
    protected <SRL extends SwipeRefreshLayout> SRL setSwipeRefreshLayout() {
        return null;
    }

    @Override
    public int initView() {
        return R.layout.activity_open_file;
    }

    /**
     * 读文件
     */
    public static ByteArrayOutputStream readFile(final File file) {
        try {
            File readFile = new File(file.getPath());
            if (!readFile.exists()) {
                return null;
            }
            FileInputStream inStream = new FileInputStream(readFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            /*
             * stream.write(buffer, 0, 64); str = stream.toString();
			 */
            stream.close();
            inStream.close();
            return stream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (parentFile != null && !parentFile.getName().equals("")
//                    && !parentFile.getName().equals("mnt")) {
//                listFiles = parentFile.listFiles(new MyFileFilter());
//                listFiles = FileUtil.sort(listFiles);
//                mViewModel.getAdapter().update(listFiles);
//                // parentFile == mnt
//                parentFile = parentFile.getParentFile();
//                // 更新路径信息
//                // SDCard/Android/data/com.itcast.explorer
//                String path = mViewModel.pathInfo.get();
//                int indexOf = path.lastIndexOf("/");
//                path = path.substring(0, indexOf);
//                mViewModel.pathInfo.set(path);
//
//            } else {
//                finish();
//            }
//        }

        return true;
    }

    @Override
    public void setListView(final List listFiles) {
        mViewDataBinding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                File file = (File) listFiles.get(position);
                String[] str = new String[2];
                str[0] = file.getName();
                ByteArrayOutputStream byteArrayOutputStream = readFile(file);
                if (byteArrayOutputStream != null) {
                    str[1] = byteArrayOutputStream.toString();
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_FILE_DATES, str);
                    // Set result and finish this Activity
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}

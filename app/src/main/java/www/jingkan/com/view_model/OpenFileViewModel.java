/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;

import www.jingkan.com.util.FileUtil;
import www.jingkan.com.util.MyFileFilter;
import www.jingkan.com.view.adapter.ItemFile;
import www.jingkan.com.view_model.base.BaseListViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by lushengbo on 2018/1/17.
 * 打开文件
 */

public class OpenFileViewModel extends BaseListViewModel<List<ItemFile>> {

    public final ObservableField<String> pathInfo = new ObservableField<>("/SDCard");
    private final MutableLiveData<List<ItemFile>> files = new MutableLiveData<>();

    public OpenFileViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<ItemFile>> loadListViewData() {
        return files;
    }

    @Override
    public void afterLoadListViewData() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            File[] listFiles = file.listFiles(new MyFileFilter());
            List<File> fileList = FileUtil.sort(listFiles);//这个List不可修改
            List<ItemFile> itemFiles = new ArrayList<>();
            for (int i = 0; i < fileList.size(); i++) {
                File mFile = fileList.get(i);
                if (mFile.isFile()) {
                    String mFileName = mFile.getName();
                    if (mFileName.contains("W.txt")) {
                        ItemFile itemFile = new ItemFile();
                        itemFile.setId(i);
                        itemFile.setFileName(mFile.getName());
                        itemFile.setFile(mFile);
                        itemFiles.add(itemFile);
                    }
                }
            }
            files.setValue(itemFiles);

        } else {
            toast("er");
        }
    }

    @Override
    public void inject(Object... objects) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

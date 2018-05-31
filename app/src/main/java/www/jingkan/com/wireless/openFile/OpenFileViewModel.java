/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.wireless.openFile;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemFileBinding;

/**
 * Created by lushengbo on 2018/1/17.
 * 打开文件
 */

public class OpenFileViewModel extends MVVMListViewModel<OpenWFileActivity> {

    public final ObservableField<String> pathInfo = new ObservableField<>("/SDCard");


    @Override
    public void loadListViewData() {

    }

    @Override
    protected void initData(Object data) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            File[] listFiles = file.listFiles(new MyFileFilter());
            List<File> fileList = FileUtil.sort(listFiles);//这个List不可修改
            List<File> mFileList = new ArrayList<>();
            for (File mFile : fileList) {
                if (mFile.isFile()) {
                    String mFileName = mFile.getName();
                    if (mFileName.contains("W.txt")) {
                        mFileList.add(mFile);
                        OpenFileItemViewModel openFileItemViewModel = new OpenFileItemViewModel();
                        openFileItemViewModel.strFileName.set(mFileName);
                        openFileItemViewModels.add(openFileItemViewModel);
                    }
                }
            }
            myView.get().setListView(mFileList);

        } else {
            getView().showToast("er");
        }
    }


    private List<OpenFileItemViewModel> openFileItemViewModels;

    @Override
    public BaseDataBindingAdapter setUpAdapter() {
        openFileItemViewModels = new ArrayList<>();
        return new FileExplorerAdapter<ItemFileBinding, OpenFileItemViewModel>(openFileItemViewModels, R.layout.item_file);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }
}

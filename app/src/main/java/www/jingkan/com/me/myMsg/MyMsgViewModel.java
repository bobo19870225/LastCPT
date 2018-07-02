


/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.me.myMsg;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.adapter.myMsgAdapter.MyMSgItemViewModel;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemMyMsgBinding;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.msgData.MsgDao;
import www.jingkan.com.localData.msgData.MsgDataModel;

/**
 * Created by lushengbo on 2018/1/23.
 * 我的信息
 */

public class MyMsgViewModel extends MVVMListViewModel<MyMsgActivity> {

    @Override
    protected void initData(Object data) {

    }

    private List<MyMSgItemViewModel> myMSgItemViewModels;

    @Override
    public BaseDataBindingAdapter setUpAdapter() {
        myMSgItemViewModels = new ArrayList<>();
        return new BaseDataBindingAdapter<ItemMyMsgBinding, MyMSgItemViewModel>(myMSgItemViewModels, R.layout.item_my_msg);

    }

    @Override
    public void loadListViewData() {

        MsgDao msgDao = DataFactory.getBaseData(MsgDao.class);
        msgDao.getData(new DataLoadCallBack<MsgDataModel>() {

            @Override
            public void onDataLoaded(List<MsgDataModel> models) {
                myMSgItemViewModels.clear();
                for (MsgDataModel msgDataModel : models
                        ) {
                    MyMSgItemViewModel myMSgItemViewModel = new MyMSgItemViewModel();
                    myMSgItemViewModel.msgTime.set(msgDataModel.time);
                    myMSgItemViewModels.add(myMSgItemViewModel);
                }
                adapter.notifyDataSetChanged();
                getView().setListView(models);
                getView().stopLoading();

            }

            @Override
            public void onDataNotAvailable() {
                getView().stopLoading();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }
}

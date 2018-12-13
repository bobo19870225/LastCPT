/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.me.myMsg;

import android.content.Intent;

import androidx.databinding.ObservableField;
import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.localData.AppDatabase;
import www.jingkan.com.localData.msgData.MsgDaoForRoom;

/**
 * Created by lushengbo on 2018/1/23.
 * 我的信息详情
 */

public class MyMsgDetailViewModel extends BaseViewModel<MyMsgDetailActivity> {
    public final ObservableField<String> msg = new ObservableField<>();
    public final ObservableField<String> time = new ObservableField<>();

    @Override
    protected void init(Object data) {
        MsgDaoForRoom msgDaoForRoom = AppDatabase.getInstance(getView().getApplicationContext()).msgDaoForRoom();
        msgDaoForRoom.deleteMsgDataEntityByMsgId((Integer) data);

//        MsgDao msgDao = DataFactory.getBaseData(MsgDao.class);
//        msgDao.getData(new DataLoadCallBack<MsgDataModel>() {
//
//            @Override
//            public void onDataLoaded(List<MsgDataModel> models) {
//                MsgDataModel msgDataModel = models.get(0);
//                msg.set(msgDataModel.title);
//                time.set(msgDataModel.time);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        }, String.valueOf(data));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }
}

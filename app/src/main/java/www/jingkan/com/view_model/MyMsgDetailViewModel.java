/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MediatorLiveData;
import www.jingkan.com.db.dao.MsgDao;
import www.jingkan.com.db.entity.MsgDataEntity;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by lushengbo on 2018/1/23.
 * 我的信息详情
 */

public class MyMsgDetailViewModel extends BaseViewModel {
    public final ObservableField<String> msg = new ObservableField<>();
    public final ObservableField<String> time = new ObservableField<>();
    public final MediatorLiveData<List<MsgDataEntity>> ldMsgDataEntities = new MediatorLiveData<>();


    public MyMsgDetailViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    public void inject(Object... objects) {
        MsgDao msgDao = (MsgDao) objects[1];
        ldMsgDataEntities.addSource(msgDao.getMsgDataEntityByMsgId((String) objects[0]), ldMsgDataEntities::setValue);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

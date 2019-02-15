package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import com.jinkan.www.cpttest.db.dao.MsgDao;
import com.jinkan.www.cpttest.db.entity.MsgDataEntity;
import com.jinkan.www.cpttest.view_model.base.BaseListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MyMsgViewModel extends BaseListViewModel<List<MsgDataEntity>> {
    private MsgDao msgDao;

    public MyMsgViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<MsgDataEntity>> loadListViewData() {
        return msgDao.getAllMsgDataEntity();
    }

    @Override
    public void afterLoadListViewData() {

    }

    @Override
    public void inject(Object... objects) {
        msgDao = (MsgDao) objects[1];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }
}

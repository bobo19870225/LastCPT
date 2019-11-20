package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import www.jingkan.com.db.dao.ProbeDao;
import www.jingkan.com.db.dao.ProbeDaoHelper;
import www.jingkan.com.db.dao.dao_factory.DataBaseCallBack;
import www.jingkan.com.db.entity.ProbeEntity;
import www.jingkan.com.view.adapter.ItemOrdinaryProbe;
import www.jingkan.com.view_model.base.BaseListViewModel;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class OrdinaryProbeVM extends BaseListViewModel<List<ProbeEntity>> {

    //    public final MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();
    private ProbeDao probeDao;
    private ProbeDaoHelper probeDaoHelper;
    public OrdinaryProbeVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<ProbeEntity>> loadListViewData() {
        return probeDao.getAllProbe();
    }

    @Override
    public void afterLoadListViewData() {

    }


    @Override
    public void inject(Object... objects) {
        probeDao = (ProbeDao) objects[1];
        probeDaoHelper = (ProbeDaoHelper) objects[2];
    }

    public void addProbe() {
        callbackMessage.setValue(0);
        getView().action(callbackMessage);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    public void deleteProbe(ItemOrdinaryProbe itemOrdinaryProbe) {
        probeDaoHelper.deleteData(new String[]{(String) itemOrdinaryProbe.getId()}, () -> action.setValue("刷新"));
//        ExecutorService DB_IO = Executors.newFixedThreadPool(2);
//        DB_IO.execute(() -> {
//            probeDao.deleteProbeByProbeId((String) );
//            DB_IO.shutdown();//关闭线程
//        });
    }
}

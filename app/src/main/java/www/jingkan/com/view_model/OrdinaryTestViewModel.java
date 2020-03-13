package www.jingkan.com.view_model;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.tencent.bugly.crashreport.CrashReport;

import java.util.List;

import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.view_model.base.BaseViewModel;

/**
 * Created by Sampson on 2018/12/16.
 * CPTTest
 */

public class OrdinaryTestViewModel extends BaseViewModel {

    public OrdinaryTestViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        testDao = (TestDao) objects[1];
    }

    private TestDao testDao;
    public final MediatorLiveData<List<TestEntity>> allTestes = new MediatorLiveData<>();
    public final MutableLiveData<Integer> test = new MutableLiveData<>();
    public void newTest() {
        callbackMessage.setValue(0);
        getView().action(callbackMessage);
//        action.setValue("NewTest");
    }

    public void reDoTest() {
        allTestes.addSource(testDao.getAllTestes(), allTestes::setValue);
        callbackMessage.setValue(3);
        getView().action(callbackMessage);
    }

    public void showHistoryData() {
        test.setValue(0);
        callbackMessage.setValue(1);
        getView().action(callbackMessage);
//        action.setValue("HistoryDataActivity");
    }

    public void showOrdinaryProbe() {
        // 测试crash
//        CrashReport.testJavaCrash();
        callbackMessage.setValue(2);
        getView().action(callbackMessage);
//        action.setValue("OrdinaryProbeActivity");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }


}

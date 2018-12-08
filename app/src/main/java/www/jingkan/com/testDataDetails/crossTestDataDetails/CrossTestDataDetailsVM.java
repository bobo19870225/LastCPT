package www.jingkan.com.testDataDetails.crossTestDataDetails;

import android.content.Intent;
import androidx.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.test.TestDao;
import www.jingkan.com.localData.test.TestModel;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDaoDao;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataModel;
import www.jingkan.com.mInterface.ISkip;
import www.jingkan.com.saveUtils.DataUtils;

/**
 * Created by Sampson on 2018/7/21.
 * LastCPT
 */
public class CrossTestDataDetailsVM extends BaseViewModel<CrossTestDataDetailsActivity> implements ISkip {
    private Integer testNumber = -1;
    private List<DataGroup> groupList;
    private List<DataItem> itemList;
    private List<List<DataItem>> childrenList;
    private CrossTestDataListAdapter crossTestDataListAdapter;
    public final ObservableField<String> obsHoleNumber = new ObservableField<>("");
    public final ObservableField<String> obsProjectNumber = new ObservableField<>("");
    public final ObservableField<String> obsTestDate = new ObservableField<>("");
    private List<CrossTestDataModel> crossTestDataModels;
    private TestModel testModel;

    @Override
    protected void init(Object data) {
        TestDao testDao = DataFactory.getBaseData(TestDao.class);
        testDao.getData(new DataLoadCallBack<TestModel>() {
            @Override
            public void onDataLoaded(List<TestModel> models) {
                testModel = models.get(0);
                obsProjectNumber.set(testModel.projectNumber);
                obsHoleNumber.set(testModel.holeNumber);
                obsTestDate.set(testModel.testDate);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, (String) data);
        CrossTestDaoDao crossTestDaoDao = DataFactory.getBaseData(CrossTestDaoDao.class);
        crossTestDaoDao.getData(new DataLoadCallBack<CrossTestDataModel>() {
            @Override
            public void onDataLoaded(List<CrossTestDataModel> models) {
                groupList = new ArrayList<>();

                childrenList = new ArrayList<>();
                for (CrossTestDataModel crossTestDataModel : models) {
                    if (crossTestDataModel.number != testNumber) {
                        if (testNumber != -1)
                            childrenList.add(itemList);
                        itemList = new ArrayList<>();
                        testNumber = crossTestDataModel.number;
                        groupList.add(new DataGroup(crossTestDataModel.deep,
                                crossTestDataModel.type,
                                crossTestDataModel.number));
                    }
                    itemList.add(new DataItem(crossTestDataModel.cu, crossTestDataModel.deg));
                }
                childrenList.add(itemList);//添加最后一组数据
                crossTestDataListAdapter = new CrossTestDataListAdapter(getView().getApplicationContext(),
                        groupList,
                        childrenList, R.layout.item_group, R.layout.item_children);
                myView.get().setListView(crossTestDataListAdapter);
            }

            @Override
            public void onDataNotAvailable() {
//                测试代码
//                groupList = new ArrayList<>();
//                childrenList = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    groupList.add(new DataGroup(i, "test", i + 1));
//                    itemList = new ArrayList<>();
//                    for (int j = 0; j < 5; j++) {
//                        itemList.add(new DataItem(j + i, j));
//                    }
//                    childrenList.add(itemList);
//                }
//                crossTestDataListAdapter = new CrossTestDataListAdapter(getView().getApplicationContext(),
//                        groupList,
//                        childrenList, R.layout.item_group, R.layout.item_children);
//                myView.get().setListView(crossTestDataListAdapter);
            }
        }, (String) data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }

    public void saveTestDataToSD() {
        CrossTestDaoDao crossTestDataDao = DataFactory.getBaseData(CrossTestDaoDao.class);
        crossTestDataDao.getData(new DataLoadCallBack<CrossTestDataModel>() {

            @Override
            public void onDataLoaded(List<CrossTestDataModel> models) {
                crossTestDataModels = models;
                DataUtils.getInstance().saveDataToSd(getView().getApplicationContext(),
                        models, testModel, CrossTestDataDetailsVM.this);
            }

            @Override
            public void onDataNotAvailable() {
                myView.get().showToast("读取数据失败！");
            }
        }, testModel.projectNumber + "_" + testModel.holeNumber);

    }

    public void emailTestData() {
        DataUtils.getInstance().emailData(getView().getApplicationContext(),
                crossTestDataModels, testModel, this);
    }

    @Override
    public void skipForResult(Intent intent, int requestCode) {
        getView().startActivityForResult(intent, requestCode);
    }

    @Override
    public void skip(Intent intent) {

    }

    @Override
    public void sendToastMsg(String msg) {
        getView().showToast(msg);
    }


}

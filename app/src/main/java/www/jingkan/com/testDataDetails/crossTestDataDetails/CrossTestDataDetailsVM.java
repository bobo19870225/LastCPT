package www.jingkan.com.testDataDetails.crossTestDataDetails;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.base.baseMVVM.BaseViewModel;
import www.jingkan.com.localData.dataFactory.DataFactory;
import www.jingkan.com.localData.dataFactory.DataLoadCallBack;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDaoDao;
import www.jingkan.com.localData.testData.CrossTestData.CrossTestDataModel;

/**
 * Created by Sampson on 2018/7/21.
 * LastCPT
 */
public class CrossTestDataDetailsVM extends BaseViewModel<CrossTestDataDetailsActivity> {
    private Integer testNumber = -1;
    private List<DataGroup> groupList;
    private List<DataItem> itemList;
    private List<List<DataItem>> childrenList;
    private CrossTestDataListAdapter crossTestDataListAdapter;

    @Override
    protected void init(Object data) {
        CrossTestDaoDao crossTestDaoDao = DataFactory.getBaseData(CrossTestDaoDao.class);
        crossTestDaoDao.getData(new DataLoadCallBack<CrossTestDataModel>() {
            @Override
            public void onDataLoaded(List<CrossTestDataModel> models) {
                groupList = new ArrayList<>();
                itemList = new ArrayList<>();
                childrenList = new ArrayList<>();
                for (CrossTestDataModel crossTestDataModel : models) {
                    if (crossTestDataModel.number != testNumber) {
                        if (testNumber != -1)
                            childrenList.add(itemList);
                        itemList.clear();
                        testNumber = crossTestDataModel.number;
                        groupList.add(new DataGroup(crossTestDataModel.deep,
                                crossTestDataModel.type,
                                crossTestDataModel.number));
                    }
                    itemList.add(new DataItem(crossTestDataModel.cu, crossTestDataModel.deg));
                }
                crossTestDataListAdapter = new CrossTestDataListAdapter(getView().getApplicationContext(),
                        groupList,
                        childrenList, R.layout.item_common_probe_list, R.layout.item_file);
                myView.get().setListView(crossTestDataListAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, (String) data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }

}

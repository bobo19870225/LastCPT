package www.jingkan.com.common.historyData;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import www.jingkan.com.R;
import www.jingkan.com.adapter.BaseDataBindingAdapter;
import www.jingkan.com.base.baseMVVM.MVVMListViewModel;
import www.jingkan.com.databinding.ItemHistoryTestBinding;
import www.jingkan.com.localData.test.TestEntity;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
public class HistoryDataViewModel extends MVVMListViewModel<HistoryDataActivity> {


    @Override
    protected void initData(Object data) {

    }

    private List<HistoryDataItemViewModel> historyDataItemViewModels;

    @Override
    public BaseDataBindingAdapter setUpAdapter() {
        historyDataItemViewModels = new ArrayList<>();
        return new BaseDataBindingAdapter<ItemHistoryTestBinding, HistoryDataItemViewModel>(historyDataItemViewModels, R.layout.item_history_test);
    }

    @Override
    public void loadListViewData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clear() {

    }

    public void getHistoryData() {
//        LiveData<List<TestEntity>> allTestes = AppDatabase.getInstance(getView().getApplicationContext())
//                .testDaoForRoom().getAllTestes();
//        List<TestEntity> testEntities = allTestes.getValue();
//        if (testEntities != null) {
//            for (TestEntity testEntity : testEntities
//                    ) {
//                HistoryDataItemViewModel historyDataItemViewModel = new HistoryDataItemViewModel();
//                historyDataItemViewModel.obsProjectNumber.set(testEntity.projectNumber);
//                historyDataItemViewModels.add(historyDataItemViewModel);
//            }
//        }

//        getView().setListView(allTestes.getValue());
    }

    public void deleteOneHistoryData(TestEntity testEntity) {

    }
}

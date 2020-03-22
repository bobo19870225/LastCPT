package www.jingkan.com.view;

import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityCrossTestDataDetailsBinding;
import www.jingkan.com.db.entity.CrossTestDataEntity;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.view.adapter.crosstestdatalistadapter.CrossTestDataListAdapter;
import www.jingkan.com.view.adapter.crosstestdatalistadapter.DataGroup;
import www.jingkan.com.view.adapter.crosstestdatalistadapter.DataItem;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.CrossTestDataDetailsMV;
import www.jingkan.com.view_model.ViewModelFactory;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class CrossTestDataDetailsActivity extends BaseMVVMDaggerActivity<CrossTestDataDetailsMV, ActivityCrossTestDataDetailsBinding> {
    @Inject
    ViewModelFactory viewModelFactory;
    private Integer testNumber = -1;
    @Override
    protected Object[] injectToViewModel() {

        return new Object[]{mData};
    }

    @Override
    protected void setMVVMView() {
        setToolBar("十字版试验数据详情");
        mViewModel.ldTestData.observe(this, crossTestDataEntities -> {
            List<DataGroup> groupList = new ArrayList<>();
            List<List<DataItem>> childrenList = new ArrayList<>();
            List<DataItem> itemList = null;
            for (CrossTestDataEntity crossTestDataModel : crossTestDataEntities) {
                if (crossTestDataModel.number != testNumber) {
                    if (testNumber != -1)
                        childrenList.add(itemList);
                    itemList = new ArrayList<>();
                    testNumber = crossTestDataModel.number;
                    groupList.add(new DataGroup(crossTestDataModel.deep,
                            crossTestDataModel.type,
                            crossTestDataModel.number));
                }
                if (itemList != null) {
                    itemList.add(new DataItem(crossTestDataModel.cu, crossTestDataModel.deg));
                }
            }
            childrenList.add(itemList);//添加最后一组数据
            CrossTestDataListAdapter crossTestDataListAdapter = new CrossTestDataListAdapter(getApplicationContext(),
                    groupList,
                    childrenList, R.layout.item_group, R.layout.item_children);
            NestedExpandableListView nestedExpandableListView = mViewDataBinding.ex;
            nestedExpandableListView.setAdapter(crossTestDataListAdapter);
            int count = nestedExpandableListView.getCount();
            for (int i = 0; i < count; i++) {
                nestedExpandableListView.expandGroup(i);
            }
            nestedExpandableListView.setGroupIndicator(null);
            nestedExpandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
        });
    }

    @Override
    public int initView() {
        return R.layout.activity_cross_test_data_details;
    }


    @Override
    public CrossTestDataDetailsMV createdViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(CrossTestDataDetailsMV.class);
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

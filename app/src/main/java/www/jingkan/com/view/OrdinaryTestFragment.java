package www.jingkan.com.view;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import www.jingkan.com.R;
import www.jingkan.com.databinding.FragmentOrdinaryTestBinding;
import www.jingkan.com.db.dao.TestDao;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.di.ActivityScoped;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.PreferencesUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.util.SystemConstant;
import www.jingkan.com.view.base.BaseMVVMDaggerFragment;
import www.jingkan.com.view_model.OrdinaryTestViewModel;

/**
 * Created by Sampson on 2018/12/16.
 * CPTTest
 */
@ActivityScoped
public class OrdinaryTestFragment extends BaseMVVMDaggerFragment<OrdinaryTestViewModel, FragmentOrdinaryTestBinding> {
    private int probeType = 0;
    @Inject
    public OrdinaryTestFragment() {
        // Requires empty public constructor
    }

    @Inject
    TestDao testDao;
    @Inject
    PreferencesUtil preferencesUtil;

    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, testDao};
    }

    @Override
    protected int setLayOutId() {
        return R.layout.fragment_ordinary_test;
    }

    @Override
    public OrdinaryTestViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(OrdinaryTestViewModel.class);
    }


    @Override
    protected void setView() {

    }

    private void showChooseDialog() {
        CharSequence[] saveItems = new CharSequence[]{"数字探头", "模拟探头"};
        AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setTitle("请选择要使用的探头类型")
                .setSingleChoiceItems(saveItems, 0, (dialog, which) -> probeType = which)
                .setPositiveButton("确定", (dialog, which) -> goTo(NewTestActivity.class, saveItems[probeType]))
                .setNegativeButton("取消", (dialog, which) -> {
                    probeType = 0;
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

        switch (callbackMessage.what) {
            case 0:
                showChooseDialog();
                break;
            case 1:
                goTo(HistoryDataActivity.class, null);
                break;
            case 2:
                goTo(OrdinaryProbeActivity.class, null);
                break;
            case 3:
                reTest();
                break;
        }


    }

    private void reTest() {
        mViewModel.allTestes.observe(this, new Observer<List<TestEntity>>() {
            @Override
            public void onChanged(List<TestEntity> testEntities) {
                {
                    //只观察一次数据
                    mViewModel.allTestes.removeObserver(this);
                    if (testEntities != null && !testEntities.isEmpty()) {
                        TestEntity testEntity = testEntities.get(0);
                        Map<String, String> linkerPreferences = preferencesUtil.getLinkerPreferences();
                        String add = linkerPreferences.get("add");
                        if (StringUtil.isEmpty(add)) {
                            goTo(LinkBluetoothActivity.class, new String[]{testEntity.projectNumber, testEntity.holeNumber, testEntity.testType});
                        } else {//mac地址，工程编号，孔号，试验类型。
                            String[] strings = {add, testEntity.projectNumber, testEntity.holeNumber};
                            switch (testEntity.testType) {
                                case SystemConstant.SINGLE_BRIDGE_TEST:
                                    goTo(SingleBridgeTestActivity.class, strings);
                                    break;
                                case SystemConstant.SINGLE_BRIDGE_MULTI_TEST:
                                    goTo(SingleBridgeMultifunctionTestActivity.class, strings);
                                    break;
                                case SystemConstant.DOUBLE_BRIDGE_TEST:
                                    goTo(DoubleBridgeTestActivity.class, strings);
                                    break;
                                case SystemConstant.DOUBLE_BRIDGE_MULTI_TEST:
                                    goTo(DoubleBridgeMultifunctionTestActivity.class, strings);
                                    break;
                                case SystemConstant.VANE_TEST:
                                    goTo(CrossTestActivity.class, strings);
                                    break;
                            }

                        }

                    } else {
                        showToast("暂无可进行二次测量的试验");
                    }


                }
            }
        });
    }
}

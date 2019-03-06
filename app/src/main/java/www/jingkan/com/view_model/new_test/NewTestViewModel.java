package www.jingkan.com.view_model.new_test;

import android.app.Application;
import android.content.Intent;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import www.jingkan.com.db.dao.TestDaoHelper;
import www.jingkan.com.db.entity.TestEntity;
import www.jingkan.com.util.PreferencesUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.view.CrossTestActivity;
import www.jingkan.com.view.DoubleBridgeMultifunctionTestActivity;
import www.jingkan.com.view.DoubleBridgeTestActivity;
import www.jingkan.com.view.LinkBluetoothActivity;
import www.jingkan.com.view.SingleBridgeMultifunctionTestActivity;
import www.jingkan.com.view.SingleBridgeTestActivity;
import www.jingkan.com.view_model.base.BaseViewModel;

import static www.jingkan.com.util.SystemConstant.DOUBLE_BRIDGE_MULTI_TEST;
import static www.jingkan.com.util.SystemConstant.DOUBLE_BRIDGE_TEST;
import static www.jingkan.com.util.SystemConstant.SINGLE_BRIDGE_MULTI_TEST;
import static www.jingkan.com.util.SystemConstant.SINGLE_BRIDGE_TEST;
import static www.jingkan.com.util.SystemConstant.VANE_TEST;

/**
 * Created by Sampson on 2018/12/13.
 * CPTTest
 */

public class NewTestViewModel extends BaseViewModel {

    public MutableLiveData<String> obsProjectNumber = new MutableLiveData<>();
    public MutableLiveData<String> obsHoleNumber = new MutableLiveData<>();
    public MutableLiveData<String> obsHoleHigh = new MutableLiveData<>();
    public MutableLiveData<String> obsWaterLevel = new MutableLiveData<>();
    public MutableLiveData<String> obsLocation = new MutableLiveData<>();
    public MutableLiveData<String> obsTester = new MutableLiveData<>();
    public MutableLiveData<String> obsTestType = new MutableLiveData<>();

    private TestDaoHelper testDaoHelper;
    private PreferencesUtil preferencesUtil;
    private boolean isAnalog;


    public NewTestViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void inject(Object... objects) {
        String mData = (String) objects[0];
        if (mData.equals("模拟探头"))
            isAnalog = true;
        testDaoHelper = (TestDaoHelper) objects[1];
        preferencesUtil = (PreferencesUtil) objects[2];
    }


    public void submit() {
        TestEntity testEntity = new TestEntity();
        if (obsProjectNumber.getValue() == null) {
            toast("工程编号不能为空");
            return;
        }
        testEntity.projectNumber = obsProjectNumber.getValue();

        if (obsHoleNumber.getValue() == null) {
            toast("孔号不能为空");
            return;
        }
        testEntity.holeNumber = obsHoleNumber.getValue();
        testEntity.testID = obsProjectNumber.getValue() + "_" + obsHoleNumber.getValue();
        if (obsHoleHigh.getValue() == null) {
            toast("孔口高程不能为空");
            return;
        }
        testEntity.holeHigh = Float.valueOf(obsHoleHigh.getValue());

        if (obsWaterLevel.getValue() == null) {
            toast("地下水位不能为空");
            return;
        }
        testEntity.waterLevel = Float.valueOf(obsWaterLevel.getValue());

        if (obsTester.getValue() == null) {
            toast("操作员不能为空");
            return;
        }
        testEntity.tester = obsTester.getValue();

        if (obsTestType.getValue() == null) {
            toast("试验类型不能为空");
            return;
        }
        testEntity.testType = obsTestType.getValue();
        testDaoHelper.addData(testEntity, () -> toast("添加成功！")
        );

        Map<String, String> linkerPreferences = preferencesUtil.getLinkerPreferences();
        String add = linkerPreferences.get("add");
        if (StringUtil.isEmpty(add)) {
            getView().action(callbackMessage);
            goTo(LinkBluetoothActivity.class, new String[]{
                    obsProjectNumber.getValue(),
                    obsHoleNumber.getValue(),
                    obsTestType.getValue(),
                    isAnalog ? "模拟探头" : "数字探头"
            });
        } else {
            String[] dataToSend = {add, testEntity.projectNumber, testEntity.holeNumber, isAnalog ? "模拟探头" : "数字探头"};
            switch (testEntity.testType) {
                case SINGLE_BRIDGE_TEST:
                    //mac地址，工程编号，孔号。
                    goTo(SingleBridgeTestActivity.class, dataToSend);
                    break;
                case SINGLE_BRIDGE_MULTI_TEST:
                    goTo(SingleBridgeMultifunctionTestActivity.class, dataToSend);
                    break;
                case DOUBLE_BRIDGE_TEST:
                    goTo(DoubleBridgeTestActivity.class, dataToSend);
                    break;
                case DOUBLE_BRIDGE_MULTI_TEST:
                    goTo(DoubleBridgeMultifunctionTestActivity.class, dataToSend);
                    break;
                case VANE_TEST:
                    goTo(CrossTestActivity.class, dataToSend);
                    break;

            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void clear() {

    }

    public void setTypeText(String testType) {
        obsTestType.setValue(testType);
    }


}

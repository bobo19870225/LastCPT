package www.jingkan.com.base.baseMVVM;

import android.app.Activity;
import androidx.databinding.ViewDataBinding;
import android.view.Gravity;
import android.view.View;

import www.jingkan.com.R;
import www.jingkan.com.framework.view.guiedView.GuideHelper;

/**
 * Created by Sampson on 2018/3/23.
 * LastCPT
 */

public abstract class MVVMGuideActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseMVVMActivity<VM, VDB> {
    protected void showGuide(Activity activity, GuideHelper.TipData... listTipData) {
        final GuideHelper guideHelper = new GuideHelper(activity);
        guideHelper.addPage(listTipData);
        View testView = guideHelper.inflate(R.layout.guide_view_close);
        testView.findViewById(R.id.guide_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideHelper.dismiss();
            }
        });
        guideHelper.addPage(false, new GuideHelper.TipData(R.layout.guide_view_close, Gravity.CENTER));
        guideHelper.show(false);
    }

}

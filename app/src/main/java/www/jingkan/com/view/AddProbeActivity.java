package www.jingkan.com.view;

import android.Manifest;
import android.content.Intent;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivityAddProbeBinding;
import www.jingkan.com.db.dao.ProbeDaoHelper;
import www.jingkan.com.db.dao.WirelessProbeDaoHelper;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.acp.Acp;
import www.jingkan.com.util.acp.AcpListener;
import www.jingkan.com.util.acp.AcpOptions;
import www.jingkan.com.util.qrcode.qrSimple.CaptureActivity;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.AddProbeVM;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;


/**
 * Created by Sampson on 2018/12/28.
 * CPTTest
 */
public class AddProbeActivity extends BaseMVVMDaggerActivity<AddProbeVM, ActivityAddProbeBinding> {
    private final int OK = 0;
    private Boolean isWireless;
    @Inject
    ProbeDaoHelper probeDaoHelper;
    @Inject
    WirelessProbeDaoHelper wirelessProbeDaoHelper;
    @Override
    protected Object[] injectToViewModel() {
        return new Object[]{mData, probeDaoHelper, wirelessProbeDaoHelper};
    }

    @Override
    protected void setMVVMView() {
        if (mData.equals("无缆探头")) {
            isWireless = true;
            setToolBar("添加普通探头");
        } else {
            isWireless = false;
            setToolBar("添加无缆探头");
        }

    }

    @Override
    public int initView() {
        return R.layout.activity_add_probe;
    }

    @Override
    public AddProbeVM createdViewModel() {
        return ViewModelProviders.of(this).get(AddProbeVM.class);
    }

    @Override
    public void action(CallbackMessage callbackMessage) {
        switch (callbackMessage.what) {
            case 0:
                Acp.getInstance(AddProbeActivity.this).request(new AcpOptions.Builder()
                                .setPermissions(Manifest.permission.CAMERA)
                                .build(),
                        new AcpListener() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(AddProbeActivity.this, CaptureActivity.class);
                                startActivityForResult(intent, OK);
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                showToast(permissions.toString() + "权限拒绝");
                            }
                        });
                break;
            case 1:
                goTo(AddProbeInfoActivity.class, new String[]{isWireless ? "无缆探头" : "普通探头"});
                break;
            case 2:
                goTo(OrdinaryProbeActivity.class, null, true);
                break;
            case 3:
                goTo(WirelessProbeActivity.class, null, true);
                break;
        }
    }
}

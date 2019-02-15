/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import www.jingkan.com.R;
import www.jingkan.com.databinding.ActivitySetEmailBinding;
import www.jingkan.com.util.CallbackMessage;
import www.jingkan.com.util.PreferencesUtil;
import www.jingkan.com.util.StringUtil;
import www.jingkan.com.view.base.BaseMVVMDaggerActivity;
import www.jingkan.com.view_model.SetEmailViewModel;

import java.util.Map;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by lushengbo on 2017/8/9.
 * 设置邮箱界面
 */

public class SetEmailActivity extends BaseMVVMDaggerActivity<SetEmailViewModel, ActivitySetEmailBinding> {

    @Inject
    PreferencesUtil preferencesUtil;


    @Override
    protected Object[] injectToViewModel() {
        return new Object[0];
    }

    @Override
    protected void setMVVMView() {
        setToolBar("设置我的邮箱", R.menu.email);
        setEditText();
    }

    private void setEditText() {
        Map<String, String> emailPreferences = preferencesUtil.getEmailPreferences();
        mViewDataBinding.email.setText(emailPreferences.get("sEmail"));
        mViewDataBinding.password.setText(emailPreferences.get("sEmailPassword"));
        mViewDataBinding.receiveEmail.setText(emailPreferences.get("rEmail"));
        mViewDataBinding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewDataBinding.ttEmail.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mViewDataBinding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewDataBinding.ttPassword.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mViewDataBinding.receiveEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mViewDataBinding.ttReceiveEmail.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int initView() {
        return R.layout.activity_set_email;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                String StrEmail = mViewDataBinding.email.getText().toString();
                String StrPassword = mViewDataBinding.password.getText().toString();
                String strReceiveEmail = mViewDataBinding.receiveEmail.getText().toString();
                if (StringUtil.isEmail(StrEmail)) {
                    if (StringUtil.isEmpty(StrPassword)) {
                        mViewDataBinding.ttPassword.setError("密码不能为空");
                    } else {
                        if (StringUtil.isEmail(strReceiveEmail)) {
                            showToast("设置成功");
                            preferencesUtil.saveEmail(StrEmail, StrPassword, strReceiveEmail);
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            mViewDataBinding.ttReceiveEmail.setError("联系人邮箱不能为空");
                        }

                    }
                } else {
                    mViewDataBinding.ttEmail.setError("邮箱地址有误");
                }
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.test:
//                MailSenderRunnable senderRunnable = new MailSenderRunnable(SetEmailActivity.this,
//                        "rain19870225@163.com", "e424291182");
//                senderRunnable.setMail("this is the test subject",
//                        "this is the test body", "rain19870225@163.com", Environment.getExternalStorageDirectory().getPath() + "/JKD10-4-020锥头标定.txt");
//                new Thread(senderRunnable).start();
//                break;
//        }
//    }


    @Override
    public SetEmailViewModel createdViewModel() {
        return ViewModelProviders.of(this).get(SetEmailViewModel.class);
    }

    @Override
    public void action(CallbackMessage callbackMessage) {

    }
}

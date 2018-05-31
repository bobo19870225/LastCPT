/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseFragment;


public class DoubleBridgeFragment extends BaseFragment {
    private DoubleBridgeData doubleBridgeData;
    private String[] tp;
    @BindView(id = R.id.qc_area)
    private EditText qc_area;
    @BindView(id = R.id.qc_coefficient)
    private EditText qc_coefficient;
    @BindView(id = R.id.qc_limit)
    private EditText qc_limit;
    @BindView(id = R.id.fs_area)
    private EditText fs_area;
    @BindView(id = R.id.fs_coefficient)
    private EditText fs_coefficient;
    @BindView(id = R.id.fs_limit)
    private EditText fs_limit;

    @Override
    public void onClick(View v) {

    }

    public interface DoubleBridgeData {
        void getDoubleBridgeData(String[] ss);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        doubleBridgeData = (DoubleBridgeData) getActivity();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setView() {
        tp = new String[6];
        if (mData != null && mData instanceof String[]) {
            tp = (String[]) mData;
            qc_area.setText(tp[0]);
            qc_coefficient.setText(tp[1]);
            qc_limit.setText(tp[2]);
            fs_area.setText(tp[3]);
            fs_coefficient.setText(tp[4]);
            fs_limit.setText(tp[5]);
            doubleBridgeData.getDoubleBridgeData(tp);
        }
        qc_area.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tp[0] = s.toString();
                doubleBridgeData.getDoubleBridgeData(tp);
            }
        });
        qc_coefficient.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tp[1] = s.toString();
                doubleBridgeData.getDoubleBridgeData(tp);
            }
        });
        qc_limit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tp[2] = s.toString();
                doubleBridgeData.getDoubleBridgeData(tp);
            }
        });
        fs_area.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tp[3] = s.toString();
                doubleBridgeData.getDoubleBridgeData(tp);
            }
        });
        fs_coefficient.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tp[4] = s.toString();
                doubleBridgeData.getDoubleBridgeData(tp);
            }
        });
        fs_limit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tp[5] = s.toString();
                doubleBridgeData.getDoubleBridgeData(tp);
            }
        });
    }


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_double_bridge, container, false);
    }

}

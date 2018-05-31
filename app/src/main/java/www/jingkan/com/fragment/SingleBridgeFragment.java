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

import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseFragment;


public class SingleBridgeFragment extends BaseFragment {
    @BindView(id = www.jingkan.com.R.id.qc_area)
    private EditText qc_area;
    @BindView(id = www.jingkan.com.R.id.qc_coefficient)
    private EditText qc_coefficient;
    @BindView(id = www.jingkan.com.R.id.qc_limit)
    private EditText qc_limit;
    private String[] strings;
    private SingleBridgeData singleBridgeData;

    @Override
    public void onClick(View v) {

    }


    public interface SingleBridgeData {
        void getSingleBridgeData(String[] data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        singleBridgeData = (SingleBridgeData) getActivity();
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void setView() {
        strings = new String[3];
        if (mData != null && mData instanceof String[]) {
            strings = (String[]) mData;
            qc_area.setText(strings[0]);
            qc_coefficient.setText(strings[1]);
            qc_limit.setText(strings[2]);
            singleBridgeData.getSingleBridgeData(strings);
        }
        qc_area.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strings[0] = s.toString();
                singleBridgeData.getSingleBridgeData(strings);
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
                strings[1] = s.toString();
                singleBridgeData.getSingleBridgeData(strings);
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
                strings[2] = s.toString();
                singleBridgeData.getSingleBridgeData(strings);
            }
        });
    }


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(www.jingkan.com.R.layout.fragment_single_bridge, container, false);
    }

}

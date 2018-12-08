/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.fragment;


import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseFragment;

public class CrossFragment extends BaseFragment {

    @BindView(id = R.id.cross_area)
    private EditText cross_area;
    @BindView(id = R.id.cross_coefficient)
    private EditText cross_coefficient;
    @BindView(id = R.id.cross_limit)
    private EditText cross_limit;
    private String[] date;
    private Cross cross;


    @Override
    public void onClick(View v) {

    }

    public interface Cross {
        void getCrossData(String[] ss);
    }


    @Override
    protected void setView() {
        cross = (Cross) getActivity();
        date = new String[3];
        if (mData != null && mData instanceof String[]) {
            date = (String[]) mData;
            cross_area.setText(date[0]);
            cross_coefficient.setText(date[1]);
            cross_limit.setText(date[2]);
            cross.getCrossData(date);
        }
        cross_area.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                date[0] = s.toString();
                cross.getCrossData(date);
            }
        });
        cross_coefficient.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                date[1] = s.toString();
                cross.getCrossData(date);
            }
        });
        cross_limit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                date[2] = s.toString();
                cross.getCrossData(date);
            }
        });

    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_cross, container, false);
    }
}

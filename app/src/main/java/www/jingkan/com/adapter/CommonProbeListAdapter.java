/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import www.jingkan.com.R;
import www.jingkan.com.localData.commonProbe.ProbeModel;

import java.util.List;

public class CommonProbeListAdapter extends ArrayAdapter<ProbeModel> {
    private int resource;
    private List<ProbeModel> list;

    public CommonProbeListAdapter(Context context, int resource, List<ProbeModel> list) {
        super(context, resource, list);
        this.resource = resource;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ProbeModel getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            v = inflater.inflate(resource, parent, false);
        } else {
            v = convertView;
        }
        TextView textView = v.findViewById(R.id.tv_common);
        ProbeModel probeModel = getItem(position);
        if (probeModel != null) {
            String number = probeModel.number;
            textView.setText(number);
        }

        return v;
    }


}

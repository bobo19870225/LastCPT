/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import www.jingkan.com.R;

import java.util.List;

import androidx.annotation.NonNull;

public class OneTextListAdapter extends ArrayAdapter<String> {
    private Context context;
    private int res;
    private List<String> ls;

    public OneTextListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        res = resource;
        ls = objects;
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public String getItem(int position) {
        return ls.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(res, parent, false);
        } else {
            v = convertView;
        }

        TextView tv_item = v.findViewById(R.id.TextView);
        tv_item.setTextSize(18);
        String item = getItem(position);
        if (item != null) {
            tv_item.setText(item);
        }
        return v;
    }


}

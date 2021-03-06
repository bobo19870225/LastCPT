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
import www.jingkan.com.view_model.MyBluetoothDevice;

import java.util.List;

import androidx.annotation.NonNull;

public class DeviceAdapter extends ArrayAdapter<MyBluetoothDevice> {
    private Context context;
    private int res;
    private List<MyBluetoothDevice> ls;

    public DeviceAdapter(Context context, int resource, List<MyBluetoothDevice> objects) {
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
    public MyBluetoothDevice getItem(int position) {
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
        TextView tv_name = v.findViewById(R.id.tv_new_name);
        TextView tv_address = v.findViewById(R.id.tv_new_address);
        MyBluetoothDevice item = getItem(position);
        if (item != null) {
            tv_name.setText(item.name);
            tv_address.setText(item.address);
        }
        return v;
    }


}

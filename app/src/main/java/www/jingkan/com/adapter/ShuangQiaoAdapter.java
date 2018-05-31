/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter;


import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import www.jingkan.com.R;

public class ShuangQiaoAdapter extends ArrayAdapter<String[]> {

	private Context context;
	private int resource;
	private List<String[]> objects;
	public ShuangQiaoAdapter(Context context, int resource, List<String[]> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resource = resource;
		this.objects = objects;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size();
	}

	@Override
	public String[] getItem(int position) {
		// TODO Auto-generated method stub
		return objects.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v;
		if (convertView == null) {
			v = LayoutInflater.from(context).inflate(resource, parent, false);
		} else {
			v = convertView;
		}
		TextView tv_shendu = v.findViewById(R.id.tv_shendu);
		TextView tv_zhuijian = v.findViewById(R.id.tv_zhuijian);
		TextView tv_cebi = v.findViewById(R.id.tv_cebi);
		tv_shendu.setText(getItem(position)[0]);
		tv_zhuijian.setText(getItem(position)[1]);
		tv_cebi.setText(getItem(position)[2]);
		return v;

	}

}

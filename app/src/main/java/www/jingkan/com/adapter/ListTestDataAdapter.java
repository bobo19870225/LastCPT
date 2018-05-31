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

import www.jingkan.com.localData.testData.TestDataModel;
import www.jingkan.com.framework.utils.StringUtils;

import java.util.List;

public class ListTestDataAdapter extends ArrayAdapter<TestDataModel> {

    private Context context;
    private int resource;
    private List<TestDataModel> objects;

    public ListTestDataAdapter(Context context, int resource, List<TestDataModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        if (objects == null) {
            return 0;
        } else {
            return objects.size();
        }

    }

    @Override
    public TestDataModel getItem(int position) {
        if (objects == null) {
            return null;
        } else {
            return objects.get(position);
        }

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(resource, parent, false);
        } else {
            v = convertView;
        }
        TextView deep = v.findViewById(www.jingkan.com.R.id.deep);
        TextView qc = v.findViewById(www.jingkan.com.R.id.qc);
        TextView fs = v.findViewById(www.jingkan.com.R.id.fs);
        TextView fa = v.findViewById(www.jingkan.com.R.id.fa);
        TestDataModel testDataModel = getItem(position);
        if (testDataModel != null) {
            deep.setText(StringUtils.format(testDataModel.deep, 1));
            qc.setText(StringUtils.format(testDataModel.qc, 2));
            fs.setText(StringUtils.format(testDataModel.fs, 2));
            fa.setText(StringUtils.format(testDataModel.fa, 1));
        }
        return v;
    }

}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import www.jingkan.com.framework.utils.StringUtils;
import www.jingkan.com.localData.testData.TestDataEntity;

public class ListTestDataAdapter extends ArrayAdapter<TestDataEntity> {

    private Context context;
    private int resource;
    private List<TestDataEntity> objects;

    public ListTestDataAdapter(Context context, int resource, List<TestDataEntity> objects) {
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
    public TestDataEntity getItem(int position) {
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
        TestDataEntity testDataEntity = getItem(position);
        if (testDataEntity != null) {
            deep.setText(StringUtils.format(testDataEntity.deep, 1));
            qc.setText(StringUtils.format(testDataEntity.qc, 2));
            fs.setText(StringUtils.format(testDataEntity.fs, 2));
            fa.setText(StringUtils.format(testDataEntity.fa, 1));
        }
        return v;
    }

}

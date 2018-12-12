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
import www.jingkan.com.R;
import www.jingkan.com.localData.test.TestEntity;

public class ListHistoryAdapter extends ArrayAdapter<TestEntity> {

    private Context context;
    private int resource;
    private List<TestEntity> objects;

    public ListHistoryAdapter(Context context, int resource, List<TestEntity> objects) {
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
    public TestEntity getItem(int position) {
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
        TextView project_number = v.findViewById(R.id.project_number);
        TextView hole_number = v.findViewById(R.id.hole_number);
        TextView date = v.findViewById(R.id.date);
        TestEntity testModel = getItem(position);
        if (testModel != null) {
            project_number.setText(testModel.projectNumber);
            hole_number.setText(testModel.holeNumber);
            date.setText(testModel.testDate);
        }
        return v;
    }

}

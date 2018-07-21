package www.jingkan.com.testDataDetails.crossTestDataDetails;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

/**
 * Created by Sampson on 2018/7/21.
 * LastCPT
 */
public class CrossTestDataListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<DataGroup> groupList;
    private List<List<DataItem>> childrenList;
    @LayoutRes
    private int groupLayout;
    @LayoutRes
    private int childrenLayout;

    public CrossTestDataListAdapter(Context context, List<DataGroup> groupList, List<List<DataItem>> childrenList, int groupLayout, int childrenLayout) {
        this.context = context;
        this.groupList = groupList;
        this.childrenList = childrenList;
        this.groupLayout = groupLayout;
        this.childrenLayout = childrenLayout;
    }

    @Override
    public int getGroupCount() {
        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (childrenList == null) return 0;
        List<DataItem> dataItems = childrenList.get(i);
        return dataItems == null ? 0 : dataItems.size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList == null ? null : groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        if (childrenList == null) return null;
        List<DataItem> dataItems = childrenList.get(i);
        return dataItems == null ? 0 : dataItems.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View mView;
        if (view == null) {
            mView = LayoutInflater.from(context).inflate(groupLayout, viewGroup, false);
        } else {
            mView = view;
        }
        DataGroup dataGroup = (DataGroup) getGroup(i);
        return mView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View mView;
        if (view == null) {
            mView = LayoutInflater.from(context).inflate(childrenLayout, viewGroup, false);
        } else {
            mView = view;
        }
        DataItem dataItem = (DataItem) getChild(i, i1);
        return mView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

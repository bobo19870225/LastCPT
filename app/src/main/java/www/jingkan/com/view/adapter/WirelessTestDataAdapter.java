package www.jingkan.com.view.adapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Sampson on 2019/2/18.
 * LastCPT 2
 */
public class WirelessTestDataAdapter extends MyBaseAdapter {
    public WirelessTestDataAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List list) {
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}

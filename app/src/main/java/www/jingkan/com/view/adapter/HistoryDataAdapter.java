package www.jingkan.com.view.adapter;

import com.jinkan.www.cpttest.databinding.ItemHistoryDataBinding;

import java.util.List;

/**
 * Created by Sampson on 2018/12/25.
 * CPTTest
 */
public class HistoryDataAdapter extends MyBaseAdapter<ItemHistoryDataBinding, ItemHistoryData> {
    public HistoryDataAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemHistoryData> list) {
        return mList.get(oldItemPosition).getId() == list.get(newItemPosition).getId()
                && mList.get(oldItemPosition).getProjectNumber().equals(list.get(newItemPosition).getProjectNumber())
                && mList.get(oldItemPosition).getHoleNumber().equals(list.get(newItemPosition).getHoleNumber())
                && mList.get(oldItemPosition).getTestDate().equals(list.get(newItemPosition).getTestDate());
    }

}

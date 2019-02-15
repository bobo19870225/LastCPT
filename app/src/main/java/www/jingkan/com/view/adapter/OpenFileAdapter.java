package www.jingkan.com.view.adapter;

import com.jinkan.www.cpttest.databinding.ItemFileBinding;

import java.util.List;

/**
 * Created by Sampson on 2019/1/4.
 * CPTTest
 */
public class OpenFileAdapter extends MyBaseAdapter<ItemFileBinding, ItemFile> {
    public OpenFileAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemFile> list) {
        return mList.get(oldItemPosition).getId().equals(list.get(newItemPosition).getId())
                && mList.get(oldItemPosition).getFileName().equals(list.get(newItemPosition).getFileName());
    }


}

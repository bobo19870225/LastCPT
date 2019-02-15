package www.jingkan.com.view.adapter;

import www.jingkan.com.databinding.ItemMarkFileBinding;

import java.util.List;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MarkFileAdapter extends MyBaseAdapter<ItemMarkFileBinding, ItemMarkupFile> {
    public MarkFileAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemMarkupFile> list) {
        return false;
    }
}

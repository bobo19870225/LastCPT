package www.jingkan.com.view.adapter;

import java.util.List;

import www.jingkan.com.databinding.ItemMarkFileBinding;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MarkFileDetailAdapter extends MyBaseAdapter<ItemMarkFileBinding, ItemMarkupFile> {
    public MarkFileDetailAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemMarkupFile> list) {
        return false;
    }
}

package www.jingkan.com.view.adapter;

import java.util.List;

import www.jingkan.com.databinding.ItemMarkFileDetailBinding;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MarkFileDetailAdapter extends MyBaseAdapter<ItemMarkFileDetailBinding, ItemMarkFileDetails> {
    public MarkFileDetailAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemMarkFileDetails> list) {
        return false;
    }


}

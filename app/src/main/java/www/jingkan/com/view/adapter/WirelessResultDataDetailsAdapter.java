package www.jingkan.com.view.adapter;

import java.util.List;

import www.jingkan.com.databinding.ItemWirelessResultDataDetailsBinding;

/**
 * Created by Sampson on 2019/2/18.
 * LastCPT 2
 */
public class WirelessResultDataDetailsAdapter extends MyBaseAdapter<ItemWirelessResultDataDetailsBinding, ItemWirelessResultDataDetails> {
    public WirelessResultDataDetailsAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemWirelessResultDataDetails> list) {
        return false;
    }


}

package www.jingkan.com.view.adapter;

import java.util.List;

import www.jingkan.com.databinding.ItemWirelessTestResultDataBinding;

/**
 * Created by Sampson on 2019/2/18.
 * LastCPT 2
 */
public class WirelessTestDataAdapter extends MyBaseAdapter<ItemWirelessTestResultDataBinding, ItemWirelessTestResultData> {
    public WirelessTestDataAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemWirelessTestResultData> list) {
        return false;
    }


}

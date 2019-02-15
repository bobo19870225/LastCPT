package www.jingkan.com.view.adapter;

import www.jingkan.com.databinding.ItemWirelessProbeBinding;

import java.util.List;

/**
 * Created by Sampson on 2019/1/2.
 * CPTTest
 */
public class WirelessProbeAdapter extends MyBaseAdapter<ItemWirelessProbeBinding, ItemWirelessProbe> {
    public WirelessProbeAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemWirelessProbe> list) {
        return mList.get(oldItemPosition).getId().equals(list.get(newItemPosition).getId())
                && mList.get(oldItemPosition).getProbeNumber().equals(list.get(newItemPosition).getProbeNumber());
    }


}

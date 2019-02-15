package www.jingkan.com.view.adapter;

import com.jinkan.www.cpttest.databinding.ItemOrdinaryProbeBinding;

import java.util.List;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class OrdinaryProbeAdapter extends MyBaseAdapter<ItemOrdinaryProbeBinding, ItemOrdinaryProbe> {
    public OrdinaryProbeAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemOrdinaryProbe> list) {
        return mList.get(oldItemPosition).getId().equals(list.get(newItemPosition).getId())
                && mList.get(oldItemPosition).getProbeNumber().equals(list.get(newItemPosition).getProbeNumber());
    }


}

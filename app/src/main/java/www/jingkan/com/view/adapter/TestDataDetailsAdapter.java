package www.jingkan.com.view.adapter;

import www.jingkan.com.databinding.ItemTestDataDetailsBinding;

import java.util.List;

/**
 * Created by Sampson on 2018/12/26.
 * CPTTest
 */
public class TestDataDetailsAdapter extends MyBaseAdapter<ItemTestDataDetailsBinding, ItemTestDataDetails> {
    public TestDataDetailsAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemTestDataDetails> list) {
        return mList.get(oldItemPosition).getId().equals(list.get(newItemPosition).getId())
                && mList.get(oldItemPosition).getDeep().equals(list.get(newItemPosition).getDeep())
                && mList.get(oldItemPosition).getQc().equals(list.get(newItemPosition).getQc())
                && mList.get(oldItemPosition).getFs().equals(list.get(newItemPosition).getFs())
                && mList.get(oldItemPosition).getFa().equals(list.get(newItemPosition).getFa());
    }


}

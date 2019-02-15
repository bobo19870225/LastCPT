package www.jingkan.com.view.adapter;

import www.jingkan.com.databinding.ItemMyMsgBinding;

import java.util.List;

/**
 * Created by Sampson on 2018/12/27.
 * CPTTest
 */
public class MyMsgAdapter extends MyBaseAdapter<ItemMyMsgBinding, ItemMyMsg> {
    public MyMsgAdapter(int layoutId, Object clickCallback) {
        super(layoutId, clickCallback);
    }

    @Override
    protected boolean ifContentsTheSame(int oldItemPosition, int newItemPosition, List<? extends ItemMyMsg> list) {
        return mList.get(oldItemPosition).getId().equals(list.get(newItemPosition).getId())
                && mList.get(newItemPosition).getMsgTime().equals(list.get(newItemPosition).getMsgTime());
    }


}

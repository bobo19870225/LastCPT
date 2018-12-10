package www.jingkan.com.common.historyData;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

/**
 * Created by Sampson on 2018/12/10.
 * LastCPT 2
 */
public class HistoryDataItemViewModel extends BaseObservable {
    public final ObservableField<String> obsProjectNumber = new ObservableField<>("");
    public final ObservableField<String> obsHoleNumber = new ObservableField<>("");
    public final ObservableField<String> obsTestDate = new ObservableField<>("");
}

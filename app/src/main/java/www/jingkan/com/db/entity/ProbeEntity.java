package www.jingkan.com.db.entity;

import com.jinkan.www.cpttest.view.adapter.ItemOrdinaryProbe;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "probe")
public class ProbeEntity implements ItemOrdinaryProbe {
    @PrimaryKey
    @NonNull
    public String probeID = "";
    public String sn;
    public String number;
    public String type;
    public String qc_area = "0";
    public String fs_area = "0";
    public float qc_coefficient = 1;
    public float fs_coefficient = 1;
    public int qc_limit = 36;
    public int fs_limit = 360;

    @Override
    public String getProbeNumber() {
        return number;
    }

    @Override
    public Object getId() {
        return probeID;
    }
}

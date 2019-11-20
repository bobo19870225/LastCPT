package www.jingkan.com.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import www.jingkan.com.view.adapter.ItemWirelessProbe;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = "wirelessProbe")
public class WirelessProbeEntity implements ItemWirelessProbe {
    @PrimaryKey
    @NonNull
    public String probeID = "";
    public String sn;
    public String number;
    public String type;
    public String qc_area;
    public String fs_area;
    public float qc_coefficient;
    public float fs_coefficient;
    public int qc_limit;
    public int fs_limit;

    @Override
    public String getProbeNumber() {
        return number;
    }

    @Override
    public Object getId() {
        return probeID;
    }
}

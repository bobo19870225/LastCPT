package www.jingkan.com.localData.wirelessResultData;

import androidx.room.Entity;

/**
 * Created by Sampson on 2018/12/11.
 * LastCPT 2
 */
@Entity(tableName = WirelessResultDataConstant.TABLE_NAME)
public class WirelessResultDataEntity {
    //strProjectNumber + "_" + strHoleNumber.
    public String testDataID;
    public String probeNumber;
    public float deep;
    public float qc;
    public float fs;
    public float fa;
}

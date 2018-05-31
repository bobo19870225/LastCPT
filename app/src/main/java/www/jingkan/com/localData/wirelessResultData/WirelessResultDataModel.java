/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.wirelessResultData;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = WirelessResultDataConstant.TABLE_NAME)
public class WirelessResultDataModel extends Model {
    @Column(name = WirelessResultDataConstant.COLUMN_TEST_DATA_ID)
//strProjectNumber + "_" + strHoleNumber.
    public String testDataID;
    @Column(name = WirelessResultDataConstant.COLUMN_PROBE_NUMBER)
    public String probeNumber;
    @Column(name = WirelessResultDataConstant.COLUMN_DEEP)
    public float deep;
    @Column(name = WirelessResultDataConstant.COLUMN_QC)
    public float qc;
    @Column(name = WirelessResultDataConstant.COLUMN_FS)
    public float fs;
    @Column(name = WirelessResultDataConstant.COLUMN_FA)
    public float fa;
}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.wirelessTestData;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = WirelessTestDataConstant.TABLE_NAME)
public class WirelessTestDataModel extends Model {
    //strProjectNumber + "_" + strHoleNumber.
    @Column(name = WirelessTestDataConstant.COLUMN_TEST_DATA_ID)
    public String testDataID;
    @Column(name = WirelessTestDataConstant.COLUMN_PROBE_NUMBER)
    public String probeNumber;
    @Column(name = WirelessTestDataConstant.COLUMN_DEEP)
    public float deep;
    @Column(name = WirelessTestDataConstant.COLUMN_RTC)
    public long rtc;
}

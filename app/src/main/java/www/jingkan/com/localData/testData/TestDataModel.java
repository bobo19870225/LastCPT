/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.testData;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = TestDataConstant.TABLE_NAME)
public class TestDataModel extends Model {
    @Column(name = TestDataConstant.COLUMN_TEST_DATA_ID)//strProjectNumber + "_" + strHoleNumber.
    public String testDataID;
    @Column(name = TestDataConstant.COLUMN_PROBE_ID)
    public String probeID;
    @Column(name = TestDataConstant.COLUMN_DEEP)
    public float deep;
    @Column(name = TestDataConstant.COLUMN_QC)
    public float qc;
    @Column(name = TestDataConstant.COLUMN_FS)
    public float fs;
    @Column(name = TestDataConstant.COLUMN_FA)
    public float fa;
}

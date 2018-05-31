/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.memoryData;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Immutable model class for a Task.
 */
@Table(name = MemoryDataConstant.TABLE_NAME)
public class MemoryDataModel extends Model {

    @Column(name = MemoryDataConstant.COLUMN_NAME_PROBE_ID)
    public String probeID;
    @Column(name = MemoryDataConstant.COLUMN_NAME_PROBE_NO)
    public String probeNo;
    @Column(name = MemoryDataConstant.COLUMN_NAME_TYPE)
    public String type;
    @Column(name = MemoryDataConstant.COLUMN_NAME_FORCE_TYPE)
    public String forceType;
    @Column(name = MemoryDataConstant.COLUMN_NAME_AD_VALUE)
    public int ADValue;

}

/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.calibrationVerification;

/**
 * The contract used for the db to save the tasks locally.
 */

public final class CalibrationVerificationConstant {
    public static final String TABLE_NAME = "calibrationVerification";//标定验证表
    public static final String COLUMN_NAME_PROBE_NO = "probeNo";//探头编号
    public static final String COLUMN_NAME_TYPE = "type";//锥头？侧壁？
    public static final String COLUMN_NAME_STANDARD_VALUE = "standardValue";//标准值
    public static final String COLUMN_NAME_FORCE_TYPE = "forceType";//加荷？卸荷？
    public static final String COLUMN_NAME_LOAD_VALUE = "loadValue";//荷载
}


///*
// * Copyright (c) 2018. 代码著作权归卢声波所有。
// */
//
//package www.jingkan.com.localData.wirelessProbe;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.activeandroid.Model;
//import com.activeandroid.annotation.Column;
//import com.activeandroid.annotation.Table;
//
//@Table(name = WirelessProbeConstant.TABLE_NAME)
//public class WirelessProbeModel extends Model implements Parcelable {
//    @Column(name = WirelessProbeConstant.COLUMN_PROBE_ID, unique = true)
//    public String probeID;
//    @Column(name = WirelessProbeConstant.COLUMN_SN)
//    public String sn;
//    @Column(name = WirelessProbeConstant.COLUMN_NUMBER)
//    public String number;
//    @Column(name = WirelessProbeConstant.COLUMN_TYPE)
//    public String type;
//    @Column(name = WirelessProbeConstant.COLUMN_QC_AREA)
//    public String qc_area;
//    @Column(name = WirelessProbeConstant.COLUMN_FS_AREA)
//    public String fs_area;
//    @Column(name = WirelessProbeConstant.COLUMN_QC_COEFFICIENT)
//    public float qc_coefficient;
//    @Column(name = WirelessProbeConstant.COLUMN_FS_COEFFICIENT)
//    public float fs_coefficient;
//    @Column(name = WirelessProbeConstant.COLUMN_QC_LIMIT)
//    public int qc_limit;
//    @Column(name = WirelessProbeConstant.COLUMN_FS_LIMIT)
//    public int fs_limit;
//
//    public WirelessProbeModel() {
//        super();
//    }
//
//    protected WirelessProbeModel(Parcel in) {
//        probeID = in.readString();
//        sn = in.readString();
//        number = in.readString();
//        type = in.readString();
//        qc_area = in.readString();
//        fs_area = in.readString();
//        qc_coefficient = in.readFloat();
//        fs_coefficient = in.readFloat();
//        qc_limit = in.readInt();
//        fs_limit = in.readInt();
//    }
//
//    public static final Creator<WirelessProbeModel> CREATOR = new Creator<WirelessProbeModel>() {
//        @Override
//        public WirelessProbeModel createFromParcel(Parcel in) {
//            return new WirelessProbeModel(in);
//        }
//
//        @Override
//        public WirelessProbeModel[] newArray(int size) {
//            return new WirelessProbeModel[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(probeID);
//        dest.writeString(sn);
//        dest.writeString(number);
//        dest.writeString(type);
//        dest.writeString(qc_area);
//        dest.writeString(fs_area);
//        dest.writeDouble(qc_coefficient);
//        dest.writeDouble(fs_coefficient);
//        dest.writeInt(qc_limit);
//        dest.writeInt(fs_limit);
//    }
//}

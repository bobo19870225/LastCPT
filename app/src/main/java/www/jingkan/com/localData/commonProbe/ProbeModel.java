/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.localData.commonProbe;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = ProbeConstant.TABLE_NAME)
public class ProbeModel extends Model implements Parcelable {
    @Column(name = ProbeConstant.COLUMN_PROBE_ID, unique = true)
    public String probeID;
    @Column(name = ProbeConstant.COLUMN_SN)
    public String sn;
    @Column(name = ProbeConstant.COLUMN_NUMBER)
    public String number;
    @Column(name = ProbeConstant.COLUMN_TYPE)
    public String type;
    @Column(name = ProbeConstant.COLUMN_QC_AREA)
    public String qc_area;
    @Column(name = ProbeConstant.COLUMN_FS_AREA)
    public String fs_area;
    @Column(name = ProbeConstant.COLUMN_QC_COEFFICIENT)
    public float qc_coefficient;
    @Column(name = ProbeConstant.COLUMN_FS_COEFFICIENT)
    public float fs_coefficient;
    @Column(name = ProbeConstant.COLUMN_QC_LIMIT)
    public int qc_limit;
    @Column(name = ProbeConstant.COLUMN_FS_LIMIT)
    public int fs_limit;

    public ProbeModel() {
        super();
    }

    protected ProbeModel(Parcel in) {
        probeID = in.readString();
        sn = in.readString();
        number = in.readString();
        type = in.readString();
        qc_area = in.readString();
        fs_area = in.readString();
        qc_coefficient = in.readFloat();
        fs_coefficient = in.readFloat();
        qc_limit = in.readInt();
        fs_limit = in.readInt();
    }

    public static final Creator<ProbeModel> CREATOR = new Creator<ProbeModel>() {
        @Override
        public ProbeModel createFromParcel(Parcel in) {
            return new ProbeModel(in);
        }

        @Override
        public ProbeModel[] newArray(int size) {
            return new ProbeModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(probeID);
        dest.writeString(sn);
        dest.writeString(number);
        dest.writeString(type);
        dest.writeString(qc_area);
        dest.writeString(fs_area);
        dest.writeFloat(qc_coefficient);
        dest.writeFloat(fs_coefficient);
        dest.writeInt(qc_limit);
        dest.writeInt(fs_limit);
    }
}

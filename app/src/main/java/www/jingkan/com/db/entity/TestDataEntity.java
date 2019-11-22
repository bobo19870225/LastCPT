package www.jingkan.com.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import www.jingkan.com.util.StringUtil;
import www.jingkan.com.view.adapter.ItemTestDataDetails;

/**
 * Created by Sampson on 2018/12/18.
 * CPTTest
 */
@Entity(tableName = "testData", primaryKeys = {"testDataID", "deep"})
public class TestDataEntity implements ItemTestDataDetails {
    @NonNull
    public String testDataID = "";
    public String probeID;
    public float deep;
    public float qc;
    public float fs;
    public float fa;

    @Override
    public String getDeep() {
        return StringUtil.format(deep, 1);
    }

    @Override
    public String getQc() {
        return StringUtil.format(qc, 2);
    }

    @Override
    public String getFs() {
        return StringUtil.format(fs, 2);
    }

    @Override
    public String getFa() {
        return StringUtil.format(fa, 1);
    }

    @Override
    public Object getId() {
        return testDataID;
    }
}

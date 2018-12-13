///*
// * Copyright (c) 2018. 代码著作权归卢声波所有。
// */
//
//package www.jingkan.com.localData.wirelessTest;
//
//import com.activeandroid.Model;
//import com.activeandroid.annotation.Column;
//import com.activeandroid.annotation.Table;
//
//import www.jingkan.com.localData.test.TestEntity;
//
//@Table(name = WirelessTestConstant.TABLE_NAME)
//public class WirelessTestEntity extends Model {
//    @Column(name = WirelessTestConstant.COLUMN_TEST_ID, unique = true)
//    public String testID;//projectNumber_holeNumber
//    @Column(name = WirelessTestConstant.COLUMN_TEST_DATE)
//    public String testDate;
//    @Column(name = WirelessTestConstant.COLUMN_PROJECT_NUMBER)
//    public String projectNumber;
//    @Column(name = WirelessTestConstant.COLUMN_HOLE_NUMBER)
//    public String holeNumber;
//    @Column(name = WirelessTestConstant.COLUMN_HOLE_HIGH)
//    public float holeHigh;
//    @Column(name = WirelessTestConstant.COLUMN_WATER_LEVEL)
//    public float waterLevel;
//    @Column(name = WirelessTestConstant.COLUMN_LOCATION)
//    public String location;
//    @Column(name = WirelessTestConstant.COLUMN_TESTER)
//    public String tester;
//    @Column(name = WirelessTestConstant.COLUMN_TEST_TYPE)
//    public String testType;
//    @Column(name = WirelessTestConstant.COLUMN_TEST_DATA_ID)
//    public String testDataID;//projectNumber_holeNumber
//
//    public TestEntity castToTestEntity() {
//        TestEntity testModel = new TestEntity();
//        testModel.testID = testID;
//        testModel.testDate = testDate;
//        testModel.projectNumber = projectNumber;
//        testModel.holeNumber = holeNumber;
//        testModel.holeHigh = holeHigh;
//        testModel.waterLevel = waterLevel;
//        testModel.location = location;
//        testModel.tester = tester;
//        testModel.testType = testType;
//        testModel.testDataID = testDataID;
//        return testModel;
//    }
//
//}

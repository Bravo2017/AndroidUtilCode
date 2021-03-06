package com.blankj.subutil.util;

import org.junit.Assert;
import org.junit.Test;

import static java.lang.Math.PI;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/03/22
 *     desc  : CoordinateConvertUtils 单元测试
 * </pre>
 */
public class CoordinateConvertUtilsTest {

    // 以下为各个坐标系的 天安门坐标
    private static final double[] locationWGS84 = new double[]{116.3912022800, 39.9075017400};
    private static final double[] locationGCJ02 = new double[]{116.3973900000, 39.9088600000};
    private static final double[] locationBD09  = new double[]{116.4038206839, 39.9152478931};

    // 以下为美国纽约坐标
    private static final double[] newyorkWGS84 = new double[]{-74.0059413000, 40.7127837000};

    @Test
    public void gcj2BD09() throws Exception {
        double[] BD09 = CoordinateConvertUtils.gcj02ToBd09(locationGCJ02[0], locationGCJ02[1]);
        double distance = distance(locationBD09[0], locationBD09[1], BD09[0], BD09[1]);
        System.out.println("distance: " + distance);
        Assert.assertTrue(distance < 10);
    }

    @Test
    public void bd092GCJ() {
        double[] GCJ02 = CoordinateConvertUtils.bd09ToGcj02(locationBD09[0], locationBD09[1]);
        double distance = distance(locationGCJ02[0], locationGCJ02[1], GCJ02[0], GCJ02[1]);
        System.out.println("distance: " + distance);
        Assert.assertTrue(distance < 10);
    }

    @Test
    public void bd092WGS() {
        double[] WGS84 = CoordinateConvertUtils.bd09ToWGS84(locationBD09[0], locationBD09[1]);
        double distance = distance(locationWGS84[0], locationWGS84[1], WGS84[0], WGS84[1]);
        System.out.println("distance: " + distance);
        Assert.assertTrue(distance < 10);
    }

    @Test
    public void wgs2BD09() {
        double[] BD09 = CoordinateConvertUtils.wgs84ToBd09(locationWGS84[0], locationWGS84[1]);
        double distance = distance(locationBD09[0], locationBD09[1], BD09[0], BD09[1]);
        System.out.println("distance: " + distance);
        Assert.assertTrue(distance < 10);
    }

    @Test
    public void wgs2GCJ() {
        double[] GCJ02 = CoordinateConvertUtils.wgs84ToGcj02(locationWGS84[0], locationWGS84[1]);
        double distance = distance(locationGCJ02[0], locationGCJ02[1], GCJ02[0], GCJ02[1]);
        System.out.println("distance: " + distance);
        Assert.assertTrue(distance < 10);
    }

    @Test
    public void gcj2WGS() {
        double[] WGS84 = CoordinateConvertUtils.gcj02ToWGS84(locationGCJ02[0], locationGCJ02[1]);
        double distance = distance(locationWGS84[0], locationWGS84[1], WGS84[0], WGS84[1]);
        System.out.println("distance: " + distance);
        Assert.assertTrue(distance < 10);
    }

    @Test
    public void gcj2WGSExactly() {
        double[] WGS84 = CoordinateConvertUtils.gcj02ToWGS84(locationGCJ02[0], locationGCJ02[1]);
        double distance = distance(locationWGS84[0], locationWGS84[1], WGS84[0], WGS84[1]);
        System.out.println("distance: " + distance);
        Assert.assertTrue(distance < 10);
    }

    @Test
    public void outOfChina() {
        Assert.assertFalse(CoordinateConvertUtils.outOfChina(locationWGS84[0], locationWGS84[1]));
        Assert.assertTrue(CoordinateConvertUtils.outOfChina(newyorkWGS84[0], newyorkWGS84[1]));
    }

    public static double distance(double lngA, double latA, double lngB, double latB) {
        int earthR = 6371000;
        double x = Math.cos(latA * PI / 180) * Math.cos(latB * PI / 180) * Math.cos((lngA - lngB) * PI / 180);
        double y = Math.sin(latA * PI / 180) * Math.sin(latB * PI / 180);
        double s = x + y;
        if (s > 1)
            s = 1;
        if (s < -1)
            s = -1;
        double alpha = Math.acos(s);
        return alpha * earthR;
    }
}
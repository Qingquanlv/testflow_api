package com.testflow.apitest.utilities;

import java.util.Date;

public class ConversionUtil {
    public static boolean DBValEquals(Object obj1, Object obj2) {

        if (obj1 instanceof Integer) {
            int value1 = ((Integer) obj1).intValue();
            int value2 = ((Integer) obj2).intValue();
            return value1 == value2;
        } else if (obj1 instanceof String) {
            String value1 = (String) obj1;
            String value2 = (String) obj2;
            return value1.equals(value2);
        } else if (obj1 instanceof Double) {
            double value1 = ((Double) obj1).doubleValue();
            double value2 = ((Double) obj2).doubleValue();
            return value1 == value2;
        } else if (obj1 instanceof Float) {
            float value1 = ((Float) obj1).floatValue();
            float value2 = ((Float) obj2).floatValue();
            return value1 == value2;
        } else if (obj1 instanceof Long) {
            long value1 = ((Long) obj1).longValue();
            long value2 = ((Long) obj2).longValue();
            return value1 == value2;
        } else if (obj1 instanceof Boolean) {
            boolean value1 = ((Boolean) obj1).booleanValue();
            boolean value2 = ((Boolean) obj2).booleanValue();
            return value1 == value2;
        } else if (obj1 instanceof Date) {
            Date value1 = (Date) obj1;
            Date value2 = (Date) obj1;
            return value1.toString().equals(value2.toString());
        } else {
            return false;
        }
    }
}

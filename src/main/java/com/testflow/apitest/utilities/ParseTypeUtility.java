package com.testflow.apitest.utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTypeUtility {
    public static Object parseBaseFieldValue(Object value, String valueType)
    {
        try {
            switch (valueType) {
                case "String": {
                    System.out.println("Parse value type, object to Date");
                    return value.toString();
                }
                case "Date": {
                    System.out.println("Parse value type, object to Date");
                    return (Date) value;
                }
                case "Integer": case "int": {
                    System.out.println("Parse value type, object to Date");
                    return (Integer) value;
                }
                case "Long": {
                    System.out.println("Parse value type, object to Date");
                    return (Long) value;
                }
                case "Double": {
                    System.out.println("Parse value type, object to Date");
                    return (Double) value;
                }
                case "Boolean": {
                    System.out.println("Parse value type, object to Date");
                    return (Boolean) value;
                }
                case "Calendar": {
                    System.out.println("Parse value type, object to Date");
                    return parseStrToCalender(value.toString());
                }
                default: {
                    return value;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Convert data value failed" + e);
            return null;
        }
    }

    public static Object parseBaseFieldValue(String value, String valueType)
    {
        try {
            switch (valueType) {
                case "Date": {
                    return parseStrToDate(value);
                }
                case "Integer": case "int": {
                    return Integer.parseInt(value);
                }
                case "Long": {
                    return Long.parseLong(value);
                }
                case "Double": {
                    return Double.parseDouble(value);
                }
                case "Boolean": {
                    return Boolean.parseBoolean(value);
                }
                case "Calendar": {
                    return parseStrToCalender(value);
                }
                case "Timestamp": {
                    return parseStrToTimeStamp(value);
                }
                default: {
                    return value;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Convert data value failed" + e);
            return null;
        }
    }


    public static boolean isParamStr(String param)
    {
        String pattern = ".*\\{(.*)\\}.*";
        Pattern r = Pattern.compile(pattern);

        return Pattern.matches(pattern, param);
    }

    public static String parseParamStr(String param)
    {
        String pattern = ".*\\{(.*)\\}.*";
        String targetVal = null;

        Pattern r = Pattern.compile(pattern);
        if (Pattern.matches(pattern, param))
        {
            Matcher ma = r.matcher(param);
            boolean rs = ma.find();
            targetVal = ma.group(1);
        }
        return targetVal;
    }

    public static boolean isListStr(String param)
    {
        String pattern = ".*\\[(\\d+)\\].*";
        Pattern r = Pattern.compile(pattern);

        return Pattern.matches(pattern, param);
    }

    public static int parseListStr(String param)
    {
        String pattern = ".*\\[(\\d+)\\].*";
        int targetVal = 0;

        Pattern r = Pattern.compile(pattern);
        if (Pattern.matches(pattern, param))
        {
            Matcher ma = r.matcher(param);
            boolean rs = ma.find();
            targetVal = Integer.parseInt(ma.group(1));
        }
        return targetVal;
    }

    public static Calendar parseStrToCalender(String dateStr)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            calendar.setTime(date);
        }
        catch (Exception ex) {
            System.out.println("发生异常：" + ex);
        }
        return calendar;
    }

    public static Date parseStrToDate(String dateStr)
    {
        Date date = new Date();
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Timestamp parseStrToTimeStamp(String date_str)
    {
        Timestamp timeStamp = null;
        try {
            Date date = parseStrToDate(date_str);
            timeStamp = new Timestamp(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStamp;
    }
}
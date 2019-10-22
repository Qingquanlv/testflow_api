package com.testflow.apitest.common;

public class Constants {
    //Static value
    public static final String MAPKEY= "key";
    public static final String MAPVALUE = "value";
    public static final String COLON = ":";

    public static final String SERVICES_CLASS_PATH = "com.testflow.apitest.servicesaccess.ParseValueFileName";
    public static final String PARSE_VALUE_FILE_NAME = "ParseValueFileName.java";
    public static final String PARSE_VALUE_FILE_SOURCE = "package com.testflow.apitest.servicesaccess;" +
            "" +
            "import java.util.ArrayList;" +
            "import java.util.List;" +
            "import java.util.stream.Collectors;" +
            "import paramType1;" +
            "import paramType2;" +
            "\n" +
            "public class ParseValueFileName " +
            "{    \n" +
            "    method" +
            "}" ;
    public static final String PARAMTYPE1 = "paramType1";
    public static final String PARAMTYPE2 = "paramType2";
    public static final String METHOD = "method";


    public static final String QUETY_DB_FILE_NAME = "queryDB.xml";
    public static final String PQUETY_DB_FILE_SOURCE =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<!DOCTYPE mapper\n" +
                    "        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                    "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                    "<mapper namespace=\"key123\">\n" +
                    "    <select id=\"queryDataBase\" parameterType=\"java.util.Map\" resultType=\"type123\">\n" +
                    "        sql123\n" +
                    "    </select>\n" +
                    "</mapper>";
    public static final String SQL = "sql123";
    public static final String KEY = "key123";
    public static final String TYPE = "type123";


}

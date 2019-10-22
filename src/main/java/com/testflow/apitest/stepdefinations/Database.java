package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.common.Constants;
import com.testflow.apitest.servicesaccess.JavaStringCompiler;
import com.testflow.apitest.servicesaccess.ServiceAccess;
import com.testflow.apitest.utilities.FastJsonUtil;
import com.testflow.apitest.utilities.ParamUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Database {
    public final String resource = "mybatis-config.xml";

//    /**
//     * 根据一个参数查询DB
//     *
//     * @param queryKey mybatis key
//     * @param param mybatis parame
//     * @return 查询结果序列化Json
//     * @throws Exception
//     */
//    public String queryDataBase(String queryKey, String param) throws Exception{
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession session = sqlSessionFactory.openSession();
//        Object blog = session.selectList(queryKey, param);
//        String str = FastJsonUtil.toJson(blog);
//        return str;
//    }

    /**
     * 根据Map查询DB
     *
     * @param sql mybatis parame
     * @return 查询结果序列化Json
     * @throws Exception
     */
    public String queryDataBase(String sql) throws Exception{
        sql = ParamUtil.parseParam(sql);
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        List<LinkedHashMap<String, Object>> value = session.selectList("select", sql);
        session.close();
        String str = FastJsonUtil.toJson(value);
        return str;
    }

    /**
     * 根据Map查询DB
     *
     * @param queryKey mybatis key
     * @param param mybatis parame
     * @return 查询结果序列化Json
     * @throws Exception
     */
    public String queryDataBase(String queryKey, String param) throws Exception{
        param = ParamUtil.parseParam(param);
        Map<String, String> map = ParamUtil.parseMapParam(param);
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        Object blog = session.selectList(queryKey, map);
        session.close();
        String str = FastJsonUtil.toJson(blog);
        return str;
    }
}

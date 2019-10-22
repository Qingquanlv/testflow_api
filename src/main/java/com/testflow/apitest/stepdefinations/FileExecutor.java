package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.TestFlowManager;
import com.testflow.apitest.utilities.Loader;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.junit.jupiter.params.provider.Arguments;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.stream.Stream;

public class FileExecutor {
    private final String PACKAGE = "package";
    private final String PACKAGELOADER = "packageloader";
    private final String DATALOADER = "dataloader";
    private final String REQUEST = "request";
    private final String BATCHREQUEST = "batchrequest";
    private final String ID = "id";
    private final String METHOD = "method";
    private final String CONTENTTYPE = "contenttype";
    private final String HEADERS = "headers";
    private final String HEADER = "header";
    private final String BODY = "body";
    private final String URL = "url";
    private final String KEY = "key";
    private final String VALUE = "value";
    private final String PARSE = "parse";
    private final String TYPE = "type";
    private final String REFLECT = "reflect";
    private final String PARAME = "parame";
    private final String CLAZZ = "class";
    private final String CLAZZLODER = "classloader";
    private final String SOURCE = "source";
    private final String RETURN = "return";
    private final String NAME = "name";
    private final String DATABASE = "database";
    private final String SQL = "sql";
    private final String VERIFY = "verify";
    private final String ENTITYS = "entitys";
    private final String ENTITY = "entity";
    private final String PKKEYS = "pkkeys";
    private final String PKKEY = "pkkey";
    private final String NOCOMPAREKEYS = "nocomparekeys";
    private final String NOCOMPAREKEY = "nocomparekey";
    private final String XPATH = "xpath";

    /**
     * loadFile中given配置
     *
     * @param filePath 读取XML文件的路径
     * @return
     */
    public List<Map<String, String>> loadFile(String filePath) throws Exception {
        Document document = openFile(filePath);

        List list = document.selectNodes("/feature/given");
        return loadFileParame(list);
    }

    /**
     * xml主执行类（Map类型）
     *
     * @param map
     * @param filePath
     * @throws Exception
     */
    public void executeFile(Map<String, String> map, String filePath) throws Exception {
        loadParame(map);
        Document document = openFile(filePath);
        List list = document.selectNodes("/feature/given");
        executeStepNoParame(list, document);
    }

    /**
     * xml主执行类
     *
     * @param filePath 读取XML文件的路径
     * @return
     */
    public void executeFile(String filePath) throws Exception {
        Document document = openFile(filePath);

        List list = document.selectNodes("/feature/given");
        loadConfig(list);
        executeStep(list, document);
    }

    /**
     * 加载对应文件夹中的class和package
     *
     * @param list
     * @throws Exception
     */
    public void loadConfig(List list) throws Exception {
        for (Iterator it = list.iterator(); it.hasNext();) {
            Element element = (Element) it.next();
            List stepList = element.elements();
            for (Iterator st = stepList.iterator(); st.hasNext();) {
                Element step = (Element) st.next();
                if (step.getName().equals(CLAZZLODER)) {
                    List clazzList = step.elements(CLAZZ);
                    for (Iterator cl = clazzList.iterator(); cl.hasNext(); ) {
                        Element clElement = (Element) cl.next();
                        String clazzPath = null == clElement ? "" : clElement.getText();
                        Loader.clazzLoader(clazzPath);
                    }
                }
                if (step.getName().equals(PACKAGELOADER)) {
                    List clazzList = step.elements(PACKAGE);
                    for (Iterator pa = clazzList.iterator(); pa.hasNext(); ) {
                        Element paElement = (Element) pa.next();
                        String packagePath = null == paElement ? "" : paElement.getText();
                        Loader.packageLoader(packagePath);
                    }
                }
            }
        }
    }

    /**
     * 加载对应文件夹中的class和package
     *
     * @param list
     * @throws Exception
     */
    public List<Map<String, String>> loadFileParame(List list) throws Exception {
        List<Map<String, String>> listMap = new ArrayList<>();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Element element = (Element) it.next();
            List stepList = element.elements();
            for (Iterator st = stepList.iterator(); st.hasNext(); ) {
                Element step = (Element) st.next();
                if (step.getName().equals(DATALOADER)) {

                    List<Element> parameList = step.elements(PARAME);
                    for (Element parame : parameList) {
                        Map<String, String> map = new HashMap<>();
                        List attributes = parame.attributes();
                        for (Iterator at = attributes.iterator(); at.hasNext(); ) {
                            Attribute attribute = (Attribute) at.next();
                            map.put(attribute.getName(), attribute.getValue());
                        }
                        listMap.add(map);
                    }

                }

            }
        }
        return listMap;
    }

    /**
     * 执行xml文件配置的step（不加载参数）
     *
     * @param list
     * @param document
     * @throws Exception
     */
    public void executeStepNoParame(List list, Document document) throws Exception {
        for (Iterator it = list.iterator(); it.hasNext();) {
            Element element = (Element) it.next();
            List stepList = element.elements();
            for (Iterator st = stepList.iterator(); st.hasNext();) {
                Element step = (Element) st.next();
                if (step.getName().equals(DATALOADER)) {
                    List givenlist = document.selectNodes("/feature/when");
                    executeWhen(givenlist);
                    List thenlist = document.selectNodes("/feature/then");
                    executeThen(thenlist);
                }
            }
        }
    }

    /**
     * 执行xml文件配置的step
     *
     * @param list
     * @param document
     * @throws Exception
     */
    public void executeStep(List list, Document document) throws Exception {
        for (Iterator it = list.iterator(); it.hasNext();) {
            Element element = (Element) it.next();
            List stepList = element.elements();
            for (Iterator st = stepList.iterator(); st.hasNext();) {
                Element step = (Element) st.next();
                if (step.getName().equals(DATALOADER)) {
                    List<Element> parameList = step.elements(PARAME);
                    for (Element parame : parameList) {
                        List attributes = parame.attributes();
                        for (Iterator at = attributes.iterator(); at.hasNext(); ) {
                            Attribute attribute = (Attribute) at.next();
                            TestFlowManager.runner().addBuffer(attribute.getName(), attribute.getValue());
                        }
                        List givenlist = document.selectNodes("/feature/when");
                        executeWhen(givenlist);
                        List thenlist = document.selectNodes("/feature/then");
                        executeThen(thenlist);
                    }
                }
            }
        }
    }

    /**
     * 执行when中的step
     *
     * @param list
     * @throws Exception
     */
    public void executeWhen(List list) throws Exception {
        for (Iterator it = list.iterator(); it.hasNext();) {
            Element element = (Element) it.next();
            List<Element> stepList = element.elements();
            for (Element e : stepList) {
                if (e.getName().equals(REQUEST)) {
                    setRequest(e);
                }
                else if (e.getName().equals(BATCHREQUEST)) {
                    setBatchRequest(e);
                }
                else if (e.getName().equals(PARSE)) {
                    setParse(e);
                }
                else if (e.getName().equals(DATABASE)) {
                    setDatabase(e);
                }
            }
        }
    }

    /**
     * 执行Then中的step
     *
     * @param list
     * @throws Exception
     */
    public void executeThen(List list) throws Exception {
        for (Iterator it = list.iterator(); it.hasNext();) {
            Element element = (Element) it.next();
            List<Element> stepList = element.elements();
            for (Element e : stepList) {
                if (e.getName().equals(VERIFY)) {
                    setVerify(e);
                }
            }
        }


    }

    /**
     * 执行request类型step
     *
     * @param el
     * @throws Exception
     */
    public void setRequest(Element el) throws Exception  {
        Request request = new Request();

        String parmeType = null == el.attribute(ID) ? "" : el.attribute(ID).getValue();
        String method =  null == el.attribute(METHOD) ? "" : el.attribute(METHOD).getValue();
        String contenttype =  null == el.attribute(CONTENTTYPE) ? "" : el.attribute(CONTENTTYPE).getValue();
        Element headers =  el.element(HEADERS);
        if (null != headers) {
            List<Element> headerList = headers.elements(HEADER);
            for (Element headerElement : headerList) {
                String headerKey = null == headerElement.attribute(KEY) ? "" : headerElement.attribute(KEY).getValue();
                String headerValue = null == headerElement.attribute(VALUE) ? "" : headerElement.attribute(VALUE).getValue();
                request.setHeaders(headerKey, headerValue);
            }
        }
        Element url =  el.element(URL);
        String urlStr =  null == url ? "" : url.getText();
        Element body =  el.element(BODY);
        String bodyStr =  null == body ? "" : body.getText();

        if ("json".equals(contenttype) && "post".equals(method))  {
            TestFlowManager.runner().sendPostRequest(bodyStr, urlStr, parmeType);
        }
        else if ("json".equals(contenttype) && "put".equals(method))  {
            TestFlowManager.runner().sendPutRequest(bodyStr, urlStr, parmeType);
        }
        else if ("json".equals(contenttype) && "delete".equals(method))  {
            TestFlowManager.runner().sendDeleteRequest(bodyStr, urlStr, parmeType);
        }
        else if ("get".equals(method))  {
            TestFlowManager.runner().sendGetRequest(urlStr, parmeType);
        }
        else if ("head".equals(method))  {
            TestFlowManager.runner().sendHeadRequest(urlStr, parmeType);
        }
        else if ("options".equals(method))  {
            TestFlowManager.runner().sendOptionsRequest(urlStr, parmeType);
        }
        else if ("trace".equals(method))  {
            TestFlowManager.runner().sendTraceRequest(urlStr, parmeType);
        }
        else if ("xml".equals(contenttype) && "post".equals(method))  {
            TestFlowManager.runner().sendPostRequestXML(bodyStr, urlStr, parmeType);
        }
        else if ("xml".equals(contenttype) && "put".equals(method))  {
            TestFlowManager.runner().sendPutRequestXML(bodyStr, urlStr, parmeType);
        }
        else {
            TestFlowManager.runner().sendPostRequest(bodyStr, urlStr, parmeType);
        }
    }

    /**
     * 执行Batchrequest类型step
     *
     * @param el
     * @throws Exception
     */
    public void setBatchRequest(Element el) throws Exception  {
        Request request = new Request();
        String responce = "";
        Buffer buffer = new Buffer();

        String parmeType = null == el.attribute(ID) ? "" : el.attribute(ID).getValue();
        String method =  null == el.attribute(METHOD) ? "" : el.attribute(METHOD).getValue();
        String contenttype =  null == el.attribute(CONTENTTYPE) ? "" : el.attribute(CONTENTTYPE).getValue();
        Element headers =  el.element(HEADERS);
        if (null != headers) {
            List<Element> headerList = headers.elements(HEADER);
            for (Element headerElement : headerList) {
                String headerKey = null == headerElement.attribute(KEY) ? "" : headerElement.attribute(KEY).getValue();
                String headerValue = null == headerElement.attribute(VALUE) ? "" : headerElement.attribute(VALUE).getValue();
                request.setHeaders(headerKey, headerValue);
            }
        }
        Element url =  el.element(URL);
        String urlStr =  null == url ? "" : url.getText();
        Element body =  el.element(BODY);
        String bodyStr =  null == body ? "" : body.getText();

        if ("json".equals(contenttype) && "post".equals(method))  {
            TestFlowManager.runner().sendBatchPostRequest(bodyStr, urlStr, parmeType);
        }
        if ("json".equals(contenttype) && "put".equals(method))  {
            TestFlowManager.runner().sendBatchPutRequest(bodyStr, urlStr, parmeType);
        }
        else if ("xml".equals(contenttype) && "post".equals(method))  {
            TestFlowManager.runner().sendBatchPostRequestXML(bodyStr, urlStr, parmeType);
        }
        else {
            TestFlowManager.runner().sendBatchPostRequest(bodyStr, urlStr, parmeType);
        }
        buffer.addBufferByKey(parmeType, responce);
    }

    /**
     * 执行Parse类型step
     *
     * @param el
     * @throws Exception
     */
    public void setParse(Element el) throws Exception  {
        String tgtId = el.attribute(ID).getValue();
        String method = el.attribute(TYPE).getValue();

        if ("reflect".equals(method)) {
            Element reflect =  el.element(REFLECT);
            String md = null == reflect.element(METHOD) ? "" : reflect.element(METHOD).getText();
            String clazz = null == reflect.element(CLAZZ) ? "" : reflect.element(CLAZZ).getText();
            List<Element> parames = reflect.elements(PARAME);
            if (1 == parames.size()) {
                String param = parames.get(0).attribute(VALUE).getValue();
                String paramType = parames.get(0).attribute(TYPE).getValue();
                if ("string".equals(paramType)) {
                    TestFlowManager.runner().reflectParse(clazz, md, param, tgtId);
                }
                else {
                    TestFlowManager.runner().reflectParse(clazz, md, param, paramType, tgtId);
                }
            } else if (2 == parames.size()) {
                String param1 = parames.get(0).attribute(VALUE).getValue();
                String paramType1 = parames.get(0).attribute(TYPE).getValue();
                String param2 = parames.get(1).attribute(VALUE).getValue();
                String paramType2 = parames.get(1).attribute(TYPE).getValue();
                TestFlowManager.runner().reflectParse(clazz, md, param1, paramType1, param2, paramType2, tgtId);
            } else if (3 == parames.size()) {
                String param1 = parames.get(0).attribute(VALUE).getValue();
                String paramType1 = parames.get(0).attribute(TYPE).getValue();
                String param2 = parames.get(1).attribute(VALUE).getValue();
                String paramType2 = parames.get(1).attribute(TYPE).getValue();
                String param3 = parames.get(2).attribute(VALUE).getValue();
                String paramType3 = parames.get(2).attribute(TYPE).getValue();
                TestFlowManager.runner().reflectParse(clazz, md, param1, paramType1, param2, paramType2, param3, paramType3, tgtId);
            } else if (4 == parames.size()) {
                String param1 = parames.get(0).attribute(VALUE).getValue();
                String paramType1 = parames.get(0).attribute(TYPE).getValue();
                String param2 = parames.get(1).attribute(VALUE).getValue();
                String paramType2 = parames.get(1).attribute(TYPE).getValue();
                String param3 = parames.get(2).attribute(VALUE).getValue();
                String paramType3 = parames.get(2).attribute(TYPE).getValue();
                String param4 = parames.get(3).attribute(VALUE).getValue();
                String paramType4 = parames.get(3).attribute(TYPE).getValue();
                TestFlowManager.runner().reflectParse(clazz, md, param1, paramType1, param2, paramType2, param3, paramType3, param4, paramType4, tgtId);
            }
        }
        else if ("source".equals(method)) {
            Element source =  el.element(SOURCE);
            Element md = source.element(METHOD);
            String mdSource = md == null ? "" : md.getText();
            String name = null == md.attribute(NAME) ? "" : md.attribute(NAME).getValue();
            String returnType = null == source.element(RETURN) ? "" : source.element(RETURN).attribute(TYPE).getValue();
            List<Element> parames = source.elements(PARAME);
            if (1 == parames.size()) {
                String param = parames.get(0).attribute(VALUE).getValue();
                String paramType = parames.get(0).attribute(TYPE).getValue();
                TestFlowManager.runner().sourceParse(mdSource, name, param, paramType, returnType, tgtId);
            } else if (2 == parames.size()) {
                String param1 = parames.get(0).attribute(VALUE).getValue();
                String paramType1 = parames.get(0).attribute(TYPE).getValue();
                String param2 = parames.get(1).attribute(VALUE).getValue();
                String paramType2 = parames.get(1).attribute(TYPE).getValue();
                TestFlowManager.runner().sourceParse(mdSource, name, param1, paramType1, param2, paramType2, returnType, tgtId);
            } else if (3 == parames.size()) {
                String param1 = parames.get(0).attribute(VALUE).getValue();
                String paramType1 = parames.get(0).attribute(TYPE).getValue();
                String param2 = parames.get(1).attribute(VALUE).getValue();
                String paramType2 = parames.get(1).attribute(TYPE).getValue();
                String param3 = parames.get(2).attribute(VALUE).getValue();
                String paramType3 = parames.get(2).attribute(TYPE).getValue();
                TestFlowManager.runner().sourceParse(mdSource, name, param1, paramType1, param2, paramType2, param3, paramType3, returnType, tgtId);
            } else if (4 == parames.size()) {
                String param1 = parames.get(0).attribute(VALUE).getValue();
                String paramType1 = parames.get(0).attribute(TYPE).getValue();
                String param2 = parames.get(1).attribute(VALUE).getValue();
                String paramType2 = parames.get(1).attribute(TYPE).getValue();
                String param3 = parames.get(2).attribute(VALUE).getValue();
                String paramType3 = parames.get(2).attribute(TYPE).getValue();
                String param4 = parames.get(3).attribute(VALUE).getValue();
                String paramType4 = parames.get(3).attribute(TYPE).getValue();
                TestFlowManager.runner().sourceParse(mdSource, name, param1, paramType1, param2, paramType2, param3, paramType3, param4, paramType4, returnType, tgtId);
            }
        }
    }

    /**
     * 执行Database类型step
     *
     * @param el
     * @throws Exception
     */
    public void setDatabase(Element el) throws Exception  {
        String parmeType = null == el.attribute(ID) ? "" : el.attribute(ID).getValue();
        Element sql =  el.element(SQL);
        String sqlStr = sql == null ? "" : sql.getText();
        TestFlowManager.runner().queryDataBase(parmeType, sqlStr);
    }

    /**
     * 执行Verify类型step
     *
     * @param el
     * @throws Exception
     */
    public void setVerify(Element el) throws Exception {
        String pkkeysStr = "";
        String nocomparekeyStr = "";

        String type = null == el.attribute(TYPE) ? "" : el.attribute(TYPE).getValue();
        if ("entity".equals(type)) {
            Element entitys = el.element(ENTITYS);
            String entitysType = null == entitys.attribute(TYPE) ? "" : entitys.attribute(TYPE).getValue();
            List<Element> entityList = entitys.elements(ENTITY);
            String entityText1 = entityList == null ? "" : entityList.get(0).getText();
            String entityText2 = entityList == null ? "" : entityList.get(1).getText();
            Element pkkeys = el.element(PKKEYS);
            List<Element> pkkeyList = pkkeys.elements(PKKEY);
            for (int i = 0; i < pkkeyList.size(); i++) {
                pkkeysStr += pkkeyList.get(i).getText();
                if (i != pkkeyList.size() - 1) {
                    pkkeysStr += ",";
                }
            }
            Element nocomparekeys = el.element(NOCOMPAREKEYS);
            List<Element> nocomparekeylist = nocomparekeys.elements(NOCOMPAREKEY);
            for (int i = 0; i < nocomparekeylist.size(); i++) {
                nocomparekeyStr += nocomparekeylist.get(i).getText();
                if (i != nocomparekeylist.size() - 1) {
                    nocomparekeyStr += ",";
                }
            }
            TestFlowManager.runner().verify(entitysType, entityText1, entityText2, pkkeysStr, nocomparekeyStr);
        }
        if ("string".equals(type)) {
            Element entitys = el.element(ENTITYS);
            List<Element> entityList = entitys.elements(ENTITY);
            String entityText1 = entityList == null ? "" : entityList.get(0).getText();
            String entityText2 = entityList == null ? "" : entityList.get(1).getText();
            TestFlowManager.runner().verify(entityText1, entityText2);
        }
        if ("xpath".equals(type)) {
            Element entitys = el.element(ENTITYS);
            List<Element> entityList = entitys.elements(ENTITY);
            String entityText1 = entityList == null ? "" : entityList.get(0).getText();
            Element xpath = entitys.element(XPATH);
            Element value = entitys.element(VALUE);
            String xpathStr = xpath == null ? "" : entitys.getText();
            String valueStr = value == null ? "" : entitys.getText();
            TestFlowManager.runner().verify(entityText1, xpathStr, valueStr);
        }
    }


    /**
     * 读取XML文件
     *
     * @param filePath 读取XML文件的路径
     * @return
     * @throws Exception
     */
    public Document openFile(String filePath) throws Exception  {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        File file = new File(filePath);
        org.w3c.dom.Document domDocument = db.parse(file);
        DOMReader reader = new DOMReader();
        return reader.read(domDocument);
    }

    public void loadParame(Map<String, String> map) {
        for (String key : map.keySet()) {
            TestFlowManager.runner().addBuffer(key, map.get(key));
        }
    }
}

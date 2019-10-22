package com.testflow.apitest.utilities;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class JAXBUtil {
    private static final String ENCODING = "UTF-8";
    //private static final Log logger = LogFactory.getLog(JAXBUtil.class);
    /**
     * xml转化成实体
     * @param clazz
     * @param xml
     * @param <T>
     * @return
     */
    public static <T> T formXML(Class<T> clazz, String xml) {
        JAXBContext jaxbContext = null;
        T object = null;
        if (xml != null && !"".equals(xml)) {
            try {
                jaxbContext = JAXBContext.newInstance(clazz);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xml.getBytes(ENCODING));
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                JAXBElement<T> jaxbElement = unmarshaller.unmarshal(new StreamSource(byteArrayInputStream), clazz);
                object = (T) jaxbElement.getValue();
            } catch (Exception e) {
                Exception ex=e;
                //logger.error("error when unmarshalling from a xml string");
            }
        }
        return object;
    }

    /**
     * xml转化成实体
     * @param clazz
     * @param xml
     * @param <T>
     * @return
     */
    public static <T> List<T> formXMLList(String xml, Class<T> clazz) {
        JAXBContext jaxbContext = null;
        List<T> objList = new ArrayList<>();
        if (xml != null && !"".equals(xml)) {
            try {
                Document doc = DocumentHelper.parseText(xml);
                List<Element> list = doc.selectNodes("/ListRoot/*");
                for (Element item : list) {
                    objList.add(formXML(clazz, item.asXML()));
                }

            } catch (Exception e) {
                Exception ex=e;
            }
        }
        return objList;
    }

    /**
     * 实体转换成xml
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String toXML(T object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            // 是否格式化生成xml
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 设置编码方式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);

            marshaller.marshal(object, sw);
        } catch (Exception e) {
            //logger.error("error when marshalling to a xml string");
        }
        return sw.toString();
    }
}


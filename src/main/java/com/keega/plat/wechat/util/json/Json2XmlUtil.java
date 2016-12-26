package com.keega.plat.wechat.util.json;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * this class is use for let json convert to xml file .
 *
 * Created by zun.wei on 2016/11/23.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class Json2XmlUtil {

    private static String path = null;

    /**
     * json string convert to xml string
     * @param rootElementName 自定义xml根节点的名称
     * @param jsonParentElementName 自定义json父节点的名称
     * @param requireFormatXml 是否需要输出格式化的xml,0表示不需要格式化，其他表示需要格式化。
     * @param json 要转换为xml的json字符串
     * @return 将json字符串转换为xml的字符串
     */
    public static String json2xml(String rootElementName,String jsonParentElementName,int requireFormatXml,String json){
        if (null == jsonParentElementName) {
            json = "{\"" + rootElementName + "\":" + json + "}";
        } else {
            json = "{\""+rootElementName+"\":{\""+jsonParentElementName+"\":"+json+"}}";
        }
        StringReader input = new StringReader(json);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();
        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            if (0 != requireFormatXml) {
                writer = new PrettyXMLEventWriter(writer);
            }
            writer.add(reader);
            reader.close();
            writer.close();
        } catch( Exception e){
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(output.toString().length()>=38){//remove <?xml version="1.0" encoding="UTF-8"?>
            return output.toString().substring(39);
        }
        return output.toString();
    }

    /**
     * xml string convert to json string
     */
    public static String xml2json(String xml){
        StringReader input = new StringReader(xml);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch( Exception e){
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(output.toString());
        return output.toString();
    }


}

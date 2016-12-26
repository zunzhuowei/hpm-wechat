package com.keega.plat.wechat.util.xml;

import com.keega.plat.wechat.model.resp.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

/**
 * Created by zun.wei on 2016/12/7.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class MessageUtil {

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 音乐消息对象转换成xml
     *
     * @param musicMessage 音乐消息对象
     * @return xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * 图片消息对象转换成xml
     * @param imageMessage 图片消息对象
     * @return
     */
    public static String imageMessgeToXml(ImageMessage imageMessage) {
        xstream.alias("xml",imageMessage.getClass());
        xstream.alias("Image",new Image().getClass());
        return xstream.toXML(imageMessage);
    }

    /**
     * 请求的map转换成xml
     * @param map 请求
     * @return
     */
    public static String requestMapMsgToXml(Map<String,String> map) {
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("xml");
            Set<String> keys = map.keySet();
            for (String key : keys) {
                root.addElement(key).addText("<![CDATA["+map.get(key)+"]]>");
            }
            StringWriter stringWriter = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(stringWriter, new OutputFormat("    ", true));
            xmlWriter.setEscapeText(false);
            xmlWriter.write(document);
            if(stringWriter.toString().length()>=38){//remove <?xml version="1.0" encoding="UTF-8"?>
                return stringWriter.toString().substring(39);
            }
            return stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 扩展xstream，使其支持CDATA块
     */
    static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

}

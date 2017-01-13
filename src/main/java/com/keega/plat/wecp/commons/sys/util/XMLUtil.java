package com.keega.plat.wecp.commons.sys.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;

/*
 * 单例模式指的是所有的对象只有一个，这样子可以提高访问的效率
 * 即：把userDocument设置成静态
 */
public class XMLUtil {
    private static Document tableDocument = null;

    public static Document getWechatDocument() {
        // 如果document存在直接返回
        if (tableDocument != null) {
            return tableDocument;
        }
        // 如果不存在就创建对象
        SAXReader reader = null;
        try {
            reader = new SAXReader();
            //System.out.println(XMLUtil.class.getClassLoader().getResource("org/keega/idea/xml/sets.xml"));
            tableDocument = reader.read(XMLUtil.class.getClassLoader()
                    .getResource("/com/keega/plat/wecp/commons/sys/conf/wechat_config.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return tableDocument;
    }

}

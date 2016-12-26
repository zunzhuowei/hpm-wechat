package com.keega.plat.wechat.quartz;


import com.keega.plat.wechat.util.WeiXinConstant;

import java.io.*;

/**
 * Created by zun.wei on 2016/12/7.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class TempAccessTokenTask {

    private static String path = null;

    static {
        String realPath = TempAccessTokenTask.class.getClassLoader().getResource("").getPath();
        if (!WeiXinConstant.isOpentJunitTest) {
            //启动tomcat的时候使用下面这个
            realPath = realPath.substring(1, realPath.length() - 16) + "temp/";
        } else {
            //用junit的时候使用下面这个
            realPath = realPath.substring(1, realPath.length() - 14) + "/hpmMavenWebapp/temp/";
        }
        path = realPath;
    }

    //保存临时文件
    public static void saveTempAccessToken(String accessToken) throws IOException {
        writeAccessToken2File(accessToken,"accessToken");
    }

    //获取临时文件的内容
    public static String getTempAccessToken() {
//System.out.println(path+"/accessToken.txt");
        return readString(path+"/accessToken.txt");
    }

    //写access token到临时文件中
    private static void writeAccessToken2File(String accessToken,String fileName) throws IOException {
        String realPath = path + fileName + ".txt";
//System.out.println(realPath);
        BufferedWriter writer = null;
        File file = new File(realPath);
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "utf-8"));
        writer.write(accessToken);
        writer.flush();
        writer.close();
    }

    /* 读取文件中的字符串 */
    private static String readString(String path) {
        StringBuilder result = new StringBuilder();
        String line = null;
        try {
    /* 创建读取对象 */
            FileReader fileReader = new FileReader(path);
    /* 创建缓存区 */
            BufferedReader reader = new BufferedReader(fileReader);
    /* 读取文件 */
            while ((line = reader.readLine()) != null) {
                result.append(line);
                //System.out.println("读取成功：" + line);
            }
    /* 关闭对象 */
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}

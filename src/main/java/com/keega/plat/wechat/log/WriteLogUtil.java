package com.keega.plat.wechat.log;

import java.io.*;

/**
 * Created by zun.wei on 2016/11/21.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class WriteLogUtil {

    private static String path = null;
    //this.getClass().getClassLoader().getResource("/").getPath();

    private WriteLogUtil() {
        //path = realPath;
    }

    public static synchronized void writeLog2File(String fileName, String requestTime, String responseResult) {
        StringBuffer context = new StringBuffer();
        context.append("调用时间: ").append(requestTime).append("      ");
        context.append("请求结果: ").append(responseResult).append("");
        try {
            WriteLogUtil.wirteString(fileName, context.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 往文件写入字符串 */
    private static void wirteString(String fileName, String context) throws IOException {
        if (path == null) {
            synchronized (WriteLogUtil.class) {
                if (path == null) {
                    // /G:/java_project/hj-project/my-wechat/target/my-wechat/WEB-INF/classes/
                   // G:/java_project/hj-project/my-wechat/target/my-wechat/weblog/
                    String realPath = WriteLogUtil.class.getClassLoader().getResource("").getPath();
                    //realPath = realPath.substring(1, realPath.length() - 16) + "META-INF/";
                    realPath = realPath.substring(1, realPath.length() - 16) + "log/";
                    path = realPath;
                }
            }
        }
        String realPath = path + fileName + ".log";
        //System.out.println(realPath+"<---path");
        BufferedWriter writer = null;
        File file = new File(realPath);
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, true), "utf-8"));
        writer.write(context + "\t\n");
        writer.flush();
        writer.close();
    }


    /* 将对象写入文件方法 */
    private void write(Object o, String path) throws IOException {
    /* 创建存取文件 */
        FileOutputStream fileStream = new FileOutputStream(path);
    /* 将存取文件写入对象 */
        ObjectOutputStream os = new ObjectOutputStream(fileStream);
    /* 写入对象 */
        os.writeObject(o);
        System.out.println("写入数据成功");
    /* 关闭ObjectOutputStream */
        os.close();
    }


    /* 将对象从文件中读出来 */
    private void read(String path) {
        try {
    /* 连接到要读取的文件 */
            FileInputStream fileStream = new FileInputStream(path);
    /* 怎样读取连接到的文件 */
            ObjectInputStream os = new ObjectInputStream(fileStream);
    /* 读取对象 */
           /* Dog dog = (Dog) os.readObject();
            System.out.println("输出结果：" + dog.getName() + " is a "
                    + dog.getSex() + ",高:" + dog.getHeight() + ",长:"
                    + dog.getLength());*/
    /* 关闭对象 */
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }


    /* 读取文件中的字符串 */
    private void readString(String path) {
        try {
    /* 创建读取对象 */
            FileReader fileReader = new FileReader(path);
    /* 创建缓存区 */
            BufferedReader reader = new BufferedReader(fileReader);
    /* 读取文件 */
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println("读取成功：" + line);
            }
    /* 关闭对象 */
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

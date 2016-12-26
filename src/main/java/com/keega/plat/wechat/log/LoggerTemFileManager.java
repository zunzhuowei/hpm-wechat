package com.keega.plat.wechat.log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by zun.wei on 2016/11/21.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class LoggerTemFileManager implements Runnable {

    private String path;//路径

    private static String RETENTION_TIME = "";// 文件保存的时间

    static {
        Properties prop = new Properties();
        InputStream inStrem = LoggerTemFileManager.class.getClassLoader()
                .getResourceAsStream("/com/keega/plat/wechat/log/logger.properties");
        //如果没有编译这个logger.properties文件，需要去pom.xml去配置包含进去。
        try {
            prop.load(inStrem);
            RETENTION_TIME = prop.getProperty("file_retention_time");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStrem != null) {
                    inStrem.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构造函数。初始化参数
     * @param path
     */
    public LoggerTemFileManager(String path) {
        this.path = path;
        //System.out.println("path____________________" + path);
    }


    @Override
    public void run() {
        //System.out.println("========临时日志管理开始=========");
        //path = path + "exportExcel";
        path = path + "weblog\\";//G:\java_project\hj-project\my-wechat\target\my-wechat\weblog\
        //System.out.println("path?????" + path);
        File file = new File(path);
        deletefiles(file);//这个是原来的人家的方法。
    }

    /**
     * 批量删除文件
     *
     * @param folder
     */
    public void deletefiles(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
    }

    /**
     * 删除文件
     *
     * @param file
     */
    private void deleteFile(File file) {
        try {
            if (file.isFile()) {
                // 删除符合条件的文件
                if (canDeleteFile(file)) {
                    if (file.delete()) {
                        System.out.println("临时日志" + file.getName() + "删除成功!");
                    } else {
                        System.out.println("临时日志" + file.getName()+ "删除失败!此临时日志可能正在被使用");
                    }
                } else {

                }
            } else {
                //System.out.println("没有可以删除的临时照片了");
            }

        } catch (Exception e) {
            System.out.println("删除临时日志失败========");
            e.printStackTrace();
        }
    }

    /**
     * 判断文件是否能够被删除
     */
    private boolean canDeleteFile(File file) {
        Date fileDate = getfileDate(file);
        Date date = new Date();
        long time = (date.getTime() - fileDate.getTime()) / 1000 / 60
                - Integer.parseInt(RETENTION_TIME);// 当前时间与文件间隔的分钟
        if (time > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取文件最后的修改时间
     *
     * @param file
     * @return
     */
    private Date getfileDate(File file) {
        long modifiedTime = file.lastModified();
        Date d = new Date(modifiedTime);
        return d;
    }
}

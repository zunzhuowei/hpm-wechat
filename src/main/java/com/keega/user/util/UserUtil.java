package com.keega.user.util;

/**
 * Created by zun.wei on 2016/12/17.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class UserUtil {


    public static Object test(Class<?> clz) throws IllegalAccessException, InstantiationException {
        if (clz.isAnnotationPresent(MyInject.class)) {
            return clz.newInstance();
        }
        return null;
    }

}

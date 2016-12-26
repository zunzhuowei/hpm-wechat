package com.keega.common.dao;

import com.keega.user.entity.User;
import com.keega.user.util.UserUtil;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by zun.wei on 2016/12/17.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class TestInject {

    @Test
    public void testInject() throws InstantiationException, IllegalAccessException {
        User user = (User) UserUtil.test(User.class);
        Field[] field = user.getClass().getDeclaredFields();
        for (Field field1 : field) {
            System.out.println(field1.getName());
        }
    }
}

package com.keega.plat.wechat.dao.sys;

import java.sql.SQLException;
import java.util.Map;

/**
 * 人员信息dao接口
 * Created by zun.wei on 2016/12/22.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IUserDao {

    /**
     * 根据用户标识获取人员信息
     * @param A0100 用户标识
     * @return Map包装的人员信息
     * @throws SQLException 异常
     */
    public Map<String,Object> getUserInfoByA0100(String A0100) throws SQLException;

}

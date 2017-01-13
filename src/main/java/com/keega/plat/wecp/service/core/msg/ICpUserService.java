package com.keega.plat.wecp.service.core.msg;

import java.sql.SQLException;

/**
 * Created by zun.wei on 2017/1/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ICpUserService {

    /**
     *
     * @param A0100 人员标志
     * @return json格式的人员信息
     * @throws SQLException 异常
     */
    public String getJsonUserInfoByA0100(String A0100) throws SQLException;



}

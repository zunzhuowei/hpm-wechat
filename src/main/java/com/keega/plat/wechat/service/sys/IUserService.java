package com.keega.plat.wechat.service.sys;

import java.sql.SQLException;

/**
 * 人员业务层接口
 * Created by zun.wei on 2016/12/22.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IUserService {

    public String getJsonUserInfoByA0100(String A0100) throws SQLException;

}

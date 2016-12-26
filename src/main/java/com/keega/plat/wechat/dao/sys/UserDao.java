package com.keega.plat.wechat.dao.sys;

import com.keega.common.dal.Dal;
import com.keega.plat.wechat.util.config.WxConfigInit;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 人员 data access object
 * Created by zun.wei on 2016/12/22.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Repository
public class UserDao implements IUserDao {

    /** 人员信息要查询的字段 */
    private static List<String> userItems = (List<String>) WxConfigInit.getWxConfigData()
            .get("myInfo_map").get("fields");


    @Override
    public Map<String, Object> getUserInfoByA0100(String A0100) throws SQLException {
        StringBuffer sb = new StringBuffer();
        for (String userItem : userItems) {
            sb.append(userItem).append(",");
        }
        String fields = sb.toString().substring(0, sb.toString().length() - 1);
        String sql = "select "+fields+" from UsrA01 where A0100=?";
        return Dal.map().query(sql, A0100);
    }
}

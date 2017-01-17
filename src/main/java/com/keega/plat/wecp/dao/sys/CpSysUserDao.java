package com.keega.plat.wecp.dao.sys;

import com.keega.common.dal.Dal;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by zun.wei on 2017/1/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Repository
public class CpSysUserDao implements ICpSysUserDao {

    @Override
    public boolean isInSys(String cpUserId) throws SQLException {
        String dbOpenId = Dal.single().query("select openid from UsrA01 where openid=?", cpUserId);
        return !"default_id".equals(dbOpenId) && null != dbOpenId && dbOpenId.equals(cpUserId);
    }

    @Override
    public Map<String, Object> getSysUserByOpenId(String openId) throws SQLException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < listUserItems.size(); i++) {
            sb.append(listUserItems.get(i)).append(",");
        }
        String fields = sb.toString().substring(0, sb.toString().length() - 1);
        return Dal.map().query("select " + fields + " from UsrA01 where openid = ?", openId);
    }

    @Override
    public void bindHrSysUser(String openId, String account) throws SQLException {
        Dal.upd().excute("update UsrA01 set openid=? where " + userName + "=?", openId, account);
    }

    @Override
    public Map<String, Object> getOpenIdByAccPss(String account, String password) throws SQLException {
        if (password != null)
            return Dal.map().query("select openid from UsrA01 where " + userName + "=? and " + passWord + "=?"
                    , new Object[]{account, password});
        else
            return Dal.map().query("select openid from UsrA01 where " + userName + "=? and " + passWord + " is null"
                    , account);
    }

    @Override
    public Map<String, Object> getUserInfoByA0100(String A0100) throws SQLException {
        StringBuffer sb = new StringBuffer();
        for (String userItem : userItems) {
            sb.append(userItem).append(",");
        }
        String fields = sb.toString().substring(0, sb.toString().length() - 1);
        String sql = "select " + fields + "  from UsrA01 where A0100=?";
        return Dal.map().query(sql, A0100);
    }

    @Override
    public Map<String, Object> getUserByOpenId(String openId) throws SQLException {
        String sql = "select openid from UsrA01 where openid=?";
        return Dal.map().query(sql, openId);
    }

    @Override
    public void unbundlingHrSysByUserId(String userId) throws SQLException {
        String sql = "update UsrA01 set openid=? where openid=?";
        Dal.upd().excute(sql, "default_id", userId);
    }


}

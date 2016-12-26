package com.keega.plat.wechat.dao.sys;

import com.keega.common.dal.Dal;
import com.keega.plat.wechat.util.config.WxConfigInit;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 系统用户Data Access Object
 * Created by zun.wei on 2016/12/15.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Repository("sysUserDao")
public class SysUserDao implements ISysUserDao {

    //迅雷的useranme字段为H01SZ；password字段为H01T5

    //从内存中获取用户名、密码对应的字段名称
    private static String userName = (String) ((Map<String, Object>) WxConfigInit.getWxConfigData().
            get("user_login_map")).get("username");//用户名字段
    private static String passWord = (String) ((Map<String, Object>) WxConfigInit.getWxConfigData().
            get("user_login_map")).get("password");//用户密码字段
    private static List<String> listUserItems = (List<String>) WxConfigInit.getWxConfigData().
            get("user_config_map").get("fields");//session中用户包含的字段

    @Override//验证用户是否存在系统
    public boolean hashUserInDatabase(String openId) throws SQLException {//null,default_id
        String dbOpenId = Dal.single().query("select openid from UsrA01 where openid=?", openId);
        return !"default_id".equals(dbOpenId) && null != dbOpenId && dbOpenId.equals(openId);
    }

    @Override//map的key区分大小写
    public Map<String, Object> getSysUserByOpenId(String openId) throws SQLException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < listUserItems.size(); i++) {
            sb.append(listUserItems.get(i)).append(",");
        }
        String fields = sb.toString().substring(0, sb.toString().length() - 1);
        return Dal.map().query("select "+fields+" from UsrA01 where openid = ?", openId);
    }

    @Override//获取系统用户的账号密码
    public Map<String, Object> getAccPassByOpenId(String openId) throws SQLException {
        return Dal.map().query("select "+userName+" as username,"+passWord+" as password from UsrA01 " +
                        "where openid = ?",openId);
    }

    @Override//绑定openid到系统用户中
    public void bindHrSysUser(String openId,String account) throws SQLException {
        Dal.upd().excute("update UsrA01 set openid=? where "+userName+"=?", openId, account);
    }

    @Override//根据用户名和密码获取openId
    public Map<String, Object> getOpenIdByAccPss(String account, String password) throws SQLException  {
        if (password != null)
            return Dal.map().query("select openid from UsrA01 where "+userName+"=? and "+passWord+"=?"
                ,new Object[]{account,password});
        else
            return Dal.map().query("select openid from UsrA01 where "+userName+"=? and "+passWord+" is null"
                    ,account);
    }

    @Override//还原默认openid值
    public void updateOpenId2Default(String fromUser) throws SQLException {
        Dal.upd().excute("update UsrA01 set openid = ? where openid = ?","default_id",fromUser);
    }

}

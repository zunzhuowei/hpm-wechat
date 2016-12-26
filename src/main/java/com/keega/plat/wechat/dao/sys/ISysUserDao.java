package com.keega.plat.wechat.dao.sys;

import java.sql.SQLException;
import java.util.Map;

/**
 * 首页接口
 * Created by zun.wei on 2016/12/15.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ISysUserDao {

    /**
     * 根据openId查询用户是否存在系统，
     * 如果存在，说明已经与系统绑定
     * @param openId 微信授权后获得到的openId
     * @return true:存在；false:不存在
     */
    public boolean hashUserInDatabase(String openId) throws SQLException;

    /**
     * 根据openId查询对应的系统用户
     * @param openId 微信授权后获得到的openId
     * @return 用Map包装的系统用户
     */
    public Map<String, Object> getSysUserByOpenId(String openId) throws SQLException;

    /**
     * 获取账号密码
     * @param openId 微信openid
     * @return map包装的账号密码
     */
    public Map<String,Object> getAccPassByOpenId(String openId) throws SQLException;

    /**
     * 执行绑定openId到UsrA01中
     * @param openId 微信openId
     * @param account 系统账号
     */
    public void bindHrSysUser(String openId,String account) throws SQLException;

    /**
     * 根据openId获取系统中用户的账号密码
     * @param account 绑定系统时输入的账号
     * @param password 绑定系统时输入的密码
     * @return map包装的系统中的openId
     */
    public Map<String,Object> getOpenIdByAccPss(String account, String password) throws SQLException ;

    /**
     * 用户退订阅的时候还原openId值
     * @param fromUser 退订的openId
     */
    public void updateOpenId2Default(String fromUser) throws SQLException;
}

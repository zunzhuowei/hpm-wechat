package com.keega.plat.wecp.service.sys;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by zun.wei on 2017/1/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ICpSysUserService {

    /**
     * 确认userId已经绑定数据库
     * @param userId cpUserId
     * @return 是否绑定
     */
    boolean hasBindDB(String userId) throws SQLException;

    /**
     * 根据cpUserId获取系统用户
     * @param userId cpUserId
     * @return  Map包装的系统用户
     * @throws SQLException 异常
     */
    Map<String,Object> getSysUserByCpUserId(String userId) throws SQLException;

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
     * 检查微信账号是否已经和系统绑定过了。
     * @param openid 微信openid
     * @return true绑定过，false未绑定过
     * @throws SQLException 异常
     */
    boolean checkWeChatUserBindSysByOpenId(String openid) throws SQLException;
}

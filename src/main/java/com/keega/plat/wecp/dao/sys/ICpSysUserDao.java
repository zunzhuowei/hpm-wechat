package com.keega.plat.wecp.dao.sys;


import com.keega.plat.wecp.dao.IBaseDao;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by zun.wei on 2017/1/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ICpSysUserDao extends IBaseDao{

    /**
     * 根据企业号的userId查询是否与系统用户绑定
     * @param cpUserId 企业userId
     * @return 是否存在
     */
    boolean isInSys(String cpUserId) throws SQLException;

    /**
     * 根据openId查询对应的系统用户
     * @param openId 微信授权后获得到的openId
     * @return 用Map包装的系统用户
     */
    Map<String, Object> getSysUserByOpenId(String openId) throws SQLException;

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
     * 根据用户标识获取人员信息
     * @param A0100 用户标识
     * @return Map包装的人员信息
     * @throws SQLException 异常
     */
    public Map<String,Object> getUserInfoByA0100(String A0100) throws SQLException;

    /**
     * 根据openid获取系统用户。
     * 用于绑定hr的时候，验证微信账号是否已经绑定过了。如果绑定过了，就不允许绑定其他账号了
     * @param openId 微信openid
     * @return map
     * @throws SQLException 异常
     */
    Map<String, Object> getUserByOpenId(String openId) throws SQLException;

}

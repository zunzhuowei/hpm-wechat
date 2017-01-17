package com.keega.plat.wecp.service.sys;

import com.keega.plat.wecp.dao.sys.ICpSysUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by zun.wei on 2017/1/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class CpSysUserService implements ICpSysUserService {

    @Resource
    private ICpSysUserDao cpSysUserDao;

    @Override
    public boolean hasBindDB(String userId) throws SQLException {
        return cpSysUserDao.isInSys(userId);
    }

    @Override
    public Map<String, Object> getSysUserByCpUserId(String userId) throws SQLException {
        return cpSysUserDao.getSysUserByOpenId(userId);
    }

    @Override
    public void bindHrSysUser(String openId, String account) throws SQLException {
        cpSysUserDao.bindHrSysUser(openId,account);
    }

    @Override
    public Map<String, Object> getOpenIdByAccPss(String account, String password) throws SQLException {
        return cpSysUserDao.getOpenIdByAccPss(account,password);
    }

    @Override
    public boolean checkWeChatUserBindSysByOpenId(String openid) throws SQLException {
        Map<String, Object> user = cpSysUserDao.getUserByOpenId(openid);
        if (user == null) return false;
        Object id = user.get("openid");
        return id != null;
    }

    @Override
    public void resetWeixinUserWithHr(String userId) throws SQLException {
        cpSysUserDao.unbundlingHrSysByUserId(userId);
    }

}

package com.keega.plat.wecp.service.core.msg.impl;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wecp.dao.sys.ICpSysUserDao;
import com.keega.plat.wecp.service.core.msg.ICpUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

/**
 * 用户业务层实现类
 * Created by zun.wei on 2017/1/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class CpUserService implements ICpUserService {

    @Resource
    private ICpSysUserDao cpSysUserDao;

    @Override
    public String getJsonUserInfoByA0100(String A0100) throws SQLException {
        Map<String, Object> userMap = cpSysUserDao.getUserInfoByA0100(A0100);
        return JsonUtil.obj2json(userMap);
    }


}

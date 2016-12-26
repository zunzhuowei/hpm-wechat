package com.keega.plat.wechat.service.sys;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wechat.dao.sys.IUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

/**
 * 人员业务层实现类
 * Created by zun.wei on 2016/12/22.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class UserService implements IUserService {

    @Resource
    private IUserDao userDao;

    @Override//获取json用户信息
    public String getJsonUserInfoByA0100(String A0100) throws SQLException {
        Map<String, Object> userMap = userDao.getUserInfoByA0100(A0100);
        return JsonUtil.obj2json(userMap);
    }

}

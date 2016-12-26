package com.keega.plat.wechat.service.sys;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wechat.dao.sys.ISysUserDao;
import com.keega.plat.wechat.oauth2.pojo.SNSUserInfo;
import com.keega.plat.wechat.util.config.MenuConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 系统用户业务层
 * Created by zun.wei on 2016/12/15.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class SysUserService implements ISysUserService {

    @Resource
    private ISysUserDao sysUserDao;

    @Override//存在系统
    public boolean isInSys(String openId) throws SQLException {
        return sysUserDao.hashUserInDatabase(openId);
    }

    @Override//获取系统用户
    public Map<String, Object> getSysUserByOpenId(String openId) throws SQLException {
        return sysUserDao.getSysUserByOpenId(openId);
    }

    @Override//设置模型与视图
    public void setModelAndView(Map<String, Object> wxUserInfo, ModelAndView modelAndView
            ,HttpSession session,List<MenuConfig> menus)
            throws SQLException {
        SNSUserInfo snsUserInfo = (SNSUserInfo) wxUserInfo.get("snsUserInfo");
        if (null == snsUserInfo){//有时候授权失败获取不到信息。
            modelAndView.setViewName("/views/error/500");
            return;
        }
        boolean a = !isInSys(snsUserInfo.getOpenId());
        if (session.getAttribute("user") == null || a) {
            if (a) {//如果openId没有和系统用户绑定，则跳转至绑定页面
                modelAndView.addObject("snsUserInfo", wxUserInfo.get("snsUserInfo"));
                modelAndView.setViewName("/views/wechat/activation/activation");
            } else {
                session.setAttribute("user", getSysUserByOpenId(snsUserInfo.getOpenId()));
                modelAndView.addObject("menuJson",JsonUtil.obj2json(menus));
                modelAndView.setViewName("/views/wechat/home");
            }
        } else {
            modelAndView.addObject("menuJson",JsonUtil.obj2json(menus));
            modelAndView.setViewName("/views/wechat/home");
        }
    }

    @Override//hr与微信绑定
    public String execBind(String openId, String account, String password,HttpSession session) throws SQLException {
        if ("".equals(password)) password = null;
        Map<String,Object> sysOpenId = sysUserDao.getOpenIdByAccPss(account, password);
        if (sysOpenId == null || sysOpenId.get("openid") == null) {//说明这个用户不存在
            return "04";//密码或者账号错误！
        }
        if (!"default_id".equals(sysOpenId.get("openid"))) {//说明已经有openId绑定了。
            return "03";//已经绑定了
        } else if ("default_id".equals(sysOpenId.get("openid"))){//如果没有绑定
            sysUserDao.bindHrSysUser(openId,account);
            session.setAttribute("user",getSysUserByOpenId(openId));
            return "01";//绑定成功
        }
        return "02";//绑定失败
    }

    @Override//用户退订时，更新回默认openId值
    public void updateOpenId2default(String fromUser,HttpSession session) throws SQLException {
        sysUserDao.updateOpenId2Default(fromUser);//还原openid
        session.removeAttribute("user");
        session.invalidate();
        //TODO 清空session,然后不起作用，微信有缓存，需要等待一会才可以清除user。
        // setModelAndView这里多加判断可能可以，不过会在平时使用的时候多查询一次数据库。得不偿失
    }

}

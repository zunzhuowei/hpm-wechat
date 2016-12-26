package com.keega.plat.wechat.service.sys;

import com.keega.plat.wechat.util.config.MenuConfig;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 系统用户业务层接口
 * Created by zun.wei on 2016/12/15.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ISysUserService {

    /**
     * 根据openId查询用户是否存在系统，
     * 如果存在，说明已经与系统绑定
     * @param openId 微信授权后获得到的openId
     * @return true:存在；false:不存在
     */
    public boolean isInSys(String openId) throws SQLException;

    /**
     * 根据openId查询对应系统用户，
     * @param openId 微信授权后获得到的openId
     * @return Map包装的系统用户
     */
    public Map<String,Object> getSysUserByOpenId(String openId) throws SQLException;

    /**
     * 配置处理模型与试图
     * @param wxUserInfo 微信用户信息
     * @param modelAndView spring模型与视图
     */
    public void setModelAndView(Map<String, Object> wxUserInfo, ModelAndView modelAndView,
                                HttpSession session,List<MenuConfig> menus)throws SQLException;

    /**
     * 微信openId与hr个人账号绑定
     * @param openId 微信用户的openId
     * @param account hr账号
     * @param password hr密码
     * @return 绑定结果,01表示绑定成功，02表示绑定失败
     */
    public String execBind(String openId, String account, String password,HttpSession session) throws SQLException;

    /**
     * 用户取消订阅的时候，将openId恢复默认值
     * @param fromUser 退订的openId人
     */
    public void updateOpenId2default(String fromUser,HttpSession session) throws SQLException;
}

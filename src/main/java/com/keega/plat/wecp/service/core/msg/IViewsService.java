package com.keega.plat.wecp.service.core.msg;

import com.keega.plat.wecp.commons.sys.conf.MenuConfig;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * 视图服务层接口
 *
 * Created by zun.wei on 2016/12/26.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IViewsService {

    /**
     * 根据code获取授权用户的userID/OpenID
     * @param code redirect 的uri获得的参数
     * @return userId/Openid
     */
    String getOauth2UserInfoIdByCode(String code) throws WxErrorException;

    /**
     *  重定向到授权页面
     * @param code redirect 的uri获得的参数
     * @param model springmvc的model
     * @return 如果是null,表示数据库存在这个userId，如果不为null表示返回重定向的uri
     * @throws WxErrorException 异常
     */
    String redirect2OauthPage(String code, Model model, HttpSession session) throws WxErrorException, SQLException;

    /**
     * 绑定hr系统
     * @param openId 微信userid
     * @param account 系统账号
     * @param password 系统密码
     * @return 绑定结果
     */
    String bindHr(String openId, String account, String password,HttpSession session)
            throws SQLException;


    /**
     * 根据人员权限获取该有的菜单。
     * @param a0100 人员标志
     * @param menus 内存中缓存的菜单
     * @return MenuConfig 的json
     */
    String getCustomMenus(String a0100, List<MenuConfig> menus) throws SQLException;

    /**
     * 微信成员关注之后，二次验证（此处相当于把绑定hr系统动作放到微信二次验证了。）
     * @param openId 微信用户的openId
     * @param account hr账号
     * @param password hr密码
     * @param session HttpSession
     * @return 绑定操作的相应结果
     */
    String secondBindHr(String openId, String account, String password, HttpSession session) throws SQLException, WxErrorException;
}

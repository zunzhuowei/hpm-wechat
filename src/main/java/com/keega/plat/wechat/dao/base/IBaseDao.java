package com.keega.plat.wechat.dao.base;

/**
 * Created by zun.wei on 2016/12/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IBaseDao {

    /**
     * 获取菜单的json
     * @return 返回要制作的菜单的json
     */
    public String getMenuJson();

    /**
     * 获取AccessToken
     * @return AccessToken
     */
    public String getAccessToken();
}

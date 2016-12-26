package com.keega.plat.wechat.util.menu;

/**
 * 放置自定义菜单的常量
 *
 * Created by zun.wei on 2016/12/15.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class MenuFinal {

    //snsapi_base 、snsapi_userinfo --获取授权信息类型
    //public static final String HOME_URL = "http://161818x71d.iask.in/wx/home";

    //public static final String KEEGA_URL = "http://161818x71d.iask.in/wx/home";

    public static final String HOME_URL = "https://open.weixin.qq.com/connect/oauth2/authorize" +
            "?appid=wxdac78f942674b126" +
            "&redirect_uri=http%3A%2F%2F161818x71d.iask.in%2Fwx%2Fhome" +
            "&response_type=code" +
            "&scope=snsapi_base&state=STATE" +
            "&connect_redirect=1#wechat_redirect";

    public static final String KEEGA_URL = "https://open.weixin.qq.com/connect/oauth2/authorize" +
            "?appid=wxdac78f942674b126" +
            "&redirect_uri=http%3A%2F%2F161818x71d.iask.in%2Fwx%2Fhome" +
            "&response_type=code" +
            "&scope=snsapi_base&state=STATE" +
            "&connect_redirect=1#wechat_redirect";

    public static final String BIND_URL = "https://open.weixin.qq.com/connect/oauth2/authorize" +
            "?appid=wxdac78f942674b126" +
            "&redirect_uri=http%3A%2F%2F161818x71d.iask.in%2Fwx%2Factivation" +
            "&response_type=code" +
            "&scope=snsapi_base&state=STATE" +
            "&connect_redirect=1#wechat_redirect";

    public static final String BAIDU_URL = "https://www.baidu.com/";

    public static final String GD_URL = "http://hr.ebchinaintl.com.cn/templates/index/ssoZZLogon.jsp?username=zengsg";

    public static final String WIKI_URL = "https://mp.weixin.qq.com/wiki";

    public static final String WIKI_CP_URL = "https://http://qydev.weixin.qq.com/wiki/index.php?title=%E9%A6%96%E9%A1%B5";

}

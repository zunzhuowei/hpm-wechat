package com.keega.plat.wecp.commons.wx.config;

import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;

/**
 * 微信企业版配置信息----主页型应用
 * 已在spring的xml配置文件中配置好。这里配置有点问题，保留下来仅是用于以后参考
 *
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
//@Configuration
@Deprecated
public class MainConfigHome {

    @Value("#{wxhomeProperties.home_corp_id}")
    private String corpId;

    @Value("#{wxhomeProperties.home_corp_secret}")
    private String corpSecret;

    @Value("#{wxhomeProperties.home_token}")
    private String token;

    @Value("#{wxhomeProperties.home_aeskey}")
    private String aesKey;

    @Value("#{wxhomeProperties.home_agent_id}")
    private int agentId;

    //@Bean(name = "homeConfigStorage")
    public WxCpConfigStorage wxCpConfigStorage() {
        WxCpInMemoryConfigStorage configStorage = new WxCpInMemoryConfigStorage();
        configStorage.setToken(this.token);
        configStorage.setAesKey(this.aesKey);
        configStorage.setAgentId(this.agentId);
        configStorage.setCorpId(this.corpId);
        configStorage.setCorpSecret(this.corpSecret);
        return configStorage;
    }

    //@Bean(name = "wxHomeCpService",autowire = Autowire.BY_NAME)
    public WxCpService wxCpService() {
        WxCpService wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(wxCpConfigStorage());
        return wxCpService;
    }

}

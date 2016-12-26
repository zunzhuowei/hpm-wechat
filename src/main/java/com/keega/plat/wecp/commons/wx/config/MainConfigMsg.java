package com.keega.plat.wecp.commons.wx.config;

import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信企业版配置信息----消息型应用
 * 已在spring的xml配置文件中配置好。这里配置有点问题，保留下来仅是用于以后参考
 *
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
//@Configuration
@Deprecated
public class MainConfigMsg {

    @Value("#{wxmsgProperties.msg_corp_id}")
    private String corpId;

    @Value("#{wxmsgProperties.msg_corp_secret}")
    private String corpSecret;

    @Value("#{wxmsgProperties.msg_token}")
    private String token;

    @Value("#{wxmsgProperties.msg_aeskey}")
    private String aesKey;

    @Value("#{wxmsgProperties.msg_agent_id}")
    private int agentId;

    //@Bean(name = "msgConfigStorage")
    public WxCpConfigStorage wxCpConfigStorage() {
        WxCpInMemoryConfigStorage configStorage = new WxCpInMemoryConfigStorage();
        configStorage.setToken(token);
        configStorage.setAesKey(aesKey);
        configStorage.setAgentId(agentId);
        configStorage.setCorpId(corpId);
        configStorage.setCorpSecret(corpSecret);
        return configStorage;
    }

    //@Bean(name = "wxMsgCpService",autowire = Autowire.BY_NAME)
    public WxCpService wxCpService() {
        WxCpService wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(wxCpConfigStorage());
        return wxCpService;
    }

}

package com.keega.plat.wecp.commons.wx.handler;

import com.keega.common.utils.JsonUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 日志操作
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class LogHandler extends AbstractHandler {

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map,
                                    WxCpService wxCpService, WxSessionManager wxSessionManager)
            throws WxErrorException {
        //this.logger.info("\n接收到请求消息，内容：【{}】", wxCpXmlMessage.toString());
        this.logger.info("\n接收到请求消息，内容：【{}】", JsonUtil.obj2json(wxCpXmlMessage));
        return null;
    }
}

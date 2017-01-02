package com.keega.plat.wecp.controller.cp.msg;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wecp.service.core.msg.ICoreServiceMsg;
import com.keega.plat.wecp.service.core.msg.IMsgService;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 消息控制器
 *
 * Created by zun.wei on 2016/12/27.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/cp")
public class MsgController {

    @Resource
    private WxCpService wxMsgCpService;
    @Resource
    private IMsgService msgService;
    @Resource
    private ICoreServiceMsg coreServiceMsg;

    //主动发送文本消息
    @RequestMapping(value = "/msg/send/text", method = RequestMethod.GET)
    public String sendTextMsg2User() throws WxErrorException {
        msgService.sendTextMsg2User();
        return "/views/wechatcp/su/success";
    }

    //主动发送图文消息
    @RequestMapping(value = "/msg/send/it", method = RequestMethod.GET)
    public String sendImgTextMsg2User() throws WxErrorException {
        msgService.sendImgTextMsg2User();
        return "/views/wechatcp/su/success";
    }

    //获取用户信息   Integer departId, Boolean fetchChild, Integer status
    @RequestMapping(value = "/msg/get/user", method = RequestMethod.GET)
    public void getCpUserInfo(HttpServletRequest request, HttpServletResponse response)
            throws WxErrorException, IOException {
        //获取用户
        WxCpUser wxCpUser = coreServiceMsg.getUserInfo("liujunhong");
        //获取部门下的人
        List<WxCpUser> cpUserList =  wxMsgCpService.departGetUsers(2, true, 1);
        String json = JsonUtil.obj2json(cpUserList);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //获取部门
        List<WxCpDepart> departList = wxMsgCpService.departGet();
        String deps = JsonUtil.obj2json(departList);

        response.getWriter().write(deps);
    }

    @RequestMapping(value = "/msg/upload/media",method = RequestMethod.GET)
    public void uploadMedia(HttpServletRequest request, HttpServletResponse response)
            throws WxErrorException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        WxMediaUploadResult wxMediaUploadResult = msgService.uploadMedia();
        response.getWriter().write(JsonUtil.obj2json(wxMediaUploadResult));

        // 下载多媒体文件   获得一个在系统临时目录的文件
        //File file = wxCpService.mediaDownload(media_id);
    }

    @RequestMapping(value = "/msg/send/media",method = RequestMethod.GET)
    public String getMediaById() throws WxErrorException {
        msgService.sendImgMsg2User();
        return "/views/wechatcp/su/success";
    }

}

package com.keega.plat.wechat.service.msg;

import com.keega.plat.wechat.service.base.BaseService;
import com.keega.plat.wechat.service.sys.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by zun.wei on 2016/12/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class EvenTypeService extends BaseService {

    @Resource
    private ISysUserService sysUserService;

    //事件类型操作
    public String evenTypeHandler(Map<String, String> msgMap,HttpSession session) throws SQLException {
        String evenType = msgMap.get("Event");
        System.out.println("evenType = " + evenType);
        //boolean check = "VIEW".equals(evenType)?true:false;
        if ("unsubscribe".equals(evenType)) {
            unsubscribeTypeHandler(msgMap,session);
        } else if ("CLICK".equals(evenType)) {
            clickTypeHandler(msgMap);
        }
        return "";
    }

    //用户取消订阅的时候，更新openId为默认值。
    private void unsubscribeTypeHandler(Map<String, String> msgMap,HttpSession session) throws SQLException {
        String fromUser = msgMap.get("FromUserName");
        sysUserService.updateOpenId2default(fromUser,session);
    }

    //点击事件操作
    private String clickTypeHandler(Map<String, String> msgMap) {

        return "";
    }


}

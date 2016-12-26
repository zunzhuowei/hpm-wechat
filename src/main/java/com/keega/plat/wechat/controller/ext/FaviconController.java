package com.keega.plat.wechat.controller.ext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用来处理，警告: No mapping found for HTTP request with URI [/favicon.ico]
 * in DispatcherServlet with name 'spring'
 *
 * Created by zun.wei on 2016/12/20.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
public class FaviconController {

    @ResponseBody
    @RequestMapping(value = "/favicon.ico")
    public void favicon() {

    }

}

package com.keega.plat.wechat.dao.base;

import com.keega.plat.wechat.util.WeiXinConstant;
import com.keega.plat.wechat.util.menu.MenuFinal;
import org.springframework.stereotype.Repository;

/**
 * Created by zun.wei on 2016/12/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Repository("baseDao")
public class BaseDao implements IBaseDao {

    @Override
    public String getMenuJson() {

        return saveTempMenu();
    }

    @Override
    public String getAccessToken() {
        return WeiXinConstant.getAccessToken();
    }

    //暂时放菜单
    private String saveTempMenu() {
        String json = " {\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"view\",\n" +
                "          \"name\":\"首页\",\n" +
                "          \"url\":\""+ MenuFinal.HOME_URL+"\"\n" +
                "      },\n" +
                "{ "+
                "          \"type\":\"view\",\n" +
                "          \"name\":\"绩佳\",\n" +
                "          \"url\":\""+ MenuFinal.KEEGA_URL+"\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"百度搜索\",\n" +
                "               \"url\":\""+ MenuFinal.BAIDU_URL+"\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"光大\",\n" +
                "               \"url\":\""+ MenuFinal.GD_URL+"\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"绑定hr\",\n" +
                                "\"url\":\""+ MenuFinal.BIND_URL+"\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"文档_CP\",\n" +
                                "\"url\":\""+ MenuFinal.WIKI_CP_URL+"\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"文档_MP\",\n" +
                "               \"url\":\""+ MenuFinal.WIKI_URL+"\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }";
        return json;
    }

}

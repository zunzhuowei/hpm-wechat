package com.keega.plat.wecp.service.core.msg;

import com.keega.plat.wecp.model.map.MapArray;

import java.util.List;

/**
 * 地图业务层接口类
 * Created by zun.wei on 2016/12/30.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IMapService {

    /**
     * 制作考勤签到范围
     * @param points 点组成json
     */
    void makeMap(String points);

    /**
     * 考勤签到
     * @param pointJson 签到的经纬度
     * @param address 签到的具体地址
     * @return 是否在范围内
     */
    String ckeckWork(String pointJson,String address);

    /**
     * 将考勤范围的点转成考勤范围的地图
     * @return 考勤范围的点
     */
    String getPoints2Map();

}

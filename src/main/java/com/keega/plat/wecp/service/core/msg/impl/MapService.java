package com.keega.plat.wecp.service.core.msg.impl;

import com.alibaba.fastjson.JSONArray;
import com.keega.common.utils.JsonUtil;
import com.keega.plat.wecp.model.map.BaiduMap;
import com.keega.plat.wecp.model.map.MapArray;
import com.keega.plat.wecp.model.map.Point;
import com.keega.plat.wecp.service.core.msg.IMapService;
import com.keega.plat.wecp.util.map.BaiduMapUtil;
import org.springframework.stereotype.Service;

import javax.json.Json;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 地图业务层接口实现类
 *
 * Created by zun.wei on 2016/12/30.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class MapService implements IMapService {

    //private static String checkMapString ;
    private static List<MapArray> maps = new ArrayList<MapArray>();

    @Override
    public void makeMap(String points) {//可能有多个考勤地点，所以后期需要保存多个考勤范围
        //checkMapString = points;//保存所有考勤范围
        MapArray mapArray = new MapArray();
        mapArray.setMap(points);
        maps.add(mapArray);
    }

    @Override//TODO address暂时没有处理
    public String ckeckWork(String pointJson, String address) {
        if (maps.size() < 1) return "未设定考勤范围，请前往cp端HR系统设置！";
        for (MapArray map : maps) {
            List<Map<String, Object>> mapList = JsonUtil.json2MapList(map.getMap());
            BaiduMap baiduMap = (BaiduMap) JsonUtil.json2Obj(pointJson, BaiduMap.class);
            List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
            if (mapList != null) {
                for (Map<String, Object> o : mapList) {
                    pts.add(new Point2D.Double((double) o.get("lng"), (double) o.get("lat")));
                }
                Point2D.Double point = new Point2D.Double(Double.parseDouble(baiduMap.getLng()),
                        Double.parseDouble(baiduMap.getLat()));
                if (BaiduMapUtil.IsPtInPoly(point, pts)) {
                    System.out.println("定位在范围内!");
                    return "定位在范围内!";
                }
            }
        }
        System.out.println("定位在范围外!");
        return "定位在范围外!";
    }

    @Override
    public String getPoints2Map() {
        return JsonUtil.obj2json(maps);
    }
}

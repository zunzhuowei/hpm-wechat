package com.keega.plat.wecp.model.map;

/**
 * 多个考勤地点对象
 * Created by zun.wei on 2017/1/2.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class MapArray {

    private String map;

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "MapArray{" +
                "map='" + map + '\'' +
                '}';
    }
}

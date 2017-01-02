package com.keega.plat.wecp.model.map;

/**
 * 点
 * Created by zun.wei on 2017/1/2.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class Point {

    private BaiduMap baiduMap;

    public BaiduMap getBaiduMap() {
        return baiduMap;
    }

    public void setBaiduMap(BaiduMap baiduMap) {
        this.baiduMap = baiduMap;
    }

    @Override
    public String toString() {
        return "Point{" +
                "baiduMap=" + baiduMap +
                '}';
    }
}

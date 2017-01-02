package com.keega.plat.wecp.model.map;

/**
 * 百度地图对象
 * Created by zun.wei on 2016/12/30.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class BaiduMap {

   /* private double lat;//纬度

    private double lng;//经度*/
    private String lat;//纬度

    private String lng;//经度

    private String address;//地址

/*    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }*/

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "BaiduMap{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", address='" + address + '\'' +
                '}';
    }
}

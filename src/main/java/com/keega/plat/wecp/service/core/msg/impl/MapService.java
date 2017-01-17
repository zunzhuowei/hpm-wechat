package com.keega.plat.wecp.service.core.msg.impl;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wecp.dao.map.IMapDao;
import com.keega.plat.wecp.model.map.BaiduMap;
import com.keega.plat.wecp.model.map.MapArray;
import com.keega.plat.wecp.service.core.msg.IMapService;
import com.keega.plat.wecp.util.map.BaiduMapUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.geom.Point2D;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * 地图业务层接口实现类
 *
 * Created by zun.wei on 2016/12/30.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class MapService implements IMapService {

    @Resource
    private IMapDao mapDao;

    @Override
    public void makeMap(String points,String A0100,String desc) throws SQLException {
        //可能有多个考勤地点，所以后期需要保存多个考勤范围
        mapDao.makeMap(points,simpleDateFormat.format(new Date()),A0100,desc);
    }

    @Override
    public String checkWorkLocation(String pointJson, String address) throws SQLException {
        List<MapArray> maps = mapDao.getAllCheckMap();
        if (maps.size() < 1) return "05";//未设定考勤范围，请前往cp端HR系统设置！
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
                    //TODO将签到坐标以及地点存到数据库
                    return "00";//定位在范围内!
                }
            }
        }
        return "01";//定位在范围外!
    }

    @Override
    public String getPoints2Map() throws SQLException {
        List<MapArray> mapArrayList = mapDao.getAllCheckMap();
        return JsonUtil.obj2json(mapArrayList);
    }

    @Override
    public String nowIsOpenSign(String A0100) throws SQLException, ParseException {
        Map<String,Object> schedule = mapDao.getScheduleByA0100(A0100, new Date());
        if (schedule == null) return "3";
        long sbkq = nowTimeDateFormat.parse(schedule.get("sbkq") + "").getTime();
        long sbjz = nowTimeDateFormat.parse(schedule.get("sbjz") + "").getTime();
        long xbkq = nowTimeDateFormat.parse(schedule.get("xbkq") + "").getTime();
        long xbjz = nowTimeDateFormat.parse(schedule.get("xbjz") + "").getTime();
        long nowTime = nowTimeDateFormat.parse(nowTimeDateFormat.format(new Date())).getTime();
        if (sbkq <= nowTime && nowTime <= sbjz) return "1";
        if (xbkq <= nowTime && nowTime <= xbjz) return "2";
        return "0";
    }

    @Override
    public String checkSignTimeStatus(String A0100) throws SQLException, ParseException {
        Map<String,Object> schedule = mapDao.getScheduleByA0100(A0100, new Date());
        if (schedule == null) return "000";//表示排班表没有排班
        long sbkq = nowTimeDateFormat.parse(schedule.get("sbkq") + "").getTime();
        long sb = nowTimeDateFormat.parse(schedule.get("sb") + "").getTime();
        long cd = nowTimeDateFormat.parse(schedule.get("cd") + "").getTime();
        long sbjz = nowTimeDateFormat.parse(schedule.get("sbjz") + "").getTime();

        long xbkq = nowTimeDateFormat.parse(schedule.get("xbkq") + "").getTime();
        long kg = nowTimeDateFormat.parse(schedule.get("kg") + "").getTime();
        long zt = nowTimeDateFormat.parse(schedule.get("zt") + "").getTime();
        long xb = nowTimeDateFormat.parse(schedule.get("xb") + "").getTime();
        long xbjz = nowTimeDateFormat.parse(schedule.get("xbjz") + "").getTime();
        //long nowTime = new Date().getTime();
        long nowTime = nowTimeDateFormat.parse(nowTimeDateFormat.format(new Date())).getTime();
        if (sbkq <= nowTime && nowTime <= sb) return "0";//早上签到
        if (sb < nowTime && nowTime <= cd) return "1"; //早上迟到
        if (cd < nowTime && nowTime <= sbjz) return "2"; //早上旷工
        if (xbkq <= nowTime && nowTime < kg) return "3"; //下午旷工
        if (kg <= nowTime && nowTime < zt) return "4";//下午早退
        if (zt <= nowTime && nowTime < xb) return "5";//正常下班
        if (xb <= nowTime && nowTime <= xbjz) return "6";//加班下班
        return "88";//当前时间不是签到/签退时间
    }

    @Override
    public String hasSignInOrOut(String A0100) throws SQLException, ParseException {
        String status = this.nowIsOpenSign(A0100);
        if ("0".equals(status)) return "0";//未开启签到/签退
        if ("3".equals(status)) return "5";//排班表没有安排
        if ("1".equals(status)) {
            int state = mapDao.checkHasSignInByA0100Date(A0100, new Date());
            if (state == 1) {
                return "1";//已签到
            } else if (state == 0) {
                return "2";//未签到
            } else {
                return "error";
            }
        }
        if ("2".equals(status)) {
            int state = mapDao.checkHasSignOutByA0100Date(A0100, new Date());
            if (state == 1) {
                return "3";//已签退
            } else if (state == 0) {
                return "4";//未签到
            } else {
                return "error";
            }
        }
        return "error";
    }

    @Override
    public String checkWorkSign(String location, String address, Map<String, Object> user,String reason) throws SQLException, ParseException {
        String scope = checkWorkLocation(location, address);//00范围内，01范围外，05未设置范围
        if ("05".equals(scope)) return "050"; //未设置考勤范围
        String A0100 = user.get("A0100") + "";
        String status = checkSignTimeStatus(A0100);
        if ("88".equals(status)) return "888";//当前时间不是签到/签退时间
        int state = mapDao.checkHasSignInByA0100Date(A0100, new Date());
        if (state != 0) return "111";//已签到或签退

        //范围内签到/签退
        if ("00".equals(scope) && "0".equals(status)) {
            //查询数据库没有签到的时候才签到。
            mapDao.normalCheck(location, address, user);
            return "00";//正常签到
        }
        if ("00".equals(scope) && "1".equals(status)) {
            //让其跳转到填写迟到理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "00";
            }
            return "01";//迟到签到
        }
        if ("00".equals(scope) && "2".equals(status)) {
            //让其跳转到填写早上旷工理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "00";
            }
            return "02";//早上旷工
        }
        if ("00".equals(scope) && "3".equals(status)) {
            //让其跳转到填写下午旷工理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "05";
            }
            return "03";//下午旷工
        }
        if ("00".equals(scope) && "4".equals(status)) {
            //让其跳转到填写下午早退理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "05";
            }
            return "04";//下午早退
        }
        if ("00".equals(scope) && "5".equals(status)) {
            mapDao.normalCheck(location, address, user);
            return "05";//正常下班
        }
        if ("00".equals(scope) && "6".equals(status)) {
            mapDao.normalCheck(location, address, user);
            return "06";//加班下班
        }

        //范围外签到/签退
        if ("01".equals(scope) && "0".equals(status)) {
            //让其跳转到填写范围外签到理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "00";
            }
            return "10";//范围外签到
        }
        if ("01".equals(scope) && "1".equals(status)) {
            //让其跳转到填写范围外迟到签到理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "00";
            }
            return "11";//范围外迟到签到
        }
        if ("01".equals(scope) && "2".equals(status)) {
            //让其跳转到填写范围外早上旷工理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "00";
            }
            return "12";//范围外早上旷工
        }
        if ("01".equals(scope) && "3".equals(status)) {
            //让其跳转到填写范围外下午旷工到理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "05";
            }
            return "13";//范围外下午旷工
        }
        if ("01".equals(scope) && "4".equals(status)) {
            //让其跳转到填写范围外下午早退理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "05";
            }
            return "14";//范围外下午早退
        }
        if ("01".equals(scope) && "5".equals(status)) {
            //让其跳转到填写范围外加班下班理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "05";
            }
            return "15";//范围外正常下班
        }
        if ("01".equals(scope) && "6".equals(status)) {
            //让其跳转到填写范围外加班下班理由的页面。
            if (null != reason) {
                mapDao.unNormalCheck(location,address,reason,user);
                return "06";
            }
            return "16";//范围外加班下班
        }
        return "error";
    }

    @Override
    public String checkSignInfo(String A0100) throws SQLException {
        return JsonUtil.obj2json(mapDao.getSignInfo(A0100));
    }

    @Override
    public boolean isManagerAccountLogin(String A0100) throws SQLException {
        int count = mapDao.isManagerAccountLogin(A0100);
        return count > 0;
    }

    @Override
    public String searchEmpCheckWorkInfoByDateAndA0100(String date, String A0100,String empName) throws SQLException, ParseException {
        List<Map<String, Object>> infos = mapDao.getEmpCheckInfoByManagerDateAndA0100(date, A0100,empName);
        List<Map<String, Object>> userLists = mapDao.getEmpInfoListByManagerDateAndA0100(date, A0100,empName);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> sw = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> xw = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> info : infos) {
            String status = info.get("status") + "";
            if ("1".equals(status)) xw.add(info);
            if ("0".equals(status)) sw.add(info);
        }
        map.put("sw", sw);
        map.put("xw", xw);
        map.put("users", userLists);
        return JsonUtil.obj2json(map);
    }

    @Override
    public String searchEmpMonthCheckList(String date, String A0100) throws SQLException, ParseException {
        List<Map<String, Object>> infos = mapDao.getMonthCheckList(date,A0100);
        List<Map<String, Object>> userLists = mapDao.getMonthRecordList(date,A0100);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> sw = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> xw = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> info : infos) {
            String status = info.get("status") + "";
            if ("0".equals(status)) sw.add(info);
            if ("1".equals(status)) xw.add(info);
        }
        map.put("sw", sw);
        map.put("xw", xw);
        map.put("users", userLists);
        return JsonUtil.obj2json(map);
    }

}

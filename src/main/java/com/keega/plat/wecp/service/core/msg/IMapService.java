package com.keega.plat.wecp.service.core.msg;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 地图业务层接口类
 * Created by zun.wei on 2016/12/30.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IMapService {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat nowTimeDateFormat = new SimpleDateFormat("HH:mm");

    /**
     * 制作考勤签到范围
     * @param points 点组成json
     */
    void makeMap(String points,String A0100,String desc) throws SQLException;

    /**
     * 检查考勤签到/签退的，当前所在位置是否在范围内。
     * @param pointJson 签到的经纬度坐标json格式
     * @param address 签到的具体地址
     * @return 是否在范围内。"05"表示没有设置考勤范围；
     *  "01" 表示考勤地点范围外；"00"表示考勤范围内；
     */
    String checkWorkLocation(String pointJson,String address) throws SQLException;

    /**
     * 将考勤范围的点转成考勤范围的地图
     * @return 考勤范围的点
     */
    String getPoints2Map() throws SQLException;

    /**
     * 判断当前时间是否是开启签到签退时间
     * @param A0100 人员标志
     * @return 0表示未开启签到/签退；1表示开启上班签到；2表示开启下班签退;
     *   3表示没有安排排班
     */
    String nowIsOpenSign(String A0100) throws SQLException, ParseException;

    /**
     * 检查当前签到/签退时间是什么状态
     * @param A0100 人员标志
     * @return 0表示正常上班签到；1表示上班迟到；2表示早上上班旷工；
     *  3表示下午下班旷工；4表示下午下班早退；5表示下午正常下班；
     *   6表示下午加班下班；000表示没有排班;88表示当前时间不是签到/签退时间;
     */
    String checkSignTimeStatus(String A0100) throws SQLException, ParseException;

    /**
     * 检查是否签到/签退，或者没有到时间开启签到/签退
     * @param A0100 人员标志
     * @return 1表示已签到，2表示未签到，3表示已签退，4表示未签退，
     *   5表示排班表没有安排，0表示未开启签到/签退
     */
    String hasSignInOrOut(String A0100) throws SQLException, ParseException;

    /** （逻辑）
     * 1.检查是否在范围内；
     * 2.检查当前时间是什么状态
     * （正常签到，早上迟到，早上旷工，下午旷工，下午早退，下午正常下班，下午加班下班）
     *
     * 考勤签到/签退
     * @param location 地理坐标经纬度
     * @param address 签到/签退地址
     * @param user 签到/签退用户
     * @param reason 签到/签退理由
     * @return 00表示范围内正常签到，
     */
    String checkWorkSign(String location, String address, Map<String, Object> user,String reason) throws SQLException, ParseException;

    /**
     * 获取签到/签退的信息（时间，地点，理由）
     * @param A0100 人员标志
     * @return json
     */
    String checkSignInfo(String A0100) throws SQLException;

    /**
     * 是否为经理账号登录
     * @param A0100 人员标志
     * @return true 经理账号登录; false 普通员工登录
     * @throws SQLException 异常
     */
    boolean isManagerAccountLogin(String A0100) throws SQLException;

    /**
     * 根据日期，部门经理标志查询员工考勤信息
     * @param date yyyy.MM.dd  为空表示查询今天
     * @param A0100 部门经理id标志
     * @param empName 员工姓名
     * @return json
     * @throws SQLException 异常
     */
    String searchEmpCheckWorkInfoByDateAndA0100(String date,String A0100,String empName) throws SQLException, ParseException;

    /**
     * 根据日期查询员工月度考勤列表
     * @param date yyyy.MM.dd  为空表示查询当前月
     * @param A0100 员工标志
     * @return json
     * @throws SQLException 异常
     */
    String searchEmpMonthCheckList(String date, String A0100) throws SQLException, ParseException;
    //String initEmpCheckWorkListName(String A0100);

}

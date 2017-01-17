package com.keega.plat.wecp.dao.map;

import com.keega.plat.wecp.model.map.MapArray;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2017/1/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IMapDao {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

    /**
     * 设置考勤范围
     * @param jsonPoints json格式的坐标点
     * @param formatDate 格式化后的日期
     * @param modifyUser 设置的人
     * @param modifyDesc 考勤范围的描述
     */
    void makeMap(String jsonPoints,String formatDate,String modifyUser,
                   String modifyDesc) throws SQLException;

    /**
     * 获取所有的考勤地点
     * @return 所有的考勤范围地点
     */
    List<MapArray> getAllCheckMap() throws SQLException;

    /**
     * 根据人员指标获取个人当日排班表
     * @param A0100 人员标志
     * @param date 当前日期 new Date()
     * @return Map包装的个上下班打卡时间安排
     * @throws SQLException 异常
     */
    Map<String, Object> getScheduleByA0100(String A0100,Date date) throws SQLException;

    /**
     *  根据人员标志与String类型的日期检查是否上班已签到
     * @param A0100 人员标志
     * @param date 当前日期 new Date()
     * @return 1表示已签到，0表示没签到，其他签到出错。
     */
    Integer checkHasSignInByA0100Date(String A0100, Date date) throws SQLException;

    /**
     * 根据人员标志与String类型的日期检查是否下班已签退
     * @param A0100 人员标志
     * @param date 当前日期 new Date()
     * @return 1表示已签退，0表示没签到，其他签退出错。
     */
    Integer checkHasSignOutByA0100Date(String A0100, Date date) throws SQLException;

    /**
     * 正常签到，签退
     * @param location 签到/签退坐标
     * @param address 签到/签退地址
     * @param user 签到/签退的人
     * @throws SQLException 异常
     */
    void normalCheck(String location, String address, Map<String, Object> user) throws SQLException;

    /**
     * 非正常签到/签退
     * @param location 签到/签退坐标
     * @param address 签到/签退地址
     * @param reason 非正常签到/签退理由
     * @param user 非正常签到/签退的人
     * @throws SQLException 异常
     */
    void unNormalCheck(String location, String address, String reason, Map<String, Object> user) throws SQLException;

    /**
     * 根据人员标志获取签到/签退信息(签到或者签退的判断在sql中完成了)
     * @param A0100 人员标志
     * @return Map 时间，日期，地点，理由
     */
    Map<String, Object> getSignInfo(String A0100) throws SQLException;

    /**
     *  判断当前登录的账号是不是经理类型
     * @param A0100 人员标志
     * @return 如果大于0表示是经理类型(查询角色表存在经理账号)
     * @throws SQLException 异常
     */
    Integer isManagerAccountLogin(String A0100) throws SQLException;

    /**
     * 根据经理A0100和日期查询员工的考勤信息
     * @param date yyyy.MM.dd  如果为空，查询当天
     * @param A0100 人员标志
     * @return 员工的考勤信息
     * @throws SQLException 异常
     */
    List<Map<String, Object>> getEmpCheckInfoByManagerDateAndA0100(String date,String A0100,String empName) throws SQLException, ParseException;

    /**
     * 根据部门经理A0100和日期查询参加考勤的岗位职位信息等
     * @param date yyyy.MM.dd 如果未空表示查当天
     * @param A0100 经理 人员标志
     * @return 员工岗位信息列表
     * @throws SQLException 异常
     */
    List<Map<String, Object>> getEmpInfoListByManagerDateAndA0100(String date, String A0100,String empName) throws SQLException, ParseException;

    /**
     * 员工月度考勤列表
     * @param date yyyy.MM.dd 如果未空表示查当前月
     * @param A0100 员工标志
     * @return 某个员工月度考勤信息
     * @throws SQLException 异常
     */
    List<Map<String,Object>> getMonthCheckList(String date,String A0100) throws SQLException, ParseException;

    /**
     * 员工月度考勤刷卡记录表
     * @param date yyyy.MM.dd 如果未空表示查当前月
     * @param A0100 员工标志
     * @return 某个员工月度考勤刷卡记录表
     * @throws SQLException 异常
     */
    List<Map<String,Object>> getMonthRecordList(String date,String A0100) throws SQLException, ParseException;

}

package com.keega.plat.wecp.dao.map;

import com.keega.common.dal.Dal;
import com.keega.plat.wecp.model.map.MapArray;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2017/1/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Repository
public class MapDao implements IMapDao {

    @Override
    public void makeMap(String jsonPoints, String formatDate, String modifyUser, String modifyDesc)
            throws SQLException {
        Dal.add().into("kq_scope_modify")
                .column("scope", "scope_desc", "modify_user", "modify_date")
                .values(jsonPoints, modifyDesc, modifyUser, formatDate).run();
    }

    @Override
    public List<MapArray> getAllCheckMap() throws SQLException {
        List<MapArray> mapArrayList = new ArrayList<MapArray>();
        String sql = "select * from kq_scope_modify";
        List<Map<String,Object>> mapList = Dal.map().queryList(sql);
        for (int i = 0; i < mapList.size(); i++) {
            MapArray mapArray = new MapArray();
            mapArray.setNumber(i);
            mapArray.setMap(mapList.get(i).get("scope") + "");
            mapArrayList.add(mapArray);
        }
        return mapArrayList;
    }

    @Override
    public Map<String, Object> getScheduleByA0100(String A0100,Date date) throws SQLException {
        String nowTime = simpleDateFormat.format(date);
        String sql = "select onduty_start_1 as sbkq," +  //上班开启
                " onduty_1 as sb," +   //上班
                " be_late_for_1 as cd," +  //迟到
                " absent_work_1 as sbjz," +  //上班截止
                " offduty_start_2 as xbkq," +  //下班开启
                " leave_early_absent_2 as kg," + //旷工
                " leave_early_2 as zt," +  //早退
                " offduty_2 as xb," +   //下班
                " offduty_end_2 as xbjz " +   //下班截止
                " from kq_class where class_id " +
                " in" +
                " (select class_id from kq_employ_shift " +
                " where A0100=? and Q03Z0 = '"+nowTime+"')";
        return Dal.map().query(sql, A0100);
    }

    @Override
    public Integer checkHasSignInByA0100Date(String A0100, Date date) throws SQLException {
        String nowDate = simpleDateFormat.format(date);
        String sql = "select COUNT(0) as signin from kq_originality_data where A0100=?" +
                " and work_date='"+nowDate+"' " +
                " and work_time between " +
                " (select onduty_start_1 as sbkq from kq_class" +
                " where class_id=(select class_id from kq_employ_shift " +
                " where A0100=? and Q03Z0 = '"+nowDate+"')) " +
                " and (select absent_work_1 as sbjz from kq_class" +
                " where class_id=(select class_id from kq_employ_shift " +
                " where A0100=? and Q03Z0 = '"+nowDate+"'))";
        return Dal.single().query(sql,new Object[]{A0100,A0100,A0100});
    }

    @Override
    public Integer checkHasSignOutByA0100Date(String A0100, Date date) throws SQLException {
        String nowDate = simpleDateFormat.format(date);
        String sql = "select COUNT(0) as signout from kq_originality_data where A0100=?" +
                " and work_date='"+nowDate+"' " +
                " and work_time between " +
                " (select offduty_start_2 as xbkq from kq_class" +
                " where class_id=(select class_id from kq_employ_shift " +
                " where A0100=? and Q03Z0 = '"+nowDate+"')) " +
                " and (select offduty_end_2 as xbjz from kq_class" +
                " where class_id=(select class_id from kq_employ_shift " +
                " where A0100=? and Q03Z0 = '"+nowDate+"'))";
        return Dal.single().query(sql,new Object[]{A0100,A0100,A0100});
    }

    @Override
    public void normalCheck(String location, String address, Map<String, Object> user) throws SQLException {
        /*String sql = "insert into kq_originality_data" +
                " (A0100,nbase,A0101,B0110,E0122,E01A1,location,card_no,zuobiao," +
                " work_date,work_time,datafrom,inout_flag,sp_flag)" +
                " values" +
                " (?,'usr',?,?,?,?,?,?,?," +
                " (SELECT CONVERT(varchar(50), GETDATE(), 102))," +
                " (SELECT left(convert(varchar, getdate(), 108), 5))," +
                " '0','0','03')";
        Dal.add().excute(sql, user.get("A0100"), user.get("A0101"), user.get("B0110"),
                user.get("E0122"), user.get("E01A1"), address, user.get("E0127"), location);*/

        String sql = "insert into kq_originality_data\n" +
                "(A0100,nbase,A0101,B0110,E0122,E01A1,location,card_no,zuobiao,status,\n" +
                "work_date,work_time,datafrom,inout_flag,sp_flag)\n" +
                "values\n" +
                "(?,'usr',?,?,?,?,?,?,?,\n" +
                "(\n" +
                "select case when \n" +
                "(select case when \n" +
                "\t\t(select absent_work_1 from kq_class \n" +
                "\t\t\twhere class_id=(select class_id from kq_employ_shift where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102))))\n" +
                "\t\t\t>= (SELECT left(convert(varchar, getdate(), 108), 5))\n" +
                "\t\t\t\tthen 0 else 1 end )\n" +
                "\t-\n" +
                "(select case when \n" +
                "\t\t((SELECT left(convert(varchar, getdate(), 108), 5)) >= (select onduty_start_1 from kq_class\n" +
                "\t\t\t\twhere class_id=(select class_id from kq_employ_shift where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102)))))\n" +
                "\t\t\t\tthen 3 else 5 end \n" +
                "\t\t\t\t)=-3\n" +
                "\t\t\t\t\n" +
                "then '0' \n" +
                "\twhen\n" +
                "\t(select case when \n" +
                "\t\t(select offduty_end_2 from kq_class \n" +
                "\t\t\twhere class_id=(select class_id from kq_employ_shift where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102))))\n" +
                "\t\t\t>= (SELECT left(convert(varchar, getdate(), 108), 5))\n" +
                "\t\t\t\tthen 0 else 1 end )\n" +
                "\t-\n" +
                "(select case when \n" +
                "\t\t((SELECT left(convert(varchar, getdate(), 108), 5)) >= (select offduty_start_2 from kq_class\n" +
                "\t\t\t\twhere class_id=(select class_id from kq_employ_shift where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102)))))\n" +
                "\t\t\t\tthen 3 else 5 end \n" +
                "\t\t\t\t)=-3\n" +
                "then '1' end\n" +
                "),\n" +
                "(SELECT CONVERT(varchar(50), GETDATE(), 102)),\n" +
                "(SELECT left(convert(varchar, getdate(), 108), 5)),\n" +
                "'0','0','03')";
        Dal.add().excute(sql, user.get("A0100"), user.get("A0101"), user.get("B0110"),
                user.get("E0122"), user.get("E01A1"), address, user.get("E0127"), location,
                user.get("A0100"),user.get("A0100"),user.get("A0100"),user.get("A0100"));
    }

    @Override
    public void unNormalCheck(String location, String address, String reason, Map<String, Object> user) throws SQLException {
        /*String sql = "insert into kq_originality_data" +
                " (A0100,nbase,A0101,B0110,E0122,E01A1,location,card_no,zuobiao,liyou," +
                " work_date,work_time,datafrom,inout_flag,sp_flag)" +
                " values" +
                " (?,'usr',?,?,?,?,?,?,?,?," +
                " (SELECT CONVERT(varchar(50), GETDATE(), 102))," +
                " (SELECT left(convert(varchar, getdate(), 108), 5))," +
                " '0','0','03')";
        Dal.add().excute(sql, user.get("A0100"), user.get("A0101"), user.get("B0110"),
                user.get("E0122"), user.get("E01A1"), address, user.get("E0127"), location,reason);*/

        String sql = "insert into kq_originality_data\n" +
                "(A0100,nbase,A0101,B0110,E0122,E01A1,location,card_no,zuobiao,liyou,status,\n" +
                "work_date,work_time,datafrom,inout_flag,sp_flag)\n" +
                "values\n" +
                "(?,'usr',?,?,?,?,?,?,?,?,\n" +
                "(" +
                "select case when \n" +
                "(select case when \n" +
                "\t\t(select absent_work_1 from kq_class \n" +
                "\t\t\twhere class_id=(select class_id from kq_employ_shift where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102))))\n" +
                "\t\t\t>= (SELECT left(convert(varchar, getdate(), 108), 5))\n" +
                "\t\t\t\tthen 0 else 1 end )\n" +
                "\t-\n" +
                "(select case when \n" +
                "\t\t((SELECT left(convert(varchar, getdate(), 108), 5)) >= (select onduty_start_1 from kq_class\n" +
                "\t\t\t\twhere class_id=(select class_id from kq_employ_shift where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102)))))\n" +
                "\t\t\t\tthen 3 else 5 end \n" +
                "\t\t\t\t)=-3\n" +
                "\t\t\t\t\n" +
                "then '0' \n" +
                "\twhen\n" +
                "\t(select case when \n" +
                "\t\t(select offduty_end_2 from kq_class \n" +
                "\t\t\twhere class_id=(select class_id from kq_employ_shift where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102))))\n" +
                "\t\t\t>= (SELECT left(convert(varchar, getdate(), 108), 5))\n" +
                "\t\t\t\tthen 0 else 1 end )\n" +
                "\t-\n" +
                "(select case when \n" +
                "\t\t((SELECT left(convert(varchar, getdate(), 108), 5)) >= (select offduty_start_2 from kq_class\n" +
                "\t\t\t\twhere class_id=(select class_id from kq_employ_shift where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102)))))\n" +
                "\t\t\t\tthen 3 else 5 end \n" +
                "\t\t\t\t)=-3\n" +
                "then '1' end" +
                "),"+
                "(SELECT CONVERT(varchar(50), GETDATE(), 102)),\n" +
                "(SELECT left(convert(varchar, getdate(), 108), 5)),\n" +
                "'0','0','03')";
        Dal.add().excute(sql, user.get("A0100"), user.get("A0101"), user.get("B0110"),
                user.get("E0122"), user.get("E01A1"), address, user.get("E0127"), location,reason,
                user.get("A0100"),user.get("A0100"),user.get("A0100"),user.get("A0100"));

    }

    @Override
    public Map<String, Object> getSignInfo(String A0100) throws SQLException {
        String sql = "select location as dz,liyou as ly,work_date as rq,work_time as sj from kq_originality_data" +
                " where" +
                " A0100=?" +
                " and" +
                " work_date=(SELECT CONVERT(varchar(50), GETDATE(), 102))" +
                " and" +
                " (" +
                "   (" +
                "   work_time between" +
                "   (select onduty_start_1 from kq_class" +
                "   where class_id=(select class_id from kq_employ_shift" +
                "   where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102))))" +
                "   and" +
                "   (select absent_work_1 from kq_class" +
                "   where class_id=(select class_id from kq_employ_shift" +
                "   where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102))))" +
                "   )" +
                " or" +
                "   (" +
                "   work_time between" +
                "   (select offduty_start_2 from kq_class" +
                "   where class_id=(select class_id from kq_employ_shift" +
                "   where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102))))" +
                "   and" +
                "   (select offduty_end_2 from kq_class" +
                "   where class_id=(select class_id from kq_employ_shift" +
                "   where A0100=? and Q03Z0 = (SELECT CONVERT(varchar(50), GETDATE(), 102))))" +
                "   )" +
                " )";
        return Dal.map().query(sql,new Object[]{A0100,A0100,A0100,A0100,A0100});
    }

    @Override
    public Integer isManagerAccountLogin(String A0100) throws SQLException {
        String sql = "select COUNT(0) from t_sys_staff_in_role " +
                " where role_id='00000003' " +
                " and LEFT(staff_id,3) = 'Usr' " +
                " and (RIGHT(staff_id,8))=? ";
        return Dal.single().query(sql,A0100);
    }

    @Override
    public List<Map<String, Object>> getEmpCheckInfoByManagerDateAndA0100(String date, String A0100,String empName) throws SQLException, ParseException {
        if (date == null || "".equals(date)) date = simpleDateFormat.format(new Date());
        date = simpleDateFormat.format(simpleDateFormat.parse(date));
        if (empName == null) empName = "";
        String sql = "select A0100,A0101," +
                "(select codeitemdesc from organization where codeitemid=B0110) as B0110 ," +
                "(select codeitemdesc from organization where codeitemid=E0122) as E0122," +
                "(select codeitemdesc from organization where codeitemid=E01A1) as E01A1," +
                " location,card_no,work_date,work_time,zuobiao,liyou,status" +
                " from kq_originality_data where work_date=? and status='1'" +
                " and A0101 like '%"+empName+"%'" +
                " and B0110=(select B0110 from UsrA01 where A0100 =?) union all\n" +
                " select A0100,A0101," +
                "(select codeitemdesc from organization where codeitemid=B0110) as B0110 ," +
                "(select codeitemdesc from organization where codeitemid=E0122) as E0122," +
                "(select codeitemdesc from organization where codeitemid=E01A1) as E01A1," +
                " location,card_no,work_date,work_time,zuobiao,liyou,status" +
                " from kq_originality_data where work_date=? and status='0' " +
                " and A0101 like '%"+empName+"%'" +
                " and B0110=(select B0110 from UsrA01 where A0100 =?)";
        return Dal.map().queryList(sql, new Object[]{date, A0100, date,A0100});
    }

    @Override
    public List<Map<String, Object>> getEmpInfoListByManagerDateAndA0100(String date, String A0100,String empName) throws SQLException, ParseException {
        if (date == null || "".equals(date)) date = simpleDateFormat.format(new Date());
        date = simpleDateFormat.format(simpleDateFormat.parse(date));
        if (empName == null) empName = "";
        String sql = "select  A0100,A0101," +
                " (select codeitemdesc from organization where codeitemid=B0110) as B0110 ," +
                " (select codeitemdesc from organization where codeitemid=E0122) as E0122 ," +
                " (select codeitemdesc from organization where codeitemid=E01A1) as E01A1 " +
                " from kq_originality_data" +
                " where work_date=? and B0110=(select B0110 from UsrA01 where A0100 =?)" +
                " and A0101 like '%"+empName+"%'" +
                "  group by A0100,A0101,B0110,E0122,E01A1";
        return Dal.map().queryList(sql, new Object[]{date, A0100});
    }

    @Override
    public List<Map<String, Object>> getMonthCheckList(String date, String A0100) throws SQLException, ParseException {
        if (date == null || "".equals(date)) date = simpleDateFormat.format(new Date());
        date = simpleDateFormat.format(simpleDateFormat.parse(date));
        String sql = "select A0100,A0101, " +
                " (select codeitemdesc from organization where codeitemid=B0110) as B0110 ," +
                " (select codeitemdesc from organization where codeitemid=E0122) as E0122," +
                " (select codeitemdesc from organization where codeitemid=E01A1) as E01A1," +
                " location,card_no,work_date,work_time,zuobiao,liyou,status" +
                " from kq_originality_data " +
                " where " +
                " A0100 = ? " +
                " and LEFT(work_date,7)= LEFT('"+date+"',7)";
        return Dal.map().queryList(sql,new Object[]{A0100});
    }

    @Override
    public List<Map<String, Object>> getMonthRecordList(String date, String A0100) throws SQLException, ParseException {
        if (date == null || "".equals(date)) date = simpleDateFormat.format(new Date());
        date = simpleDateFormat.format(simpleDateFormat.parse(date));
        String sql = "select A0100,A0101, \n" +
                " (select codeitemdesc from organization where codeitemid=B0110) as B0110 ,\n" +
                " (select codeitemdesc from organization where codeitemid=E0122) as E0122,\n" +
                " (select codeitemdesc from organization where codeitemid=E01A1) as E01A1,\n" +
                " work_date\n" +
                " from kq_originality_data \n" +
                " where \n" +
                " A0100 = ? \n" +
                " and LEFT(work_date,7)= LEFT('"+date+"',7)\n" +
                " group by A0100,A0101,B0110,E0122,E01A1,work_date";
        return Dal.map().queryList(sql,new Object[]{A0100});
    }

    @Override
    public Map<String, Object> getDepartmentLeaderInfoByEmpA0100(String A0100) throws SQLException {
        String sql = "select A0100,A0101,\n" +
                " (select top 1 codeitemdesc from codeitem where codeitemid=(select B0110 from UsrA01 \n" +
                " where A0100 = (\n" +
                " select RIGHT(staff_id,8) as A0100 from t_sys_staff_in_role \n" +
                " where RIGHT(staff_id,8) \n" +
                " in(select A0100 from UsrA01 where E0122 \n" +
                " in (select E0122 from UsrA01 where A0100=?)) and role_id='00000003'))) as danwei,\n" +
                " (select top 1 codeitemdesc from codeitem where codeitemid=(select E0122 from UsrA01 \n" +
                " where A0100 = (\n" +
                " select RIGHT(staff_id,8) as A0100 from t_sys_staff_in_role \n" +
                " where RIGHT(staff_id,8) \n" +
                " in(select A0100 from UsrA01 where E0122 \n" +
                " in (select E0122 from UsrA01 where A0100=?)) and role_id='00000003'))) as danwei,\n" +
                " (select top 1 codeitemdesc from codeitem where codeitemid=(select E01A1 from UsrA01 \n" +
                " where A0100 = (\n" +
                " select RIGHT(staff_id,8) as A0100 from t_sys_staff_in_role \n" +
                " where RIGHT(staff_id,8) \n" +
                " in(select A0100 from UsrA01 where E0122 \n" +
                " in (select E0122 from UsrA01 where A0100=?)) and role_id='00000003'))) as gangwei\n" +
                " from UsrA01 \n" +
                " where \n" +
                " A0100 = (\n" +
                " select RIGHT(staff_id,8) as A0100 from t_sys_staff_in_role \n" +
                " where RIGHT(staff_id,8) \n" +
                " in(select A0100 from UsrA01 where E0122 \n" +
                " in (select E0122 from UsrA01 where A0100=?)) and role_id='00000003')";
        return Dal.map().query(sql,new Object[]{A0100,A0100,A0100,A0100});
    }

}

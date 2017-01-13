package com.keega.plat.wecp.dao.sys;

import com.keega.common.dal.Dal;
import com.keega.plat.wecp.model.sys.CpSalaryDate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 薪资dao实现类
 * Created by zun.wei on 2017/1/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Repository
public class CpSalaryDao implements ICpSalaryDao {

    @Override
    public List<CpSalaryDate> getSendSalaryDateByA0100(String A0100) throws SQLException {
        String sql = "select top 12 A58Z0 as sendDate from UsrA58 where A0100 ='"+A0100+"' order by A58Z0 desc";
        return Dal.bean().queryList(CpSalaryDate.class, sql);
    }

    @Override
    public Map<String, Object> getSalaryInfoByDateA0100(String a0100, String salaryDate) throws SQLException {
        String sql = "select E58AD as salary from UsrA58 where A0100 =? and A58Z0 = ?";
        return Dal.map().query(sql, new String[]{a0100, salaryDate});
    }

}

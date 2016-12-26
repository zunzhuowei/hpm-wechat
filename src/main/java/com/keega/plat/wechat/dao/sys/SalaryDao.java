package com.keega.plat.wechat.dao.sys;

import com.keega.common.dal.Dal;
import com.keega.plat.wechat.model.sys.salary.SalaryDate;
import com.keega.plat.wechat.util.config.WxConfigInit;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 我的薪资Data Access Object
 *
 * Created by zun.wei on 2016/12/21.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Repository
public class SalaryDao implements ISalaryDao {

    private static List<String> salaryItems = (List<String>) WxConfigInit.getWxConfigData().get("mySalary_map")
            .get("fields");

    @Override
    public List<SalaryDate> getSendSalaryDateByA0100(String A0100) throws SQLException {
        String sql = "select top 12 A58Z0 as sendDate from UsrA58 where A0100 ='"+A0100+"' order by A58Z0 desc";
        return Dal.bean().queryList(SalaryDate.class, sql);
    }

    @Override
    public Map<String, Object> getSalaryInfoByDateA0100(String a0100, String salaryDate) throws SQLException {
        String sql = "select E58AD as salary from UsrA58 where A0100 =? and A58Z0 = ?";
        return Dal.map().query(sql, new String[]{a0100, salaryDate});
    }


}

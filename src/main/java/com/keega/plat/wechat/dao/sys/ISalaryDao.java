package com.keega.plat.wechat.dao.sys;

import com.keega.plat.wechat.model.sys.salary.SalaryDate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2016/12/21.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ISalaryDao {

    /**
     * 获取薪资发放日期
     * @param A0100 人员标志字段
     * @return 薪资发放实体类
     * @throws SQLException 异常
     */
    public List<SalaryDate> getSendSalaryDateByA0100(String A0100) throws SQLException;

    /**
     * 获取薪资
     * @param a0100 人员标志字段
     * @param salaryDate 薪资发放日期
     * @return Map包装的薪资信息
     */
    Map<String,Object> getSalaryInfoByDateA0100(String a0100, String salaryDate) throws SQLException;

}

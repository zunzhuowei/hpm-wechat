package com.keega.plat.wecp.service.core.msg.impl;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wecp.dao.sys.ICpSalaryDao;
import com.keega.plat.wecp.model.sys.CpSalaryDate;
import com.keega.plat.wecp.service.core.msg.ICpSalaryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 薪资业务层实现类
 * Created by zun.wei on 2017/1/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class CpSalaryService implements ICpSalaryService {

    @Resource
    private ICpSalaryDao cpSalaryDao;

    @Override
    public String getSendSalaryDateJsonByA0100(String a0100) throws SQLException {
        List<CpSalaryDate> salaryDateList = cpSalaryDao.getSendSalaryDateByA0100(a0100);
        List<CpSalaryDate> salaryDateListFormat = new ArrayList<CpSalaryDate>();
        for (int i = 0; i < salaryDateList.size(); i++) {
            CpSalaryDate salaryDate = new CpSalaryDate();
            salaryDate.setSendDate(salaryDateList.get(i).getSendDate().substring(0,10));
            salaryDateListFormat.add(salaryDate);
        }
        return JsonUtil.obj2json(salaryDateListFormat);
    }

    @Override
    public String getSalaryInfoByDateA0100(String a0100, String salaryDate) throws SQLException {
        Map<String,Object> salaryMap =  cpSalaryDao.getSalaryInfoByDateA0100(a0100, salaryDate);
        if (salaryMap == null) return null;
        return salaryMap.get("salary") + "";
    }

    @Override
    public String getThisMonthSalaryInfo(String userId) throws SQLException {
        Map<String, Object> salaryMap = cpSalaryDao.getThisMonthSalaryByUserId(userId);
        if (salaryMap == null) return null;
        return salaryMap.get("salary") + "";
    }

    @Override
    public String getTopYearSalaryInfoByUserId(String userId) throws SQLException {
        List<Map<String, Object>> salaryList = cpSalaryDao.getTopYearSalaryByUserId(userId);
        StringBuilder sb = new StringBuilder();
        sb.append("最近12个月的应发工资:\n\n");
        for (int i = 0; i < salaryList.size(); i++) {
            sb.append("时间:").append(salaryList.get(salaryList.size() - 1 - i).get("date")).append("  ")
                    .append("薪资:").append(salaryList.get(salaryList.size() - 1 - i).get("salary")).append("元\n");
        }
        return sb.toString();
    }

}

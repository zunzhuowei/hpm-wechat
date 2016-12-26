package com.keega.plat.wechat.service.sys;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wechat.dao.sys.ISalaryDao;
import com.keega.plat.wechat.model.sys.salary.SalaryDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 薪资接口实现类
 * Created by zun.wei on 2016/12/21.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class SalaryService implements ISalaryService {

    @Resource
    private ISalaryDao salaryDao;

    @Override//获取发放薪资日期的json字符串
    public String getSendSalaryDateJsonByA0100(String a0100) throws SQLException {
        List<SalaryDate> salaryDateList = salaryDao.getSendSalaryDateByA0100(a0100);
        List<SalaryDate> salaryDateListFormat = new ArrayList<SalaryDate>();
        for (int i = 0; i < salaryDateList.size(); i++) {
            SalaryDate salaryDate = new SalaryDate();
            salaryDate.setSendDate(salaryDateList.get(i).getSendDate().substring(0,10));
            salaryDateListFormat.add(salaryDate);
        }
        return JsonUtil.obj2json(salaryDateListFormat);
    }

    @Override//获取薪资
    public String getSalaryInfoByDateA0100(String a0100, String salaryDate) throws SQLException {
        Map<String,Object> salaryMap =  salaryDao.getSalaryInfoByDateA0100(a0100, salaryDate);
        return salaryMap.get("salary") + "";
    }


}

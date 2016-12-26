package com.keega.common.dal;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.keega.common.dal.edit.EditInsert;
import com.keega.common.dal.edit.EditUpdate;
import com.keega.common.dal.edit.EditDelete;
import com.keega.common.dal.query.QueryArray;
import com.keega.common.dal.query.QueryBean;
import com.keega.common.dal.query.QueryMap;
import com.keega.common.dal.query.QuerySingle;

/**
 *<p>名称：数据库操作主类</p>
 *<p>描述：该类在经过spring的@Component注册并初始化后，将父类DalInstance的注入资源转换为本类静态资源</p>
 * @author Administrator
 * @version V1.0
 * @see DalInstance
 */
@Component
public class Dal extends DalInstance{

	/*public Dal(){
		System.out.println("执行了！");
	}*/
	
	/**查询相关操作静态化资源对象**/
	private static QueryArray array;
	private static QueryBean bean;
	private static QueryMap map;
	private static QuerySingle single;


	/**增删改相关操静态化资源对象**/
	private static EditInsert add;
	private static EditUpdate upd;
	private static EditDelete del;
		
	/**将spring注入资源转换为静态资源**/
	@PostConstruct 
    public void init() {
		array = (QueryArray) queryArray;
		bean =  queryBean;
		map = (QueryMap) queryMap;
		single = (QuerySingle) querySingle;
		add = (EditInsert) editInsert;
		upd = (EditUpdate) editUpdate;
		del = (EditDelete) editDelete;
    }

	public static QueryArray array(){
		return array;
	}
	
	public static QueryBean bean(){
		return bean;
	}
	
	public static QueryMap map(){
		return map;
	}
	
	public static QuerySingle single(){
		return single;
	}
	
	public static EditInsert add(){
		return add;
	}
	
	public static EditInsert add(String tableName){
		return add.into(tableName);
	}
	
	public static EditUpdate upd(){
		return upd;
	}
	
	public static EditUpdate upd(String tableName){
		return upd.from(tableName);
	}
	
	public static EditDelete del(){
		return del;
	}
	
	public static EditDelete del(String tableName){
		return del.from(tableName);
	}		
	
}

package com.keega.common.dal;

import java.sql.SQLException;
import java.util.List;

/**
 *<p>名称：查询操作接口类</p>
 *<p>描述：查询操作接口类</p>
 *<p>创建时间：2016-12-07</p>
 *<p>修改时间：2016-12-07</p>
 * @author LiuJH
 * @version V1.0
 * @see 查询操作实现类：
 * <p>com.keega.common.dal.query{@link #QueryArray,QueryBean,QueryMap,QuerySingle}</p>
 */
public interface DalQuery {	
	
	/**
	 * 得到单行查询结果
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public <T> T query(String sql) throws SQLException;
	
	/**
	 * 得到单行查询结果
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public <T> T query(String sql,Object params) throws SQLException;
	
	/**
	 * 得到单行查询结果
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public <T> T query(String sql,Object[] params) throws SQLException;
    
	/**
	 * 得到多行查询结果
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> queryList(String sql) throws SQLException;
	
	/**
	 * 得到多行查询结果
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> queryList(String sql,Object[] params) throws SQLException;
    
	/**
	 * 得到多行查询结果
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> queryList(String sql,Object params) throws SQLException;
	
}

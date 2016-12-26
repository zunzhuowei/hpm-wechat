package com.keega.common.dal;

import java.sql.SQLException;
import java.util.List;

/**
 *<p>名称：增删改操作接口类</p>
 *<p>描述：增删改操作接口类</p>
 * @author Administrator
 * @version V1.0
 * @see 增删改操作实现类：
 * <p>com.keega.common.dal.edit{@link #Insert,Update,Delete}</p>
 */
public interface DalEdit {
	
	/**
	 * 单行处理，sql直传方式
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int excute(String sql) throws SQLException;
	
	/**
	 * 单行处理，预传参方式
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int excute(String sql, Object...params) throws SQLException;
	
	/**
	 * 批sql处理，用于新增、删除
	 * @param sql
	 * @param listValus
	 * @return
	 * @throws SQLException
	 */
	public int batchExcute(String sql, final List<Object[]> listValus) throws SQLException;
	
	/**
	 * 批sql处理，用于删除
	 * @param sql
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public int batchExcute(String sql, final Object... values) throws SQLException;
	
	
}

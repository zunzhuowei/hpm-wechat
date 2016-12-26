package com.keega.common.dal.edit;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import com.keega.common.dal.DalEdit;

/**
 *<p>类描述</p>
 * @author Administrator
 * @version V1.0
 * @see 相关的类
 */
public abstract class Edit implements DalEdit{
	
	@Resource protected JdbcTemplate jdbc;
	
	protected boolean batchFalg;				/**批处理标志，用于判断是否执行批处理**/
	protected String tableName;					/**表名，通用**/
	protected String fieldName;					/**列名，暂时只有删除时有**/
	protected Object fieldValue;				/**列值，暂时只有删除时有**/
	protected List<Object[]> listFieldValue;	/**列值集合，批量操作时用**/
	protected String[] columns;					/**列名数组，用于新增、更新**/	
	protected Object[] values;					/**值数组，用于新增、更新**/
	protected List<Object[]> listValues;		/**值集合数组，用于（批处理）新增、更新**/
	
	public int excute(String sql) throws SQLException {
		return jdbc.update(sql);
	}

	public int excute(String sql, Object...params) throws SQLException {
		return jdbc.update(sql, params);
	}

	public int batchExcute(String sql, final List<Object[]> listValus) throws SQLException {
		return jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {	
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Object[] object = (Object[])listValus.get(i);  
				for (int j = 0; j < object.length; j++) {
					ps.setObject(j+1, object[j]);
				}
			}
			@Override
			public int getBatchSize() {
				return listValus.size();
			}
		}).length;
	}
	
	public int batchExcute(String sql, final Object... values) throws SQLException {
		return jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {	
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, values[i]);
			}
			@Override
			public int getBatchSize() {
				return values.length;
			}
		}).length;
	}
	
}

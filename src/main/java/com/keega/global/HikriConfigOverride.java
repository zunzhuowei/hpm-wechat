package com.keega.global;

import java.util.Properties;
import com.keega.common.utils.DesUtil;
import com.keega.common.utils.ProptsUtil;
import com.zaxxer.hikari.HikariConfig;

/**
 *<p>对HikariConfig配置属性的重写</p>
 *<p>1、由系统辨别使用哪种数据库</p>
 *<p>2、实现对配置文件密码的加解密</p>
 * @author Administrator
 * @version V1.0
 * @see HikariConfig
 */
public class HikriConfigOverride extends HikariConfig{
	
	private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				
	public HikriConfigOverride(){
		//加载数据库属性配置文件
		Properties prop = ProptsUtil.prop("/hikariConfig.properties");
		
		//初始化连接池配置
		init(prop);
		
	}
	
	public void init(Properties prop){
		//解密用户和密码的密匙
		String system_key = ProptsUtil.getString(prop,"system_key");
		
		//连接池相关配置
		String poolName = ProptsUtil.getString(prop,"jdbc.poolName");
		String dbName = ProptsUtil.getString(prop,"jdbc.dbName");
		String dbServer = ProptsUtil.getString(prop,"jdbc.dbServer");
		String dbPort = ProptsUtil.getString(prop,"jdbc.dbPort");
		String username = ProptsUtil.getString(prop,"jdbc.username");
		String password = ProptsUtil.getString(prop,"jdbc.password");
		if (poolName.equals("mssql")) {
			this.setDriverClassName(SQLSERVER_DRIVER);
			this.setJdbcUrl("jdbc:sqlserver://"+dbServer+":"+dbPort+";databaseName=" + dbName);
		}else if(poolName.equals("oracle")){
			this.setDriverClassName(ORACLE_DRIVER);
			this.setJdbcUrl("jdbc:oracle:thin:@"+dbServer+":"+dbPort+":"+dbName);
		}
		this.setUsername(DesUtil.decode(system_key, username));
		this.setPassword(DesUtil.decode(system_key, password));
		this.setAutoCommit(true);
		this.setConnectionTimeout(ProptsUtil.getLong(prop,"jdbc.connectionTimeout"));
		this.setIdleTimeout(ProptsUtil.getLong(prop,"jdbc.idleTimeout"));
		this.setMinimumIdle(ProptsUtil.getInt(prop,"jdbc.minimumIdle"));
		this.setMaximumPoolSize(ProptsUtil.getInt(prop,"jdbc.maximumPoolSize"));
		this.setMaxLifetime(ProptsUtil.getInt(prop,"jdbc.maxLifetime"));
		this.setAllowPoolSuspension(true);
	}
		
}

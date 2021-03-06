package com.tmt.core.persistence.dialect;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import com.google.common.collect.Maps;
import com.tmt.core.persistence.Database;
import com.tmt.core.persistence.datasource.DataSourceHolder;

/**
 * 设置一些不同数据库的支持的方案
 * 
 * @author lifeng
 */
public interface Dialect {

	/**
	 * 是否支持分页
	 * 
	 * @return
	 */
	default boolean supportsLimit() {
		return true;
	}

	/**
	 * 分页
	 * 
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	String getLimitString(String sql, int offset, int limit);

	/**
	 * 高效的插入数据
	 * 
	 * @param sql
	 * @param bytes
	 */
	default int bulkSave(List<String> sqls, byte[] bytes) {
		DataSource dataSource = DataSourceHolder.getDataSource();
		Connection conn = DataSourceUtils.getConnection(dataSource);
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			for (String sql : sqls) {
				stmt.addBatch(sql);
			}
			int[] result = stmt.executeBatch();
			return result.length;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
		return 0;
	}

	/**
	 * 默认 Mysql ，会根据mysql 定义一些必要的命令集
	 * 
	 * @return
	 */
	default Database getDatabase() {
		return Database.mysql;
	}

	/**
	 * 定义一些特殊的全局变量
	 * 
	 * @return
	 */
	default Map<String, String> variables() {
		return Maps.newHashMap();
	}
}
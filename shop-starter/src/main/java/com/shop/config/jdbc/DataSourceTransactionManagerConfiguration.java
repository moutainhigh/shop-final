package com.shop.config.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.shop.config.jdbc.DataBaseAutoConfiguration.DataSourceAutoConfiguration;
import com.shop.config.jdbc.database.DruidDataSourceAutoConfiguration;
import com.shop.config.jdbc.database.HikariDataSourceAutoConfiguration;
import com.shop.config.jdbc.database.SqlLiteDataSourceAutoConfiguration;
import com.shop.config.jdbc.transaction.DataSourceTransactionManagerAutoConfiguration;
import com.shop.config.jdbc.transaction.TransactionAutoConfiguration;

/**
 * 数据库事务 -- 使用spring boot 的配置
 * 
 * @author lifeng
 */
@org.springframework.context.annotation.Configuration
@ConditionalOnClass({ JdbcTemplate.class, PlatformTransactionManager.class })
@ConditionalOnSingleCandidate(DataSource.class)
@Import({ DataSourceTransactionManagerAutoConfiguration.class, TransactionAutoConfiguration.class })
@AutoConfigureAfter({DataSourceAutoConfiguration.class,SqlLiteDataSourceAutoConfiguration.class, DruidDataSourceAutoConfiguration.class,
		HikariDataSourceAutoConfiguration.class})
public class DataSourceTransactionManagerConfiguration {

}

package com.shop.config.jdbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.tmt.Constants;
import com.tmt.common.utils.StringUtil3;

/**
 * Configuration properties for MyBatis.
 *
 * @author Eddú Meléndez
 * @author Kazuki Shimizu
 */
@ConfigurationProperties(prefix = Constants.MYBATIS_PREFIX)
public class MybatisProperties {

	/**
	 * Location of MyBatis xml config file.
	 */
	private String configLocation;

	/**
	 * Locations of MyBatis mapper files.
	 */
	private String[] mapperLocations;

	/**
	 * Packages to search type aliases. (Package delimiters are ",; \t\n")
	 */
	private String typeAliasesPackage;

	/**
	 * Packages to search for type handlers. (Package delimiters are ",; \t\n")
	 */
	private String typeHandlersPackage;

	/**
	 * Indicates whether perform presence check of the MyBatis xml config file.
	 */
	private boolean checkConfigLocation = false;

	/**
	 * Execution mode for {@link org.mybatis.spring.SqlSessionTemplate}.
	 */
	private ExecutorType executorType;

	/**
	 * Externalized properties for MyBatis configuration.
	 */
	private Properties configurationProperties;

	/**
	 * A Configuration object for customize default settings. If
	 * {@link #configLocation} is specified, this property is not used.
	 */
	@NestedConfigurationProperty
	private Configuration configuration;

	/**
	 * @since 1.1.0
	 */
	public String getConfigLocation() {
		return this.configLocation;
	}

	/**
	 * @since 1.1.0
	 */
	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}

	@Deprecated
	public String getConfig() {
		return this.configLocation;
	}

	@Deprecated
	public void setConfig(String config) {
		this.configLocation = config;
	}

	public String[] getMapperLocations() {
		return this.mapperLocations;
	}

	public void setMapperLocations(String[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public String getTypeHandlersPackage() {
		return this.typeHandlersPackage;
	}

	public void setTypeHandlersPackage(String typeHandlersPackage) {
		this.typeHandlersPackage = typeHandlersPackage;
	}

	public String getTypeAliasesPackage() {
		return this.typeAliasesPackage;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public boolean isCheckConfigLocation() {
		return this.checkConfigLocation;
	}

	public void setCheckConfigLocation(boolean checkConfigLocation) {
		this.checkConfigLocation = checkConfigLocation;
	}

	public ExecutorType getExecutorType() {
		return this.executorType;
	}

	public void setExecutorType(ExecutorType executorType) {
		this.executorType = executorType;
	}

	/**
	 * @since 1.2.0
	 */
	public Properties getConfigurationProperties() {
		return configurationProperties;
	}

	/**
	 * @since 1.2.0
	 */
	public void setConfigurationProperties(Properties configurationProperties) {
		this.configurationProperties = configurationProperties;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * 获取 Mappers
	 * 
	 * @return
	 */
	public Resource[] resolveMapperLocations() {
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		List<Resource> resources = new ArrayList<Resource>();

		// 自定义的配置
		if (this.mapperLocations != null) {
			for (String mapperLocation : this.mapperLocations) {
				try {
					Resource[] mappers = resourceResolver.getResources(mapperLocation);
					resources.addAll(Arrays.asList(mappers));
				} catch (IOException e) {
					// ignore
				}
			}
		}

		// 默认的配置
		if (Constants.BOOT_CLASSES.size() != 0) {
			String mapperLocations = StringUtil3.format("classpath*:%s/**/dao/*.Mapper.xml", StringUtil3
					.substringBeforeLast(Constants.BOOT_CLASSES.get(0).getName(), ".").replaceAll("\\.", "/"));
			this.mapperLocations = new String[] { "classpath*:com/tmt/**/dao/*.Mapper.xml", mapperLocations };
			for (String mapperLocation : this.mapperLocations) {
				try {
					Resource[] mappers = resourceResolver.getResources(mapperLocation);
					resources.addAll(Arrays.asList(mappers));
				} catch (IOException e) {
					// ignore
				}
			}
		}

		// 默认添加一层配置
		try {
			resources.addAll(Arrays.asList(
					resourceResolver.getResources("classpath*:com/tmt/common/persistence/config/*.Mapper.xml")));
		} catch (IOException e) {
			// ignore
		}
		return resources.toArray(new Resource[resources.size()]);
	}
}
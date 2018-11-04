package com.shop.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 系统配置
 * @author lifeng
 */
@ConfigurationProperties(prefix = "spring.application")
public class ApplicationProperties {

	// 系统配置
	private String version = "1.0";
	private String serverSn = "server-1-1";
	private String serialization = "kryo_pool";
	
	private Web web = new Web();
	private Security security = new Security();
	
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	public Web getWeb() {
		return web;
	}
	public void setWeb(Web web) {
		this.web = web;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getServerSn() {
		return serverSn;
	}
	public void setServerSn(String serverSn) {
		this.serverSn = serverSn;
	}
	public String getSerialization() {
		return serialization;
	}
	public void setSerialization(String serialization) {
		this.serialization = serialization;
	}
	
	public static class Web {
		
        private Integer maxUploadSize = 52428800;
        private Integer maxInMemorySize = 4096;
        
		public Integer getMaxUploadSize() {
			return maxUploadSize;
		}
		public void setMaxUploadSize(Integer maxUploadSize) {
			this.maxUploadSize = maxUploadSize;
		}
		public Integer getMaxInMemorySize() {
			return maxInMemorySize;
		}
		public void setMaxInMemorySize(Integer maxInMemorySize) {
			this.maxInMemorySize = maxInMemorySize;
		}
	}
	
	public static class Security {
		
		private Integer maxAge = 60 * 60* 24 * 10;
		private String domain;
		private String path;
		private Integer sessionTimeout = 1800;
		private String cacheName = "authorization";
		
		public String getCacheName() {
			return cacheName;
		}
		public void setCacheName(String cacheName) {
			this.cacheName = cacheName;
		}
		public int getMaxAge() {
			return maxAge;
		}
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public Integer getSessionTimeout() {
			return sessionTimeout;
		}
		public void setSessionTimeout(Integer sessionTimeout) {
			this.sessionTimeout = sessionTimeout;
		}
		public void setMaxAge(Integer maxAge) {
			this.maxAge = maxAge;
		}
	}
}
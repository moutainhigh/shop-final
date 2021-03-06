package com.shop.config.storager;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 存储的属性
 * 
 * @author lifeng
 */
@ConfigurationProperties("spring.storager")
public class StoragerProperties {

	private String storagePath = "file:statics/";
	private String urlPath;
	private String domain;
	
	public String getStoragePath() {
		return storagePath;
	}
	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
}
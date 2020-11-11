package com.tmt.wechat.bean.massmsg;

import java.io.Serializable;

public class MassVideo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mediaId;
	private String title;
	private String description;
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
package com.weixin.dubbo.vo;

import java.io.Serializable;

public class AccessToken implements Serializable {
	private static final long serialVersionUID = 1L;
	String aid;
	String app_Id;
	String app_secret;
	String access_token;
	String cre_time;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getApp_Id() {
		return app_Id;
	}

	public void setApp_Id(String appId) {
		app_Id = appId;
	}

	public String getApp_secret() {
		return app_secret;
	}

	public void setApp_secret(String appSecret) {
		app_secret = appSecret;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}

	public String getCre_time() {
		return cre_time;
	}

	public void setCre_time(String creTime) {
		cre_time = creTime;
	}
}

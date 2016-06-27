package com.weixin.dubbo.service;

import com.weixin.dubbo.vo.AccessToken;

/** 
 * @author 王振宇
 * @version 1.0 2016-2-24 NEW
 * @function 
 */
public interface IWeiXinService {
   
	/**
	 * 保存微信AccessToken
	 * @param entity
	 * @return
	 */
	public Boolean saveAccessToken(AccessToken entity);
	
	/**
	 * 获取授权key
	 * @param grant_type
	 * @param appid
	 * @param secret
	 * @return
	 */
	public AccessToken getAccessToken(String grant_type, String appid, String secret);
}

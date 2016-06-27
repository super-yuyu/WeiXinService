package com.weixin.dubbo.service;

import com.weixin.dubbo.vo.AccessToken;

/** 
 * @author ������
 * @version 1.0 2016-2-24 NEW
 * @function 
 */
public interface IWeiXinService {
   
	/**
	 * ����΢��AccessToken
	 * @param entity
	 * @return
	 */
	public Boolean saveAccessToken(AccessToken entity);
	
	/**
	 * ��ȡ��Ȩkey
	 * @param grant_type
	 * @param appid
	 * @param secret
	 * @return
	 */
	public AccessToken getAccessToken(String grant_type, String appid, String secret);
}

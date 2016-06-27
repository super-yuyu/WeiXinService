package com.weixin.dubbo.dao;

import com.weixin.dubbo.vo.AccessToken;

/**
 * @author Õı’Ò”Ó
 * @version 1.0 2016-2-25 NEW
 * @function
 */
public interface IWeiXinReaderServiceDao {
	
	/**
	 * ±£¥ÊŒ¢–≈AccessToken
	 * @param entity
	 * @return
	 */
	public AccessToken getAccessToken(String appid, String app_secret);
	
}

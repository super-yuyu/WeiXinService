package com.weixin.dubbo.dao;

import com.weixin.dubbo.vo.AccessToken;

/**
 * @author ������
 * @version 1.0 2016-2-25 NEW
 * @function
 */
public interface IWeiXinWriteServiceDao {
	
	/**
	 * ����΢��AccessToken
	 * @param entity
	 * @return
	 */
	public Boolean save(AccessToken entity);
	
	public Boolean update(AccessToken entity);
}

package com.weixin.dubbo.service.impl;

import java.util.Date;

import com.weixin.dubbo.dao.IWeiXinReaderServiceDao;
import com.weixin.dubbo.dao.IWeiXinWriteServiceDao;
import com.weixin.dubbo.service.IWeiXinService;
import com.weixin.dubbo.vo.AccessToken;
import com.weixin.dubbo.wx.GetAccessToken;
import com.weixin.dubbo.wx.WXToken;

/**
 * @author 王振宇
 * @version 1.0 2016-2-24 NEW
 * @function
 */
public class WeiXinServiceImpl implements IWeiXinService {
	IWeiXinWriteServiceDao writeDao = null;
	IWeiXinReaderServiceDao readerDao = null;
	GetAccessToken getAccessToken;
	
	/**
	 * 保存token信息
	 */
	@Override
	public Boolean saveAccessToken(AccessToken entity) {
		return writeDao.save(entity);
	}
	
	/**
	 * 获取token信息
	 */
	public AccessToken getAccessToken(String grant_type, String appid, String secret) {
		AccessToken entity = readerDao.getAccessToken(appid, secret);
		if (entity != null) {
			Long times = Long.valueOf(entity.getCre_time());
			Long newTimes = new Date().getTime();
			Long etimes = newTimes - times;
			WXToken token = null;
			if ((etimes / 1000) >= 7200) {
				token = getAccessToken.toAccessToKen(grant_type, appid, secret);	
			}
			if (token != null) {
				entity.setCre_time(newTimes.toString());
				entity.setAccess_token(token.getAccess_token());
				// 重新填充数据
				writeDao.update(entity);
			}
		}
		return entity;
	}

	public IWeiXinWriteServiceDao getDao() {
		return writeDao;
	}

	public void setDao(IWeiXinWriteServiceDao writeDao) {
		this.writeDao = writeDao;
	}

	public IWeiXinWriteServiceDao getWriteDao() {
		return writeDao;
	}

	public void setWriteDao(IWeiXinWriteServiceDao writeDao) {
		this.writeDao = writeDao;
	}

	public IWeiXinReaderServiceDao getReaderDao() {
		return readerDao;
	}

	public GetAccessToken getGetAccessToken() {
		return getAccessToken;
	}

	public void setGetAccessToken(GetAccessToken getAccessToken) {
		this.getAccessToken = getAccessToken;
	}

	public void setReaderDao(IWeiXinReaderServiceDao readerDao) {
		this.readerDao = readerDao;
	}
}

package com.weixin.dubbo.dao.impl;

import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import redis.clients.jedis.Jedis;

import com.weixin.dubbo.dao.IWeiXinReaderServiceDao;
import com.weixin.dubbo.sqldao.AccessTokenDao;
import com.weixin.dubbo.sqldao.IDaoBack;
import com.weixin.dubbo.util.AbstractBaseRedisReaderDao;
import com.weixin.dubbo.vo.AccessToken;

/**
 * @author 王振宇
 * @version 1.0 2016-2-25 NEW
 * @function
 */
public class WeiXinReaderServiceDao extends AbstractBaseRedisReaderDao<String, AccessToken> implements IWeiXinReaderServiceDao {
	AccessTokenDao sqldao = null;
	WeiXinWriteServiceDao writeDao;
	
	@Override
	public AccessToken getAccessToken(final String appid, final String app_secret) {
		// TODO Auto-generated method stub
		AccessToken result = redisTemplate.execute(new RedisCallback<AccessToken>() {
			public AccessToken doInRedis(RedisConnection connection)
					throws DataAccessException {
				final Jedis jedis = (Jedis) connection.getNativeConnection();
				
				// 当redis不存在数据的时候 查询数据库查询到数据重新插入到redis当中
				sqldao.setDaoBack(new IDaoBack() {
					
					@Override
					public void run(AccessToken entity) {
						// TODO Auto-generated method stub
						writeDao.update(entity);
					}
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
					}
				});
				// 检查是否存在此key
				Set<String> suzu = jedis.sinter("sean.access.token.app_Id."+appid, "sean.access.token.app_secret."+app_secret);
				AccessToken enttiy = null;
				for(String str: suzu) {
					enttiy = new AccessToken();
					String value = str;
					String aid = jedis.hget("access_token_"+value, "aid");
					String app_Id = jedis.hget("access_token_"+value, "app_Id");
					String app_secret = jedis.hget("access_token_"+value, "app_secret");
					String access_token = jedis.hget("access_token_"+value, "access_token");
					String cre_time = jedis.hget("access_token_"+value, "cre_time");
					enttiy.setAid(aid);
					enttiy.setApp_Id(app_Id);
					enttiy.setApp_secret(app_secret);
					enttiy.setAccess_token(access_token);
					enttiy.setCre_time(cre_time);
				}
				if (enttiy == null) {
					enttiy = sqldao.getAccessToKen(appid, app_secret);
				}
				return enttiy;
			}
		});
		return result;
	}

	public AccessTokenDao getSqldao() {
		return sqldao;
	}

	public void setSqldao(AccessTokenDao sqldao) {
		this.sqldao = sqldao;
	}

	public WeiXinWriteServiceDao getWriteDao() {
		return writeDao;
	}

	public void setWriteDao(WeiXinWriteServiceDao writeDao) {
		this.writeDao = writeDao;
	}
}

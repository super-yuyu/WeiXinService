package com.weixin.dubbo.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import redis.clients.jedis.Jedis;

import com.weixin.dubbo.dao.IWeiXinWriteServiceDao;
import com.weixin.dubbo.sqldao.AccessTokenDao;
import com.weixin.dubbo.sqldao.IDaoBack;
import com.weixin.dubbo.util.AbstractBaseRedisWriteDao;
import com.weixin.dubbo.vo.AccessToken;

/**
 * @author 王振宇
 * @version 1.0 2016-2-25 NEW
 * @function
 */
public class WeiXinWriteServiceDao extends AbstractBaseRedisWriteDao<String, AccessToken> implements IWeiXinWriteServiceDao {
	AccessTokenDao sqldao = null;
	
	@Override
	public Boolean save(final AccessToken entity) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				final Jedis jedis = (Jedis) connection.getNativeConnection();
				
				// 通过SQLDao 里进行调用 可以进行混滚操作
				sqldao.setDaoBack(new IDaoBack() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						jedis.sadd("sean.access.token.aid."+entity.getAid(), entity.getAid());
						jedis.sadd("sean.access.token.app_Id."+entity.getApp_Id(), entity.getAid());
						jedis.sadd("sean.access.token.app_secret."+entity.getApp_secret(), entity.getAid());
						
						// 保存数据
						Map<String, String> map = new HashMap<String, String>();
						map.put("aid", entity.getAid());
						map.put("app_Id", entity.getApp_Id());
						map.put("app_secret", entity.getApp_secret());
						map.put("access_token", entity.getAccess_token());
						map.put("cre_time", entity.getCre_time());
						jedis.hmset("access_token_"+entity.getAid(), map);
					}

					@Override
					public void run(AccessToken token) {
						// TODO Auto-generated method stub
						
					}
				});
				sqldao.save(entity);
				return true;
			}
		});
		return result;
	}
	
	@Override
	public Boolean update(final AccessToken entity) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				
				Jedis jedis = (Jedis) connection.getNativeConnection();
				
				jedis.sadd("sean.access.token.aid."+entity.getAid(), entity.getAid());
				jedis.sadd("sean.access.token.app_Id."+entity.getApp_Id(), entity.getAid());
				jedis.sadd("sean.access.token.app_secret."+entity.getApp_secret(), entity.getAid());
				// 保存数据
				Map<String, String> map = new HashMap<String, String>();
				map.put("aid", entity.getAid());
				map.put("app_Id", entity.getApp_Id());
				map.put("app_secret", entity.getApp_secret());
				map.put("access_token", entity.getAccess_token());
				map.put("cre_time", entity.getCre_time());
				jedis.hmset("access_token_"+entity.getAid(), map);
				return true;
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
}

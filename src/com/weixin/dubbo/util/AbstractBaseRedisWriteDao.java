package com.weixin.dubbo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author ������
 * @version 1.0 2016-2-25 NEW
 * @function
 */
public abstract class AbstractBaseRedisWriteDao<K, V> {
	@Autowired
	protected RedisTemplate<K, V> redisTemplate;

	/**
	 * ����redisTemplate
	 * 
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * ��ȡ RedisSerializer <br>
	 * ------------------------------<br>
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}
}

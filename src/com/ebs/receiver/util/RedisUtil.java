package com.ebs.receiver.util;

import java.util.Map;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class RedisUtil {

	public static Logger logger = Logger.getLogger(RedisUtil.class);
	// map存储
	public static void setMap(String key, Map<String, String> map) {
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			jedis.hmset(key, map);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
	}

	// map获取
	public static Map<String, String> getMap(String key) {
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
		return null;
	}

	public static void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			Redis.getInstance().returnResource(jedis);
		}

	}

	public static String getValue(String key) {
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			return jedis.get(key);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
		return null;
	}

	  /**
	 * 设置过期时间
	 * 
	 * @author ruan 2013-4-11
	 * @param key
	 * @param seconds
	 */
	public static void expire(String key, int seconds) {
		if (seconds <= 0) { 
			return;
		}
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
	}
	/**
	 * 通过map里面的key值获取value值
	 * 
	 * **/
	public static String  getValueFromMap(String key,String mapKey)
	{
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			return jedis.hmget(key, mapKey).get(0);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
		return null;
		
		
	}

	
	

}

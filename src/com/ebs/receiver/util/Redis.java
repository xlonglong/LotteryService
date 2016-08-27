package com.ebs.receiver.util;

import com.ebs.receiver.conf.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {

	//Redis服务器IP
		//private static String addr=Configuration.getGlobalMsg("redis.addr");
		private static String addr="";
		//Redis的端口
	   // private static int port=Integer.parseInt(Configuration.getGlobalMsg("redis.port"));
		private static int port=6379;
	    //访问密码
	   // private static String AUTH=Configuration.getUserMsg("redis.auth");
	    private static String AUTH="admin";
	  //可用连接实例的最大数目，默认值为8；
	       //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	     //private static int MAX_ACTIVE = Integer.parseInt(Configuration.getGlobalMsg("redis.max_active"));
	     private static int MAX_ACTIVE = 1024;
	   //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	   //    private static int MAX_IDLE = Integer.parseInt(Configuration.getGlobalMsg("redis.max_idle"));
	       private static int MAX_IDLE=200;
	       //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
//	     private static String MAX_WAIT = Configuration.getGlobalMsg("redis.max_wait");
	       private static String MAX_WAIT="10000";
	   //  private static int TIMEOUT = Integer.parseInt(Configuration.getGlobalMsg("redis.timeout"));
	       private static int TIMEOUT=10000;
	    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	     private static boolean TEST_ON_BORROW = true;
	     
	     private static JedisPool jedisPool ;
	    
	     private static Redis redis=new Redis();
	     public Redis()
	     {
	    	 //初始化参数
	    	 
	    	initParam(); 
	    	//初始化Redis连接池
	    	initRedisPool();
	    	 
	     }
	     
	     //初始化参数
	     public void initParam()
	     {
	    	 addr= Configuration.getGlobalMsg("redis.addr");
	    	 port=Integer.parseInt(Configuration.getGlobalMsg("redis.port"));
	    	 AUTH=Configuration.getGlobalMsg("redis.auth");
	    	 MAX_ACTIVE = Integer.parseInt(Configuration.getGlobalMsg("redis.max_active"));
	    	 MAX_IDLE = Integer.parseInt(Configuration.getGlobalMsg("redis.max_idle"));
	    	 MAX_WAIT = Configuration.getGlobalMsg("redis.max_wait");
	    	 TIMEOUT = Integer.parseInt(Configuration.getGlobalMsg("redis.timeout"));
	     }
	     
	     /**
	       * 初始化Redis连接池
	     */
	     
	     public void initRedisPool(){
	      try{
	    		  JedisPoolConfig config = new JedisPoolConfig();
	       	   		config.setMaxActive(MAX_ACTIVE);
	    	       config.setMaxIdle(MAX_IDLE);
	    		   config.setMaxWait(Long.parseLong(MAX_WAIT));
	    	       config.setTestOnBorrow(TEST_ON_BORROW);
	    		   jedisPool = new JedisPool(config,addr,port,TIMEOUT,AUTH);  
	    		 }catch(Exception e)
	    	 {
	    		 e.printStackTrace();
	    	 }

	     }
	     /**
	      * 获取Jedis实例
	      * @return
	     */
	     public synchronized  Jedis getJedis() {
	         try {
	             if (jedisPool != null) {
	                 Jedis resource = jedisPool.getResource();
	                 return resource;
	            } else {
	                 return null;
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	            return null;
	        }
	     }
	     
	    /**
	     * 释放jedis资源
	     * @param jedis
	     */
	     public  void returnResource(final Jedis jedis) {
	        if (jedis != null) {
	            jedisPool.returnResource(jedis);
	        }
	    }
	     
	     /**
			 * 获取唯一实例.
			 * @return
			 */
			public  static Redis getInstance(){
				if(redis==null){
					redis=new Redis();
				}
				
				 return redis;
			}
			
			
}

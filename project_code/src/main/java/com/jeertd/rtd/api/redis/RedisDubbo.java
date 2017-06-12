/**
 * Copyright &copy; 2012-2016 <a href="https://git.oschina.net/guanshijiehnan/JeeRTD">JeeSite</a> All rights reserved.
 */
package com.jeertd.rtd.api.redis;

/**
 * 单表生成Servicez
 * @author ThinkGem
 * @version 2017-05-04
 */
public interface RedisDubbo {
	public String get(String key);
	public String set(String key,String value,int cacheSeconds);
	public long delete(String key);
	
}
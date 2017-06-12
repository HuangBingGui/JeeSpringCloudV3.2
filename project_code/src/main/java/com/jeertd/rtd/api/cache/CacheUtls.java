/**
 * Copyright &copy; 2012-2016 <a href="https://git.oschina.net/guanshijiehnan/JeeRTD">JeeSite</a> All rights reserved.
 */
package com.jeertd.rtd.api.cache;

/**
 * 单表生成Servicez
 * @author ThinkGem
 * @version 2017-05-04
 */
public interface CacheUtls {
	public String get(String key,String cacheName);
	public String set(String key,String value,String cacheName);
	public boolean delete(String key,String cacheName);
	
}
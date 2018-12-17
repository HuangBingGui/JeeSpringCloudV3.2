package com.jeespring.common.redis;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.jeespring.common.config.Global;
import com.jeespring.common.security.MD5Tools;
import com.sun.tools.javac.util.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * redicache 工具类
 * 在application.yml文件内的Spring-redis的run配置启动,true为启动;false为不启动;
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtils {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    public static String SHIRO_REDIS="shiro_redis_cache";
    public static String SHIRO_REDIS_OBJECT="org.apache.shiro.subject.SimplePrincipalCollection";
    public static String GET_ID =".get id=";
    public static String TOTAL_KEY =".total key=";
    public static String FIND_LIST_KEY =".findList key=";
    public static String FIND_LIST_FIRST_KEY =".findListFirst key=";
    public static String FIND_PAGE_KEY =".findPage key=";
    public static String FIND_LIST_KEY_PATTERN =".findList key=*";
    public static String FIND_LIST_FIRST_KEY_PATTERN =".findListFirst key=*";
    public static String FIND_PAGE_KEY_PATTERN =".findPage key=*";
    public static String KEY_PREFIX =Global.getConfig("spring.redis.keyPrefix");
    public static Long expireTime= Long.parseLong(Global.getConfig("spring.redis.expireTime"));
    public static Long expireTimeShiro= Long.parseLong(Global.getConfig("spring.redis.expireTimeShiro"));
    public static String RUN_MESSAGE="请开启Redis服务,还有Redis密码配置是否有误，而且密码不能为空（为空时Redis服务会连接不上）。";
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static String getExpire(){
        if(expireTime!=null) {
            return String.valueOf(expireTime / 60) + "分钟";
        } else {
            return "0分钟";
        }
    }

    public static String getExpireShiro(){
        if(expireTimeShiro!=null) {
            return String.valueOf(expireTimeShiro / 60) + "分钟";
        } else {
            return "0分钟";
        }
    }

    public static String getKey(String className,String keyName,String keyId){
        if(className==null){className="";}
        if(keyId==null){keyId="";}
        return RedisUtils.KEY_PREFIX+className+keyName+keyId;
    }

    public static String getIdKey(String className,String keyId){
        if(className==null){className="";}
        if(keyId==null){keyId="";}
        return RedisUtils.KEY_PREFIX+className+RedisUtils.GET_ID+keyId;
    }

    public static String getTotalKey(String className,String keyId){
        if(className==null){className="";}
        if(keyId==null){keyId="";}
        return RedisUtils.KEY_PREFIX+className+RedisUtils.TOTAL_KEY+MD5Tools.MD5(keyId).substring(0,20);
    }

    public static String getFindListKey(String className,String keyId){
        if(className==null){className="";}
        if(keyId==null){keyId="";}
        return RedisUtils.KEY_PREFIX+className+RedisUtils.FIND_LIST_KEY+MD5Tools.MD5(keyId).substring(0,20);
    }

    public static String getFindListFirstKey(String className,String keyId){
        if(className==null){className="";}
        if(keyId==null){keyId="";}
        return RedisUtils.KEY_PREFIX+className+RedisUtils.FIND_LIST_FIRST_KEY+MD5Tools.MD5(keyId).substring(0,20);
    }

    public static String getFindPageKey(String className,String keyId){
        if(className==null){className="";}
        if(keyId==null){keyId="";}
        return  RedisUtils.KEY_PREFIX+className+RedisUtils.FIND_PAGE_KEY+MD5Tools.MD5(keyId).substring(0,20);
    }

    public static String getFindListKeyPattern(String className){
        if(className==null){className="";}
        return RedisUtils.KEY_PREFIX+className+FIND_LIST_KEY_PATTERN;
    }

    public static String getFinPageKeyPattern(String className){
        if(className==null){className="";}
        return RedisUtils.KEY_PREFIX+className+FIND_PAGE_KEY_PATTERN;
    }

    /**
     * 获取token的有效期（秒）
     * @param key
     */
    public long getExpireTime(String key){
        long time = redisTemplate.getExpire(key,TimeUnit.SECONDS);
        return time;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        if(!run()) { return; }
        try{
            for (String key : keys) {
                remove(key);
            }
        } catch (Exception e) {
            logger.error("RedisUtils remove:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(String pattern) {
        if(!run()) { return;}
        if(!listFlush()){
            return ;
        }
        try{
            if(pattern==null) {
                pattern = "";
            }
            Set<String> keys=getKyes(pattern);
            if (keys.size() > 0) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            logger.error("RedisUtils removePattern:"+RUN_MESSAGE+e.getMessage(),RUN_MESSAGE+ e.getMessage());
        }
    }

    public void removePatternShiroReids(String pattern){
        if(!run()) { return;}
        if(!listFlush()){
            return ;
        }
        try{
            if(pattern==null) {
                pattern = "";
            }
            Set<String> keys=getKyesShiroReids(pattern);
            if (keys.size() > 0){
                stringRedisTemplate.delete(keys);
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            logger.error("RedisUtils removePattern:"+RUN_MESSAGE+e.getMessage(),RUN_MESSAGE+ e.getMessage());
        }
    }
    /**
     * 获取keys
     *
     * @param pattern
     */
    public Set<String> getKyes(String pattern) {
        if(!run()) { return null; }
        try{
            if(pattern==null){ pattern=""; }
            Set<String> keys=stringRedisTemplate.keys("*"+pattern);
            Set<String> keysnew=new HashSet<String>();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                keysnew.add(it.next().substring(7));
            }
           return keysnew;
        } catch (Exception e) {
            logger.error("RedisUtils getKyes:"+RUN_MESSAGE+e.getMessage(), e.getMessage());
            return null;
        }
    }

    public Set<String> getKyesShiroReids(String pattern) {
        if(!run()) { return null; }
        try{
            if(pattern==null){ pattern=""; }
            Set<String> keys=stringRedisTemplate.keys("*"+pattern);
            Set<String> keysnew=new HashSet<String>();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String tr = it.next();
                if(tr.contains(SHIRO_REDIS)) {
                    keysnew.add(tr);
                } else if(tr.contains(SHIRO_REDIS_OBJECT)) {
                    keysnew.add(tr.substring(8));
                }
            }
            return keysnew;
        } catch (Exception e) {
            logger.error("RedisUtils getKyes:"+RUN_MESSAGE+e.getMessage(), e.getMessage());
            return null;
        }
    }

    public Set<String> getKyesAll() {
        if(!run()) { return null; }
        try{
            Set<String> keys=stringRedisTemplate.keys("*");
            Set<String> keysnew=new HashSet<String>();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                keysnew.add(it.next());
            }
            return keysnew;
        } catch (Exception e) {
            logger.error("RedisUtils getKyes:"+RUN_MESSAGE+e.getMessage(), e.getMessage());
            return null;
        }
    }

    /**
     * 获取Count
     *
     */
    public int getCount() {
        if(!run()) { return 0; }
        try{
            Set<String> keys=stringRedisTemplate.keys("*");
            return keys.size();
        } catch (Exception e) {
            logger.error("RedisUtils getCount:"+RUN_MESSAGE+e.getMessage(), e.getMessage());
            return 0;
        }
    }

    public int getCountShiro() {
        if(!run()) { return 0; }
        try{
            Set<String> keys=stringRedisTemplate.keys(SHIRO_REDIS+"*");
            return keys.size();
        } catch (Exception e) {
            logger.error("RedisUtils getCount:"+RUN_MESSAGE+e.getMessage(), e.getMessage());
            return 0;
        }
    }
    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if(!run()) { return ; }
        try{
            if(key.contains(SHIRO_REDIS)) {
                stringRedisTemplate.delete(key);
            }else{
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            logger.error("RedisUtils exists:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        if(!run()) { return false; }
        boolean retuslt=false;
        try{
            if(key.contains(SHIRO_REDIS)) {
                retuslt = stringRedisTemplate.hasKey(key);
            } else {
                retuslt = redisTemplate.hasKey(key);
            }
        } catch (Exception e) {
            logger.error("RedisUtils exists:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
        return retuslt;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        if(!run()) { return null; }
        Object result = null;
        try{
            if(key.contains(SHIRO_REDIS)){
                ValueOperations<String, String> operationsString = stringRedisTemplate.opsForValue();
                result = operationsString.get(key);
            }else {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                result = operations.get(key);
            }
            return result;
        }catch (Exception e){
            logger.error("RedisUtils get:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        if(!run()) { return false; }
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("RedisUtils set:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        if(!run()) { return false; }
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("RedisUtils set:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
        return result;
    }

    public boolean set(final String key, Object value, Long expireTime,TimeUnit unit) {
        if(!run()) { return false; }
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, unit);
            result = true;
        } catch (Exception e) {
            logger.error("RedisUtils set:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
        return result;
    }

    private boolean run(){
        if(Global.getConfig("spring.redis.run")=="true") {
            return true;
        }
        return false;
    }

    public static boolean isRun(){
        if(Global.getConfig("spring.redis.run")=="true") {
            return true;
        }
        return false;
    }

    public static boolean isShireRedis(){
        if(Global.getConfig("spring.redis.run")!="true") {
            return false;
        }
        if(Global.getConfig("shiro.redis")!="true") {
            return false;
        }
        return true;
    }

    private boolean listFlush(){
        if(Global.getConfig("spring.redis.listFlush")=="true") {
            return true;
        }
        return false;
    }
}
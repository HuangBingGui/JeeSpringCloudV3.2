package com.jeespring.common.redis;

import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/rest/redis")
@Api(value="Redis云数据缓存接口", description="Redis云数据缓存接口")
public class RedisRestController {

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "/test",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis Test信息(Content-Type为text/html)", notes="Redis Test(Content-Type为text/html)")
    public String  test(){
        redisUtils.set("Redis Test", "Redis Test");
        String string= redisUtils.get("Redis Test").toString();
        return string;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    @RequestMapping(value ="/removeList",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis remove接口(Content-Type为text/html)", notes="Redis remove接口(Content-Type为text/html)")
    public Result removeList(@RequestParam(required=false) String keys) {
        String [] keysList=keys.split(",");
        for(String key:keysList) {
            redisUtils.remove(key);
        }
        return ResultFactory.getSuccessResult("移除成功！");
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    @RequestMapping(value ="/removePattern",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis remove pattern接口(Content-Type为text/html)", notes="Redis remove pattern接口接口(Content-Type为text/html)")
    public Result removePattern(@RequestParam(required=false) String pattern) {
        redisUtils.removePattern(pattern);
        return ResultFactory.getSuccessResult("移除成功！");
    }

    @RequestMapping(value ="/removePatternShiroRedis",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis remove pattern Shiro Reids接口(Content-Type为text/html)", notes="Redis remove pattern Shiro Reids接口接口(Content-Type为text/html)")
    public Result removePatternShiroReids(@RequestParam(required=false) String pattern) {
        redisUtils.removePatternShiroReids(pattern);
        return ResultFactory.getSuccessResult("移除成功！");
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    @RequestMapping(value ="/remove",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis remove接口(Content-Type为text/html)", notes="Redis remove接口(Content-Type为text/html)")
    public Result remove(@RequestParam(required=false) String key) {
        redisUtils.remove(key);
        return ResultFactory.getSuccessResult("移除成功！");
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @RequestMapping(value ="/exists",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis exists接口(Content-Type为text/html)", notes="Redis exists接口(Content-Type为text/html)")
    public Result exists(@RequestParam(required=false) String key) {
        if(redisUtils.exists(key)) {
            return ResultFactory.getSuccessResult("存在！");
        } else {
            return ResultFactory.getErrorResult("不存在！");
        }
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @RequestMapping(value ="/get",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis get接口(Content-Type为text/html)", notes="Redis get接口(Content-Type为text/html)")
    public Result get(@RequestParam(required=false) String key) {
        if(redisUtils.exists(key)){
            Result result = ResultFactory.getSuccessResult();
            Object obj=redisUtils.get(key);
            if(obj!=null) {
                result.setResultObject(obj.toString());
            }
            return result;
        }else{
            return ResultFactory.getErrorResult("不存在！");
        }
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value ="/set",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis set接口(Content-Type为text/html)", notes="Redis set接口(Content-Type为text/html)")
    public Result set(@RequestParam(required=false) String key,@RequestParam(required=false)  String value) {
        if(redisUtils.set(key,value)){
            Result result = ResultFactory.getSuccessResult("添加/更新成功！");
            return result;
        }else{
            return ResultFactory.getErrorResult("添加/更新失败！");
        }
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value ="/setExpireTime",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis set expireTime接口(Content-Type为text/html)", notes="Redis set expireTime接口接口(Content-Type为text/html)")
    public Result set(@RequestParam(required=false) String key,@RequestParam(required=false) String value,@RequestParam(required=false) Long expireTime) {
        if(redisUtils.set(key,value,expireTime)){
            Result result = ResultFactory.getSuccessResult("添加/更新成功！");
            return result;
        }else{
            return ResultFactory.getErrorResult("添加/更新失败！");
        }
    }

    /**
     * 获取keys
     *
     * @param pattern
     */
    @RequestMapping(value ="/getKyes",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis get kyes接口(Content-Type为text/html)", notes="Redis get kyes接口接口(Content-Type为text/html)")
    public Result getKyes(@RequestParam(required=false) String pattern) {
        Set<String> keys = redisUtils.getKyes(pattern);
        Result result = ResultFactory.getSuccessResult("获取Keys成功！");
        result.setResultObject(keys);
        return result;
    }

    @RequestMapping(value ="/getKyesAll",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis get kyes all接口(Content-Type为text/html)", notes="Redis get kyes all接口(Content-Type为text/html)")
    public Result getKyesAll() {
        Set<String> keys = redisUtils.getKyesAll();
        Result result = ResultFactory.getSuccessResult("获取Keys成功！");
        result.setResultObject(keys);
        return result;
    }

    /**
     * 获取keys
     *
     * @param pattern
     */
    @RequestMapping(value ="/getCount",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis get count接口(Content-Type为text/html)", notes="Redis get count接口(Content-Type为text/html)")
    public Result getCount(@RequestParam(required=false) String pattern) {
        Result result = ResultFactory.getSuccessResult("获取数量成功！");
        result.setResultObject(redisUtils.getCount());
        return result;
    }

    @RequestMapping(value ="/getCountShiro",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis get count shiro接口(Content-Type为text/html)", notes="Redis get count shiro接口(Content-Type为text/html)")
    public Result getCountShiro(@RequestParam(required=false) String pattern) {
        Result result = ResultFactory.getSuccessResult("获取数量成功！");
        result.setResultObject(redisUtils.getCountShiro());
        return result;
    }

    /**
     * 获取token的有效期（秒）
     * @param key
     */
    @RequestMapping(value ="/getExpireTime",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis get ExpireTime接口(Content-Type为text/html)", notes="Redis get ExpireTime接口接口(Content-Type为text/html)")
    public Result getExpireTime(@RequestParam(required=false) String key){
        Result result = ResultFactory.getSuccessResult("获取token的有效期（秒）成功！");
        result.setResultObject(redisUtils.getExpireTime(key));
        return result;
    }

    /**
     * 获取缓存有效期成功
     */
    @RequestMapping(value ="/getExpire",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis get getExpire(Content-Type为text/html)", notes="Redis get getExpire(Content-Type为text/html)")
    public Result getExpire(){
        Result result = ResultFactory.getSuccessResult("获取缓存有效期成功！");
        result.setResultObject(RedisUtils.getExpire());
        return result;
    }

    /**
     * 获取单点登录缓存有效期成功
     */
    @RequestMapping(value ="/getExpireShiro",method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="Redis get getExpireShiro(Content-Type为text/html)", notes="Redis get getExpireShiro(Content-Type为text/html)")
    public Result getExpireShiro(){
        Result result = ResultFactory.getSuccessResult("获取单点登录缓存有效期成功！");
        result.setResultObject(RedisUtils.getExpireShiro());
        return result;
    }
}
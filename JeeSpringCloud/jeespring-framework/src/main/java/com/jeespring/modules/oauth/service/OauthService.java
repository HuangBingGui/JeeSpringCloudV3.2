package com.jeespring.modules.oauth.service;

import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.utils.IdGen;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import com.jeespring.modules.sys.dao.UserDao;
import com.jeespring.modules.sys.entity.SysConfig;
import com.jeespring.modules.oauth.entity.TokenInfo;
import com.jeespring.modules.sys.entity.User;
import com.jeespring.modules.sys.service.SysConfigService;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OauthService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private RedisUtils redisUtils;

    public boolean isOauthOpen(){
        SysConfig sysConfigOauth=new SysConfig();
        sysConfigOauth.setType("oauthOpen");
        SysConfig sysConfigsOauth=sysConfigService.findListFirstCache(sysConfigOauth);
        if("true".equals(sysConfigsOauth.getValue())) {
            return true;
        }
        return false;
    }

    public Result token( String oauthId, String oauthSecret,String ip){
        if(oauthId==null || oauthSecret==null) {
            return ResultFactory.getErrorResult("授权ID和授权密钥不能为空！");
        }

        String tokenTime="360000";
        String tokenRedis= String.valueOf(redisUtils.get(oauthId));
        if(tokenRedis!=null){
            TokenInfo tokenInfoRedis=(TokenInfo)redisUtils.get(tokenRedis);
            redisUtils.remove(oauthId);
            redisUtils.remove(tokenInfoRedis.getToken());
            redisUtils.remove(ip);
            redisUtils.set(tokenInfoRedis.getToken(),tokenInfoRedis,Long.valueOf(tokenTime));
            redisUtils.set(oauthId,tokenInfoRedis.getToken(),Long.valueOf(tokenTime));
            redisUtils.set(ip,tokenInfoRedis.getToken(),Long.valueOf(tokenTime));
            Result result = ResultFactory.getSuccessResult();
            result.setResultObject(tokenInfoRedis);
            return result;
        }

        SysConfig sysConfigTokenTime=new SysConfig();
        sysConfigTokenTime.setType("tokenTime");
        List<SysConfig> sysConfigsTokenTimes=sysConfigService.findList(sysConfigTokenTime);
        if(sysConfigsTokenTimes.size()>0){
            tokenTime=sysConfigsTokenTimes.get(0).getValue();
        }

        User user=new User();
        user.setOauthId(oauthId);
        user.setOauthSecret(oauthSecret);
        List<User> users=userDao.findList(user);

        SysConfig sysConfigOauthId=new SysConfig();
        sysConfigOauthId.setType("oauthId");
        List<SysConfig> sysConfigsOauthId=sysConfigService.findList(sysConfigOauthId);
        SysConfig sysConfigOauthSecret=new SysConfig();
        sysConfigOauthSecret.setType("oauthSecret");
        List<SysConfig> sysConfigsOauthSecret=sysConfigService.findList(sysConfigOauthSecret);

        if( sysConfigsOauthId.size()>0 && sysConfigsOauthSecret.size()>0 && (!sysConfigsOauthId.get(0).getValue().equals(oauthId) || !sysConfigsOauthSecret.get(0).getValue().equals(oauthSecret))){
            return ResultFactory.getErrorResult("授权ID和授权密钥不正确！");
        }
        if( sysConfigsOauthId.size()==0 &&sysConfigsOauthSecret.size()==0 && users.size()!=1){
            return ResultFactory.getErrorResult("授权ID和授权密钥不正确！");
        }
        Result result = ResultFactory.getSuccessResult();
        TokenInfo tokenInfo=new TokenInfo();
        tokenInfo.setToken(IdGen.uuid());
        tokenInfo.setTokenDate(new Date());
        tokenInfo.setOauthId(oauthId);
        tokenInfo.setOauthSecret(oauthSecret);
        tokenInfo.setIp(ip);
        result.setResultObject(tokenInfo);
        redisUtils.set(tokenInfo.getToken(),tokenInfo,Long.valueOf(tokenTime));
        redisUtils.set(oauthId,tokenInfo.getToken(),Long.valueOf(tokenTime));
        redisUtils.set(ip,tokenInfo.getToken(),Long.valueOf(tokenTime));
        return result;
    }

    public Result checkToken(String token,String ip){
        String tokenRedis=String.valueOf(redisUtils.get(ip));
        if(token==null && tokenRedis==null){
            return  ResultFactory.getErrorResult("token不能为空！");
        }
        if(token==null && tokenRedis!=null){
            TokenInfo tokenInfoRedis=(TokenInfo)redisUtils.get(tokenRedis);
            Result result = ResultFactory.getSuccessResult();
            result.setResultObject(tokenInfoRedis);
            return  result;
        }
        SysConfig sysConfigOauth=new SysConfig();
        sysConfigOauth.setType("oauthOpen");
        List<SysConfig> sysConfigsOauth=sysConfigService.findList(sysConfigOauth);
        if(sysConfigsOauth.size()<=0){
            Result result =  ResultFactory.getErrorResult("oauth权限服务未开启！");
            result.setResultCode(100);
            return result;
        }
        if(sysConfigsOauth.get(0).getValue()=="false"){
            Result result =  ResultFactory.getErrorResult("oauth权限服务未开启！");
            result.setResultCode(100);
            return result;
        }

        TokenInfo tokenInfo=(TokenInfo)redisUtils.get(token);
        if(tokenInfo==null){
            return  ResultFactory.getErrorResult("Token不正确！");
        }
        Result result = ResultFactory.getSuccessResult();
        result.setResultObject(tokenInfo);
        return  result;
    }

    public Result userOnlineAmount(){
        SysConfig 	userOnlineAmount=new SysConfig();
        userOnlineAmount.setType("userOnlineAmount");
        userOnlineAmount=sysConfigService.findListFirstCache(userOnlineAmount);
        int countShiro=redisUtils.getCountShiro();
        Subject subject = SecurityUtils.getSubject();
        String key=null;
        if (subject != null && subject.getSession() != null) {
            key = redisUtils.SHIRO_REDIS + ":" + subject.getSession().getId().toString();
        }
        if(Integer.valueOf(userOnlineAmount.getValue())<countShiro && key!=null){
            redisUtils.remove(key);
            return ResultFactory.getErrorResult("在线控制：在线"+countShiro+"人/总控制"+userOnlineAmount.getValue()+"人");
        }
        if(!redisUtils.exists(key)){
            return ResultFactory.getErrorResult("在线控制：在线"+countShiro+"人/总控制"+userOnlineAmount.getValue()+"人");
        }
        return ResultFactory.getSuccessResult("在线控制：在线"+countShiro+"人/总控制"+userOnlineAmount.getValue()+"人");
    }


    public Result getApiTimeLimi(String ip){
        String redisKey="ApiTimeLimi_"+ip;
        SysConfig apiTimeLimi=new SysConfig();
        apiTimeLimi.setType("apiTimeLimi");
        apiTimeLimi=sysConfigService.findListFirstCache(apiTimeLimi);
        Object result=redisUtils.get(redisKey);
        if(result ==null){
            return ResultFactory.getSuccessResult("/"+apiTimeLimi.getValue());
        }
        return ResultFactory.getSuccessResult(result+"/"+apiTimeLimi.getValue());
    }

    public Result getApiTime(){
        String redisKeyDay="ApiTimeDate";
        String redisKeyMonth="ApiTimeMonth";
        Object apiTimeDay=redisUtils.get(redisKeyDay);
        Object apiTimeMonth=redisUtils.get(redisKeyMonth);
        if(apiTimeDay==null) {
            apiTimeDay = "0";
        }
        if(apiTimeMonth==null) {
            apiTimeMonth = "0";
        }
        return ResultFactory.getSuccessResult("Day:"+apiTimeDay+" Time;Month:"+apiTimeMonth+" Time");
    }
    public Result setApiTime(){
        String redisKeyDay="ApiTimeDate";
        String redisKeyMonth="ApiTimeMonth";
        Object apiTimeDay=redisUtils.get(redisKeyDay);
        Object apiTimeMonth=redisUtils.get(redisKeyMonth);
        Long apiTimeDayLong=0L;
        Long apiTimeMonthLong=0L;
        if(apiTimeDay==null) {
            apiTimeDay = 0;
        }
        if(apiTimeMonth==null) {
            apiTimeMonth = 0;
        }
        if(apiTimeDay!=null){
            apiTimeDayLong=Long.valueOf(apiTimeDay.toString());
            apiTimeDayLong=apiTimeDayLong+1;
        }
        if(apiTimeMonth!=null){
            apiTimeMonthLong=Long.valueOf(apiTimeMonth.toString());
            apiTimeMonthLong=apiTimeMonthLong+1;
        }
        redisUtils.set(redisKeyDay,apiTimeDayLong,1L, TimeUnit.DAYS);
        redisUtils.set(redisKeyMonth,apiTimeMonthLong,30L, TimeUnit.DAYS);
        return ResultFactory.getSuccessResult("Day:"+apiTimeDayLong+" Time;Month:"+apiTimeMonthLong+" Time");
    }
    public Result ApiTimeLimi(String ip){
        String redisKey="ApiTimeLimi_"+ip;
        Object apiTime=redisUtils.get(redisKey);
        if(apiTime==null){
            apiTime=0;
        }
        Object apiTimeLongRedis=redisUtils.get(redisKey);
        Long apiTimeLong=0L;
        if(apiTimeLongRedis!=null){
            apiTimeLong=Long.valueOf(apiTimeLongRedis.toString());
        }
        Long apiTimeLongSysConfig=0L;
        apiTimeLong+=1;

        SysConfig apiTimeLimi=new SysConfig();
        apiTimeLimi.setType("apiTimeLimi");
        List<SysConfig> apiTimeLimis=sysConfigService.findList(apiTimeLimi);
        if(apiTimeLimis.size()>0){
            apiTimeLongSysConfig=Long.valueOf(apiTimeLimis.get(0).getValue());
        }
        if("-1".equals(apiTimeLongSysConfig)){
            apiTimeLongSysConfig=100000000L;
        }
        if(apiTimeLimis.size()==0){
            apiTimeLongSysConfig=10000L;
        }
        if(apiTime!=null && apiTimeLongSysConfig>apiTimeLong){
            redisUtils.remove(redisKey);
        }
        if(apiTimeLongSysConfig<=apiTimeLong){
            Result result=ResultFactory.getErrorResult("调用失败，接口允许最多调用"+apiTimeLongSysConfig+"次！");
            result.setResultObject(apiTimeLongSysConfig);
            return result;
        }
        redisUtils.set(redisKey,apiTimeLong);
        return ResultFactory.getSuccessResult();
    }
}

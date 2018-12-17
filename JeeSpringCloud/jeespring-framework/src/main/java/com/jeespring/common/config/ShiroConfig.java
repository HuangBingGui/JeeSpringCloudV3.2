package com.jeespring.common.config;

import com.jeespring.common.filter.LogoutFilter;
import com.jeespring.common.filter.OnlineSessionFilter;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.security.shiro.session.CacheSessionDAO;
import com.jeespring.common.security.shiro.session.SessionManager;
import com.jeespring.modules.sys.dao.OnlineSessionDAO;
import com.jeespring.modules.sys.dao.OnlineSessionFactory;
import com.jeespring.modules.sys.security.FormAuthenticationFilter;
import com.jeespring.modules.sys.security.SystemAuthorizingRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;
import com.jeespring.common.filter.SyncOnlineSessionFilter;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro的控制类
 * 下面方法的顺序不能乱
 * Created by zhao.weiwei
 * create on 2017/1/11 10:59
 * the email is zhao.weiwei@jyall.com.
 */
@Component
public class ShiroConfig {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    public OnlineSessionDAO sessionDAO;
    @Autowired
    public OnlineSessionFactory sessionFactory;

    // Session超时时间，单位为毫秒（默认30分钟）
    @Value("${shiro.session.expireTime}")
    private int expireTime;

    //启动shiro redis缓存，单点登录
    //@Value("${shiro.redis}")
    //private String shiroRedis;

    // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
    @Value("${shiro.session.validationInterval}")
    private int validationInterval;

    // 验证码开关
    @Value("${shiro.user.captchaEbabled}")
    private boolean captchaEbabled;

    // 验证码类型
    @Value("${shiro.user.captchaType}")
    private String captchaType;

    // 设置Cookie的域名
    @Value("${shiro.cookie.domain}")
    private String domain;

    // 设置cookie的有效访问路径
    @Value("${shiro.cookie.path}")
    private String path;

    // 设置HttpOnly属性
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

    // 设置Cookie的过期时间，秒为单位
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    // 登录地址
    @Value("${shiro.user.loginUrl}")
    private String loginUrl="/admin/login";

    // 权限认证失败地址
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

    /**
     * 全局的环境变量的设置
     * shiro的拦截
     *
     * @param environment
     * @param adminPath
     * @return
     */
    @Bean(name = "shiroFilterChainDefinitions")
    public String shiroFilterChainDefinitions(Environment environment
            , @Value("${adminPath}") String adminPath
            , @Value("${frontPath}") String frontPath) {
        //Global.resolver = new RelaxedPropertyResolver(environment);
        StringBuilder string=new StringBuilder();
        string.append("/static/** = anon\n");
        string.append("/staticViews/** = anon\n");
        string.append("/jeeSpringStatic/** = anon\n");
        string.append("/userfiles/** = anon\n");
        string.append("/rest/** = anon\n");
        string.append(frontPath+"/** = anon\n");
        string.append( adminPath + "/basic = basic\n");
        string.append( adminPath + "/login = authc\n");
        string.append( adminPath + "/loginBase = anon\n");
        string.append( adminPath + "/logout = logout\n");
        string.append(  adminPath + "/register = anon\n");
        string.append( adminPath + "/sys/register/registerUser = anon\n");
        string.append( adminPath + "/sys/user/validateLoginName = anon\n");
        string.append( adminPath + "/sys/user/validateMobile = anon\n");
        string.append(  adminPath + "/** = user\n");
        string.append(  "/ReportServer/** = user");
        return string.toString();
    }

    @Bean(name = "basicHttpAuthenticationFilter")
    public BasicHttpAuthenticationFilter casFilter(@Value("${adminPath:/a}") String adminPath) {
        BasicHttpAuthenticationFilter basicHttpAuthenticationFilter = new BasicHttpAuthenticationFilter();
        basicHttpAuthenticationFilter.setLoginUrl(adminPath + "/login");
        return basicHttpAuthenticationFilter;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            @Value("${adminPath:/a}") String adminPath,
            BasicHttpAuthenticationFilter basicHttpAuthenticationFilter,
            FormAuthenticationFilter formAuthenticationFilter,
            DefaultWebSecurityManager securityManager,
            @Qualifier("shiroFilterChainDefinitions") String shiroFilterChainDefinitions) {
        Map<String, Filter> filters = new HashMap<>();
        filters.put("basic", basicHttpAuthenticationFilter);
        filters.put("authc", formAuthenticationFilter);
        filters.put("syncOnlineSession", syncOnlineSessionFilter());
        //filters.put("onlineSession", onlineSessionFilter());
        filters.put("logout", logoutFilter());
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setFilters(filters);
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl(adminPath + "/login");
        bean.setSuccessUrl(adminPath + "?login");
         // Shiro过滤器配置
        bean.setFilterChainDefinitions(shiroFilterChainDefinitions);
        return bean;
    }

    @Bean(name = "shiroCacheManager")
    public EhCacheManager shiroCacheManager(CacheManager manager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(manager);
        return ehCacheManager;
    }


    //@Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager(String redisHostName,String reidsPassword,int reidsPort,int expireTimeShiro) {
        RedisCacheManager redisCacheManager=  new RedisCacheManager();
        RedisManager redisManager= new RedisManager();
        redisManager.setHost(redisHostName);
        redisManager.setPassword(reidsPassword);
        redisManager.setPort(reidsPort);
        redisManager.setExpire(expireTimeShiro);
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }



    @Bean(name = "sessionManager")
    public SessionManager sessionManager(CacheSessionDAO dao) {
        SessionManager sessionManager = new SessionManager();
        sessionManager.setSessionDAO(dao);
        // 设置全局session超时时间
        sessionManager.setGlobalSessionTimeout(86400000);
        // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
        sessionManager.setSessionValidationInterval(1800000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookie(new SimpleCookie("com.jeespring.session.id"));
        sessionManager.setSessionIdCookieEnabled(true);
        // 删除过期的session
        sessionManager.setDeleteInvalidSessions(true);
        // 去掉 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 是否定时检查session
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 自定义SessionDao
        //sessionManager.setSessionDAO(sessionDAO());
        // 自定义sessionFactory
        //sessionManager.setSessionFactory(sessionFactory());
        return sessionManager;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(
            SystemAuthorizingRealm systemAuthorizingRealm,
            SessionManager sessionManager,
            EhCacheManager ehCacheManager,
            @Value("${spring.redis.run}") String redisRun,
            @Value("${spring.redis.hostName}") String redisHostName,
            @Value("${spring.redis.password}") String reidsPassword,
            @Value("${spring.redis.port}") int redisPort,
            @Value("${spring.redis.expireTimeShiro}") int expireTimeShiro,
            @Value("${shiro.redis}") String shiroRedis
            ) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager);
        if("true".equals(redisRun) && "true".equals(shiroRedis)){
            try{
                // 加入缓存管理器
                defaultWebSecurityManager.setCacheManager(redisCacheManager(redisHostName,reidsPassword,redisPort,expireTimeShiro));
            } catch (Exception e) {
                logger.error("RedisUtils run:"+RedisUtils.RUN_MESSAGE+e.getMessage(), RedisUtils.RUN_MESSAGE+e.getMessage());
                defaultWebSecurityManager.setCacheManager(ehCacheManager);
            }
        }else{
            // 加入缓存管理器
            defaultWebSecurityManager.setCacheManager(ehCacheManager);
        }
        defaultWebSecurityManager.setRealm(systemAuthorizingRealm);
        return defaultWebSecurityManager;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 自定义在线用户处理过滤器
     */

    public OnlineSessionFilter onlineSessionFilter()
    {
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        //onlineSessionFilter.setLoginUrl(loginUrl);
        return onlineSessionFilter;
    }

    /**
     * 自定义在线用户同步过滤器
     */
    @Bean
    public SyncOnlineSessionFilter syncOnlineSessionFilter()
    {
        SyncOnlineSessionFilter syncOnlineSessionFilter = new SyncOnlineSessionFilter();
        return syncOnlineSessionFilter;
    }


    public LogoutFilter logoutFilter()
    {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setLoginUrl(loginUrl);
        return logoutFilter;
    }
}

package com.jeespring.modules.sys.interceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeespring.common.config.Global;
import com.jeespring.common.utils.CacheUtils;
import com.jeespring.common.utils.Exceptions;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.modules.sys.dao.LogDao;
import com.jeespring.modules.sys.dao.MenuDao;
import com.jeespring.modules.sys.entity.Log;
import com.jeespring.modules.sys.entity.Menu;

/**
 * Created by
 * Created on 2017/1/15 20:55
 * JeeSpring
 */
@Component
public class LogThread extends Thread {

    public static LinkedBlockingQueue<InterceptorLogEntity> interceptorLogQueue = new LinkedBlockingQueue<>();
    private static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";
    private static Logger logger = LoggerFactory.getLogger(LogThread.class);
    @Autowired
    private LogDao logDao;
    @Autowired
    private MenuDao menuDao;

    @Override
	public void run() {
        logger.info("start the InterceptorLog  thread");
        while (true) {
            try {
                InterceptorLogEntity entiry = interceptorLogQueue.take();
                Log log = entiry.getLog();
                Exception ex = entiry.getEx();
                Object handler = entiry.getHandler();
                // 获取日志标题
                if (StringUtils.isBlank(log.getTitle())) {
                    String permission = "";
                    if (handler instanceof HandlerMethod) {
                        Method m = ((HandlerMethod) handler).getMethod();
                        RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
                        permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");
                    }
                    log.setTitle(getMenuNamePath(log.getRequestUri(), permission));
                    if(StringUtils.isBlank(log.getTitle())){
                        log.setTitle(log.getRequestUri());
                    }
                }
                // 如果有异常，设置异常信息
                //log.setException(Exceptions.getStackTraceAsString(ex));
                if(ex!=null) {
                    log.setException(ex.getMessage());
                }
                // 如果无标题并无异常日志，则不保存信息
                if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())) {
                    continue;
                }
                log.setId(UUID.randomUUID().toString());
                if(log.getParams()!=null && log.getParams().length()>=500) {
                    log.setParams(log.getParams().substring(0, 200));
                }
                logDao.insert(log);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

    /**
     * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
     */
    private String getMenuNamePath(String requestUri, String permission) {
        String href = StringUtils.substringAfter(requestUri, Global.getAdminPath());
        @SuppressWarnings("unchecked")
        Map<String, String> menuMap = (Map<String, String>) CacheUtils.get(CACHE_MENU_NAME_PATH_MAP);
        if (menuMap == null) {
            menuMap = Maps.newHashMap();
            List<Menu> menuList = menuDao.findAllList(new Menu());
            for (Menu menu : menuList) {
                // 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
                String namePath = "";
                if (menu.getParentIds() != null) {
                    List<String> namePathList = Lists.newArrayList();
                    for (String id : StringUtils.split(menu.getParentIds(), ",")) {
                        if (Menu.getRootId().equals(id)) {
                            continue; // 过滤跟节点
                        }
                        for (Menu m : menuList) {
                            if (m.getId().equals(id)) {
                                namePathList.add(m.getName());
                                break;
                            }
                        }
                    }
                    namePathList.add(menu.getName());
                    namePath = StringUtils.join(namePathList, "-");
                }
                // 设置菜单名称路径
                if (StringUtils.isNotBlank(menu.getHref())) {
                    menuMap.put(menu.getHref(), namePath);
                } else if (StringUtils.isNotBlank(menu.getPermission())) {
                    for (String p : StringUtils.split(menu.getPermission())) {
                        menuMap.put(p, namePath);
                    }
                }

            }
            CacheUtils.put(CACHE_MENU_NAME_PATH_MAP, menuMap);
        }
        String menuNamePath = menuMap.get(href);
        if (menuNamePath == null) {
            for (String p : StringUtils.split(permission)) {
                menuNamePath = menuMap.get(p);
                if (StringUtils.isNotBlank(menuNamePath)) {
                    break;
                }
            }
            if (menuNamePath == null) {
                return "";
            }
        }
        return menuNamePath;
    }
}

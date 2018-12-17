package com.jeespring.modules.job.task;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.utils.HttpRequest;
import com.jeespring.common.utils.SendMailUtil;
import com.jeespring.modules.sys.entity.SysConfig;
import com.jeespring.modules.server.entity.SysServer;
import com.jeespring.modules.sys.entity.SysUserOnline;
import com.jeespring.modules.sys.service.SysConfigService;
import com.jeespring.modules.server.service.SysServerService;
import com.jeespring.modules.server.service.ISysServerService;
import com.jeespring.modules.sys.service.SysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务调度测试
 * 
 * @author JeeSpring
 */
@Component("jeeSpringTask")
public class JeeSpringTask
{
    @Autowired
    SysUserOnlineService sysUserOnlineService;
    @Autowired
    private ISysServerService sysServerService;
    @Autowired
    private SysConfigService sysConfigService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void jeeSpringParams(String params)
    {
        System.out.println(dateFormat.format(new Date()) + " | " + "定时任务调度测试 com.jeespring.modules.job.task.JeeSpringTask.jeeSpringParams("+params+")");
    }

    public void jeeSpringNoParams()
    {
        System.out.println(dateFormat.format(new Date()) + " | " + "定时任务调度测试 com.jeespring.modules.job.task.JeeSpringTask.jeeSpringNoParams()");
        SysUserOnlineCount();
    }

    public void SysUserOnlineCount(){
        Page<com.jeespring.modules.sys.entity.SysUserOnline> page=new Page<com.jeespring.modules.sys.entity.SysUserOnline>();
        SysUserOnline sysUserOnline=new SysUserOnline();
        page.setPageSize(100000);
        sysUserOnline.setStatus("on_line");
        Page<SysUserOnline> pageSysUserOnline=sysUserOnlineService.findPageCache(page,sysUserOnline);
        SysConfig sysConfig = new SysConfig();
        sysConfig.setType("toExceptionMailAddr");
        sysConfig=sysConfigService.findListFirstCache(sysConfig);
        if(pageSysUserOnline.getList().size()>0){
            SendMailUtil.sendCommonMail(sysConfig.getValue(),"发送在线用户数邮件 SysUserOnlineCount "+pageSysUserOnline.getList().size()+"人","发送在线用户数邮件 SysUserOnlineCount "+pageSysUserOnline.getList().size()+"人");
        }
    }

    public void serverStatus(){
        List<SysServer> sysServers=sysServerService.findAllList(new SysServer());
        List<SysServer> sysServersBug=new ArrayList<SysServer>();
        String message="";
        for (SysServer item:sysServers){
            item.getServerAddress();
            if("Down".equals(HttpRequest.sendGet(item.getServerAddress(), ""))){
                sysServersBug.add(item);
                message=message+item.getName()+" "+item.getServerAddress()+" Down;<br>";
                item.setStatus("off_line");
                sysServerService.save(item);
            }else{
                message=message+item.getName()+" "+item.getServerAddress()+" OK;<br>";
                item.setStatus("on_line");
                sysServerService.save(item);
            }
        }
        SysConfig sysConfig = new SysConfig();
        sysConfig.setType("toExceptionMailAddr");
        sysConfig=sysConfigService.findListFirstCache(sysConfig);
        if(sysServersBug.size()>0){
            SendMailUtil.sendCommonMail(sysConfig.getValue(),
                    "服务器监控正常("+(sysServers.size()-sysServersBug.size())+")异常（"+sysServersBug.size()+")",
                    message);
        }
    }
}

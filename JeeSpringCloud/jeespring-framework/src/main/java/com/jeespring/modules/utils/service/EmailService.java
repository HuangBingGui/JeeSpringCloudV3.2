package com.jeespring.modules.utils.service;

import com.jeespring.common.utils.SendMailUtil;
import com.jeespring.modules.sys.entity.SysConfig;
import com.jeespring.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 发邮件服务
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@Service
public class EmailService {
    @Autowired
    private SysConfigService sysConfigService;

    public void sendMailException(@RequestParam(required=false) String subject, @RequestParam(required=false) String message) {
        SysConfig entity = new SysConfig();
        entity.setType("toExceptionMailAddr");
        List<SysConfig> sysConfigList=sysConfigService.findList(entity);
        entity.setType("ExceptionMailRun");
        entity=sysConfigService.findListFirstCache(entity);
        if(sysConfigList.size()>0 && "true".equals(entity.getValue())){
            SendMailUtil.sendCommonMail(sysConfigList.get(0).getValue(),"(异常邮件)"+subject,message);
        }
    }
}

package com.jeespring.modules.monitor.web;


import java.util.Map;

import org.hyperic.sigar.Sigar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeespring.common.json.AjaxJson;
import com.jeespring.common.mail.MailSendUtils;
import com.jeespring.common.utils.MyBeanUtils;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.monitor.entity.Monitor;
import com.jeespring.modules.monitor.service.MonitorService;
import com.jeespring.modules.monitor.utils.SystemInfo;
import com.jeespring.modules.sys.entity.SystemConfig;
import com.jeespring.modules.sys.service.SystemConfigService;


/**
 * 系统监控Controller
 * @author liugf
 * @version 2016-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/monitor")
public class MonitorController extends AbstractBaseController {
	@Autowired
	private MonitorService monitorService;
	@Autowired
	private SystemConfigService systemConfigService;
	
	@ModelAttribute
	public Monitor get(@RequestParam(required=false) String id) {
		Monitor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = monitorService.get(id);
		}
		if (entity == null){
			entity = new Monitor();
		}
		return entity;
	}
	
	@RequestMapping("info")
	public String info(Model model) throws Exception {
		Monitor monitor = monitorService.get("1");
		model.addAttribute("cpu", monitor.getCpu());
		model.addAttribute("jvm", monitor.getJvm());
		model.addAttribute("ram", monitor.getRam());
		model.addAttribute("toEmail", monitor.getToEmail());
		return  "modules/monitor/info";
	}
	
	@RequestMapping("monitor")
	public String monitor() throws Exception {
		return "modules/monitor/monitor";
	}
	
	@RequestMapping("systemInfo")
	public String systemInfo(Model model) throws Exception {
		model.addAttribute("systemInfo", SystemInfo.SystemProperty());
		return "modules/monitor/systemInfo";
	}
	
	@ResponseBody
	@RequestMapping("usage")
	public Map usage(Model model) throws Exception {
		SystemConfig config = systemConfigService.get("1");
		Monitor monitor = monitorService.get("1");
		Map<?, ?> sigar = SystemInfo.usage(new Sigar());
		String content="";
		content += "您预设的cpu使用率警告线是"+monitor.getCpu()+"%, 当前使用率是"+sigar.get("cpuUsage")+"%";
		content += "您预设的jvm使用率警告线是"+monitor.getJvm()+"%, 当前使用率是"+sigar.get("jvmUsage")+"%";
		content += "您预设的ram使用率警告线是"+monitor.getRam()+"%, 当前使用率是"+sigar.get("ramUsage")+"%";
		if(Float.valueOf(sigar.get("cpuUsage").toString()) >= Float.valueOf(monitor.getCpu())
				||Float.valueOf(sigar.get("jvmUsage").toString()) >= Float.valueOf(monitor.getJvm())
				||Float.valueOf(sigar.get("ramUsage").toString()) >= Float.valueOf(monitor.getRam())){
			MailSendUtils.sendEmail(config.getSmtp(), config.getPort(), config.getMailName(), config.getMailPassword(), monitor.getToEmail(), "服务器监控预警", content, "0");
			
		};
		return sigar;
	}
	/**
	 * 修改配置　
	 * @param request
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
    @ResponseBody
	@RequestMapping("modifySetting")
    public AjaxJson save(Monitor monitor, Model model) {
    	AjaxJson j = new AjaxJson();
		String message = "保存成功";
		Monitor t = monitorService.get("1");
		try {
			monitor.setId("1");
			MyBeanUtils.copyBeanNotNull2Bean(monitor, t);
			monitorService.save(t);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "保存失败";
		}
		j.setMsg(message);
		return j;
    }
}
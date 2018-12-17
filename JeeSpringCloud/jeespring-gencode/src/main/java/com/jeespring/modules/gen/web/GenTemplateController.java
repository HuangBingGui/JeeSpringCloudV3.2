// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:22:52
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenTemplateController.java

package com.jeespring.modules.gen.web;

import com.jeespring.common.config.Global;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.gen.entity.GenTemplate;
import com.jeespring.modules.gen.service.GenTemplateService;
import com.jeespring.modules.sys.entity.User;
import com.jeespring.modules.sys.service.SysConfigService;
import com.jeespring.modules.sys.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"${adminPath}/gen/genTemplate"})
public class GenTemplateController extends AbstractBaseController
{
    @Autowired
    private SysConfigService sysConfigService;

    public GenTemplateController()
    {
    }

    @ModelAttribute
    public GenTemplate get(@RequestParam(required=true) String id)
    {
        if(StringUtils.isNotBlank(id)) {
            return genTemplateService.get(id);
        } else {
            return new GenTemplate();
        }
    }

    @RequiresPermissions(value={"gen:genTemplate:view"})
    @RequestMapping(value={"list", ""})
    public String list(GenTemplate genTemplate, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        User user = UserUtils.getUser();
        if(!user.isAdmin()) {
            genTemplate.setCreateBy(user);
        }
        Page page = genTemplateService.find(new Page(request, response), genTemplate);
        model.addAttribute("page", page);
        return "modules/gen/genTemplateList";
    }

    @RequiresPermissions(value={"gen:genTemplate:view"})
    @RequestMapping(value={"form"})
    public String form(GenTemplate genTemplate, Model model)
    {
        model.addAttribute("genTemplate", genTemplate);
        return "modules/gen/genTemplateForm";
    }

    @RequiresPermissions(value={"gen:genTemplate:edit"})
    @RequestMapping(value={"save"})
    public String save(GenTemplate genTemplate, Model model, RedirectAttributes redirectAttributes)
    {
        if(sysConfigService.isDemoMode()){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genTemplate/";
        }

        if (!beanValidator(model, genTemplate)) {
            return form(genTemplate, model);
          }
          this.genTemplateService.save(genTemplate);
          addMessage(redirectAttributes, "保存代码模板'" + genTemplate.getName() + "'成功");
          return "redirect:" + this.adminPath + "/gen/genTemplate/?repage";
    }

    @RequiresPermissions(value={"gen:genTemplate:edit"})
    @RequestMapping(value={"delete"})
    public String delete(GenTemplate genTemplate, RedirectAttributes redirectAttributes)
    {
        if(sysConfigService.isDemoMode()){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genTemplate/";
        }

        this.genTemplateService.delete(genTemplate);
        addMessage(redirectAttributes, "删除代码模板成功");
        return "redirect:" + this.adminPath + "/gen/genTemplate/?repage";    }

    @Autowired
    private GenTemplateService genTemplateService;
}

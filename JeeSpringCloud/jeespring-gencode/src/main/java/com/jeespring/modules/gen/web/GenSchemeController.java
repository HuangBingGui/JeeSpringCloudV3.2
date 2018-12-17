// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:22:40
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenSchemeController.java

package com.jeespring.modules.gen.web;

import com.jeespring.common.config.Global;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.gen.entity.GenScheme;
import com.jeespring.modules.gen.service.GenSchemeService;
import com.jeespring.modules.gen.service.GenTableService;
import com.jeespring.modules.gen.util.GenUtils;
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
@RequestMapping(value={"${adminPath}/gen/genScheme"})
public class GenSchemeController extends AbstractBaseController
{

    @Autowired
    private SysConfigService sysConfigService;

    public GenSchemeController()
    {
    }

    @ModelAttribute
    public GenScheme get(@RequestParam(required=true) String id)
    {
        if(StringUtils.isNotBlank(id)) {
            return genSchemeService.get(id);
        } else {
            return new GenScheme();
        }
    }

    @RequiresPermissions(value={"gen:genScheme:view"})
    @RequestMapping(value={"list", ""})
    public String list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        User user = UserUtils.getUser();
        if(!user.isAdmin()) {
            genScheme.setCreateBy(user);
        }
        Page page = genSchemeService.find(new Page(request, response), genScheme);
        model.addAttribute("page", page);
        return "modules/gen/genSchemeList";
    }

    @RequiresPermissions(value={"gen:genScheme:view"})
    @RequestMapping(value={"form"})
    public String form(GenScheme genScheme, Model model)
    {
        if(StringUtils.isBlank(genScheme.getPackageName())) {
            genScheme.setPackageName("com.jeespring.modules");
        }
        model.addAttribute("genScheme", genScheme);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", genTableService.findAll());
        return "modules/gen/genSchemeForm";
    }

    @RequiresPermissions(value={"gen:genScheme:edit"})
    @RequestMapping(value={"save"})
    public String save(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes)
    {
        if(sysConfigService.isDemoMode()){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genScheme/";
        }

        if(!beanValidator(model, genScheme))
        {
            return form(genScheme, model);
        } else
        {
            String result = genSchemeService.save(genScheme);
            addMessage(redirectAttributes, "操作生成方案'" + genScheme.getName() + "'成功<br/>" + result);
            return "redirect:" + this.adminPath + "/gen/genScheme/?repage";
        }
    }

    @RequiresPermissions(value={"gen:genScheme:edit"})
    @RequestMapping(value={"delete"})
    public String delete(GenScheme genScheme, RedirectAttributes redirectAttributes)
    {
        if(sysConfigService.isDemoMode()){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genTemplate/";
        }

        this.genSchemeService.delete(genScheme);
        addMessage(redirectAttributes, "删除生成方案成功");
        return "redirect:" + this.adminPath + "/gen/genScheme/?repage";
    }

    @Autowired
    private GenSchemeService genSchemeService;
    @Autowired
    private GenTableService genTableService;
}

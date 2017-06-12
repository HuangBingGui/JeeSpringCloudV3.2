// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:22:40
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenSchemeController.java

package com.jeertd.modules.gen.web;

import com.jeertd.common.persistence.Page;
import com.jeertd.common.utils.StringUtils;
import com.jeertd.common.web.BaseController;
import com.jeertd.modules.gen.entity.GenScheme;
import com.jeertd.modules.gen.service.GenSchemeService;
import com.jeertd.modules.gen.service.GenTableService;
import com.jeertd.modules.gen.util.GenUtils;
import com.jeertd.modules.sys.entity.User;
import com.jeertd.modules.sys.utils.UserUtils;

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
public class GenSchemeController extends BaseController
{

    public GenSchemeController()
    {
    }

    @ModelAttribute
    public GenScheme get(@RequestParam(required=true) String id)
    {
        if(StringUtils.isNotBlank(id))
            return genSchemeService.get(id);
        else
            return new GenScheme();
    }

    @RequiresPermissions(value={"gen:genScheme:view"})
    @RequestMapping(value={"list", ""})
    public String list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        User user = UserUtils.getUser();
        if(!user.isAdmin())
            genScheme.setCreateBy(user);
        Page page = genSchemeService.find(new Page(request, response), genScheme);
        model.addAttribute("page", page);
        return "modules/gen/genSchemeList";
    }

    @RequiresPermissions(value={"gen:genScheme:view"})
    @RequestMapping(value={"form"})
    public String form(GenScheme genScheme, Model model)
    {
        if(StringUtils.isBlank(genScheme.getPackageName()))
            genScheme.setPackageName("com.jeeplus.modules");
        model.addAttribute("genScheme", genScheme);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", genTableService.findAll());
        return "modules/gen/genSchemeForm";
    }

    @RequiresPermissions(value={"gen:genScheme:edit"})
    @RequestMapping(value={"save"})
    public String save(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes)
    {
        if(!beanValidator(model, genScheme, new Class[0]))
        {
            return form(genScheme, model);
        } else
        {
            String result = genSchemeService.save(genScheme);
            addMessage(redirectAttributes, new String[] { "操作生成方案'" + genScheme.getName() + "'成功<br/>" + result });
            return "redirect:" + this.adminPath + "/gen/genScheme/?repage";
        }
    }

    @RequiresPermissions(value={"gen:genScheme:edit"})
    @RequestMapping(value={"delete"})
    public String delete(GenScheme genScheme, RedirectAttributes redirectAttributes)
    {
        this.genSchemeService.delete(genScheme);
        addMessage(redirectAttributes, new String[] { "删除生成方案成功" });
        return "redirect:" + this.adminPath + "/gen/genScheme/?repage";
    }

    @Autowired
    private GenSchemeService genSchemeService;
    @Autowired
    private GenTableService genTableService;
}

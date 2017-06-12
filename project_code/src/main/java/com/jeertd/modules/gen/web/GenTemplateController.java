// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:22:52
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenTemplateController.java

package com.jeertd.modules.gen.web;

import com.jeertd.common.persistence.Page;
import com.jeertd.common.utils.StringUtils;
import com.jeertd.common.web.BaseController;
import com.jeertd.modules.gen.entity.GenTemplate;
import com.jeertd.modules.gen.service.GenTemplateService;
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
@RequestMapping(value={"${adminPath}/gen/genTemplate"})
public class GenTemplateController extends BaseController
{

    public GenTemplateController()
    {
    }

    @ModelAttribute
    public GenTemplate get(@RequestParam(required=true) String id)
    {
        if(StringUtils.isNotBlank(id))
            return genTemplateService.get(id);
        else
            return new GenTemplate();
    }

    @RequiresPermissions(value={"gen:genTemplate:view"})
    @RequestMapping(value={"list", ""})
    public String list(GenTemplate genTemplate, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        User user = UserUtils.getUser();
        if(!user.isAdmin())
            genTemplate.setCreateBy(user);
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
        if (!beanValidator(model, genTemplate, new Class[0])) {
            return form(genTemplate, model);
          }
          this.genTemplateService.save(genTemplate);
          addMessage(redirectAttributes, new String[] { "保存代码模板'" + genTemplate.getName() + "'成功" });
          return "redirect:" + this.adminPath + "/gen/genTemplate/?repage";
    }

    @RequiresPermissions(value={"gen:genTemplate:edit"})
    @RequestMapping(value={"delete"})
    public String delete(GenTemplate genTemplate, RedirectAttributes redirectAttributes)
    {
        this.genTemplateService.delete(genTemplate);
        addMessage(redirectAttributes, new String[] { "删除代码模板成功" });
        return "redirect:" + this.adminPath + "/gen/genTemplate/?repage";    }

    @Autowired
    private GenTemplateService genTemplateService;
}

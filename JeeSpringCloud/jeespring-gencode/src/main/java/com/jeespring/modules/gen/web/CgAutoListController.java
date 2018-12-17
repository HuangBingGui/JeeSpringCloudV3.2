// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:22:29
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CgAutoListController.java

package com.jeespring.modules.gen.web;

import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.gen.entity.GenScheme;
import com.jeespring.modules.gen.service.*;
import com.jeespring.modules.gen.template.FreemarkerHelper;
import com.jeespring.modules.gen.util.GenUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"${adminPath}/gen/cgAutoList"})
public class CgAutoListController extends AbstractBaseController
{

    public CgAutoListController()
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

    @RequestMapping(value={"list"})
    public void list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response)
    {
        long start = System.currentTimeMillis();
        FreemarkerHelper viewEngine = new FreemarkerHelper();
        Map model = GenUtils.getDataModel(genScheme);
        String html = cgAutoListService.generateCode(genScheme);
        String findListSql = cgAutoListService.generateListCode(genScheme);
        try
        {
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-store");
            PrintWriter writer = response.getWriter();
            writer.println(html);
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.debug("动态列表生成耗时：" + (end - start) + " ms");
    }

    @RequestMapping(value={"test", ""})
    public ModelAndView list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        return new ModelAndView("com/jeespring/modules/gen/template/viewList");
    }

    private static Logger log = Logger.getLogger(CgAutoListController.class);
    @Autowired
    private GenSchemeService genSchemeService;
    @Autowired
    private GenTableService genTableService;
    @Autowired
    private CgAutoListService cgAutoListService;

}

// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:22:45
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenTableController.java

package com.jeertd.modules.gen.web;

import com.jeertd.common.persistence.Page;
import com.jeertd.common.utils.StringUtils;
import com.jeertd.common.web.BaseController;
import com.jeertd.modules.gen.entity.*;
import com.jeertd.modules.gen.service.GenSchemeService;
import com.jeertd.modules.gen.service.GenTableService;
import com.jeertd.modules.gen.util.GenUtils;
import com.jeertd.modules.sys.entity.User;
import com.jeertd.modules.sys.utils.UserUtils;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"${adminPath}/gen/genTable"})
public class GenTableController extends BaseController
{

    public GenTableController()
    {
    }

    public GenTable get(GenTable genTable)
    {
        if(StringUtils.isNotBlank(genTable.getId()))
            return genTableService.get(genTable.getId());
        else
            return genTable;
    }

    @RequiresPermissions(value={"gen:genTable:list"})
    @RequestMapping(value={"list", ""})
    public String list(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        genTable = get(genTable);
        User user = UserUtils.getUser();
        if(!user.isAdmin())
            genTable.setCreateBy(user);
        Page page = genTableService.find(new Page(request, response), genTable);
        model.addAttribute("page", page);
        return "modules/gen/genTableList";
    }

    @RequiresPermissions(value={"gen:genTable:view", "gen:genTable:add", "gen:genTable:edit"}, logical=org.apache.shiro.authz.annotation.Logical.OR)
    @RequestMapping(value={"form"})
    public String form(GenTable genTable, Model model)
    {
        genTable = get(genTable);
        model.addAttribute("genTable", genTable);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", genTableService.findAll());
        return "modules/gen/genTableForm";
    }

    @RequiresPermissions(value={"gen:genTable:add", "gen:genTable:edit"}, logical=org.apache.shiro.authz.annotation.Logical.OR)
    @RequestMapping(value={"save"})
    public String save(GenTable genTable, Model model, RedirectAttributes redirectAttributes)
    {
        if (!beanValidator(model, genTable, new Class[0])) {
            return form(genTable, model);
          }
          if ((StringUtils.isBlank(genTable.getId())) && (!this.genTableService.checkTableName(genTable.getName())))
          {
            addMessage(redirectAttributes, new String[] { "添加失败！" + genTable.getName() + " 记录已存在！" });
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
          }
          if ((StringUtils.isBlank(genTable.getId())) && (!this.genTableService.checkTableNameFromDB(genTable.getName())))
          {
            addMessage(redirectAttributes, new String[] { "添加失败！" + genTable.getName() + "表已经在数据库中存在,请从数据库导入表单！" });
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
          }
          this.genTableService.save(genTable);
          addMessage(redirectAttributes, new String[] { "保存业务表'" + genTable.getName() + "'成功" });
          return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    @RequiresPermissions(value={"gen:genTable:importDb"})
    @RequestMapping(value={"importTableFromDB"})
    public String importTableFromDB(GenTable genTable, Model model, RedirectAttributes redirectAttributes)
    {
        genTable = get(genTable);
        if (!StringUtils.isBlank(genTable.getName()))
        {
          if (!this.genTableService.checkTableName(genTable.getName()))
          {
            addMessage(redirectAttributes, new String[] {"下一步失败！" + genTable.getName() + 
              " 表已经添加！" });
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
          }
          genTable = this.genTableService.getTableFormDb(genTable);
          genTable.setTableType("0");
          this.genTableService.saveFromDB(genTable);
          addMessage(redirectAttributes, new String[] {"数据库导入表单'" + genTable.getName() + 
            "'成功" });
          return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        }
        List<GenTable> tableList = this.genTableService
          .findTableListFormDb(new GenTable());
        model.addAttribute("tableList", tableList);
        model.addAttribute("config", GenUtils.getConfig());
        return "modules/gen/importTableFromDB";
    }

    @RequiresPermissions(value={"gen:genTable:del"})
    @RequestMapping(value={"delete"})
    public String delete(GenTable genTable, RedirectAttributes redirectAttributes)
    {
        genTable = get(genTable);
        this.genTableService.delete(genTable);
        this.genSchemeService.delete(this.genSchemeService.findUniqueByProperty("gen_table_id", genTable.getId()));
        addMessage(redirectAttributes, new String[] { "移除业务表记录成功" });
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
     }

    @RequiresPermissions(value={"gen:genTable:del"})
    @RequestMapping(value={"deleteDb"})
    public String deleteDb(GenTable genTable, RedirectAttributes redirectAttributes)
    {
    	genTable = get(genTable);
        this.genTableService.delete(genTable);
        this.genSchemeService.delete(this.genSchemeService.findUniqueByProperty("gen_table_id", genTable.getId()));
        StringBuffer sql = new StringBuffer();
        sql.append("drop table if exists " + genTable.getName() + " ;");
        this.genTableService.buildTable(sql.toString());
        addMessage(redirectAttributes, new String[] { "删除业务表记录和数据库表成功" });
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";  
     }

    @RequiresPermissions(value={"gen:genTable:del"})
    @RequestMapping(value={"deleteAll"})
    public String deleteAll(String ids, RedirectAttributes redirectAttributes)
    {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
          this.genTableService.delete(this.genTableService.get(id));
        }
        addMessage(redirectAttributes, new String[] { "删除业务表成功" });
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";    }

    @RequiresPermissions(value={"gen:genTable:synchDb"})
    @RequestMapping(value={"synchDb"})
    public String synchDb(GenTable genTable, RedirectAttributes redirectAttributes)
    {
        genTable = get(genTable);
        StringBuffer sql = new StringBuffer();
        List getTableColumnList = genTable.getColumnList();
        sql.append((new StringBuilder("drop table if exists ")).append(genTable.getName()).append(" ;").toString());
        genTableService.buildTable(sql.toString());
        sql = new StringBuffer();
        sql.append((new StringBuilder("create table ")).append(genTable.getName()).append(" (").toString());
        String pk = "";
        for(Iterator iterator = getTableColumnList.iterator(); iterator.hasNext();)
        {
            GenTableColumn column = (GenTableColumn)iterator.next();
            if(column.getIsPk().equals("1"))
            {
                sql.append((new StringBuilder("  ")).append(column.getName()).append(" ").append(column.getJdbcType()).append(" comment '").append(column.getComments()).append("',").toString());
                pk = (new StringBuilder(String.valueOf(pk))).append(column.getName()).append(",").toString();
            } else
            {
                sql.append((new StringBuilder("  ")).append(column.getName()).append(" ").append(column.getJdbcType()).append(" comment '").append(column.getComments()).append("',").toString());
            }
        }

        sql.append("primary key (" + pk.substring(0, pk.length() - 1) + ") ");
        sql.append(") comment '" + genTable.getComments() + "'");
        this.genTableService.buildTable(sql.toString());
        this.genTableService.syncSave(genTable);
        addMessage(redirectAttributes, new String[] { "强制同步数据库表成功" });
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";    }

    @RequiresPermissions(value={"gen:genTable:genCode"})
    @RequestMapping(value={"genCodeForm"})
    public String genCodeForm(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes)
    {
        if(StringUtils.isBlank(genScheme.getPackageName()))
            genScheme.setPackageName("com.jeeplus.modules");
        GenScheme oldGenScheme = genSchemeService.findUniqueByProperty("gen_table_id", genScheme.getGenTable().getId());
        if(oldGenScheme != null)
            genScheme = oldGenScheme;
        model.addAttribute("genScheme", genScheme);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", genTableService.findAll());
        return "modules/gen/genCodeForm";
    }

    @RequestMapping(value={"genCode"})
    public String genCode(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes)
    {
        String result = this.genSchemeService.save(genScheme);
        addMessage(redirectAttributes, new String[] { genScheme.getGenTable().getName() + "代码生成成功<br/>" + result });
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
     }

    @Autowired
    private GenTableService genTableService;
    @Autowired
    private GenSchemeService genSchemeService;
}

// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:22:45
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenTableController.java

package com.jeespring.modules.gen.web;

import com.jeespring.common.config.Global;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.gen.entity.*;
import com.jeespring.modules.gen.service.GenSchemeService;
import com.jeespring.modules.gen.service.GenTableService;
import com.jeespring.modules.gen.util.GenUtils;
import com.jeespring.modules.sys.entity.Menu;
import com.jeespring.modules.sys.entity.User;
import com.jeespring.modules.sys.service.SysConfigService;
import com.jeespring.modules.sys.service.SystemService;
import com.jeespring.modules.sys.utils.UserUtils;

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
public class GenTableController extends AbstractBaseController
{
    @Autowired
    private SystemService systemService;
    @Autowired
    private SysConfigService sysConfigService;

    public GenTableController()
    {
    }

    public GenTable get(GenTable genTable)
    {
        if(StringUtils.isNotBlank(genTable.getId())) {
            return genTableService.get(genTable.getId());
        } else {
            return genTable;
        }
    }

    @RequiresPermissions(value={"gen:genTable:list"})
    @RequestMapping(value={"list", ""})
    public String list(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        genTable = get(genTable);
        User user = UserUtils.getUser();
        if(!user.isAdmin()) {
            genTable.setCreateBy(user);
        }
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
        if(sysConfigService.isDemoMode()){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genTable/";
        }

        if (!beanValidator(model, genTable)) {
            return form(genTable, model);
          }
          if ((StringUtils.isBlank(genTable.getId())) && (!this.genTableService.checkTableName(genTable.getName())))
          {
            addMessage(redirectAttributes, "添加失败！" + genTable.getName() + " 记录已存在！");
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
          }
          if ((StringUtils.isBlank(genTable.getId())) && (!this.genTableService.checkTableNameFromDB(genTable.getName())))
          {
            addMessage(redirectAttributes, "添加失败！" + genTable.getName() + "表已经在数据库中存在,请从数据库导入表单！");
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
          }
          this.genTableService.save(genTable);
          addMessage(redirectAttributes, "保存业务表'" + genTable.getName() + "'成功");
          return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    @RequiresPermissions(value={"gen:genTable:importDb"})
    @RequestMapping(value={"importTableFromDB"})
    public String importTableFromDB(GenTable genTable, Model model, RedirectAttributes redirectAttributes)
    {
        if(sysConfigService.isDemoMode()){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genTable/";
        }
        genTable = get(genTable);
        if (!StringUtils.isBlank(genTable.getName()))
        {
          if (!this.genTableService.checkTableName(genTable.getName()))
          {
            addMessage(redirectAttributes, "下一步失败！" + genTable.getName() +
              " 表已经添加！");
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
          }
          genTable = this.genTableService.getTableFormDb(genTable);
          genTable.setTableType("0");
          this.genTableService.saveFromDB(genTable);
          addMessage(redirectAttributes, "数据库导入表单'" + genTable.getName() +
            "'成功");
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
        if(sysConfigService.isDemoMode() ){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genTable/";
        }

        genTable = get(genTable);
        this.genTableService.delete(genTable);
        this.genSchemeService.delete(this.genSchemeService.findUniqueByProperty("gen_table_id", genTable.getId()));
        addMessage(redirectAttributes, "移除业务表记录成功");
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
     }

    @RequiresPermissions(value={"gen:genTable:del"})
    @RequestMapping(value={"deleteDb"})
    public String deleteDb(GenTable genTable, RedirectAttributes redirectAttributes)
    {
        if(sysConfigService.isDemoMode()){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genTable/";
        }

    	genTable = get(genTable);
        this.genTableService.delete(genTable);
        this.genSchemeService.delete(this.genSchemeService.findUniqueByProperty("gen_table_id", genTable.getId()));
        StringBuffer sql = new StringBuffer();
        sql.append("drop table if exists " + genTable.getName() + " ;");
        this.genTableService.buildTable(sql.toString());
        addMessage(redirectAttributes, "删除业务表记录和数据库表成功");
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";  
     }

    @RequiresPermissions(value={"gen:genTable:del"})
    @RequestMapping(value={"deleteAll"})
    public String deleteAll(String ids, RedirectAttributes redirectAttributes)
    {
        if(sysConfigService.isDemoMode()){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genTable/";
        }

        String[] idArray = ids.split(",");
        for (String id : idArray) {
          this.genTableService.delete(this.genTableService.get(id));
        }
        addMessage(redirectAttributes, "删除业务表成功");
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";    }

    @RequiresPermissions(value={"gen:genTable:synchDb"})
    @RequestMapping(value={"synchDb"})
    public String synchDb(GenTable genTable, RedirectAttributes redirectAttributes)
    {
        if(sysConfigService.isDemoMode()){
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
            return "redirect:" + adminPath + "/gen/genTable/";
        }

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
            if("1".equals(column.getIsPk()))
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
        addMessage(redirectAttributes, "强制同步数据库表成功");
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";    }

    @RequiresPermissions(value={"gen:genTable:genCode"})
    @RequestMapping(value={"genCodeForm"})
    public String genCodeForm(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes)
    {
        if(StringUtils.isBlank(genScheme.getPackageName())) {
            genScheme.setPackageName("com.company.project.modules");
        }
        GenScheme oldGenScheme = genSchemeService.findUniqueByProperty("gen_table_id", genScheme.getGenTable().getId());
        if(oldGenScheme != null) {
            genScheme = oldGenScheme;
        }
        model.addAttribute("genScheme", genScheme);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", genTableService.findAll());
        return "modules/gen/genCodeForm";
    }

    @RequestMapping(value={"genCode"})
    public String genCode(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes)
    {
        genScheme.setGenTable(genTableService.get(genScheme.getGenTable().getId()));
        if(sysConfigService.isDemoMode()){
            String msg="<br>"+genScheme.getGenTable().getName()
                    + "代码和菜单、接口已生成成功;<br/>如果启用redis:代码生成后，要退出，再登陆。<br/>菜单路径：<br/>"
                    + "接口路径："+"rest/list或者get或者save或者delete<br/>...";
            addMessage(redirectAttributes, sysConfigService.isDemoModeDescription()+msg);
            return "redirect:" + adminPath + "/gen/genTable/";
        }
        String href="";
        if(genScheme.getSubModuleName()!=null && genScheme.getSubModuleName().length()>0){
            href="/"+genScheme.getModuleName()+"/"+genScheme.getSubModuleName()+"/"+StringUtils.toCamelCase(genScheme.getGenTable().getName());
        }else{
            href="/"+genScheme.getModuleName()+"/"+StringUtils.toCamelCase(genScheme.getGenTable().getName());
        }
        if("tree".equals(genScheme.getCategory())){
            href=href+"Tree";
        }
        genMenu(genScheme,href);

        String result = this.genSchemeService.save(genScheme);
        addMessage(redirectAttributes, genScheme.getGenTable().getName()
                + "代码和菜单、接口已生成成功;<br/>如果启用redis:代码生成后，要退出，再登陆。<br/>菜单路径："+href+"<br/>"
                + "接口路径："+"rest/"+href+"/list或者get或者save或者delete<br/>"
                + result);

        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
     }

     public void genMenu(GenScheme genScheme,String href){
         String paraentId="158586ffb6b44175885680d1c93f05bd";
         Menu menu=new Menu();
         List<Menu> menus=systemService.findAllMenu();
         for (Menu menuItem:menus) {
             if(href.equals(menuItem.getHref())){
                 return;
             }
         }
         menu.setHref(href);
         menu.setName(genScheme.getFunctionName()+genScheme.getGenTable().getName());
         if(genScheme.getSubModuleName()!=null && genScheme.getSubModuleName().length()>0){
             menu.setPermission(genScheme.getModuleName()+":"+genScheme.getSubModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":list");
         }else{
             menu.setPermission(genScheme.getModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":list");
         }
         menu.setSort(30);
         menu.setIsShow("1");
         menu.setDelFlag("0");
         Menu parent=new Menu();
         parent.setId(paraentId);
         parent.setName("生成模块");
         menu.setParent(parent);
         systemService.saveMenu(menu);

         //View
         Menu menuView=new Menu();
         menuView.setName(genScheme.getFunctionName()+"查看");
         for (Menu menuItem:menus) {
             if((menuView.getName().equals(menuItem.getName()) || menuItem.getName().contains(genScheme.getFunctionName())) && menuItem.getParentId().equals(menu.getParentId())){
                 menuView=menuItem;
             }
         }
         if(genScheme.getSubModuleName()!=null && genScheme.getSubModuleName().length()>0){
             menuView.setPermission(genScheme.getModuleName()+":"+genScheme.getSubModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":view");
         }else{
             menuView.setPermission(genScheme.getModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":view");
         }
         menuView.setSort(30);
         menuView.setIsShow("1");
         menuView.setDelFlag("0");
         menuView.setParent(menu);
         systemService.saveMenu(menuView);

         //edit
         Menu menuEdit=new Menu();
         menuEdit.setName(genScheme.getFunctionName()+"编辑");
         for (Menu menuItem:menus) {
             if((menuEdit.getName().equals(menuItem.getName()) || menuItem.getName().contains(genScheme.getFunctionName())) && menuItem.getParentId().equals(menu.getParentId())){
                 menuEdit=menuItem;
             }
         }
         if(genScheme.getSubModuleName()!=null && genScheme.getSubModuleName().length()>0){
             menuEdit.setPermission(genScheme.getModuleName()+":"+genScheme.getSubModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":edit");
         }else{
             menuEdit.setPermission(genScheme.getModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":edit");
         }
         menuEdit.setSort(60);
         menuEdit.setIsShow("1");
         menuEdit.setDelFlag("0");
         menuEdit.setParent(menu);
         systemService.saveMenu(menuEdit);

         //add
         Menu menuAdd=new Menu();
         menuAdd.setName(genScheme.getFunctionName()+"添加");
         for (Menu menuItem:menus) {
             if((menuAdd.getName().equals(menuItem.getName()) || menuItem.getName().contains(genScheme.getFunctionName())) && menuItem.getParentId().equals(menu.getParentId())){
                 menuAdd=menuItem;
             }
         }
         if(genScheme.getSubModuleName()!=null && genScheme.getSubModuleName().length()>0){
             menuAdd.setPermission(genScheme.getModuleName()+":"+genScheme.getSubModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":add");
         }else{
             menuAdd.setPermission(genScheme.getModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":add");
         }
         menuAdd.setSort(90);
         menuAdd.setIsShow("1");
         menuAdd.setDelFlag("0");
         menuAdd.setParent(menu);
         systemService.saveMenu(menuAdd);

         //del
         Menu menuDel=new Menu();
         menuDel.setName(genScheme.getFunctionName()+"删除");
         for (Menu menuItem:menus) {
             if((menuDel.getName().equals(menuItem.getName()) || menuItem.getName().contains(genScheme.getFunctionName())) && menuItem.getParentId().equals(menu.getParentId())){
                 menuDel=menuItem;
             }
         }
         if(genScheme.getSubModuleName()!=null && genScheme.getSubModuleName().length()>0){
             menuDel.setPermission(genScheme.getModuleName()+":"+genScheme.getSubModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":del");
         }else{
             menuDel.setPermission(genScheme.getModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":del");
         }
         menuDel.setSort(120);
         menuDel.setIsShow("1");
         menuDel.setDelFlag("0");
         menuDel.setParent(menu);
         systemService.saveMenu(menuDel);

         //export
         Menu menuExport=new Menu();
         menuExport.setName(genScheme.getFunctionName()+"导出");
         for (Menu menuItem:menus) {
             if((menuExport.getName().equals(menuItem.getName()) || menuItem.getName().contains(genScheme.getFunctionName())) && menuItem.getParentId().equals(menu.getParentId())){
                 menuExport=menuItem;
             }
         }
         if(genScheme.getSubModuleName()!=null && genScheme.getSubModuleName().length()>0){
             menuExport.setPermission(genScheme.getModuleName()+":"+genScheme.getSubModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":export");
         }else{
             menuExport.setPermission(genScheme.getModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":export");
         }
         menuExport.setSort(150);
         menuExport.setIsShow("1");
         menuExport.setDelFlag("0");
         menuExport.setParent(menu);
         systemService.saveMenu(menuExport);

         //import
         Menu menuImport=new Menu();
         menuImport.setName(genScheme.getFunctionName()+"导入");
         for (Menu menuItem:menus) {
             if((menuImport.getName().equals(menuItem.getName()) || menuItem.getName().contains(genScheme.getFunctionName())) && menuItem.getParentId().equals(menu.getParentId())){
                 menuImport=menuItem;
             }
         }
         if(genScheme.getSubModuleName()!=null && genScheme.getSubModuleName().length()>0){
             menuImport.setPermission(genScheme.getModuleName()+":"+genScheme.getSubModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":import");
         }else{
             menuImport.setPermission(genScheme.getModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":import");
         }
         menuImport.setSort(180);
         menuImport.setIsShow("1");
         menuImport.setDelFlag("0");
         menuImport.setParent(menu);
         systemService.saveMenu(menuImport);

         //total
         Menu menuTotal=new Menu();
         menuTotal.setName(genScheme.getFunctionName()+"统计");
         for (Menu menuItem:menus) {
             if((menuTotal.getName().equals(menuItem.getName()) || menuItem.getName().contains(genScheme.getFunctionName())) && menuItem.getParentId().equals(menu.getParentId())){
                 menuTotal=menuItem;
             }
         }
         if(genScheme.getSubModuleName()!=null && genScheme.getSubModuleName().length()>0){
             menuTotal.setPermission(genScheme.getModuleName()+":"+genScheme.getSubModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":total");
         }else{
             menuTotal.setPermission(genScheme.getModuleName()+":"+StringUtils.toCamelCase(genScheme.getGenTable().getName())+":total");
         }
         menuTotal.setSort(210);
         menuTotal.setIsShow("1");
         menuTotal.setDelFlag("0");
         menuTotal.setParent(menu);
         systemService.saveMenu(menuTotal);
     }
    @Autowired
    private GenTableService genTableService;
    @Autowired
    private GenSchemeService genSchemeService;
}

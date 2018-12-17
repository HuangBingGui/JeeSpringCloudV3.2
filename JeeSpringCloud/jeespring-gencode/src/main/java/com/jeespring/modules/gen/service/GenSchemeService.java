// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:23:26
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenSchemeService.java

package com.jeespring.modules.gen.service;

import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractService;
import com.jeespring.modules.gen.dao.*;
import com.jeespring.modules.gen.entity.*;
import com.jeespring.modules.gen.util.GenUtils;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Transactional(readOnly=true)
public class GenSchemeService extends AbstractService
{

    public GenSchemeService()
    {
    }

    public GenScheme get(String id)
    {
        return genSchemeDao.get(id);
    }

    public Page find(Page page, GenScheme genScheme)
    {
        GenUtils.getTemplatePath();
        genScheme.setPage(page);
        page.setList(genSchemeDao.findList(genScheme));
        return page;
    }

    @Transactional(readOnly=false)
    public String save(GenScheme genScheme)
    {
        if(StringUtils.isBlank(genScheme.getId()))
        {
            genScheme.preInsert();
            genSchemeDao.insert(genScheme);
        } else
        {
            genScheme.preUpdate();
            genSchemeDao.update(genScheme);
        }
        return generateCode(genScheme);
    }

    @Transactional(readOnly=false)
    public void delete(GenScheme genScheme)
    {
        genSchemeDao.delete(genScheme);
    }

    private String generateCode(GenScheme genScheme)
    {
        StringBuilder result = new StringBuilder();
        GenTable genTable = genTableDao.get(genScheme.getGenTable().getId());
        genTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        GenConfig config = GenUtils.getConfig();
        List templateList = GenUtils.getTemplateList(config, genScheme.getCategory(), false);
        List childTableTemplateList = GenUtils.getTemplateList(config, genScheme.getCategory(), true);
        if(childTableTemplateList.size() > 0)
        {
            GenTable parentTable = new GenTable();
            parentTable.setParentTable(genTable.getName());
            genTable.setChildList(genTableDao.findList(parentTable));
        }
        for(Iterator iterator = genTable.getChildList().iterator(); iterator.hasNext();)
        {
            GenTable childTable = (GenTable)iterator.next();
            childTable.setParent(genTable);
            childTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(childTable.getId()))));
            genScheme.setGenTable(childTable);
            Map childTableModel = GenUtils.getDataModel(genScheme);
            GenTemplate tpl;
            for(Iterator iterator2 = childTableTemplateList.iterator(); iterator2.hasNext(); result.append(GenUtils.generateToFile(tpl, childTableModel, true))) {
                tpl = (GenTemplate) iterator2.next();
            }

        }

        genScheme.setGenTable(genTable);
        Map model = GenUtils.getDataModel(genScheme);
        GenTemplate tpl;
        for(Iterator iterator1 = templateList.iterator(); iterator1.hasNext(); result.append(GenUtils.generateToFile(tpl, model, true))) {
            tpl = (GenTemplate) iterator1.next();
        }

        return result.toString();
    }

    public GenScheme findUniqueByProperty(String propertyName, String value)
    {
        return genSchemeDao.findUniqueByProperty(propertyName, value);
    }

    @Autowired
    private GenSchemeDao genSchemeDao;
    @Autowired
    private GenTableDao genTableDao;
    @Autowired
    private GenTableColumnDao genTableColumnDao;
}

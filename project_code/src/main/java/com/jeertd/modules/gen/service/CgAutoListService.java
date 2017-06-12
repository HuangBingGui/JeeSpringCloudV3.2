// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:23:22
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CgAutoListService.java

package com.jeertd.modules.gen.service;

import com.jeertd.common.persistence.Page;
import com.jeertd.common.service.BaseService;
import com.jeertd.common.utils.StringUtils;
import com.jeertd.modules.gen.dao.*;
import com.jeertd.modules.gen.entity.*;
import com.jeertd.modules.gen.template.FreemarkerHelper;
import com.jeertd.modules.gen.util.GenUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class CgAutoListService extends BaseService
{

    public CgAutoListService()
    {
    }

    public GenTable get(String id)
    {
        GenTable genTable = (GenTable)genTableDao.get(id);
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setGenTable(new GenTable(genTable.getId()));
        genTable.setColumnList(genTableColumnDao.findList(genTableColumn));
        return genTable;
    }

    public Page find(Page page, GenTable genTable)
    {
        genTable.setPage(page);
        page.setList(genTableDao.findList(genTable));
        return page;
    }

    public List findAll()
    {
        return genTableDao.findAllList(new GenTable());
    }

    public List findTableListFormDb(GenTable genTable)
    {
        return genDataBaseDictDao.findTableList(genTable);
    }

    public boolean checkTableName(String tableName)
    {
        if(StringUtils.isBlank(tableName))
            return true;
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List list = genTableDao.findList(genTable);
        return list.size() == 0;
    }

    public boolean checkTableNameFromDB(String tableName)
    {
        if(StringUtils.isBlank(tableName))
            return true;
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List list = genDataBaseDictDao.findTableList(genTable);
        return list.size() == 0;
    }

    public String generateCode(GenScheme genScheme)
    {
        StringBuilder result = new StringBuilder();
        GenTable genTable = (GenTable)genTableDao.get(genScheme.getGenTable().getId());
        genTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        com.jeertd.modules.gen.entity.GenConfig config = GenUtils.getConfig();
        genScheme.setGenTable(genTable);
        java.util.Map model = GenUtils.getDataModel(genScheme);
        FreemarkerHelper viewEngine = new FreemarkerHelper();
        String html = viewEngine.parseTemplate("/com/jeeplus/modules/gen/template/viewList.ftl", model);
        return html;
    }

    public String generateListCode(GenScheme genScheme)
    {
        StringBuilder result = new StringBuilder();
        GenTable genTable = (GenTable)genTableDao.get(genScheme.getGenTable().getId());
        genTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        com.jeertd.modules.gen.entity.GenConfig config = GenUtils.getConfig();
        genScheme.setGenTable(genTable);
        java.util.Map model = GenUtils.getDataModel(genScheme);
        FreemarkerHelper viewEngine = new FreemarkerHelper();
        String html = viewEngine.parseTemplate("/com/jeeplus/modules/gen/template/findList.ftl", model);
        return html;
    }

    @Autowired
    private GenTableDao genTableDao;
    @Autowired
    private GenTableColumnDao genTableColumnDao;
    @Autowired
    private GenDataBaseDictDao genDataBaseDictDao;
}

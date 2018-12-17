// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:23:30
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenTableService.java

package com.jeespring.modules.gen.service;

import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractService;
import com.jeespring.modules.gen.dao.*;
import com.jeespring.modules.gen.entity.GenTable;
import com.jeespring.modules.gen.entity.GenTableColumn;
import com.jeespring.modules.gen.util.GenUtils;

import java.util.Iterator;
import java.util.List;

import com.jeespring.modules.sys.dao.DictDao;
import com.jeespring.modules.sys.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Transactional(readOnly=true)
public class GenTableService extends AbstractService
{

    public GenTableService()
    {
    }

    public GenTable get(String id)
    {
        GenTable genTable = genTableDao.get(id);
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
        if(StringUtils.isBlank(tableName)) {
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List list = genTableDao.findList(genTable);
        return list.size() == 0;
    }

    public boolean checkTableNameFromDB(String tableName)
    {
        if(StringUtils.isBlank(tableName)) {
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List list = genDataBaseDictDao.findTableList(genTable);
        return list.size() == 0;
    }

    public GenTable getTableFormDb(GenTable genTable)
    {
        if(StringUtils.isNotBlank(genTable.getName()))
        {
            List list = genDataBaseDictDao.findTableList(genTable);
            if(list.size() > 0)
            {
                if(StringUtils.isBlank(genTable.getId()))
                {
                    genTable = (GenTable)list.get(0);
                    if(StringUtils.isBlank(genTable.getComments())) {
                        genTable.setComments(genTable.getName());
                    }
                    genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getName()));
                }
                List columnList = genDataBaseDictDao.findTableColumnList(genTable);
                for(Iterator iterator = columnList.iterator(); iterator.hasNext();)
                {
                    GenTableColumn column = (GenTableColumn)iterator.next();
                    boolean b = false;
                    for(Iterator iterator2 = genTable.getColumnList().iterator(); iterator2.hasNext();)
                    {
                        GenTableColumn e = (GenTableColumn)iterator2.next();
                        if(e.getName() != null && e.getName().equals(column.getName())) {
                            b = true;
                        }
                    }

                    if(!b) {
                        genTable.getColumnList().add(column);
                    }
                }

                for(Iterator iterator1 = genTable.getColumnList().iterator(); iterator1.hasNext();)
                {
                    GenTableColumn e = (GenTableColumn)iterator1.next();
                    boolean b = false;
                    for(Iterator iterator3 = columnList.iterator(); iterator3.hasNext();)
                    {
                        GenTableColumn column = (GenTableColumn)iterator3.next();
                        if(column.getName().equals(e.getName())) {
                            b = true;
                        }
                    }

                    if(!b) {
                        e.setDelFlag("1");
                    }
                }

                genTable.setPkList(genDataBaseDictDao.findTablePK(genTable));
                GenUtils.initColumnField(genTable,dictDao.findTypeList(new Dict()));
            }
        }
        return genTable;
    }

    @Transactional(readOnly=false)
    public void save(GenTable genTable)
    {
        boolean isSync = true;
        if(StringUtils.isBlank(genTable.getId()))
        {
            isSync = false;
        } else
        {
            GenTable oldTable = get(genTable.getId());
            if(oldTable.getColumnList().size() != genTable.getColumnList().size() || !oldTable.getName().equals(genTable.getName()) || !oldTable.getComments().equals(genTable.getComments()))
            {
                isSync = false;
            } else
            {
                for(Iterator iterator1 = genTable.getColumnList().iterator(); iterator1.hasNext();)
                {
                    GenTableColumn column = (GenTableColumn)iterator1.next();
                    if(StringUtils.isBlank(column.getId()))
                    {
                        isSync = false;
                    } else
                    {
                        GenTableColumn oldColumn = genTableColumnDao.get(column.getId());
                        if(!oldColumn.getName().equals(column.getName()) || !oldColumn.getJdbcType().equals(column.getJdbcType()) || !oldColumn.getIsPk().equals(column.getIsPk()) || !oldColumn.getComments().equals(column.getComments())) {
                            isSync = false;
                        }
                    }
                }

            }
        }
        if(!isSync) {
            genTable.setIsSync("0");
        }
        if(StringUtils.isBlank(genTable.getId()))
        {
            genTable.preInsert();
            genTableDao.insert(genTable);
        } else
        {
            genTable.preUpdate();
            genTableDao.update(genTable);
        }
        genTableColumnDao.deleteByGenTable(genTable);
        GenTableColumn column;
        for(Iterator iterator = genTable.getColumnList().iterator(); iterator.hasNext(); genTableColumnDao.insert(column))
        {
            column = (GenTableColumn)iterator.next();
            column.setGenTable(genTable);
            column.setId(null);
            column.preInsert();
        }

    }

    @Transactional(readOnly=false)
    public void syncSave(GenTable genTable)
    {
        genTable.setIsSync("1");
        genTableDao.update(genTable);
    }

    @Transactional(readOnly=false)
    public void saveFromDB(GenTable genTable)
    {
        genTable.preInsert();
        genTableDao.insert(genTable);
        GenTableColumn column;
        for(Iterator iterator = genTable.getColumnList().iterator(); iterator.hasNext(); genTableColumnDao.insert(column))
        {
            column = (GenTableColumn)iterator.next();
            column.setGenTable(genTable);
            column.setId(null);
            column.preInsert();
        }

    }

    @Transactional(readOnly=false)
    public void delete(GenTable genTable)
    {
        genTableDao.delete(genTable);
        genTableColumnDao.deleteByGenTable(genTable);
    }

    @Transactional(readOnly=false)
    public void buildTable(String sql)
    {
        genTableDao.buildTable(sql);
    }

    @Autowired
    private GenTableDao genTableDao;
    @Autowired
    private GenTableColumnDao genTableColumnDao;
    @Autowired
    private GenDataBaseDictDao genDataBaseDictDao;
    @Autowired
    private DictDao dictDao;
}

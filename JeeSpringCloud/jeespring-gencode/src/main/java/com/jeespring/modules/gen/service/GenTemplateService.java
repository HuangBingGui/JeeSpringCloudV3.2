// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2017/5/31 16:23:35
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenTemplateService.java

package com.jeespring.modules.gen.service;

import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractService;
import com.jeespring.modules.gen.dao.GenTemplateDao;
import com.jeespring.modules.gen.entity.GenTemplate;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Transactional(readOnly=true)
public class GenTemplateService extends AbstractService
{

    public GenTemplateService()
    {
    }

    public GenTemplate get(String id)
    {
        return genTemplateDao.get(id);
    }

    public Page find(Page page, GenTemplate genTemplate)
    {
        genTemplate.setPage(page);
        page.setList(genTemplateDao.findList(genTemplate));
        return page;
    }

    @Transactional(readOnly=false)
    public void save(GenTemplate genTemplate)
    {
        if(genTemplate.getContent() != null) {
            genTemplate.setContent(StringEscapeUtils.unescapeHtml4(genTemplate.getContent()));
        }
        if(StringUtils.isBlank(genTemplate.getId()))
        {
            genTemplate.preInsert();
            genTemplateDao.insert(genTemplate);
        } else
        {
            genTemplate.preUpdate();
            genTemplateDao.update(genTemplate);
        }
    }

    @Transactional(readOnly=false)
    public void delete(GenTemplate genTemplate)
    {
        genTemplateDao.delete(genTemplate);
    }

    @Autowired
    private GenTemplateDao genTemplateDao;
}

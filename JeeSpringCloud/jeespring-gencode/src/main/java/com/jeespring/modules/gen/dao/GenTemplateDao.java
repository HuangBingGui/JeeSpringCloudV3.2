package com.jeespring.modules.gen.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.gen.entity.GenTemplate;

@Mapper
public interface GenTemplateDao
  extends InterfaceBaseDao<GenTemplate>
{}

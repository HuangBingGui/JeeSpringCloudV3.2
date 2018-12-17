package com.jeespring.modules.gen.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.gen.entity.GenTable;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface GenTableDao
  extends InterfaceBaseDao<GenTable>
{
  int buildTable(@Param("sql") String paramString);
}

package com.jeespring.modules.gen.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.gen.entity.GenTable;
import com.jeespring.modules.gen.entity.GenTableColumn;

@Mapper
public interface GenTableColumnDao
  extends InterfaceBaseDao<GenTableColumn>
{
  void deleteByGenTable(GenTable paramGenTable);
}

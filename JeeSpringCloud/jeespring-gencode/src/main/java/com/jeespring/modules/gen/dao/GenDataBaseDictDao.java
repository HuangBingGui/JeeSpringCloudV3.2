package com.jeespring.modules.gen.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.gen.entity.GenTable;
import com.jeespring.modules.gen.entity.GenTableColumn;

import java.util.List;

@Mapper
public interface GenDataBaseDictDao
  extends InterfaceBaseDao<GenTableColumn>
{
  List<GenTable> findTableList(GenTable paramGenTable);
  
  List<GenTableColumn> findTableColumnList(GenTable paramGenTable);
  
  List<String> findTablePK(GenTable paramGenTable);
}
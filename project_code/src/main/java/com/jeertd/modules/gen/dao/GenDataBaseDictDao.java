package com.jeertd.modules.gen.dao;

import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.gen.entity.GenTable;
import com.jeertd.modules.gen.entity.GenTableColumn;
import java.util.List;

@MyBatisDao
public abstract interface GenDataBaseDictDao
  extends CrudDao<GenTableColumn>
{
  public abstract List<GenTable> findTableList(GenTable paramGenTable);
  
  public abstract List<GenTableColumn> findTableColumnList(GenTable paramGenTable);
  
  public abstract List<String> findTablePK(GenTable paramGenTable);
}
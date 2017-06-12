package com.jeertd.modules.gen.dao;

import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.gen.entity.GenTable;
import com.jeertd.modules.gen.entity.GenTableColumn;

@MyBatisDao
public abstract interface GenTableColumnDao
  extends CrudDao<GenTableColumn>
{
  public abstract void deleteByGenTable(GenTable paramGenTable);
}

package com.jeertd.modules.gen.dao;

import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.gen.entity.GenTable;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public abstract interface GenTableDao
  extends CrudDao<GenTable>
{
  public abstract int buildTable(@Param("sql") String paramString);
}

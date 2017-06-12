package com.jeertd.modules.gen.dao;

import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.gen.entity.GenScheme;

@MyBatisDao
public abstract interface GenSchemeDao
  extends CrudDao<GenScheme>
{}

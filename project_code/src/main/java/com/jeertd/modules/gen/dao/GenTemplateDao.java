package com.jeertd.modules.gen.dao;

import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.gen.entity.GenTemplate;

@MyBatisDao
public abstract interface GenTemplateDao
  extends CrudDao<GenTemplate>
{}

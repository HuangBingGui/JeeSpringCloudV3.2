/**
 * Copyright &copy; 2012-2016 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.oa.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.common.persistence.annotation.MyBatisDao;
import com.jeespring.modules.oa.entity.TestAudit;
import org.apache.ibatis.annotations.Mapper;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@Mapper
public interface TestAuditDao extends InterfaceBaseDao<TestAudit> {

	public TestAudit getByProcInsId(String procInsId);
	
	public int updateInsId(TestAudit testAudit);
	
	public int updateHrText(TestAudit testAudit);
	
	public int updateLeadText(TestAudit testAudit);
	
	public int updateMainLeadText(TestAudit testAudit);
	
}

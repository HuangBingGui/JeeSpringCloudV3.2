/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.test.service.onetomany;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeertd.common.persistence.Page;
import com.jeertd.common.service.CrudService;
import com.jeertd.common.utils.StringUtils;
import com.jeertd.modules.test.dao.onetomany.TestDataChild2Dao;
import com.jeertd.modules.test.dao.onetomany.TestDataChild3Dao;
import com.jeertd.modules.test.dao.onetomany.TestDataChildDao;
import com.jeertd.modules.test.dao.onetomany.TestDataMainDao;
import com.jeertd.modules.test.entity.onetomany.TestDataChild;
import com.jeertd.modules.test.entity.onetomany.TestDataChild2;
import com.jeertd.modules.test.entity.onetomany.TestDataChild3;
import com.jeertd.modules.test.entity.onetomany.TestDataMain;

/**
 * 票务代理Service
 * @author liugf
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class TestDataMainService extends CrudService<TestDataMainDao, TestDataMain> {

	@Autowired
	private TestDataChildDao testDataChildDao;
	@Autowired
	private TestDataChild2Dao testDataChild2Dao;
	@Autowired
	private TestDataChild3Dao testDataChild3Dao;
	
	public TestDataMain get(String id) {
		TestDataMain testDataMain = super.get(id);
		testDataMain.setTestDataChildList(testDataChildDao.findList(new TestDataChild(testDataMain)));
		testDataMain.setTestDataChild2List(testDataChild2Dao.findList(new TestDataChild2(testDataMain)));
		testDataMain.setTestDataChild3List(testDataChild3Dao.findList(new TestDataChild3(testDataMain)));
		return testDataMain;
	}
	
	public List<TestDataMain> findList(TestDataMain testDataMain) {
		return super.findList(testDataMain);
	}
	
	public Page<TestDataMain> findPage(Page<TestDataMain> page, TestDataMain testDataMain) {
		return super.findPage(page, testDataMain);
	}
	
	@Transactional(readOnly = false)
	public void save(TestDataMain testDataMain) {
		super.save(testDataMain);
		for (TestDataChild testDataChild : testDataMain.getTestDataChildList()){
			if (testDataChild.getId() == null){
				continue;
			}
			if (TestDataChild.DEL_FLAG_NORMAL.equals(testDataChild.getDelFlag())){
				if (StringUtils.isBlank(testDataChild.getId())){
					testDataChild.setTestDataMain(testDataMain);
					testDataChild.preInsert();
					testDataChildDao.insert(testDataChild);
				}else{
					testDataChild.preUpdate();
					testDataChildDao.update(testDataChild);
				}
			}else{
				testDataChildDao.delete(testDataChild);
			}
		}
		for (TestDataChild2 testDataChild2 : testDataMain.getTestDataChild2List()){
			if (testDataChild2.getId() == null){
				continue;
			}
			if (TestDataChild2.DEL_FLAG_NORMAL.equals(testDataChild2.getDelFlag())){
				if (StringUtils.isBlank(testDataChild2.getId())){
					testDataChild2.setTestDataMain(testDataMain);
					testDataChild2.preInsert();
					testDataChild2Dao.insert(testDataChild2);
				}else{
					testDataChild2.preUpdate();
					testDataChild2Dao.update(testDataChild2);
				}
			}else{
				testDataChild2Dao.delete(testDataChild2);
			}
		}
		for (TestDataChild3 testDataChild3 : testDataMain.getTestDataChild3List()){
			if (testDataChild3.getId() == null){
				continue;
			}
			if (TestDataChild3.DEL_FLAG_NORMAL.equals(testDataChild3.getDelFlag())){
				if (StringUtils.isBlank(testDataChild3.getId())){
					testDataChild3.setTestDataMain(testDataMain);
					testDataChild3.preInsert();
					testDataChild3Dao.insert(testDataChild3);
				}else{
					testDataChild3.preUpdate();
					testDataChild3Dao.update(testDataChild3);
				}
			}else{
				testDataChild3Dao.delete(testDataChild3);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TestDataMain testDataMain) {
		super.delete(testDataMain);
		testDataChildDao.delete(new TestDataChild(testDataMain));
		testDataChild2Dao.delete(new TestDataChild2(testDataMain));
		testDataChild3Dao.delete(new TestDataChild3(testDataMain));
	}
	
}
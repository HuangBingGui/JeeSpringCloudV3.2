/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.service.onetomany;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.security.MD5Tools;
import com.jeespring.modules.test.entity.onetomany.TestDataMain;
import com.jeespring.modules.test.dao.onetomany.TestDataMainDao;
import com.jeespring.modules.test.entity.onetomany.TestDataChild;
import com.jeespring.modules.test.dao.onetomany.TestDataChildDao;
import com.jeespring.modules.test.entity.onetomany.TestDataChild2;
import com.jeespring.modules.test.dao.onetomany.TestDataChild2Dao;
import com.jeespring.modules.test.entity.onetomany.TestDataChild3;
import com.jeespring.modules.test.dao.onetomany.TestDataChild3Dao;
//import com.alibaba.dubbo.config.annotation.Service;
import com.jeespring.common.config.Global;

/**
 * 订票Service
 * @author JeeSpring
 * @version 2018-10-12
 */
 //com.alibaba.dubbo.config.annotation.Service(interfaceClass = ISysServerService.class,version = "1.0.0", timeout = 60000)
@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class TestDataMainService extends AbstractBaseService<TestDataMainDao, TestDataMain> implements ITestDataMainService{

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private TestDataChildDao testDataChildDao;
	@Autowired
	private TestDataChild2Dao testDataChild2Dao;
	@Autowired
	private TestDataChild3Dao testDataChild3Dao;
	
	@Override
	public TestDataMain get(String id) {
		//获取数据库数据
		TestDataMain testDataMain = super.get(id);
		if(testDataMain ==null) {
            return new TestDataMain();
        }
		testDataMain.setTestDataChildList(testDataChildDao.findList(new TestDataChild(testDataMain)));
		testDataMain.setTestDataChild2List(testDataChild2Dao.findList(new TestDataChild2(testDataMain)));
		testDataMain.setTestDataChild3List(testDataChild3Dao.findList(new TestDataChild3(testDataMain)));
		return testDataMain;
	}

	@Override
	public TestDataMain getCache(String id) {
		//获取缓存数据
		TestDataMain testDataMain=(TestDataMain)redisUtils.get(RedisUtils.getIdKey(TestDataMainService.class.getName(),id));
		if( testDataMain!=null) {
            return testDataMain;
        }
		//获取数据库数据
		testDataMain = super.get(id);
		if(testDataMain ==null) {
            return new TestDataMain();
        }
		testDataMain.setTestDataChildList(testDataChildDao.findList(new TestDataChild(testDataMain)));
		testDataMain.setTestDataChild2List(testDataChild2Dao.findList(new TestDataChild2(testDataMain)));
		testDataMain.setTestDataChild3List(testDataChild3Dao.findList(new TestDataChild3(testDataMain)));
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(TestDataMainService.class.getName(),id),testDataMain);
		return testDataMain;
	}

	@Override
	public List<TestDataMain> total(TestDataMain testDataMain) {
		//获取数据库数据
		List<TestDataMain> testDataMainList=super.total(testDataMain);
		return testDataMainList;
	}

	@Override
	public List<TestDataMain> totalCache(TestDataMain testDataMain) {
		//获取缓存数据
		String totalKey = RedisUtils.getTotalKey(TestDataMainService.class.getName(),JSON.toJSONString(testDataMain));
		List<TestDataMain> testDataMainList=(List<TestDataMain>)redisUtils.get(totalKey);
		if(testDataMainList!=null) {
            return testDataMainList;
        }
		//获取数据库数据
		testDataMainList=super.total(testDataMain);
		//设置缓存数据
		redisUtils.set(totalKey,testDataMainList);
		return testDataMainList;
	}

	public TestDataMain findListFirst(TestDataMain testDataMain) {
		//获取数据库数据
		List<TestDataMain> testDataMainList=super.findList(testDataMain);
		if(testDataMainList.size()>0) {
            testDataMain = testDataMainList.get(0);
        }
		return testDataMain;
	}

	public TestDataMain findListFirstCache(TestDataMain testDataMain) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(TestDataMainService.class.getName(),JSON.toJSONString(testDataMain));
		TestDataMain testDataMainRedis=(TestDataMain)redisUtils.get(findListFirstKey);
		if(testDataMainRedis!=null) {
            return testDataMainRedis;
        }
		//获取数据库数据
		List<TestDataMain> testDataMainList=super.findList(testDataMain);
		if(testDataMainList.size()>0) {
            testDataMain = testDataMainList.get(0);
        } else {
            testDataMain = new TestDataMain();
        }
		//设置缓存数据
		redisUtils.set(findListFirstKey,testDataMain);
		return testDataMain;
	}

	@Override
	public List<TestDataMain> findList(TestDataMain testDataMain) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(TestDataMainService.class.getName(),JSON.toJSONString(testDataMain));
		List<TestDataMain> testDataMainList=(List<TestDataMain>)redisUtils.get(findListKey);
		if(testDataMainList!=null) {
            return testDataMainList;
        }
		//获取数据库数据
		testDataMainList=super.findList(testDataMain);
		//设置缓存数据
		redisUtils.set(findListKey,testDataMainList);
		return testDataMainList;
	}

	@Override
	public List<TestDataMain> findListCache(TestDataMain testDataMain) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(TestDataMainService.class.getName(),JSON.toJSONString(testDataMain));
		List<TestDataMain> testDataMainList=(List<TestDataMain>)redisUtils.get(findListKey);
		if(testDataMainList!=null) {
            return testDataMainList;
        }
		//获取数据库数据
		testDataMainList=super.findList(testDataMain);
		//设置缓存数据
		redisUtils.set(findListKey,testDataMainList);
		return testDataMainList;
	}

	@Override
	public Page<TestDataMain> findPage(Page<TestDataMain> page, TestDataMain testDataMain) {
		//获取数据库数据
		Page<TestDataMain> pageReuslt=super.findPage(page, testDataMain);
		return pageReuslt;
	}

	@Override
	public Page<TestDataMain> findPageCache(Page<TestDataMain> page, TestDataMain testDataMain) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(TestDataMainService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(testDataMain));
		Page<TestDataMain> pageReuslt=(Page<TestDataMain>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) {
            return pageReuslt;
        }
		//获取数据库数据
		pageReuslt=super.findPage(page, testDataMain);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(TestDataMain testDataMain) {
		//保存数据库记录
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
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(TestDataMainService.class.getName(),testDataMain.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(TestDataMainService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(TestDataMainService.class.getName()));
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(TestDataMain testDataMain) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(TestDataMainService.class.getName(),testDataMain.getId()));
		//删除数据库记录
		super.delete(testDataMain);
		testDataChildDao.delete(new TestDataChild(testDataMain));
		testDataChild2Dao.delete(new TestDataChild2(testDataMain));
		testDataChild3Dao.delete(new TestDataChild3(testDataMain));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(TestDataMainService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(TestDataMainService.class.getName()));
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByLogic(TestDataMain testDataMain) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(TestDataMainService.class.getName(),testDataMain.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(testDataMain);
		testDataChildDao.deleteByLogic(new TestDataChild(testDataMain));
		testDataChild2Dao.deleteByLogic(new TestDataChild2(testDataMain));
		testDataChild3Dao.deleteByLogic(new TestDataChild3(testDataMain));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(TestDataMainService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(TestDataMainService.class.getName()));
	}

}
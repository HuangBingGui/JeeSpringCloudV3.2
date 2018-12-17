/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.job.service;

import java.util.List;

import com.jeespring.common.constant.ScheduleConstants;
import com.jeespring.common.security.ShiroUtils;
import com.jeespring.modules.job.util.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.job.entity.SysJob;
import com.jeespring.modules.job.dao.SysJobDao;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.security.MD5Tools;

import javax.annotation.PostConstruct;

/**
 * 定时任务调度Service
 * @author JeeSpring
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
public class SysJobService extends AbstractBaseService<SysJobDao, SysJob> {

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	@Override
    public SysJob get(String id) {
		//获取数据库数据
		SysJob  sysJob=super.get(id);
		return sysJob;
	}

	public SysJob getCache(String id) {
		//获取缓存数据
		SysJob sysJob=(SysJob)redisUtils.get(RedisUtils.getIdKey(SysJobService.class.getName(),id));
		if( sysJob!=null) {
            return sysJob;
        }
		//获取数据库数据
		sysJob=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(SysJobService.class.getName(),id),sysJob);
		return sysJob;
	}

	@Override
    public List<SysJob> total(SysJob sysJob) {
		//获取数据库数据
		List<SysJob> sysJobList=super.total(sysJob);
		return sysJobList;
	}

	public List<SysJob> totalCache(SysJob sysJob) {
		//获取缓存数据
		String totalKey = RedisUtils.getTotalKey(SysJobService.class.getName(),JSON.toJSONString(sysJob));
		List<SysJob> sysJobList=(List<SysJob>)redisUtils.get(totalKey);
		if(sysJobList!=null) {
            return sysJobList;
        }
		//获取数据库数据
		sysJobList=super.total(sysJob);
		//设置缓存数据
		redisUtils.set(totalKey,sysJobList);
		return sysJobList;
	}

	@Override
    public List<SysJob> findList(SysJob sysJob) {
		//获取数据库数据
		List<SysJob> sysJobList=super.findList(sysJob);
		//设置缓存数据
		return sysJobList;
	}

	public List<SysJob> findListCache(SysJob sysJob) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(SysJobService.class.getName(),JSON.toJSONString(sysJob));
		List<SysJob> sysJobList=(List<SysJob>)redisUtils.get(findListKey);
		if(sysJobList!=null) {
            return sysJobList;
        }
		//获取数据库数据
		sysJobList=super.findList(sysJob);
		//设置缓存数据
		redisUtils.set(findListKey,sysJobList);
		return sysJobList;
	}

	public SysJob findListFirst(SysJob sysJob) {;
		//获取数据库数据
		List<SysJob> sysJobList=super.findList(sysJob);
		if(sysJobList.size()>0) {
            sysJob = sysJobList.get(0);
        }
		return sysJob;
	}

	public SysJob findListFirstCache(SysJob sysJob) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(SysJobService.class.getName(),JSON.toJSONString(sysJob));
		SysJob sysJobRedis=(SysJob)redisUtils.get(findListFirstKey);
		if(sysJobRedis!=null) {
            return sysJobRedis;
        }
		//获取数据库数据
		List<SysJob> sysJobList=super.findList(sysJob);
		if(sysJobList.size()>0) {
            sysJob = sysJobList.get(0);
        } else {
            sysJob = new SysJob();
        }
		//设置缓存数据
		redisUtils.set(findListFirstKey,sysJob);
		return sysJob;
	}

	@Override
    public Page<SysJob> findPage(Page<SysJob> page, SysJob sysJob) {
		//获取数据库数据
		Page<SysJob> pageReuslt=super.findPage(page, sysJob);
		return pageReuslt;
	}

	public Page<SysJob> findPageCache(Page<SysJob> page, SysJob sysJob) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(SysJobService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(sysJob));
		Page<SysJob> pageReuslt=(Page<SysJob>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) {
            return pageReuslt;
        }
		//获取数据库数据
		pageReuslt=super.findPage(page, sysJob);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Override
    @Transactional(readOnly = false)
	public void save(SysJob sysJob) {
		if(sysJob.getIsNewRecord()) {
            sysJob.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        }
		//保存数据库记录
		super.save(sysJob);
		if(sysJob.getIsNewRecord()) {
            ScheduleUtils.createScheduleJob(scheduler, sysJob);
        } else {
            ScheduleUtils.updateScheduleJob(scheduler, sysJob);
        }

		/*if (ScheduleConstants.Status.NORMAL.getValue().equals(sysJob.getStatus()))
		{
			resumeJob(sysJob);
		}
		else if (ScheduleConstants.Status.PAUSE.getValue().equals(sysJob.getStatus()))
		{
			pauseJob(sysJob);
		}*/

		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysJobService.class.getName(),sysJob.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysJobService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysJobService.class.getName()));
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(SysJob sysJob) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysJobService.class.getName(),sysJob.getId()));
		//删除数据库记录
		super.delete(sysJob);
		ScheduleUtils.deleteScheduleJob(scheduler, sysJob.getId());
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysJobService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysJobService.class.getName()));
	}

	@Override
    @Transactional(readOnly = false)
	public void deleteByLogic(SysJob sysJob) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysJobService.class.getName(),sysJob.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(sysJob);
		ScheduleUtils.deleteScheduleJob(scheduler, sysJob.getId());
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysJobService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysJobService.class.getName()));
	}

	@Autowired
	private Scheduler scheduler;
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init()
	{
		List<SysJob> jobList = super.findAllList(new SysJob());
		for (SysJob job : jobList)
		{
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getId());
			// 如果不存在，则创建
			if (cronTrigger == null)
			{
				ScheduleUtils.createScheduleJob(scheduler, job);
			}
			else
			{
				ScheduleUtils.updateScheduleJob(scheduler, job);
			}
		}
	}

	/**
	 * 暂停任务
	 *
	 * @param job 调度信息
	 */
	@Transactional(readOnly = false)
	public void pauseJob(SysJob job)
	{
		job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		super.save(job);
		ScheduleUtils.pauseJob(scheduler, job.getId());
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysJobService.class.getName(),job.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysJobService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysJobService.class.getName()));
	}

	/**
	 * 恢复任务
	 *
	 * @param job 调度信息
	 */
	@Transactional(readOnly = false)
	public void resumeJob(SysJob job)
	{
		job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
		super.save(job);
		ScheduleUtils.resumeJob(scheduler, job.getId());
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysJobService.class.getName(),job.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysJobService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysJobService.class.getName()));
	}

	/**
	 * 立即运行任务
	 *
	 * @param job 调度信息
	 */
	@Transactional(readOnly = false)
	public int run(SysJob job)
	{
		return ScheduleUtils.run(scheduler, get(job.getId()));
	}
	/**
	 * 任务调度状态修改
	 *
	 * @param job 调度信息
	 */
	@Transactional(readOnly = false)
	public void changeStatus(SysJob job)
	{
		String status = job.getStatus();
		if (ScheduleConstants.Status.NORMAL.getValue().equals(status))
		{
			pauseJob(job);
		}
		else if (ScheduleConstants.Status.PAUSE.getValue().equals(status))
		{
			resumeJob(job);
		}
	}
}
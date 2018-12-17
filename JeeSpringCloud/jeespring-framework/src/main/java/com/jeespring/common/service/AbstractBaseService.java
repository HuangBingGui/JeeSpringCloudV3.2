/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/HuangBingGui/jeespring">jeespring</a> All rights reserved.
 */
package com.jeespring.common.service;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.persistence.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service基类
 *
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class AbstractBaseService<Dao extends InterfaceBaseDao<T>, T extends AbstractBaseEntity<T>> extends AbstractService {

    /**
     * 持久层对象
     */
    @Autowired
    protected Dao dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询统计数据
     *
     * @param entity
     * @return
     */
    public List<T> total(T entity) {
        return dao.total(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 查询所有
     *
     * @param entity
     * @return
     */
    public List<T> findAllList(T entity) {
        return dao.findAllList(entity);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    public Page<T> findPage(Page<T> page, T entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(T entity) {
        int result=0;
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            result=dao.insert(entity);
        } else {
            entity.preUpdate();
            result=dao.update(entity);
        }
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        dao.delete(entity);
    }

    /**
     * 删除数据（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    public void deleteByLogic(T entity) {
        dao.deleteByLogic(entity);
    }


}

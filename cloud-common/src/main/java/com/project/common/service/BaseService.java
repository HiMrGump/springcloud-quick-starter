package com.project.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.common.dao.BaseDao;
import com.project.common.entity.BaseEntity;
import com.project.util.IDUtils;
import com.project.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 该类实现了XXXX相关操作接口的具体功能
 * 根据阿里巴巴需求规范，
 *
 * 获取单个对象的方法用get做前缀。
 * 获取多个对象的方法用list做前缀。
 * 获取统计值的方法用count做前缀。
 * 插入的方法用save（推荐）或insert做前缀。
 * 删除的方法用remove（推荐）或delete做前缀。
 * 修改的方法用update做前缀。
 *
 * @ClassName: BaseService
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 11:29
 * @Version: 1.0
 * </pre>
 */
public abstract class BaseService<E extends BaseEntity,D extends BaseDao<E>> {

    @Autowired
    protected D dao;

    /**
     *  根据主键获取一条数据
     * @param id 主键W
     * @return 数据
     */
    public E get(Serializable id){
        return dao.selectById(id);
    }

    public List<E> list(Map<String,Object> searchMap){
        return dao.selectByMap(searchMap);
    }

    public PageHelper<E> listByPage(PageHelper<E> pageHelper, Map<String,Object> searchMap){
        if(pageHelper == null){
            pageHelper = new PageHelper<E>();
        }

        Page page = new Page(pageHelper.getCurrentPage(),pageHelper.getPageSize());
        //   dao.selectPage(page,);
        return pageHelper;
    }

    @Transactional
    public int delete(Serializable id){
        return dao.deleteById(id);
    }

    @Transactional
    public int deleteByIds(List<Serializable> idList){
        return dao.deleteBatchIds(idList);
    }

    @Transactional
    public int update(E entity){
        entity.setLastModifyDate(new Date());
        return dao.updateById(entity);
    }

    @Transactional
    public int save(E entity){
        entity.setId(IDUtils.generate());
        entity.setCreateDate(new Date());
        return dao.insert(entity);
    }



}


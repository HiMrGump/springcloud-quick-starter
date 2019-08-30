package com.project.common.service;

import com.project.common.db.DBHelper;
import com.project.common.entity.BaseEntity;
import com.project.util.PageHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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
public interface BaseService<E extends BaseEntity> {
    /**
     *  根据主键获取一条数据
     * @param id 主键W
     * @return 数据
     */
    public Optional<E> get(String id);

    public Optional<E> getOne(E entity);

    public List<E> list(E entity);

    public PageHelper<E> listByPage(E e);

    public int delete(String id);

    public int update(E entity);

    public int updateBySelective(E entity);

    public int save(E entity);
}


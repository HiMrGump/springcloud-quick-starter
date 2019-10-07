package com.project.common.service;

import com.project.common.db.DBHelper;
import com.project.common.entity.BaseEntity;
import com.project.util.PageHelper;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * 该类定义了service层相关操作接口
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
     * @param id 主键
     * @return 数据
     */
    public Optional<E> get(String id);

    /**
     * 获取一条数据
     * @param entity 条件实体
     * @return
     */
    public Optional<E> getOne(E entity);

    /**
     * 获取多条数据
     * @param entity 条件实体
     * @return
     */
    public List<E> list(E entity);

    /**
     * 获取多条数据
     * @param dbHelper 数据条件搜索
     * @return
     */
    public List<E> list(DBHelper dbHelper);

    /**
     * 分页
     * @param entity 条件实体
     * @return
     */
    public PageHelper<E> listByPage(E entity);

    /**
     * 分页
     * @param pageHelper 分页工具类
     * @param dbHelper 数据条件搜索
     * @return
     */
    public PageHelper<E> listByPage(PageHelper<E> pageHelper,DBHelper dbHelper);

    /**
     * 根据主键删除一条数据
     * @param id 主键
     * @return
     */
    public int delete(String id);

    /**
     * 修改一条数据
     * @param entity 实体
     * @return
     */
    public int update(E entity);
    /**
     * selective方式修改一条数据
     * @param entity 实体
     * @return
     */
    public int updateBySelective(E entity);

    /**
     * 保存一条数据
     * @param entity 实体
     * @return
     */
    public int save(E entity);
}


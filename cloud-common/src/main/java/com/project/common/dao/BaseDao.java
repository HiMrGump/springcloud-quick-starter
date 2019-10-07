package com.project.common.dao;

import com.project.common.entity.BaseEntity;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * 自定义BaseDao，并继承了TkMybatis相关功能
 * @ClassName: BaseDao
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 11:31
 * @Version: 1.0
 */
public interface BaseDao<E extends BaseEntity> extends TkMybatisMapper<E> {
}
